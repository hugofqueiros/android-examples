package pt.ipp.isep.cma.homecontrol;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class HomeControlService extends Service {
	public static final String TAG = "HomeControl";
	
	public static final String DEVICE_EXTRA = "BT_DEVICE";
	
	// Para saber qual o estado da conecção
	public static final int STATE_DISCONECTED = 0;
	public static final int STATE_CONNECTED = 1;
	public static final int STATE_ERROR = 2;
	
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_RECEIVED = 2;
	
	private final IBinder mBinder = new HomeControlServiceBinder();
	
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	
	public class HomeControlServiceBinder extends Binder {
		public HomeControlService getService() {
			return HomeControlService.this;
		}
	}
}
