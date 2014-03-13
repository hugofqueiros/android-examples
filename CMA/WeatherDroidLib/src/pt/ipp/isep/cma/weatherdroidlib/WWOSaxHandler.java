package pt.ipp.isep.cma.weatherdroidlib;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// handler para parsing do XML devolvido pelo World Weather Online
// faz o parsing 
public class WWOSaxHandler extends DefaultHandler {

	// timeout para o pedido HTTP em milisegundos
	private static final int WWO_TIMEOUT = 6000;
	private static final String WWO_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx?key=398zk68r9vg3t2kyudcr98h3&format=xml&num_of_days=";
	private static final int WWO_NUMDAYS = 5;

	private boolean in_weather = false;
	private boolean in_current_condition = false;
	private boolean in_temp_c = false;
	private boolean in_temp_max_c = false;
	private boolean in_temp_min_c = false;
	private boolean in_img = false;

	private boolean in_observation_time = false;
	private boolean in_weather_desc = false;
	private boolean in_windspeed_miles = false;
	private boolean in_windspeed_kmph = false;
	private boolean in_winddir = false;
	private boolean in_precip_mm = false;
	private boolean in_humidity = false;

	private SQLiteDatabase db = null;
	private ContentValues cv = null;
	private long p_id;
	
	private String tmpIcon = "";

	public WWOSaxHandler(SQLiteDatabase db) {
		this.db = db;
	}

	// actualizar so para um lugar
	public void updateWeatherConditions(Place p) {
		try {
			WeatherCondition.deleteAllForPlace(p.getId(), db);
			p_id = p.getId();

			Log.d("WWO", WWO_URL + WWO_NUMDAYS + "&q=" + p.getLatitude() + ","
					+ p.getLongitude());

			URL url = new URL(WWO_URL + WWO_NUMDAYS + "&q=" + p.getLatitude()
					+ "," + p.getLongitude());

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			// handler para lidar com o parsing do XML
			xr.setContentHandler(this);

			URLConnection conn = url.openConnection();
			// os timeouts asseguram que o cliente não fica indefinidamente
			// bloqueado quando o servidor ou a ligação está com problemas
			conn.setConnectTimeout(WWO_TIMEOUT);
			conn.setReadTimeout(WWO_TIMEOUT);
			InputStream in = conn.getInputStream();
			xr.parse(new InputSource(in));

		} catch (Exception e) {
			// FIXME e pode ser null?
			StackTraceElement[] st = e.getStackTrace();
			Log.d("WWO", e.toString());
			for (StackTraceElement el : st)
				Log.d("WWO", el.toString());
		}
	}

	// actualizar para múltiplos lugares
	public void updateWeatherConditions(long place_id) {
		Place p = Place.get(place_id, db);
		updateWeatherConditions(p);
	}

	@Override
	public void startDocument() throws SAXException {
		// NADA
	}

	@Override
	public void endDocument() throws SAXException {
		// NADA
	}

	// invocado na abertura de cada tag XML
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {

		// abertura da tag "weather" ou "current_condition"
		if (localName.equals("weather")) {
			cv = new ContentValues();
			cv.put(WeatherCondition.ID_PLACE, p_id);
			in_weather = true;
		}

		if (localName.equals("current_condition")) {
			cv = new ContentValues();
			cv.put(WeatherCondition.ID_PLACE, p_id);
			in_current_condition = true;
		}

		// tag com o URL da imagem da condição
		if (localName.equals("weatherIconUrl"))
			in_img = true;

		// tag com a temperatura (para condições actuais)
		if (localName.equals("temp_C"))
			in_temp_c = true;

		// tag com temperatura máxima (para previsões)
		if (localName.equals("tempMaxC"))
			in_temp_max_c = true;

		// tag com temperatura mínima (para previsões)
		if (localName.equals("tempMinC"))
			in_temp_min_c = true;

		if (localName.equals("observation_time") || localName.equals("date"))
			in_observation_time = true;

		if (localName.equals("weatherDesc"))
			in_weather_desc = true;

		if (localName.equals("windspeedMiles"))
			in_windspeed_miles = true;

		if (localName.equals("windspeedKmph"))
			in_windspeed_kmph = true;

		if (localName.equals("winddir16Point")
				|| localName.equals("winddirection"))
			in_winddir = true;

		if (localName.equals("precipMM"))
			in_precip_mm = true;

		if (localName.equals("humidity"))
			in_humidity = true;
	}

