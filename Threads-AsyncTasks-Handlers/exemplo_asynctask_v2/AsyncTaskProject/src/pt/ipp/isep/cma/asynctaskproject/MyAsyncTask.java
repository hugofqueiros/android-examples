package pt.ipp.isep.cma.asynctaskproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {
	
	private TextView txtNumber;
	private Context mContext;
	
	public MyAsyncTask(Context context, TextView mTxtView) {
		this.txtNumber = mTxtView;
		this.mContext = context;
	}
	
	@Override
	protected Integer doInBackground(Integer... params) {
		
		int i = 0;
		
		try {		
			for(i=0; i<params[0] && !isCancelled(); i++) {
				publishProgress(i);			
				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			Log.w("MyAsyncTask", "Sleep interrupted! Cancel may have been invoked!", e);
		}
		
		return i;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		txtNumber.setText(String.valueOf(values[0]));
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		txtNumber.setText(mContext.getString(R.string.finish) + " " + result);
	}
	
	@Override
	protected void onCancelled() {
		txtNumber.setText(R.string.canceled);
	}
}
