/**
 * 
 */
package com.example.downloadproject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	// atrav�s do Intent que lan�a o service
	public final static String DOWNLOAD_URL_KEY = "DOWNLOAD_URL";
	
	private final IBinder mBinder = new DownloadServiceBinder();
	
	// Guarda, para cada URL, a inst�ncia de DownloadFileAsyncTask associada
	// que est� encarregue de fazer o download numa Thread particular
	private LinkedHashMap<String, DownloadFileAsyncTask> asyncTaskMap = new LinkedHashMap<String, DownloadFileAsyncTask>();
	
	public class DownloadServiceBinder extends Binder {
		public DownloadService getService() {
			return DownloadService.this;
		}
	}
	
	// percorre o asyncTaskMap e remove todos os URLs e DownloadFileAsyncTasks
	// cuja Thread particular tenha concluido a execu��o
	protected void removeFinishedDownloads() {
		List<String> toRemove = new ArrayList<String>();
		
		for(Map.Entry<String, DownloadFileAsyncTask> entry : asyncTaskMap.entrySet()) {
			if(entry.getValue().getStatus() == AsyncTask.Status.FINISHED)
				toRemove.add(entry.getKey());
		}
		for(String key : toRemove)
			asyncTaskMap.remove(key);
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG, "DownloadService onCreate called.");
		super.onCreate();
		Toast.makeText(this, "New Service was Created.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//return super.onStartCommand(intent, flags, startId);
		Log.d(TAG, DownloadService.class.getSimpleName() + "onStartCommand called.");
		
		removeFinishedDownloads();
		// obt�m o URL enviado atrav�s do Intent
		String downloadURL = intent.getExtras().getString(DOWNLOAD_URL_KEY);
		// lan�a o download
		startDownloadAsyncTask(downloadURL);
		
		// Flags poss�veis:
		// START_NOT_STICKY: 		se o Android destruir o servi�o depois de onStartCommand(), N�O recriar o servi�o, a n�o ser que existam Intents pendentes para lan�ar.
		// START_STICKY: 			se o Android destruir o servi�o depois de onStartCommand(), recriar o servi�o e invocar onStartCommand(), no entanto n�o volta a enviar o �ltimo Intent.
		// START_REDELIVER_INTENT: 	se o Android destruir o servi�o depois de onStartCommand(), recriar o servi�o e invocar onStartCommand() com o �ltimo Intent que foi enviado para o servi�o.
		return START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, DownloadService.class.getSimpleName() + " onBind()");
		return mBinder;
	}

	@Override
	public void onRebind(Intent intent) {
		Log.d(TAG, DownloadService.class.getSimpleName() + " onRebind()");
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, DownloadService.class.getSimpleName() + " onUnbind()");
		// se n�o for devolvido true, o onRebind n�o ser� executado em futuros binds
		return super.onUnbind(intent);
	}
		
	@Override
	public void onDestroy() {
		Log.d(TAG, "DownloadService onDestroy.");
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Inicia o download ass�ncrono de um ficheiro atrav�s do protocolo HTTP
	 * @param downloadURL
	 * @return true caso o download tenha sido lan�ado com sucesso
	 * 			false caso o download n�o tenha sido lan�ado
	 */
	protected boolean startDownloadAsyncTask(String downloadURL) {
		// apenas lan�a o download se o mesmo j� n�o estiver a decorrer
		if(asyncTaskMap.containsKey(downloadURL)) {
			Log.w(TAG, "Already Downloading " + downloadURL);
			return false;
		}
		
		Log.i(TAG, DownloadService.class.getSimpleName() + "download: " + downloadURL);
		
		URL url;
		try {
			url = new URL(downloadURL);
			// lan�a a DownloadFileAsyncTask...
			DownloadFileAsyncTask asyncTask = new DownloadFileAsyncTask(this, url);
			asyncTask.execute();
			// ... e coloca-a no HashMap para que n�o se perca a sua refer�ncia
			asyncTaskMap.put(downloadURL, asyncTask);
		} catch (MalformedURLException e) {
			// caso o URL seja inv�lido...
			Log.e(TAG, "Invalid URL", e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Cancela o download de um ficheiro
	 * @param downloadURL
	 * @return true se o download tiver sido cancelado
	 * 			false se n�o tiver sido cancelado qualquer download
	 */
	public boolean cancelDownload(String downloadURL) {
		if(asyncTaskMap.containsKey(downloadURL)) {
			asyncTaskMap.get(downloadURL).cancel(true);
			return true;
		}
		return false;
	}
}
