package course.labs.activitylab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityOne extends Activity {

	private static final String RESTART_KEY = "restart";
	private static final String RESUME_KEY = "resume";
	private static final String START_KEY = "start";
	private static final String CREATE_KEY = "create";

	// String for LogCat documentation
	private final static String TAG = "Lab-ActivityOne";
	
	// Lifecycle counters

	// TODO:
	// Create counter variables for onCreate(), onRestart(), onStart() and
	// onResume(), called mCreate, etc.
	// You will need to increment these variables' values when their
	// corresponding lifecycle methods get called
	private int mCreate = 0;
	private int mRestart = 0;
	private int mStart = 0;
	private int mResume = 0;

	// TODO: Create variables for each of the TextViews, called
        // mTvCreate, etc.
	private TextView mTvCreate;
	private TextView mTvRestart;
	private TextView mTvStart;
	private TextView mTvResume;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one);
		
		// TODO: Assign the appropriate TextViews to the TextView variables
		// Hint: Access the TextView by calling Activity's findViewById()
		// textView1 = (TextView) findViewById(R.id.textView1);
		mTvCreate = (TextView) findViewById(R.id.create);
		mTvRestart = (TextView) findViewById(R.id.restart);
		mTvStart = (TextView) findViewById(R.id.start);
		mTvResume = (TextView) findViewById(R.id.resume);

		Button launchActivityTwoButton = (Button) findViewById(R.id.bLaunchActivityTwo); 
		launchActivityTwoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO:
				// Launch Activity Two
				// Hint: use Context's startActivity() method

				// Create an intent stating which Activity you would like to start
				Intent intStartActivtwo = new Intent(ActivityOne.this, ActivityTwo.class);
			
				// Launch the Activity using the intent
				startActivity(intStartActivtwo);		
			}
		});
		
		// Check for previously saved state
		if (savedInstanceState != null) {

			// TODO:
			// Restore value of counters from saved state
			// Only need 4 lines of code, one for every count variable
			mCreate = (Integer) savedInstanceState.get(CREATE_KEY);
			mRestart = (Integer) savedInstanceState.get(RESTART_KEY);
			mStart = (Integer) savedInstanceState.get(START_KEY);
			mResume = (Integer) savedInstanceState.get(RESUME_KEY);
			
			Log.i("debug", "saved data: \n" + mCreate + "\n" + mRestart + "\n" + mStart + "\n" + mResume);
		
		}

		// TODO: Emit LogCat message
		Log.i(TAG, getClass().getSimpleName() + ": onCreate()");


		// TODO:
		// Update the appropriate count variable
		// Update the user interface via the displayCounts() method
		++mCreate;
		displayCounts();
	}

	// Lifecycle callback overrides

	@Override
	public void onStart() {
		super.onStart();

		// TODO: Emit LogCat message
		Log.i(TAG, getClass().getSimpleName() + ": onStart()");


		// TODO:
		// Update the appropriate count variable
		// Update the user interface
		++mStart;
		displayCounts();
	}

	@Override
	public void onResume() {
		super.onResume();

		// TODO: Emit LogCat message
		Log.i(TAG, getClass().getSimpleName() + ": onResume()");


		// TODO:
		// Update the appropriate count variable
		// Update the user interface
		++mResume;
		displayCounts();
	}

	@Override
	public void onPause() {
		super.onPause();

		// TODO: Emit LogCat message
		Log.i(TAG, getClass().getSimpleName() + ": onPause()");

	}

	@Override
	public void onStop() {
		super.onStop();

		// TODO: Emit LogCat message
		Log.i(TAG, getClass().getSimpleName() + ": onStop()");

	}

	@Override
	public void onRestart() {
		super.onRestart();

		// TODO: Emit LogCat message
		Log.i(TAG, getClass().getSimpleName() + ": onRestart()");

		// TODO:
		// Update the appropriate count variable
		// Update the user interface
		++mRestart;
		displayCounts();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// TODO: Emit LogCat message
		Log.i(TAG, getClass().getSimpleName() + ": OnDestroy()");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// TODO:
		// Save state information with a collection of key-value pairs
		// 4 lines of code, one for every count variable
		savedInstanceState.putInt(CREATE_KEY, mCreate);
		savedInstanceState.putInt(RESUME_KEY, mResume);
		savedInstanceState.putInt(START_KEY, mStart);
		savedInstanceState.putInt(RESTART_KEY, mRestart);
	}
	
	// Updates the displayed counters
	public void displayCounts() {
		mTvCreate.setText("onCreate() calls: " + mCreate);
		mTvStart.setText("onStart() calls: " + mStart);
		mTvResume.setText("onResume() calls: " + mResume);
		mTvRestart.setText("onRestart() calls: " + mRestart);	
	}
}
