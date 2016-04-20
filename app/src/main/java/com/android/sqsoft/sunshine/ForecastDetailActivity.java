package com.android.sqsoft.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.sqsoft.sunshine.entities.DayForecast;

public class ForecastDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_city_detail);
        DayForecast dayForecast =(DayForecast) getIntent().getSerializableExtra(getString(R.string.day_forecast_for_intent_key));

        TextView textView =  (TextView) findViewById(R.id.tv_Detail);
        textView.setText("Minimum Temperature: " + String.valueOf(dayForecast.getTmin()));

        String location = Utility.getLocationName(this);

        // Setup Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (myToolbar != null) {
            myToolbar.setTitle(location + " - Detalle");
            setSupportActionBar(myToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
