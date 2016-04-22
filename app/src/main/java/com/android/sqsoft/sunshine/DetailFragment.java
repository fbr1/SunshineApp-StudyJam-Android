package com.android.sqsoft.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.sqsoft.sunshine.entities.DayForecast;

public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
        this.setArguments(new Bundle());
    }

    public static DetailFragment newInstance(String param1, String param2) {

        return new DetailFragment();
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

        Bundle bundle = getArguments();

        if(bundle != null){
            DayForecast dayForecast =  bundle.getParcelable("dayForecast");
            if(dayForecast != null){
                setViews(view, dayForecast);
            }

        }
        return view;
    }

    private void setViews(View view, DayForecast dayForecast) {
        ((ImageView)view.findViewById(R.id.ivWeatherImage)).setImageResource(Utility.getArtResourceForWeatherCondition(dayForecast.getWeather().get(0).getId()));
        String dateStr = String.valueOf(dayForecast.getDate()).substring(0,10);
        ((TextView)view.findViewById(R.id.tvDate)).setText(dateStr);
        ((TextView)view.findViewById(R.id.tvWeatherName)).setText(String.valueOf(dayForecast.getWeather().get(0).getName()));
        ((TextView)view.findViewById(R.id.tvWeatherDescription)).setText(String.valueOf(dayForecast.getWeather().get(0).getDescription()));
        ((TextView)view.findViewById(R.id.tvTmax)).setText(Utility.formatTemperature(getContext(),dayForecast.getTmax()));
        ((TextView)view.findViewById(R.id.tvTmin)).setText(Utility.formatTemperature(getContext(),dayForecast.getTmin()));
        ((TextView)view.findViewById(R.id.tvPressure)).setText(getContext().getString(R.string.format_pressure,dayForecast.getPressure()));
        ((TextView)view.findViewById(R.id.tvHumidity)).setText(getContext().getString(R.string.format_humidity,dayForecast.getHumidity()));
        ((TextView)view.findViewById(R.id.tvClouds)).setText(getContext().getString(R.string.format_humidity,dayForecast.getClouds()));
        ((TextView)view.findViewById(R.id.tvRain)).setText(getContext().getString(R.string.format_humidity,dayForecast.getRain()));
    }

}
