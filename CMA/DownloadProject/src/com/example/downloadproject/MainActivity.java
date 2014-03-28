package com.example.downloadproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.downloadproject.DownloadService.DownloadServiceBinder;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "DOWNLOAD";
	
	private EditText mEditTextUrl;
	private Button mButtonDownload;
	private ProgressBar mProgressBar;
	
	private ProgressDialog mProgressDialog = null;
	
	// binder devolvido pelo DownloadService.onBind() c/ a inst�ncia do servi�o
	private DownloadServiceBinder mDownloadServiceBinder = null;
	// BroadcastReceiver que ir� receber broadcasts da DownloadFileAsyncTask
	private MyDownloadReceiver mDownloadReceiver = null;
	// IntentFilter que ir� permitir o BroadcastReceiver filtrar apenas pelas
	// ac��es desejadas
	private IntentFilter mIntentFilter = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mEditTextUrl = (EditText) findViewById(R.id.url_text_id);
		
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_id);
		
		mButtonDownload = (Button) findViewById(R.id.button_id);
		mButtonDownload.setOnClickListener(this);
		
		// progress dialog que ir� aparecer quando se iniciar o download
		mProgressDialog = new ProgressDialog(MainActivity.this);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setMessage("Downloading...");
		mProgressDialog.setCancelable(true);
		
		mProgressDialog.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface dialog) {
				mDownloadServiceBinder.getService().cancelDownload(mEditTextUrl.getText().toString());
			}
		});
		
		// configura��o do broadcast receiver...
		mIntentFilter = new IntentFilter();
		// ...que apenas recebe as seguintes ac��es:
		mIntentFilter.addAction(DownloadFileAsyncTask.DOWNLOAD_UPDATE_ACTION);
		mIntentFilter.addAction(DownloadFileAsyncTask.DOWNLOAD_CANCELED_ACTION);
		mDownloadReceiver = new MyDownloadReceiver();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// inicia o download do ficheiro especificado na EditText
		if (v.getId() == R.id.button_id) {
			mProgressDialog.setProgress(0);
			mProgressDialog.show();
			
			// coloca o URL como um extra no intent que ir� enviar um comando para o servi�o
			Intent mIntent = new Intent(this, DownloadService.class);
			mIntent.putExtra(DownloadService.DOWNLOAD_URL_KEY, mEditTextUrl.getText().toString());			
			// lan�a o servi�o enviando-lhe um comando (ir� provocar a execu��o de onStartCommand)
			getApplicationContext().startService(mIntent);
		}
	}
	
	@Override
	protected void onRestart() {
		Log.i(TAG, MainActivity.class.getSimpleName() + "onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, MainActivity.class.getSimpleName() + "onResume()");
		// bind ao servi�o
		getApplicationContext().bindService(new Intent(MainActivity.this, DownloadService.class), 
				mConnection, Context.BIND_AUTO_CREATE);
		registerReceiver(mDownloadReceiver, mIntentFilter);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.i(TAG, MainActivity.class.getSimpleName() + "onPause()");
		// unbind ao servi�o
		getApplicationContext().unbindService(mConnection);
		unregisterReceiver(mDownloadReceiver);
		
		if(mProgressDialog.isShowing())
			mProgressDialog.dismiss();
		
		super.onPause();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	class MyDownloadReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context c, Intent i) {

			String url = i.getExtras().getString(DownloadFileAsyncTask.URL_EXTRA);
			
			// se a ac��o foi o cancelamento do download...
			if(i.getAction().compareTo(DownloadFileAsyncTask.DOWNLOAD_CANCELED_ACTION) == 0) {
				mProgressDialog.dismiss();
				Toast.makeText(MainActivity.this,
						"The download of " + url + " was canceled!",
						Toast.LENGTH_LONG).show();
			} else {
			// caso contr�rio tr�ta-se de um update do progresso
				int mPercentage = i.getIntExtra(
						DownloadFileAsyncTask.PERCENT_EXTRA, 0);
				
				// se a percentagem de download for <0 quer dizer que ocorreu um erro
				if(mPercentage < 0) {
					mProgressDialog.dismiss();
					Toast.makeText(MainActivity.this,
							"A problem ocurred while trying to download " + url + "!",
							Toast.LENGTH_LONG).show();
				} 
				// se for inferior a 100 quer dizer que o download est� a decorrer
				else if (mPercentage < 100)
					mProgressDialog.setProgress(mPercentage);
				else {
				// caso contr�rio o download foi conclu�do (100%)
					mProgressDialog.dismiss();
					Toast.makeText(MainActivity.this,
							"The download of " + url + " completed successfully!",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	// "listener" para eventos relacionados com o servi�o DownloadService
	private ServiceConnection mConnection = new ServiceConnection() {
		
		// executado quando a liga��o ao servi�o � destru�da
		// normalmente acontece quando o servi�o falha ou � morto
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mDownloadServiceBinder = null;
		}
		
		// executado quando o onBind ou onRebind do servi�o � concluido
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mDownloadServiceBinder = (DownloadServiceBinder) service;		
		}
	};
}
