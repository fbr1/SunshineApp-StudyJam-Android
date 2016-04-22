package com.android.sqsoft.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.sqsoft.sunshine.entities.DayForecast;

public class ForecastDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_city_detail);

        String location = Utility.getLocationName(this);


        DayForecast dayForecast = getIntent().getParcelableExtra(getString(R.string.day_forecast_for_intent_key));

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("dayForecast",dayForecast);
        detailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.forecast_detail_container,detailFragment).commit();

        // Setup Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (myToolbar != null) {
            myToolbar.setTitle(location + " - Detalle");
            setSupportActionBar(myToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
