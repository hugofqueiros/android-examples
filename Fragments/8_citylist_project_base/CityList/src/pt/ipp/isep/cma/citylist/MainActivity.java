package pt.ipp.isep.cma.citylist;

import pt.ipp.isep.cma.citylist.ListCities.OnCitySelectedListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class MainActivity extends FragmentActivity implements OnCitySelectedListener {
	private ListCities mListCities;
	private DetailFragment mDetailFragment;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": onCreate");
		
		setContentView(R.layout.main_activity);
		
		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null)
				return;
			
			mListCities = new ListCities();
			
			FragmentManager mFragManager = getSupportFragmentManager();
			FragmentTransaction firstTransaction = mFragManager.beginTransaction();
			firstTransaction.add(R.id.fragment_container, mListCities);
			firstTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			firstTransaction.commit();
		}
	}
	
	@Override
	protected void onStart() {
		Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": onStart");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": onDestroy");
		super.onDestroy();
	}

	
	@Override
	public void onCitySelected(City city) {
		Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": onCitySelected");
		
		Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": " + city.toString());
		//mDetailFragment = new DetailFragment();
		//mDetailFragment = (DetailFragment) mFragManager.findFragmentById(R.id.frag_detail);
		FragmentManager mFragManager = getSupportFragmentManager();
		mDetailFragment = (DetailFragment) mFragManager.findFragmentById(R.id.frag_detail);
		Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": findFragmentById");
		
		if (mDetailFragment != null) {
			Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": tablet");
			mDetailFragment.setCity(city);
		}
		else {
			Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": smartphone");
			mDetailFragment = new DetailFragment();
			FragmentTransaction secondTransaction = mFragManager.beginTransaction();
			Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": beginTransaction");
			secondTransaction.replace(R.id.fragment_container, mDetailFragment);
			Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": replace");
			secondTransaction.addToBackStack(null);
			Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": addToBackStack");
			secondTransaction.commit();	
			Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": commit");
			
			Log.i(Global.TAG, MainActivity.class.getSimpleName() + ": setCity");
			mDetailFragment.setCity(city);
		}
	}
}
