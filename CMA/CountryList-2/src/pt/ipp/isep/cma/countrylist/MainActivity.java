package pt.ipp.isep.cma.countrylist;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ListActivity {
	
	private CountryAdapter mAdapter;
	private ArrayList<Country> countries = new ArrayList<Country>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAdapter = new CountryAdapter(this, countries);
		setListAdapter(mAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		loadCountries();
		mAdapter.notifyDataSetChanged();
	}

	private String getContinentFilter() {
		
		SharedPreferences prefs = PreferenceManager.
				getDefaultSharedPreferences(getBaseContext());
		
		String continentFilter = prefs.getString(
				CountryPreferenceActivity.CONTINENT_PREF,
				CountryPreferenceActivity.DEFAULT_CONTINENT
				);
		return continentFilter;
	}
	
	private void loadCountries() {
		MyDbHelper dbHelper = new MyDbHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		countries.clear();
		
		String continentFilter = getContinentFilter();
		
		Cursor c = null;
		
		if(continentFilter.
				compareTo(CountryPreferenceActivity.DEFAULT_CONTINENT) == 0)
		{
			c = db.rawQuery("SELECT * FROM tbl_country ORDER BY name ASC", null);
		} else {
			c = db.rawQuery("SELECT * FROM tbl_country " +
							" WHERE continent='" + continentFilter + "'" +
							" ORDER BY name ASC", null);
				
		}
		
		if(c != null && c.moveToFirst()) {
			do {				
				countries.add(new Country(c.getInt(0), c.getString(1), c.getString(2)));				
			} while(c.moveToNext());
		}
		
		c.close();
		db.close();		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuItem mnu1 = menu.add(0, 0, 0, R.string.preferences_label);
		mnu1.setAlphabeticShortcut('p');
		mnu1.setIcon(R.drawable.ic_menu_preferences);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == 0) {
			Intent intent = new Intent(this, 
						CountryPreferenceActivity.class);
			startActivity(intent);
			
			return true;
		}

		return false;
	}
	
	

}







