package pt.ipp.isep.cma.boundedservice;

import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

public class MainActivity extends Activity implements OnClickListener {

    private MyService mService;
    
    private TextView txtServiceStatus;
    private Button btnService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtServiceStatus = (TextView) findViewById(R.id.txtServiceStatus);
		btnService = (Button) findViewById(R.id.btnService);
		btnService.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		bindService(new Intent(this, MyService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unbindService(mConnection);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = ((MyService.MyServiceBinder) service).getService();
			txtServiceStatus.setText(R.string.service_active);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
			txtServiceStatus.setText(R.string.service_not_active);
		}		
	};

	@Override
	public void onClick(View v) {

		if(v.getId() == R.id.btnService) {
			if(mService != null) {
				String text = mService.testServiceConnectivity();
				Toast.makeText(this, text, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, R.string.service_not_active, Toast.LENGTH_LONG).show();
			}
		}		
	}
}
