package pt.ipp.isep.cma.weatherdroidlib;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

// representa uma previsão meteorológica
public class WeatherCondition {

	private long placeId;
	private DateContainer date;

	private int currentTemperature;
	private int minTemperature;
	private int maxTemperature;

	private String description;
	private String observationTime;

	private int windSpeedMiles;
	private int windSpeedKmph;
	private String windDirection;

	private float precipitationMM;
	private int humidity;

	// URL do ícone da previsão
	private String icon;

	public static String NAME = "tbl_weather_condition";
	public static String ID_PLACE = "id_place";
	public static String DATE_CONDITION = "date_condition";
	public static String TEMPERATURE = "temperature";
	public static String MIN_TEMPERATURE = "min_temperature";
	public static String MAX_TEMPERATURE = "max_temperature";
	public static String DESCRIPTION = "description";
	public static String OBSERVATION_TIME = "observation_time";
	public static String WINDSPEED_MILES = "windspeed_miles";
	public static String WINDSPEED_KMPH = "windspeed_kmph";
	public static String WIND_DIRECTION = "wind_direction";
	public static String PRECIPITATION = "precipitation";
	public static String HUMIDITY = "humidity";
	public static String ICON = "icon";
	public static String[] FIELDS = { ID_PLACE, DATE_CONDITION, TEMPERATURE,
			MIN_TEMPERATURE, MAX_TEMPERATURE, DESCRIPTION, OBSERVATION_TIME,
			WINDSPEED_MILES, WINDSPEED_KMPH, WIND_DIRECTION, PRECIPITATION,
			HUMIDITY, ICON };

	public static ArrayList<WeatherCondition> getForPlace(
			ArrayList<WeatherCondition> alist, long id_place, SQLiteDatabase db) {
		ArrayList<WeatherCondition> tlist = (alist == null ? new ArrayList<WeatherCondition>()
				: alist);

		tlist.clear();
		Cursor c = db.rawQuery("SELECT * FROM " + NAME + " WHERE "
				+ ID_PLACE + "=" + id_place + " ORDER BY " + DATE_CONDITION
				+ " ASC", null);
		if (c.moveToFirst()) {
			do {
				tlist.add(new WeatherCondition(c.getLong(0),
						c.getString(1) == null ? null : new DateContainer(
								DateContainer.DateContainerType.DATE, c
										.getString(1)), c.getInt(2), c
								.getInt(3), c.getInt(4), c.getString(5), c
								.getString(6), c.getInt(7), c.getInt(8), c
								.getString(9), c.getFloat(10), c.getInt(11), c
								.getString(12)));
			} while (c.moveToNext());
		}
		c.close();
		return tlist;
	}

	public static WeatherCondition getCurrentForPlace(long id_place, SQLiteDatabase db) {
		WeatherCondition wc = null;

		Cursor c = db.query(NAME, FIELDS, ID_PLACE + "=" + id_place
				+ " AND " + DATE_CONDITION + " ISNULL", null, null, null, null);
		if (c.moveToFirst()) {
			wc = new WeatherCondition(c.getLong(0),
					c.getString(1) == null ? null : new DateContainer(
							DateContainer.DateContainerType.DATE, c
									.getString(1)), c.getInt(2), c.getInt(3),
					c.getInt(4), c.getString(5), c.getString(6), c.getInt(7),
					c.getInt(8), c.getString(9), c.getFloat(10), c.getInt(11),
					c.getString(12));
		}
		c.close();

		return wc;
	}

	public static void create(long id_place, DateContainer date_condition,
			Integer temperature, Integer min_temperature,
			Integer max_temperature, String description,
			String observation_time, Integer windspeed_miles,
			Integer windspeed_kmph, String wind_direction, Float precipitation,
			Integer humidity, String icon, SQLiteDatabase db) {
		
		ContentValues cv = new ContentValues();
		cv.put(ID_PLACE, id_place);
		cv.put(DATE_CONDITION, date_condition.getDateTimeString());
		cv.put(TEMPERATURE, temperature);
		cv.put(MIN_TEMPERATURE, min_temperature);
		cv.put(MAX_TEMPERATURE, max_temperature);
		cv.put(DESCRIPTION, description);
		cv.put(OBSERVATION_TIME, observation_time);
		cv.put(WINDSPEED_MILES, windspeed_miles);
		cv.put(WINDSPEED_KMPH, windspeed_kmph);
		cv.put(WIND_DIRECTION, wind_direction);
		cv.put(PRECIPITATION, precipitation);
		cv.put(HUMIDITY, humidity);
		cv.put(ICON, icon);

		create(cv, db);
	}

	public static void create(ContentValues cv, SQLiteDatabase db) {
		long rowId = db.insert(NAME, null, cv);
		if(rowId < 0) throw new RuntimeException("Erro ao inserir WeatherCondition na base de dados!");
	}

	public static void deleteAllForPlace(long id_place, SQLiteDatabase db) {
		db.delete(NAME, ID_PLACE + "=" + id_place, null);
	}

	public WeatherCondition() {
	}

	public WeatherCondition(long id_place, DateContainer date_condition,
			Integer temperature, Integer min_temperature,
			Integer max_temperature, String description,
			String observation_time, Integer windspeed_miles,
			Integer windspeed_kmph, String wind_direction, Float precipitation,
			Integer humidity, String icon) {

		this.placeId = id_place;
		this.date = date_condition;
		this.currentTemperature = temperature;
		this.minTemperature = min_temperature;
		this.maxTemperature = max_temperature;
		this.description = description;
		this.observationTime = observation_time;
		this.windSpeedMiles = windspeed_miles;
		this.windSpeedKmph = windspeed_kmph;
		this.windDirection = wind_direction;
		this.precipitationMM = precipitation;
		this.humidity = humidity;
		this.icon = icon;
	}

	public DateContainer getDate() {
		return date;
	}

	public void setDate(DateContainer date) {
		this.date = date;
	}

	public long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}

	public int getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(int currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public int getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(int minTemperature) {
		this.minTemperature = minTemperature;
	}

	public int getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(int maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservationTime() {
		return observationTime;
	}

	public void setObservationTime(String observationTime) {
		this.observationTime = observationTime;
	}

	public int getWindSpeedMiles() {
		return windSpeedMiles;
	}

	public void setWindSpeedMiles(int windSpeedMiles) {
		this.windSpeedMiles = windSpeedMiles;
	}

	public int getWindSpeedKmph() {
		return windSpeedKmph;
	}

	public void setWindSpeedKmph(int windSpeedKmph) {
		this.windSpeedKmph = windSpeedKmph;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public float getPrecipitationMM() {
		return precipitationMM;
	}

	public void setPrecipitationMM(float precipitationMM) {
		this.precipitationMM = precipitationMM;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
