package pt.ipp.isep.cma.homecontrol;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

	private List<BluetoothDevice> devices;
	private Context context;
	
	public DeviceListAdapter(Context context, List<BluetoothDevice> objects) {
		super(context, R.layout.device, objects);
		
		this.devices = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		View v = convertView;
		if(v == null) {
			LayoutInflater vi = (LayoutInflater) 
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.device, null);
		}
		
		BluetoothDevice device = devices.get(position);
		
		TextView txtName = (TextView) v.findViewById(R.id.txtName);
		TextView txtAddress = (TextView) v.findViewById(R.id.txtAddress);
		
		txtName.setText(device.getName());
		txtAddress.setText(device.getAddress());
		
		return v;
	}
}













