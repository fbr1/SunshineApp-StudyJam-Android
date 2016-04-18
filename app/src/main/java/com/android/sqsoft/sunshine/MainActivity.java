package com.android.sqsoft.sunshine;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.sqsoft.sunshine.entities.DayForecast;
import com.android.sqsoft.sunshine.logic.ForecastLogic;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.android.sqsoft.sunshine.CITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ForecastLogic.initializeInstance(this);

        if(isLocationSet() == true){
            Intent intent = new Intent(this, CityForecastActivity.class);
            intent.putExtra(EXTRA_MESSAGE, getLocation());
            startActivity(intent);
        }else{
            setContentView(R.layout.activity_main);
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onBtnForecastClicked(View view) {
        String cityText = ((EditText) findViewById(R.id.etCity)).getText().toString();
        Toast.makeText(getApplicationContext(), getLocation(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, CityForecastActivity.class);
        intent.putExtra(EXTRA_MESSAGE, cityText);
        startActivity(intent);
    }

    private String getLocation(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.saved_location_default);
        return sharedPref.getString(getString(R.string.saved_location), defaultValue);
    }

    private boolean isLocationSet(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(getString(R.string.saved_location_status), false);
    }

    private void setLocation(String location){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().putString(getString(R.string.saved_location),location);
        sharedPref.edit().putBoolean(getString(R.string.saved_location_status),true);
    }


}
