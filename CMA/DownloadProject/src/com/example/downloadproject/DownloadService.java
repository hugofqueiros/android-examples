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
	
	// percorre o asyncTaskMap e remove todos os URLs e DownloadFileAsyncTasks
	// cuja Thread particular tenha concluido a execução
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
		// obtém o URL enviado através do Intent
		String downloadURL = intent.getExtras().getString(DOWNLOAD_URL_KEY);
		// lança o download
		startDownloadAsyncTask(downloadURL);
		
		// Flags possíveis:
		// START_NOT_STICKY: 		se o Android destruir o serviço depois de onStartCommand(), NÃO recriar o serviço, a não ser que existam Intents pendentes para lançar.
		// START_STICKY: 			se o Android destruir o serviço depois de onStartCommand(), recriar o serviço e invocar onStartCommand(), no entanto não volta a enviar o último Intent.
		// START_REDELIVER_INTENT: 	se o Android destruir o serviço depois de onStartCommand(), recriar o serviço e invocar onStartCommand() com o último Intent que foi enviado para o serviço.
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
		// se não for devolvido true, o onRebind não será executado em futuros binds
		return super.onUnbind(intent);
	}
		
	@Override
	public void onDestroy() {
		Log.d(TAG, "DownloadService onDestroy.");
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Inicia o download assíncrono de um ficheiro através do protocolo HTTP
	 * @param downloadURL
	 * @return true caso o download tenha sido lançado com sucesso
	 * 			false caso o download não tenha sido lançado
	 */
	protected boolean startDownloadAsyncTask(String downloadURL) {
		// apenas lança o download se o mesmo já não estiver a decorrer
		if(asyncTaskMap.containsKey(downloadURL)) {
			Log.w(TAG, "Already Downloading " + downloadURL);
			return false;
		}
		
		Log.i(TAG, DownloadService.class.getSimpleName() + "download: " + downloadURL);
		
		URL url;
		try {
			url = new URL(downloadURL);
			// lança a DownloadFileAsyncTask...
			DownloadFileAsyncTask asyncTask = new DownloadFileAsyncTask(this, url);
			asyncTask.execute();
			// ... e coloca-a no HashMap para que não se perca a sua referência
			asyncTaskMap.put(downloadURL, asyncTask);
		} catch (MalformedURLException e) {
			// caso o URL seja inválido...
			Log.e(TAG, "Invalid URL", e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Cancela o download de um ficheiro
	 * @param downloadURL
	 * @return true se o download tiver sido cancelado
	 * 			false se não tiver sido cancelado qualquer download
	 */
	public boolean cancelDownload(String downloadURL) {
		if(asyncTaskMap.containsKey(downloadURL)) {
			asyncTaskMap.get(downloadURL).cancel(true);
			return true;
		}
		return false;
	}
}
