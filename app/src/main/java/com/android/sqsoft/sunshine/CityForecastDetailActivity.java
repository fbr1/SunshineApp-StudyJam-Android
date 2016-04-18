package com.android.sqsoft.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.sqsoft.sunshine.entities.DayForecast;

public class CityForecastDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_city_detail);
        DayForecast dayForecast =(DayForecast) getIntent().getSerializableExtra(getString(R.string.day_forecast_for_intent_key));

        TextView textView = new TextView(this);
        textView.setText(String.valueOf(dayForecast.getTmin()));

        RelativeLayout rl =  (RelativeLayout) findViewById(R.id.container);
        rl.addView(textView);
    }
}
