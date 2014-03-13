package pt.ipp.isep.cma.countrylist;

import java.util.ArrayList;

import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class CountryPreferenceActivity extends PreferenceActivity {

	private ArrayList<String> continents = new ArrayList<String>();
	public static final String CONTINENT_PREF = "continent_pref";
	public static final String DEFAULT_CONTINENT = "Show All";
	
	private ListPreference continentPref;
	
	protected void loadContinentsFromDatabase() {
		MyDbHelper dbHelper = new MyDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        continents.clear();
        // adicionar a opção por omissão que não se encontra na base de dados
        continents.add(DEFAULT_CONTINENT);

        Cursor c = db.rawQuery("SELECT DISTINCT continent" 
            + " FROM tbl_country" 
            + " ORDER BY continent ASC", null);

        if (c.moveToFirst()) {
            do {
                continents.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();

        db.close();
        db = null;
	}
	
	
	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    addPreferencesFromResource(R.xml.preferences);
	    loadContinentsFromDatabase();
	    
	    String [] destinationList = new String[continents.size()];
	    destinationList = continents.toArray(destinationList);
	    
	    continentPref = (ListPreference) findPreference(CONTINENT_PREF);
	    
	    continentPref.setEntries(destinationList);
	    continentPref.setEntryValues(destinationList);
	    
	    
	    
	}

}
