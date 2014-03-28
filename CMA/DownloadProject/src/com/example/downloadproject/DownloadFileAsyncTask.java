package com.example.downloadproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

// Void, Integer, Integer
public class DownloadFileAsyncTask extends AsyncTask<Void, Integer, Integer> {

	public static final String TAG = "DOWNLOAD";

	// ac��es a enviar por broadcast
	// DOWNLOAD_UPDATE_ACTION: para enviar updates do progresso do download
	public static final String DOWNLOAD_UPDATE_ACTION = "com.example.DOWNLOAD_UPDATE";
	// DOWNLOAD_CANCELED_ACTION: para notificar que o download foi cancelado
	public static final String DOWNLOAD_CANCELED_ACTION = "com.example.DOWNLOAD_CANCELED";

	// KEY do par Key/Value que cont�m a percentagem de progresso do download
	public static final String PERCENT_EXTRA = "PERCENT_EXTRA";
	public static final String URL_EXTRA = "URL_EXTRA";
	// Diferen�a em % do progresso do download para que seja emitido um
	// broadcast com DOWNLOAD_UPDATE_ACTION
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
	 * Efectua o download de um ficheiro atrav�s do protocolo HTTP Este m�todo �
	 * executado numa Thread de execu��o � parte (particular)
	 */
	@Override
	protected Integer doInBackground(Void... params) {
		int mDownloadPercentage = 0;

		try {
			// AndroidHttpClient conn = AndroidHttpClient.newInstance("");
			// configura e estabelece um pedido HTTP
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000); // timeout ao estabelecer a liga��o
			conn.setReadTimeout(3000); // timeout ao ler informa��o da liga��o
			conn.setRequestMethod("GET"); // m�todo HTTP
			conn.setDoOutput(false);

			double contentSize = conn.getContentLength();

			Log.d(TAG, DownloadFileAsyncTask.class.getSimpleName()
					+ "\nHTTP File Size: " + contentSize);

			// se o tamanho for menor que 0 � porque ocorreu um erro
			// � poss�vel verificar o c�digo/msg da resposta HTTP atrav�s de
			// conn.getResponseCode() e conn.getResponseMessage()
			if (contentSize < 0) {
				Log.d(TAG, "Message code: " + conn.getResponseCode());
				Log.d(TAG, "Response Message: " + conn.getResponseMessage());
				conn.disconnect();
				return -1;
			}

			double downloadedSize = 0; // guarda a quantidade de bytes recebidos
			int previousPercentage = 0; // guarda a percentagem de progresso da
										// itera��o anterior (ciclo while)

			// obt�m acesso ao cart�o de mem�ria e estabelece uma output stream
			// para guardar dados no ficheiro
			String fileName = url.getFile().substring(
					url.getFile().lastIndexOf("/"));
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/" + fileName);
			FileOutputStream fileOutput = new FileOutputStream(file);

			// estabelece uma input stream para recep��o do ficheiro da resposta
			// HTTP
			InputStream inputStream = conn.getInputStream();

			// buffer de recep��o de 1024 bytes
			byte[] buffer = new byte[1024];
			int receivedBytes = 0; // ir� ter no m�x. o valor 1024

			// enquanto existirem bytes na input stream (receivedBytes > 0)
			// e o download do ficheiro n�o for cancelado
			while ((receivedBytes = inputStream.read(buffer)) > 0
					&& !isCancelled()) {
				// imprime os bytes recebidos para o ficheiro no cart�o de
				// mem�ria
				fileOutput.write(buffer, 0, receivedBytes);
				// adiciona a quantidade de bytes recebidos nesta itera��o �
				// quantidade total
				downloadedSize += receivedBytes;
				Log.i(TAG, "DownloadSize: " + downloadedSize);

				// calcula a percentagem (arredondada)
				mDownloadPercentage = (int) Math.round(downloadedSize
						/ contentSize * 100.0d);
				
				Log.i(TAG, "mDownloadPercentage: " + mDownloadPercentage);
				// se a diferen�a entre a percentagem enviada da �ltima vez e a
				// actual for >= PERCENT_DIFF
				// faz o publishProgress que ir� enviar um broadcast
				if (mDownloadPercentage - previousPercentage >= PERCENT_DIFF) {
					previousPercentage = mDownloadPercentage; // actualiza o valor
															// da �ltima perc.
															// enviada
					publishProgress(mDownloadPercentage);
				}
			}

			// liberta a liga��o
			fileOutput.close();
			conn.disconnect();

		} catch (Exception e) {
			Log.d(TAG, DownloadFileAsyncTask.class.getSimpleName()
					+ ": Error Downloading File", e);
			return -1;
		}

		return mDownloadPercentage;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// super.onProgressUpdate(values);
		// mProgressBar.setProgress(values[0]);
		// values -> refers to progress
		Log.d(TAG, DownloadFileAsyncTask.class.getSimpleName()
				+ "onProgressUpdate: " + values[0] + "%");

		Intent mPercentChangedIntent = new Intent(DOWNLOAD_UPDATE_ACTION);
		mPercentChangedIntent.putExtra(PERCENT_EXTRA, values[0]);
		mPercentChangedIntent.putExtra(URL_EXTRA, url.toString());
		context.sendBroadcast(mPercentChangedIntent);
	}

	/**
	 * Invocado quando a Thread conclui a sua execu��o e se o m�todo cancel()
	 * N�O tiver sido invocado. Este m�todo � executado na Thread de execu��o
	 * principal (UI)
	 */
	@Override
	protected void onPostExecute(Integer result) {
		Log.d(TAG, DownloadFileAsyncTask.class.getSimpleName()
				+ "onPostExecute: " + result);
		Intent mPercentChangedIntent = new Intent(DOWNLOAD_UPDATE_ACTION);
		mPercentChangedIntent.putExtra(PERCENT_EXTRA, result);
		mPercentChangedIntent.putExtra(URL_EXTRA, url.toString());
		context.sendBroadcast(mPercentChangedIntent);

		// super.onPostExecute(result);
	}

	/**
	 * Invocado quando a Thread conclui a sua execu��o e se o m�todo cancel()
	 * tiver sido invocado. Este m�todo � executado na Thread de execu��o
	 * principal (UI)
	 */
	@Override
	protected void onCancelled(Integer result) {
		// super.onCancelled(result);
		Log.d(TAG, DownloadFileAsyncTask.class.getSimpleName()
				+ "onCancelled: " + result);
		Intent mCanceledIntent = new Intent(DOWNLOAD_CANCELED_ACTION);
		mCanceledIntent.putExtra(PERCENT_EXTRA, result);
		mCanceledIntent.putExtra(URL_EXTRA, url.toString());
		context.sendBroadcast(mCanceledIntent);
	}

}
