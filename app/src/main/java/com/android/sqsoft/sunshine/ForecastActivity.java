package com.android.sqsoft.sunshine;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.android.sqsoft.sunshine.entities.DayForecast;
import com.android.sqsoft.sunshine.logic.ForecastLogic;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.gson.Gson;

public class ForecastActivity extends AppCompatActivity implements ForecastFragment.OnListFragmentInteractionListener {

    public final static String EXTRA_MESSAGE = "com.android.sqsoft.sunshine.CITY";
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private final String TAG = ForecastActivity.class.getSimpleName();
    private LinearLayout parentView;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ForecastLogic.initializeInstance(this);

        setContentView(R.layout.activity_forecast_city);

        String location = Utility.getLocationName(this);

        parentView = (LinearLayout) findViewById(R.id.layout_container);

        // Setup Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (myToolbar != null) {
            myToolbar.setTitle(location);
            setSupportActionBar(myToolbar);
        }

        // Determine layout
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (Utility.isDebuggeable(this)) {
            MenuItem item = menu.add("Clear Location");
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Utility.clearLocation(getApplicationContext());
                    return true;
                }
            });
            MenuItem item2 = menu.add("Show Snackbar");
            item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item2) {
                    Snackbar.make(parentView,getString(R.string.default_error_message),Snackbar.LENGTH_LONG).show();
                    return true;
                }
            });

        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_changeCity:
                startCityAutocomplete();
                return true;
            case R.id.action_about:

                return true;
        }
        return false;
    }

    private void startCityAutocomplete() {
        try {

            // Filter results for only cities
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);

            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

        } catch (GooglePlayServicesRepairableException e) {

            // Display dialog prompt to download google play services
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
            googleAPI.getErrorDialog(this,e.getConnectionStatusCode(),PLACE_AUTOCOMPLETE_REQUEST_CODE).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG,e.getMessage());
        }
    }

    @Override
    public void onListFragmentInteraction(DayForecast item) {
        if (mTwoPane) {
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();

            bundle.putString("item", new Gson().toJson(item));
            detailFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.forecast_detail_container,detailFragment).commit();
        } else {
            Intent intent = new Intent(this, ForecastDetailActivity.class);
            intent.putExtra(getString(R.string.day_forecast_for_intent_key), item);
            startActivity(intent);
        }

    }

    private void Updatelist() {
        String location = Utility.getLocationName(this);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle(location);
        }
        ForecastFragment forecastFragment = (ForecastFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_forecast);

        forecastFragment.updateList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);

                // Store location
                Utility.saveLocation(this, place.getName().toString(),place.getLatLng());

                Updatelist();

                Log.d(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.w(TAG, status.getStatusMessage());
                Snackbar.make(parentView,getString(R.string.default_error_message),Snackbar.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
