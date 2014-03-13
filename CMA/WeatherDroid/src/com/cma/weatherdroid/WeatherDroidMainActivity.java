package com.cma.weatherdroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class WeatherDroidMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_weather_droid_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.weather_droid_main, menu);
	return true;
    }
}
