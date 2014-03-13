/**
 * 
 */
package com.example.downloadproject;

import java.net.URL;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/**
 * @author Zhao
 *
 */
public class DownloadService extends Service {

	private DownloadTask task;
	private final IBinder mBinder = new DownloadServiceBinder();
	
	public class DownloadServiceBinder extends Binder {
		public DownloadService getService() {
			return DownloadService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		// super.onCreate();
		Toast.makeText(this, "New Service was Created.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		//super.onStart(intent, startId);
		Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy() {
		//super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
	}
	
	private class DownloadAsyncTask extends AsyncTask<URL, Integer, Integer> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}
		
		@Override
		protected Integer doInBackground(URL... params) {

			
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			//super.onProgressUpdate(values);
			mProgressBar.setProgress(values[0]);
		}
		
		@Override
		protected void onPostExecute(Integer result) {

			super.onPostExecute(result);
		}		
	}

}
