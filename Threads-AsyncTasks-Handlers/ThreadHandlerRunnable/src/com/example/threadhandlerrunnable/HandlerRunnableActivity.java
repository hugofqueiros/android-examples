package com.example.threadhandlerrunnable;

import com.example.threadhandlerrunnable.R.drawable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HandlerRunnableActivity extends Activity {

	private ImageView mImageView;
	private ProgressBar mProgressBar;
	private Bitmap mBitmap;
	private int mDelay = 500;
	private final Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler_runnable);
		
		mImageView = (ImageView) findViewById(R.id.imageView);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
	
		final Button button = (Button) findViewById(R.id.loadButton);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new LoadIconTask(R.drawable.painter)).start();
				
			}
		});
		
		final Button otherButton = (Button) findViewById(R.id.otherButton);
		otherButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(HandlerRunnableActivity.this,
						"I'm working dude!", Toast.LENGTH_SHORT).show();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.handler_runnable, menu);
		return true;
	}
	
	private class LoadIconTask implements Runnable {
		int resId;
		
		public LoadIconTask(int resId) {
			this.resId = resId;
		}

		@Override
		public void run() {
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					mProgressBar.setVisibility(ProgressBar.VISIBLE);
					
				}
			});
			
			mBitmap = BitmapFactory.decodeResource(getResources(), resId);
		
			// Simulating long-running operation
			for (int i = 1; i < 11; ++i) {
				sleep();
				final int step = i;
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						mProgressBar.setProgress(step * 10);
						
					}
				});
			}
			
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					mImageView.setImageBitmap(mBitmap);
				}
			});
			
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					mProgressBar.setVisibility(ProgressBar.INVISIBLE);
				}
			});
		}
		
	}
	
	private void sleep() {
		try {
			Thread.sleep(mDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
