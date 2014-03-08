package com.cmaexample.threadasynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AsyncTaskActivity extends Activity {
	
	private final static String TAG = "ThreadAsyncTask";
	
	private ImageView mImageView;
	private ProgressBar mProgressBar;
	
	private final int mDelay = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_task);
		
		mImageView = (ImageView) findViewById(R.id.imageView);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		
		final Button button = (Button) findViewById(R.id.loadButton);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new LoadIconAsyncTask().execute(R.drawable.batman);				
			}
		});
		
		final Button otherButton = (Button) findViewById(R.id.otherButton);
		otherButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(AsyncTaskActivity.this, "I'm working dude!",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.async_task, menu);
		return true;
	}
	
	class LoadIconAsyncTask extends AsyncTask<Integer, Integer, Bitmap> {

		@Override
		protected void onPreExecute() {
			//super.onPreExecute();
			mProgressBar.setVisibility(ProgressBar.VISIBLE);
		}
		
		@Override
		protected Bitmap doInBackground(Integer... params) {
			Bitmap tmp = BitmapFactory.decodeResource(getResources(), params[0]);
			
			// Simulate a long-running operation
			for (int i = 1; i < 11; i ++) {
				sleep();
				publishProgress(i * 10);
			}
			
			return tmp;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			//super.onProgressUpdate(values);
			mProgressBar.setProgress(values[0]);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			//super.onPostExecute(result);
			mProgressBar.setVisibility(ProgressBar.INVISIBLE);
			mImageView.setImageBitmap(result);
		}

		private void sleep() {
			try {
				Thread.sleep(mDelay);
			} catch (InterruptedException e) {
				Log.e(TAG, e.toString());
			}		
		}	
	}

}
