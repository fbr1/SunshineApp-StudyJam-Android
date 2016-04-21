package com.android.sqsoft.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.sqsoft.sunshine.entities.DayForecast;
import com.google.gson.Gson;

public class ForecastDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_city_detail);
        DayForecast dayForecast =(DayForecast) getIntent().getSerializableExtra(getString(R.string.day_forecast_for_intent_key));

        String location = Utility.getLocationName(this);

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();

        bundle.putString("item", new Gson().toJson(dayForecast));
        detailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,detailFragment).commit();
        // Setup Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (myToolbar != null) {
            myToolbar.setTitle(location + " - Detalle");
            setSupportActionBar(myToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
