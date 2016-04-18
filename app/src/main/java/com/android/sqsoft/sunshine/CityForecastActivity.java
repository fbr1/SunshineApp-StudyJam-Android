package com.android.sqsoft.sunshine;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.sqsoft.sunshine.entities.DayForecast;
import com.android.sqsoft.sunshine.logic.ForecastLogic;

public class CityForecastActivity extends AppCompatActivity implements ForecastFragment.OnListFragmentInteractionListener{

    private final String TAG = CityForecastActivity.class.getSimpleName();
    private boolean mTwoPane;
    private String mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO change
        mLocation = "London";

        setContentView(R.layout.activity_forecast_city);
        if (findViewById(R.id.forecast_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.forecast_detail_container, new DetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

//       getActionBar().setDisplayHomeAsUpEnabled(true);

//    TODO: Recive the string city parameter, get the weather forecast for that city and create the list trought the adapter
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onListFragmentInteraction(DayForecast item) {

    }
}
