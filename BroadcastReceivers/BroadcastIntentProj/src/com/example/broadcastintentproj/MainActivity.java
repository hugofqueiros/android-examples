package com.example.broadcastintentproj;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	private EditText txtSeconds;
	private Button btnStartAlert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i(Global.TAG, "Main act: on create");
		
		txtSeconds = (EditText) findViewById(R.id.txtSeconds);
		
		btnStartAlert = (Button) findViewById(R.id.btnAction);
		btnStartAlert.setOnClickListener(this);
		
		if (savedInstanceState == null) {
			Log.i(Global.TAG, "savedInstanceState == null");
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(Global.TAG, "onCreateOptionsMenu");
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Log.i(Global.TAG, "onOptionsItemSelected");
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.i(Global.TAG, "onCreateView");
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnAction) {
			int i = Integer.parseInt(txtSeconds.getText().toString());
			
			// Intent that indicates the action to execute and has the data to send
			// The action identifier is registered in the intent-filter of the broadcast receiver on the manifest.
			Intent intent = new Intent();
			intent.setAction(Global.INTENT_WAKEUP);
			intent.putExtra(Global.INTENT_WAKEUP_EXTRA, txtSeconds.getText().toString());
			
			// The pending intent serves as an envelop of the intent. Allows another app to launch the intent with the permissions of ours
			// getBroadcast means that the Intent should be launched through a broadcast. ther are also getActivity(), getService()....
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 123456789, intent, 0);
		
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i + 1000), pendingIntent);
			Toast.makeText(this, getString(R.string.alarmSet) + " " + i + " " + getString(R.string.seconds), Toast.LENGTH_LONG);			
		}
	}

}
