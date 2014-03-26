package com.example.downloadproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "DOWNLOAD";
	
	EditText mEditTextUrl;
	Button mButtonDownload;
	ProgressBar mProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mEditTextUrl = (EditText) findViewById(R.id.url_text_id);
		
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_id);
		
		mButtonDownload = (Button) findViewById(R.id.button_id);
		mButtonDownload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i(TAG, "Download Button Clicked");
				if(v.getId() == R.id.button_id) {
					Intent intentDownloadServ = new Intent(getApplicationContext(), DownloadService.class);
					intentDownloadServ.putExtra("myURL", mEditTextUrl.getText().toString());
					startService(intentDownloadServ);
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
}
