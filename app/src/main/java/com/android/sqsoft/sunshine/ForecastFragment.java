package com.android.sqsoft.sunshine;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sqsoft.sunshine.adapters.DayForecastRecyclerViewAdapter;
import com.android.sqsoft.sunshine.entities.DayForecast;
import com.android.sqsoft.sunshine.logic.ForecastLogic;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ForecastFragment extends Fragment {

    private static final String TAG = ForecastFragment.class.getSimpleName();

    RecyclerView recyclerView;
    private List<DayForecast> forecastList = new ArrayList<>();
    private OnListFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeContainer;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ForecastFragment() {
    }

    public static ForecastFragment newInstance() {
        ForecastFragment fragment = new ForecastFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast_list, container, false);

        // Set the adapter
        recyclerView = (RecyclerView)view.findViewById(R.id.list);
        updateList();

        // Setup Pull to refresh
        swipeContainer = (SwipeRefreshLayout)  view.findViewById(R.id.swipeContainer);

        // Start refresh animation on load
        swipeContainer.post(new Runnable() {
            @Override public void run() {
                swipeContainer.setRefreshing(true);
            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               updateList(false);
            }
        });

        return view;
    }

    public void updateList(boolean useCache) {

        // Get City coordinates
        LatLng coordinates = Utility.getLocationLatLng(getContext());

        // Get data
        if (recyclerView != null) {

            ForecastLogic.getInstance().getExtendedWeatherByCoords(new ForecastLogic.Listener<ArrayList<DayForecast>>() {

                @Override
                public void onResult(ArrayList<DayForecast> fl) {
                    if (fl != null) {
                        forecastList = fl;
                        if(recyclerView.getAdapter() == null){
                            recyclerView.setAdapter(new DayForecastRecyclerViewAdapter(forecastList, mListener));
                        }else{
                            ((DayForecastRecyclerViewAdapter)recyclerView.getAdapter()).updateData(forecastList);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    } else {
                        Log.d(TAG, "Warning: forecastList is empty");
                    }
                    swipeContainer.setRefreshing(false);
                }
            }, coordinates.latitude,coordinates.longitude,useCache);
        }
    }

    public void updateList() {
        updateList(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DayForecast item);
    }
}
