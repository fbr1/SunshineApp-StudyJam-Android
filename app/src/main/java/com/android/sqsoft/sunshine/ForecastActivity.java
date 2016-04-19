package com.android.sqsoft.sunshine;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.sqsoft.sunshine.entities.DayForecast;

public class ForecastActivity extends AppCompatActivity implements ForecastFragment.OnListFragmentInteractionListener{

    public final static String EXTRA_MESSAGE = "com.android.sqsoft.sunshine.CITY";
    private final String TAG = ForecastActivity.class.getSimpleName();
    private boolean mTwoPane;
    private String mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO validar vacio
        mLocation =  getIntent().getStringExtra(EXTRA_MESSAGE);

        setContentView(R.layout.activity_forecast_city);

        ForecastFragment forecastFragment =  ((ForecastFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_forecast));
        forecastFragment.setLocation(mLocation);

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
        Intent intent = new Intent(this, ForecastDetailActivity.class);
        intent.putExtra(getString(R.string.day_forecast_for_intent_key), item);
        startActivity(intent);

    }
}
