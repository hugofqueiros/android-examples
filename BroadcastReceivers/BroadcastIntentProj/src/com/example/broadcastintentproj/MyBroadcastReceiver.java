package com.example.broadcastintentproj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

	// The broadcast is received on the onReceive method
	@Override
	public void onReceive(Context context, Intent intent) {
		
		// Present a toast for the intent action that was received
		Toast.makeText(context, "context broadcastReceived: " + context.getString(R.string.broadcastReceived)
				+ " Intent action: " + intent.getAction(), Toast.LENGTH_LONG).show();
		
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
	}
}
