package pt.ipp.isep.cma.weatherdroidlib;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Place {

	private long id;
	private String name;
	private float latitude, longitude;

	private ArrayList<WeatherCondition> conditions;

	public final static String TBL_NAME = "tbl_place";
	public final static String ID = "id_place";
	public final static String NAME = "name";
	public final static String LATITUDE = "latitude";
	public final static String LONGITUDE = "longitude";
	public final static String[] FIELDS = { ID, NAME, LATITUDE, LONGITUDE };

	public static ArrayList<Place> getAll(ArrayList<Place> alist, SQLiteDatabase db) {
		ArrayList<Place> tlist = (alist == null ? new ArrayList<Place>()
				: alist);

		tlist.clear();
		Cursor c = db.rawQuery("SELECT * FROM " + TBL_NAME
				+ " ORDER BY " + NAME + " ASC", null);
		if (c.moveToFirst()) {
			do {
				Place p = new Place(c.getLong(0), c.getString(1),
						c.getFloat(2), c.getFloat(3));
				p.setConditions(WeatherCondition.getForPlace(null, p.getId(),
						db));
				tlist.add(p);
			} while (c.moveToNext());
		}
		c.close();
		return tlist;
	}

	public static Place get(long id, SQLiteDatabase db) {
		Place cc = null;

		Cursor c = db.query(TBL_NAME, FIELDS, ID + "=" + id, null, null, null, null);
		if (c.moveToFirst()) {
			cc = new Place(c.getLong(0), c.getString(1), c.getFloat(2),
					c.getFloat(3));
			cc.setConditions(WeatherCondition.getForPlace(null, cc.getId(), db));

		}
		c.close();

		return cc;
	}

	public static void create(String name, float lat, float lon, SQLiteDatabase db) {
		ContentValues cv = new ContentValues();
		cv.put(NAME, name);
		cv.put(LATITUDE, lat);
		cv.put(LONGITUDE, lon);

		long rowId = db.insert(TBL_NAME, null, cv);
		if(rowId < 0) throw new RuntimeException("Erro ao inserir Place na base de dados!");
	}

	public static void delete(long id, SQLiteDatabase db) {
		db.delete(TBL_NAME, ID + "=" + id, null);
	}

	public Place(long id, String name, float latitude, float longitude) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public ArrayList<WeatherCondition> getConditions() {
		return conditions;
	}

	public void setConditions(ArrayList<WeatherCondition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public String toString() {
		return "#" + id + " " + name;
	}

}
