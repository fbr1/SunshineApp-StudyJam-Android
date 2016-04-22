package com.android.sqsoft.sunshine;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

        // Setup Pull to refresh
        swipeContainer = (SwipeRefreshLayout)  view.findViewById(R.id.swipeContainer);

        // Start refresh animation on load
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
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

        updateList();

        return view;
    }

    public void updateList(boolean useCache) {

        // Get City coordinates
        LatLng coordinates = Utility.getLocationLatLng(getContext());

        // Get data
        if (recyclerView != null) {

            ForecastLogic.getInstance().getExtendedWeatherByCoords(new ForecastLogic.Listener<ArrayList<DayForecast>>() {

                @Override
                public void onResult(ArrayList<DayForecast> forecastList) {

                    setDataInAdapter(forecastList);

                    if (forecastList.isEmpty()) {
                        Log.d(TAG, "Warning: forecastList is empty");
                    }

                }

                @Override
                public void onError(String errorMessage) {
                    if(errorMessage== null){
                        Snackbar.make(getView(),getString(R.string.default_error_message),Snackbar.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(getView(),errorMessage,Snackbar.LENGTH_LONG).show();
                    }
                    setDataInAdapter(new ArrayList<DayForecast>());
                }

            }, coordinates.latitude,coordinates.longitude,useCache);
        }
    }

    private void setDataInAdapter(ArrayList<DayForecast> forecastList){
        if(recyclerView.getAdapter() == null){
            recyclerView.setAdapter(new DayForecastRecyclerViewAdapter(forecastList, mListener));
        }else{
            ((DayForecastRecyclerViewAdapter)recyclerView.getAdapter()).updateData(forecastList);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(false);
            }
        });
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
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DayForecast item);
    }
}
