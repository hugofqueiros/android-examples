package pt.ipp.isep.cma.homecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class HomeControlActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {

	public static final String TAG = "HomeControl";
	
	private TextView txtTemp1;
	private TextView txtTemp2;
	private TextView txtTemp3;
	private TextView txtBluetoothStatus;

	private ToggleButton tgButton;
	private Button btnRefresh;
	private SeekBar seekBar;
	
	// progress var status value
	private int progressBarValue = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onCreate");
		
		setContentView(R.layout.home_control);
		
		txtTemp1 = (TextView) findViewById(R.id.txtTemp1);
		txtTemp2 = (TextView) findViewById(R.id.txtTemp2);
		txtTemp3 = (TextView) findViewById(R.id.txtTemp3);
		txtBluetoothStatus = (TextView) findViewById(R.id.txtBluetoothStatus);

		tgButton = (ToggleButton) findViewById(R.id.toggle1);
		tgButton.setOnClickListener(this);

		btnRefresh = (Button) findViewById(R.id.refresh);
		btnRefresh.setOnClickListener(this);

		seekBar = (SeekBar) findViewById(R.id.seekbar1);
		seekBar.setOnSeekBarChangeListener(this);
	}

	@Override
	protected void onStart() {
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onStop");
		super.onStop();
	}
	
	@Override
	public void onClick(View v) {
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onClick");
		
			switch (v.getId()) {
			case R.id.toggle1:
				// Send msg to garden lights on/off
				Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onClick toogle1: " + v.getId());
				break;
			case R.id.refresh:
				// refresh Home control
				// get bluetooth status - txtBluetoothStatus - disconnected by default
				// set temperature 1 - txtTemp1
				// set temperature 2 - txtTemp2
				// set temperature 3 - txtTemp3
				Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onClick refresh: " + v.getId());
				break;
			}
	}

	// Methods to control intensity on lights
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// Equaliting progress to the value on this activity
		progressBarValue = progress;
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onProgressChanged: " + progressBarValue);
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// do nothing?
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onStartTrackingTouch: ");
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// send value to control lights?
		Toast.makeText(HomeControlActivity.this, "seek bar progress: " + progressBarValue, Toast.LENGTH_SHORT).show();
		
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onStopTrackingTouch: " + progressBarValue);		
	}
}
