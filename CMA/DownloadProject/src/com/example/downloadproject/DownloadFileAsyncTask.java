package com.example.downloadproject;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadFileAsyncTask extends AsyncTask<URL, Integer, Integer> {

	public static final String TAG = "DOWNLOAD";
	
	// acções a enviar por broadcast
	// DOWNLOAD_UPDATE_ACTION: para enviar updates do progresso do download
	public static final String DOWNLOAD_UPDATE_ACTION = "DOWNLOAD_UPDATE";
	// DOWNLOAD_CANCELED_ACTION: para notificar que o download foi cancelado
	public static final String DOWNLOAD_CANCELED_ACTION = "DOWNLOAD_CANCELED";
	
	// KEY do par Key/Value que contém a percentagem de progresso do download
	public static final String PERCENT_EXTRA = "PERCENT_EXTRA";
	public static final String URL_EXTRA = "URL_EXTRA";
	// Diferença em % do progresso do download para que seja emitido um broadcast com DOWNLOAD_UPDATE_ACTION
	private final static int PERCENT_DIFF = 1;
	
	private Context context;
	private URL url;
	
	public DownloadFileAsyncTask(Context context, URL url) {
		super();
		this.context = context;
		this.url = url;
	}
	
	@Override
	protected void onPreExecute() {

		super.onPreExecute();
	}

	/**
	 * Efectua o download de um ficheiro através do protocolo HTTP
	 * Este método é executado numa Thread de execução à parte (particular)
	 */
	@Override
	protected Integer doInBackground(URL... params) {
		int mDownloadPercentage = 0;
		
		try {
			// AndroidHttpClient conn = AndroidHttpClient.newInstance("");
			// configura e estabelece um pedido HTTP
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);					// timeout ao estabelecer a ligação
			conn.setReadTimeout(3000);						// timeout ao ler informação da ligação
			conn.setRequestMethod("GET");					// método HTTP
			conn.setDoOutput(false);
			
			double contentSize = conn.getContentLength();
			
			Log.d(TAG, DownloadFileAsyncTask.class.getSimpleName() + "\nHTTP File Size: " + contentSize);
			
			// se o tamanho for menor que 0 é porque ocorreu um erro
			// é possível verificar o código/msg da resposta HTTP através de
			// conn.getResponseCode() e conn.getResponseMessage()
			if (contentSize < 0) {
				Log.d(TAG, "Message code: " + conn.getResponseCode());
				Log.d(TAG, "Response Message: " + conn.getResponseMessage());
				conn.disconnect();
				return -1;
			}
			
			double downloadedSize = 0;     	// guarda a quantidade de bytes recebidos 
			int previousPercentage = 0;		// guarda a percentagem de progresso da iteração anterior (ciclo while)
			
		} catch (Exception e) {
			Log.d(TAG, DownloadFileAsyncTask.class.getSimpleName() + ": Error Downloading File", e);
			return -1;
		}
		
		return mDownloadPercentage;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		//super.onProgressUpdate(values);
		//mProgressBar.setProgress(values[0]);
		// values -> refers to progress
		Log.d(TAG, DownloadFileAsyncTask.class.getSimpleName() + "onProgressUpdate: " + values[0].toString());
		
		Intent mPercentChangedIntent = new Intent(DOWNLOAD_UPDATE_ACTION);
		mPercentChangedIntent.putExtra(PERCENT_EXTRA, values[0]);
		mPercentChangedIntent.putExtra(URL_EXTRA, url.toString());
		context.sendBroadcast(mPercentChangedIntent);
	}

	/**
	 * Invocado quando a Thread conclui a sua execução e se o
	 * método cancel() NÂO tiver sido invocado.
	 * Este método é executado na Thread de execução principal (UI)
	 */
	@Override
	protected void onPostExecute(Integer result) {
		Log.d(TAG, DownloadFileAsyncTask.class.getSimpleName() + "onPostExecute: " + result);
		Intent mPercentChangedIntent = new Intent(DOWNLOAD_UPDATE_ACTION);
		mPercentChangedIntent.putExtra(PERCENT_EXTRA, result);
		mPercentChangedIntent.putExtra(URL_EXTRA, url.toString());
		context.sendBroadcast(mPercentChangedIntent);
		
		// super.onPostExecute(result);
	}		
}

