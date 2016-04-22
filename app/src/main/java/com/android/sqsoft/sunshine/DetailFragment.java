package com.android.sqsoft.sunshine;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sqsoft.sunshine.entities.DayForecast;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.Console;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
        this.setArguments(new Bundle());
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        DayForecast dayForecast = (new Gson()).fromJson(getArguments().getString("item"),DayForecast.class);
        if (dayForecast!=null) {
            setViews(view, dayForecast);
        } else {

        }
        return view;
    }

    private void setViews(View view, DayForecast dayForecast) {
        ((ImageView)view.findViewById(R.id.ivWeatherImage)).setImageResource(Utility.getArtResourceForWeatherCondition(dayForecast.getWeather().get(0).getId()));
        String dateStr = String.valueOf(dayForecast.getDate()).substring(0,10);
        ((TextView)view.findViewById(R.id.tvDate)).setText(dateStr);
        ((TextView)view.findViewById(R.id.tvNombreClima)).setText(String.valueOf(dayForecast.getWeather().get(0).getName()));
        ((TextView)view.findViewById(R.id.tvDescripcionClima)).setText(String.valueOf(dayForecast.getWeather().get(0).getDescription()));
        ((TextView)view.findViewById(R.id.tvTmax)).setText(String.valueOf(dayForecast.getTmax())+"°C");
        ((TextView)view.findViewById(R.id.tvTmin)).setText(String.valueOf(dayForecast.getTmin())+"°C");
        ((TextView)view.findViewById(R.id.tvPresión)).setText(String.valueOf(dayForecast.getPressure())+" Pa");
        ((TextView)view.findViewById(R.id.tvHumedad)).setText(String.valueOf(dayForecast.getHumidity())+"%");
        ((TextView)view.findViewById(R.id.tvNublado)).setText(String.valueOf(dayForecast.getClouds())+"%");
        ((TextView)view.findViewById(R.id.tvLluvia)).setText(String.valueOf(dayForecast.getRain())+"%");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