	// invocado no encerramento de cada tag XML
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("weather")) {
			in_weather = false;
			WeatherCondition.create(cv, db);
		}

		if (localName.equals("current_condition")) {
			WeatherCondition.create(cv, db);
			in_current_condition = false;
		}

		if (localName.equals("weatherIconUrl"))
			in_img = false;

		if (localName.equals("temp_C"))
			in_temp_c = false;

		if (localName.equals("tempMaxC"))
			in_temp_max_c = false;

		if (localName.equals("tempMinC"))
			in_temp_min_c = false;

		if (localName.equals("observation_time") || localName.equals("date"))
			in_observation_time = false;

		if (localName.equals("weatherDesc"))
			in_weather_desc = false;

		if (localName.equals("windspeedMiles"))
			in_windspeed_miles = false;

		if (localName.equals("windspeedKmph"))
			in_windspeed_kmph = false;

		if (localName.equals("winddir16Point")
				|| localName.equals("winddirection"))
			in_winddir = false;

		if (localName.equals("precipMM"))
			in_precip_mm = false;

		if (localName.equals("humidity"))
			in_humidity = false;
	}

	// invocado para se obter o conteúdo que está no interior das tags XML
	@Override
	public void characters(char ch[], int start, int length) {

		if (in_weather || in_current_condition) {

			if (in_current_condition) {
				if (in_temp_c) {
					String s = new String(ch, start, length).trim();
					cv.put(WeatherCondition.TEMPERATURE, Integer.valueOf(s));
				}

				if (in_humidity) {
					String s = new String(ch, start, length).trim();
					cv.put(WeatherCondition.HUMIDITY, Integer.valueOf(s));
				}
			}

			if (in_observation_time) {
				String s = new String(ch, start, length).trim();
				cv.put(WeatherCondition.OBSERVATION_TIME, s);
			}

			if (in_img) {				
				String s = new String(ch, start, length).replaceAll("\\s","");
				if(s.contains(".png")) {
					tmpIcon += s;					
					cv.put(WeatherCondition.ICON, tmpIcon.substring(tmpIcon.lastIndexOf("/") + 1, tmpIcon.lastIndexOf(".")));
					tmpIcon = "";
				} else
					tmpIcon = s;
			}

			if (in_temp_min_c) {
				String s = new String(ch, start, length).trim();
				cv.put(WeatherCondition.MIN_TEMPERATURE, Integer.valueOf(s));
			}

			if (in_temp_max_c) {
				String s = new String(ch, start, length).trim();
				cv.put(WeatherCondition.MAX_TEMPERATURE, Integer.valueOf(s));
			}

			if (in_weather_desc) {
				String s = new String(ch, start, length).trim();
				cv.put(WeatherCondition.DESCRIPTION, s);
			}

			if (in_windspeed_miles) {
				String s = new String(ch, start, length).trim();
				cv.put(WeatherCondition.WINDSPEED_MILES, Integer.valueOf(s));
			}

			if (in_windspeed_kmph) {
				String s = new String(ch, start, length).trim();
				cv.put(WeatherCondition.WINDSPEED_KMPH, Integer.valueOf(s));
			}

			if (in_winddir) {
				String s = new String(ch, start, length).trim();
				cv.put(WeatherCondition.WIND_DIRECTION, s);
			}

			if (in_precip_mm) {
				String s = new String(ch, start, length).trim();
				cv.put(WeatherCondition.PRECIPITATION, Float.valueOf(s));
			}
		}
	}
}
