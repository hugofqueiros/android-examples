/**
 * 
 */
package com.example.widgetexample;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * @author Zhao
 *
 */
public class MyBroadcastReceiverWidget extends AppWidgetProvider {
	private String lastAction = "No action";
	private String lastMsg = "No message";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		ComponentName thisWidget = new ComponentName(context, MyBroadcastReceiverWidget.class);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		
		if (intent.hasExtra(Global.INTENT_MESSAGE_EXTRA))
			lastMsg = intent.getStringExtra(Global.INTENT_MESSAGE_EXTRA);
		
		lastAction = intent.getAction();
		
		// exacutes onUpdate everytime it receives an intent
		onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(thisWidget));
		
		super.onReceive(context, intent);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// super.onUpdate(context, appWidgetManager, appWidgetIds);
		// Update each instance of the widget
		final int N = appWidgetIds.length;
		for ( int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	private void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {
		// creates a RemoteViews object with a widget layout
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		views.setTextViewText(R.id.txtMsg, lastMsg + " (" + lastAction + ")");
	    appWidgetManager.updateAppWidget(appWidgetId, views);		
	}

}
