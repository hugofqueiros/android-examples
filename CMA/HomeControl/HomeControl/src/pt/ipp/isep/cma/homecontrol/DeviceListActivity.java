package pt.ipp.isep.cma.homecontrol;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class DeviceListActivity extends ListActivity implements OnClickListener {
	
	public static final String TAG = "HomeControl";
	
	private BluetoothToolkit btToolkit;
	
    //interface views
    private Button scanButton;
    
    private DeviceListAdapter deviceAdapter;
    private List<BluetoothDevice> deviceArray = new ArrayList<BluetoothDevice>();
    
    private void refreshDevices() {
    	Log.i(TAG, DeviceListActivity.class.getSimpleName() + " refreshDevices");
    	deviceArray.clear();
    	
    	// TODO: add devices here - criar um intent que vai mandar a list para a outra activity
    	
    	deviceAdapter.notifyDataSetChanged();
    }
   
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
    	Log.i(TAG, DeviceListActivity.class.getSimpleName() + " onCreate");
	    
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    requestWindowFeature(Window.FEATURE_PROGRESS);
	    setContentView(R.layout.device_list);
	    
	    // TODO: bt adapter e toolkit...
	    
	    
	    scanButton = (Button) findViewById(R.id.button_scan);
	    scanButton.setOnClickListener(this);

	    // dispositivos emparelhados
	    deviceAdapter = new DeviceListAdapter(this, deviceArray);
        setListAdapter(deviceAdapter);  
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
    	Log.i(TAG, DeviceListActivity.class.getSimpleName() + " onResume");		
		
		refreshDevices();
	}
	
	@Override
	protected void onPause() {
    	Log.i(TAG, DeviceListActivity.class.getSimpleName() + " onPause");			
		super.onPause();
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button_scan) {
	    	Log.i(TAG, DeviceListActivity.class.getSimpleName() + " onClick");	
				// TODO: start scan...
			
		
		}	
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

	}

}
