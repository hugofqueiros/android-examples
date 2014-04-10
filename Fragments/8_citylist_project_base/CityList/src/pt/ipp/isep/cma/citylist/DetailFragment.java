package pt.ipp.isep.cma.citylist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//public class DetailActivity extends Activity {
public class DetailFragment extends Fragment {	

	private TextView txtName, txtCountry, txtDescription;
    private City mCity = null;
    
    /**
     * Create a new instance of DetailFragment, initialized to
     * show the text at 'index'
     * @param index
     * @return
     */
    public static DetailFragment newInstance(int index) {
    	DetailFragment f = new DetailFragment();
    	
    	// Supply index input as an argument
    	Bundle args = new Bundle();
    	args.putInt(Global.INDEX_ARG, index);
    	f.setArguments(args);
    	
    	return f;
    }
    
    public void setCity(City city) {
    	mCity = city;
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": setCity");
		if (city != null && txtCountry != null && txtCountry != null && txtDescription != null) {
			Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": setCity + not null");
		txtName.setText(city.getName());
		txtCountry.setText(city.getCountry());
		txtDescription.setText(city.getDescription());
		}
    }
    
	@Override
	public void onAttach(Activity activity) {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onCreate");
	    
	    // for the activity
/*	    setContentView(R.layout.detail_layout);
	    
	    mCity = City.getById(this, getIntent().getLongExtra(MainActivity.EXTRA_CITY_ID, 0));

		txtName = (TextView) findViewById(R.id.txtCityName);
		txtCountry = (TextView) findViewById(R.id.txtCountryName);
		txtDescription = (TextView) findViewById(R.id.txtDescription);
    	
		if(mCity != null) {
    		txtName.setText(mCity.getName());
    		txtCountry.setText(mCity.getCountry());
    		txtDescription.setText(mCity.getDescription());
    	}*/
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onCreateView");
		//return super.onCreateView(inflater, container, savedInstanceState);
		
//		if (container == null) {
//			// We have different layouts, and in one of them this
//            // fragment's containing frame doesn't exist.  The fragment
//            // may still be created from its saved state, but there is
//            // no reason to try to create its view hierarchy because it
//            // won't be displayed.  Note this is not needed -- we could
//            // just run the code below, where we would create and return
//            // the view hierarchy; it would just never be used.
//			return null;
//		}
		
		View mContentView = inflater.inflate(R.layout.detail_layout, container, false);
		// mCity = City.getById(this, getActivity().getIntent().getLongExtra(MainActivity.EXTRA_CITY_ID, 0));
		
		txtName = (TextView) mContentView.findViewById(R.id.txtCityName);
		txtCountry = (TextView) mContentView.findViewById(R.id.txtCountryName);
		txtDescription = (TextView) mContentView.findViewById(R.id.txtDescription);
		
		if(mCity != null) {
			txtName.setText(mCity.getName());
			txtCountry.setText(mCity.getCountry());
			txtDescription.setText(mCity.getDescription());
		}
		
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onResume");
		if (mCity != null && txtCountry != null && txtCountry != null && txtDescription != null) {
			Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": setCity + not null");
		txtName.setText(mCity.getName());
		txtCountry.setText(mCity.getCountry());
		txtDescription.setText(mCity.getDescription());
		}
		super.onResume();
	}
	
	@Override
	public void onPause() {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onPause");
		super.onPause();
	}
	
	@Override
	public void onStop() {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onDestroyView");
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.i(Global.TAG, DetailFragment.class.getSimpleName() + ": onDetach");
		super.onDetach();
	}	
}
