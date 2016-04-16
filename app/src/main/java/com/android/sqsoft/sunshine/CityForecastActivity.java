package com.android.sqsoft.sunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CityForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_city);

//        Code for test
//        TextView textView = new TextView(this);
//        textView.setText(getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));
//        FrameLayout  frameLayout = (FrameLayout) findViewById(R.id.container);
//        frameLayout.addView(textView);
    }

}
