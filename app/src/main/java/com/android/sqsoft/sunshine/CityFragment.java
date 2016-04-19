package com.android.sqsoft.sunshine;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sqsoft.sunshine.adapters.DayForecastRecyclerViewAdapter;
import com.android.sqsoft.sunshine.entities.DayForecast;
import com.android.sqsoft.sunshine.logic.CityLogic;
import com.android.sqsoft.sunshine.logic.ForecastLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CityFragment extends Fragment {

    private static final String TAG = ForecastFragment.class.getSimpleName();

    RecyclerView recyclerView;
    private List<String> forecastList = new ArrayList<>();
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityFragment() {
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
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;

            // Get data
            CityLogic.getInstance().getExtendedWeather(new ForecastLogic.Listener<ArrayList<DayForecast>>(){

                @Override
                public void onResult(ArrayList<DayForecast> fl) {

                    if(fl != null){
                        forecastList = fl;
                        recyclerView.setAdapter(new DayForecastRecyclerViewAdapter(forecastList, mListener));
                    }else{
                        Log.d(TAG, "Warning: forecastList is empty");
                    }
                }
            });

        }
        return view;
    }

    public void updateList(String wordPart){

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
