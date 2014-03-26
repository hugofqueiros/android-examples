/**
 * 
 */
package com.example.downloadproject;

import java.net.URL;
import java.util.LinkedHashMap;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Zhao
 *
 */
public class DownloadService extends Service {
	
	private final static String TAG = "DOWNLOAD";
	
	// Key do Par/Value com o URL do ficheiro enviado
	// através do Intent que lanºa o service
	public final static String DOWNLOAD_URL_KEY = "DOWNLOAD_URL";
	
	private final IBinder mBinder = new DownloadServiceBinder();
	
	// Guarda, para cada URL, a instância de DownloadFileAsyncTask associada
	// que está encarregue de fazer o download numa Thread particular
	private LinkedHashMap<String, DownloadFileAsyncTask> asyncTaskMap = new LinkedHashMap<String, DownloadFileAsyncTask>();
	
	public class DownloadServiceBinder extends Binder {
		public DownloadService getService() {
			return DownloadService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "Download Service: onBind()");
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG, "DownloadService onCreate called.");
		super.onCreate();
		Toast.makeText(this, "New Service was Created.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "DownloadService onDestroy.");
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
	}
}
