package com.android.sqsoft.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.sqsoft.sunshine.entities.DayForecast;
import com.android.sqsoft.sunshine.logic.ForecastLogic;

public class MainActivity extends AppCompatActivity implements ForecastFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ForecastLogic.initializeInstance(this);

        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(DayForecast item) {

    }
}
