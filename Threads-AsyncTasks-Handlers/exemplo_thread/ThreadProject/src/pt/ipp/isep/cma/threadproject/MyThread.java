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
	 * Semelhante ao método execute() da AsyncTask
	 * @param url do ficheiro para download
	 */
	public void execute(Integer maxCount) {
		this.mMaxCount = maxCount;
		// a Thread de execução particular só é lançada
		// após a execução do start()!
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
		
		// isto já é feito no código da classe AsyncTask
		if(isCancelled())
			onCancelled();
		else
			onPostExecute(i);
	}
	
	/**
	 * Semelhante ao método publishProgress() da AsyncTask
	 * Neste caso o método onProgressUpdate() é removido e o código
	 * que geralmente nele se encontra é colocado no interior de 
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
	 * Semelhante ao método onPostExecute() da AsyncTask
	 * Neste caso o método onPostExecute() será invocado diretamente
	 * da Thread particular, pelo que será necessário fazer com que
	 * o código seja executado na Thread principal
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
	 * Semelhante ao método onCancelled() da AsyncTask
	 * Neste caso o método onCancelled() será invocado diretamente
	 * da Thread particular, pelo que será necessário fazer com que
	 * o código seja executado na Thread principal
	 * @param result
	 */
	public void onCancelled() {
		// obtém um Handler para a Thread principal
		Handler mainHandler = new Handler(mContext.getMainLooper());
		// cria um Runnable (pedaço de código que irá executar em outra Thread)
		Runnable cancelledRunnable = new Runnable() {
			@Override
			public void run() {
				txtNumber.setText(R.string.canceled);
			}
		};
		// envia o Runnable para a Thread principal para execução
		mainHandler.post(cancelledRunnable);
	}
	
	/**
	 * Semelhante ao método cancel() da AsyncTask
	 * @param mayInterrupt
	 */
	public void cancel(boolean mayInterrupt) {
		cancelled = true;
		// o interrupt() interrompe uma Thread adormecida
		// de forma a esta continuar a sua execução
		// uma Thread pode estar adormecida após a execução de um
		// sleep(), wait() ou uma operação de I/O
		if(mayInterrupt) interrupt();
	}
	
	/**
	 * Semelhante ao método isCancelled() da AsyncTask
	 * @return
	 */
	public boolean isCancelled() {
		return cancelled;
	}

}
