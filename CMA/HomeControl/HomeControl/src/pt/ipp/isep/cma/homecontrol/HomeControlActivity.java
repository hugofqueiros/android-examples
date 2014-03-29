package pt.ipp.isep.cma.homecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class HomeControlActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {

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
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	public void onClick(View v) {

			switch (v.getId()) {
			case R.id.toggle1:
				break;
			case R.id.refresh:
				break;
			}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}
}
