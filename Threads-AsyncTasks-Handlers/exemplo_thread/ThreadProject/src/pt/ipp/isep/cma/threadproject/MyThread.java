package pt.ipp.isep.cma.threadproject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MyThread extends Thread {
	
	private TextView txtNumber;
	private Context mContext;
	private Integer mMaxCount;
	private boolean cancelled = false;
	
	public MyThread(Context context, TextView mTxtView) {
		this.txtNumber = mTxtView;
		this.mContext = context;
	}
	
	/**
	 * Semelhante ao m�todo execute() da AsyncTask
	 * @param url do ficheiro para download
	 */
	public void execute(Integer maxCount) {
		this.mMaxCount = maxCount;
		// a Thread de execu��o particular s� � lan�ada
		// ap�s a execu��o do start()!
		start();
	}
	
	@Override
	public void run() {
		
		int i = 0;
		
		try {		
			for(i=0; i<mMaxCount && !isCancelled(); i++) {
				publishProgress(i);			
				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			Log.w("MyThread", "Sleep interrupted! Cancel may have been invoked!", e);
		}
		
		// isto j� � feito no c�digo da classe AsyncTask
		if(isCancelled())
			onCancelled();
		else
			onPostExecute(i);
	}
	
	/**
	 * Semelhante ao m�todo publishProgress() da AsyncTask
	 * Neste caso o m�todo onProgressUpdate() � removido e o c�digo
	 * que geralmente nele se encontra � colocado no interior de 
	 * progressRunnable.run()
	 * @param result
	 */
	public void publishProgress(final Integer progress) {
		Handler mainHandler = new Handler(mContext.getMainLooper());
		Runnable progressRunnable = new Runnable() {
			@Override
			public void run() {
				txtNumber.setText(String.valueOf(progress));
			}
		};
		mainHandler.post(progressRunnable);
	}
	
	/**
	 * Semelhante ao m�todo onPostExecute() da AsyncTask
	 * Neste caso o m�todo onPostExecute() ser� invocado diretamente
	 * da Thread particular, pelo que ser� necess�rio fazer com que
	 * o c�digo seja executado na Thread principal
	 * @param result
	 */
	public void onPostExecute(final Integer result) {
		Handler mainHandler = new Handler(mContext.getMainLooper());
		Runnable postRunnable = new Runnable() {
			@Override
			public void run() {
				txtNumber.setText(mContext.getString(R.string.finish) + " " + result);
			}
		};
		mainHandler.post(postRunnable);
	}
	
	/**
	 * Semelhante ao m�todo onCancelled() da AsyncTask
	 * Neste caso o m�todo onCancelled() ser� invocado diretamente
	 * da Thread particular, pelo que ser� necess�rio fazer com que
	 * o c�digo seja executado na Thread principal
	 * @param result
	 */
	public void onCancelled() {
		// obt�m um Handler para a Thread principal
		Handler mainHandler = new Handler(mContext.getMainLooper());
		// cria um Runnable (peda�o de c�digo que ir� executar em outra Thread)
		Runnable cancelledRunnable = new Runnable() {
			@Override
			public void run() {
				txtNumber.setText(R.string.canceled);
			}
		};
		// envia o Runnable para a Thread principal para execu��o
		mainHandler.post(cancelledRunnable);
	}
	
	/**
	 * Semelhante ao m�todo cancel() da AsyncTask
	 * @param mayInterrupt
	 */
	public void cancel(boolean mayInterrupt) {
		cancelled = true;
		// o interrupt() interrompe uma Thread adormecida
		// de forma a esta continuar a sua execu��o
		// uma Thread pode estar adormecida ap�s a execu��o de um
		// sleep(), wait() ou uma opera��o de I/O
		if(mayInterrupt) interrupt();
	}
	
	/**
	 * Semelhante ao m�todo isCancelled() da AsyncTask
	 * @return
	 */
	public boolean isCancelled() {
		return cancelled;
	}

}
