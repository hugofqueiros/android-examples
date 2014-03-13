package pt.ipp.isep.cma.startedservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
	
	@SuppressWarnings("unused")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// Obter o extra enviado pelo Intent da Activity 
		String texto = intent.getExtras().getString(MainActivity.INTENT_EXTRA);
		
		// Do something
		
		return START_REDELIVER_INTENT;
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
		return null;
	}
}
