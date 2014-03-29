package com.example.downloadproject;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MyBroadcastReceiverWidget extends AppWidgetProvider {

	public static final String TAG = "DOWNLOAD";
	
	private int mDownloadPercentage = 0;
	private String mDownloadURL = null;
	private boolean isUpdateAction = false;
	private boolean isCancelledAction = false;
	@Override
	public void onReceive(Context context, Intent intent) {
		// super.onReceive(context, intent);
		Log.i(TAG, MyBroadcastReceiverWidget.class.getSimpleName() + " onReceive called.");
		
		ComponentName thisWidget = new ComponentName(context, MyBroadcastReceiverWidget.class);
		
	}
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// super.onDeleted(context, appWidgetIds);
		Log.i(TAG, MyBroadcastReceiverWidget.class.getSimpleName() + " onDeleted called.");
	}
	@Override
	public void onDisabled(Context context) {
		// super.onDisabled(context);
		Log.i(TAG, MyBroadcastReceiverWidget.class.getSimpleName() + " onDisabled called.");
	}
	@Override
	public void onEnabled(Context context) {
		// super.onEnabled(context);
		Log.i(TAG, MyBroadcastReceiverWidget.class.getSimpleName() + " onEnabled called.");
	}
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.i(TAG, MyBroadcastReceiverWidget.class.getSimpleName() + " onUpdate called.");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	
	
}
