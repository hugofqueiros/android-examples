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
				Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onClick: " + v.getId());
				break;
			case R.id.refresh:
				Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onClick: " + v.getId());
				break;
			}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onProgressChanged ");
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onStartTrackingTouch ");
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Log.i(TAG, HomeControlActivity.class.getSimpleName() + " onStopTrackingTouch ");		
	}
}
