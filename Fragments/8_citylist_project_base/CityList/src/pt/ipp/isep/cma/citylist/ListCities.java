package pt.ipp.isep.cma.citylist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

// public class MainActivity extents ListActivity
public class ListCities extends ListFragment {
	
	public static final String EXTRA_CITY_ID = "city_id";

	private final ArrayList<City> cities = new ArrayList<City>();
	private CityListAdapter mlistAdapter = null;
	
	private OnCitySelectedListener mListener;
	
	private boolean mDualPane;
	private int mCurCheckPosition = 0;
	
	
	public interface OnCitySelectedListener {
		public void onCitySelected(City city);
	}
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(Global.TAG, ListCities.class.getSimpleName() + ": onAttach");
		super.onAttach(activity);
		try {
			mListener = (OnCitySelectedListener) activity;
		} catch (ClassCastException c) {
			Log.e(Global.TAG, ListCities.class.getSimpleName(), c);
			throw new ClassCastException(activity.toString() + " must implement OnCitySelectedListener");
		}

	}

	@Override
	//listactivity -> protected void onCreate(Bundle savedInstaceState) {
	public void onCreate(Bundle savedInstanceState) {
		Log.i(Global.TAG, ListCities.class.getSimpleName() + ": onCreate");
		super.onCreate(savedInstanceState);
		
		// listactivity-> listAdapter = new CityListAdapter(this, cities);
		
		City.getAll(getActivity().getApplicationContext(), cities);
		
		mlistAdapter = new CityListAdapter(getActivity().getApplicationContext(), cities);
		setListAdapter(mlistAdapter);
		// mlistAdapter.notifyDataSetChanged();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Log.i(Global.TAG, ListCities.class.getSimpleName() + ": onActivityCreated");
		
		// Check to see if we have a frame in which to embed the details
		// fragment directly in the containing UI
		View detailFrag = getActivity().findViewById(R.id.frag_detail);
		mDualPane = detailFrag != null && detailFrag.getVisibility() == View.VISIBLE;
		Log.i(Global.TAG, "ListCities.class.getSimpleName()" + ": dualPane? " + mDualPane);
		
		if (savedInstanceState != null) {
			// Restore last state for checked position
			mCurCheckPosition = savedInstanceState.getInt(Global.CURR_CITY_CHOICE, 0);
		}
		
		if (mDualPane) {
			// In dual-pane mode, the list view highlights the selected item.
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(Global.CURR_CITY_CHOICE, mCurCheckPosition);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return super.onCreateView(inflater, container, savedInstanceState);
		Log.i(Global.TAG, ListCities.class.getSimpleName() + ": onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		Log.i(Global.TAG, ListCities.class.getSimpleName() + ": onResume");
		super.onResume();

//		City.getAll(this, cities);
//		mlistAdapter.notifyDataSetChanged();
		
//		City.getAll(getActivity().getApplicationContext(), cities);
//		mlistAdapter.notifyDataSetChanged();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showDetails(position);
//		Intent mIntent = new Intent(this, DetailFragment.class);
//		mIntent.putExtra(EXTRA_CITY_ID, cities.get(position).getId());
//		
//		startActivity(mIntent);
		
//		Log.i(Global.TAG, ListCities.class.getSimpleName() + ": onListItemClick");
//		
//		mCurCheckPosition = position;
//		Log.i(Global.TAG, ListCities.class.getSimpleName() + ": current index: " + mCurCheckPosition);
//		
//		Activity activity = getActivity();
//		
//		if (activity != null) {
//			mlistAdapter = (CityListAdapter) getListAdapter();
//			City city = mlistAdapter.getItem(position);
//			mListener.onCitySelected(city);
//		}
	}

	private void showDetails(int position) {
		Log.i(Global.TAG, ListCities.class.getSimpleName() + ": showDetails");
		
		mCurCheckPosition = position;
		Log.i(Global.TAG, ListCities.class.getSimpleName() + ": current index: " + mCurCheckPosition);
		
		
//		if(mDualPane) {
	        // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
			getListView().setItemChecked(position, true);
			
			Activity activity = getActivity();
			
			if (activity != null) {
				Log.i(Global.TAG, ListCities.class.getSimpleName() + " activity != null");
				// mlistAdapter = (CityListAdapter) getListAdapter();
				String itemString = mlistAdapter.getItem(position).toString();
				Log.i(Global.TAG, ListCities.class.getSimpleName() + ": " + itemString);
				Toast.makeText(activity, "Selected: " + itemString, Toast.LENGTH_LONG).show();
				City city = mlistAdapter.getItem(position);
				mListener.onCitySelected(city);
			}
//		}
//		else {
//			Intent mintent = new Intent();
//			mintent.setClass(getActivity(), DetailFragment.class);
//			mintent.putExtra(EXTRA_CITY_ID, cities.get(position).getId());
//			startActivity(mintent);
//		}		
	}
}