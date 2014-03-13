package pt.ipp.isep.cma.boundedservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
	
	private final IBinder mBinder = new MyServiceBinder();

	public class MyServiceBinder extends Binder {
	    public MyService getService() { 
             return MyService.this; 
         }
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		return true;
	}
	
	public String testServiceConnectivity() {
		return getString(R.string.service_working);
	}
}
