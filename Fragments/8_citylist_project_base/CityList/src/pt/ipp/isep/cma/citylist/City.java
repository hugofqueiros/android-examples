package pt.ipp.isep.cma.citylist;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class City {
	
	private long id;
	private String name;
	private String country;
	private String description;
	private double latitude, longitude;
	
	public static void getAll(Context context, ArrayList<City> cities) {
		Log.i(Global.TAG, City.class.getSimpleName() + ": getAll");
        MyDbHelper dbHelper = new MyDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        cities.clear();

        Cursor c = db.rawQuery("SELECT * FROM tbl_place ORDER BY name ASC", null);
        
        if (c != null && c.moveToFirst()) {
            do {
            	//0-id, 1-name, 2-latitude, 3-longitude, 4-country, 5-description
                City city = new City(c.getLong(0), c.getString(1), c.getString(4), c.getString(5), c.getDouble(2), c.getDouble(3));
                cities.add(city);
            } while (c.moveToNext());
        }
        c.close();

        db.close();
        db = null;
    }
	
	public static City getById(Context context, long id) {
		Log.i(Global.TAG, City.class.getSimpleName() + ": getById");
        City city = null;
		
		MyDbHelper dbHelper = new MyDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM tbl_place WHERE id_place = " + id, null);
        
        if (c != null && c.moveToFirst()) {
        	//0-id, 1-name, 2-latitude, 3-longitude, 4-country, 5-description
            city = new City(c.getLong(0), c.getString(1), c.getString(4), c.getString(5), c.getDouble(2), c.getDouble(3));
        }
        c.close();

        db.close();
        db = null;
        
        return city;
    }

	public City() {
		Log.i(Global.TAG, City.class.getSimpleName() + ": City()");
	}

	public City(long id, String name, String country, String description,
			double latitude, double longitude) {
		super();
		Log.i(Global.TAG, City.class.getSimpleName() + ": City(long id, String name, String country, String description, double latitude, double longitude)");
		this.id = id;
		this.name = name;
		this.country = country;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public long getId() {
		Log.i(Global.TAG, City.class.getSimpleName() + ": getId");
		return id;
	}

	public void setId(long id) {
		Log.i(Global.TAG, City.class.getSimpleName() + ": setId");
		this.id = id;
	}

	public String getName() {
		Log.i(Global.TAG, City.class.getSimpleName() + ": getName");
		return name;
	}

	public void setName(String name) {
		Log.i(Global.TAG, City.class.getSimpleName() + ": setName");
		this.name = name;
	}

	public String getCountry() {
		Log.i(Global.TAG, City.class.getSimpleName() + ": getCountry");
		return country;
	}

	public void setCountry(String country) {
		Log.i(Global.TAG, City.class.getSimpleName() + ": setCountry");
		this.country = country;
	}

	public String getDescription() {
		Log.i(Global.TAG, City.class.getSimpleName() + ": getDescription");
		return description;
	}

	public void setDescription(String description) {
		Log.i(Global.TAG, City.class.getSimpleName() + ": setDescription");
		this.description = description;
	}

	public double getLatitude() {
		Log.i(Global.TAG, City.class.getSimpleName() + ": setId");
		return latitude;
	}

	public void setLatitude(double latitude) {
		Log.i(Global.TAG, City.class.getSimpleName() + ": setLatitude");
		this.latitude = latitude;
	}

	public double getLongitude() {
		Log.i(Global.TAG, City.class.getSimpleName() + ": getLongitude");
		return longitude;
	}

	public void setLongitude(double longitude) {
		Log.i(Global.TAG, City.class.getSimpleName() + ": setLongitude");
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		Log.i(Global.TAG, City.class.getSimpleName() + ": toString");
		return "City [id=" + id + ", name=" + name + ", country=" + country
				+ ", description=" + description + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

}
