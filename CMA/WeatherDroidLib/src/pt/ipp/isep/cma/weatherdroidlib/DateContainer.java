package pt.ipp.isep.cma.weatherdroidlib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateContainer {

	private Calendar c = Calendar.getInstance();

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "hh:mm:ss";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd' 'hh:mm:ss";

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			DATE_FORMAT);
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			TIME_FORMAT);
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
			DATETIME_FORMAT);

	public enum DateContainerType {
		DATE, TIME, DATETIME
	}

	private DateContainerType type = DateContainerType.DATE;

	public DateContainer(DateContainerType t) {
		type = t;
	}

	public DateContainer(DateContainerType t, String date) {
		this(t);
		setFromString(date);
	}

	public void setFromString(String date) {
		try {
			switch (type) {
			case DATE:
				c.setTime(dateFormat.parse(date));
				break;
			case DATETIME:
				c.setTime(dateTimeFormat.parse(date));
				break;
			case TIME:
				c.setTime(timeFormat.parse(date));
				break;
			}
		} catch (ParseException e) {
			// FIXME catch exception
		}
	}

	public String getDateTimeString() {
		switch (type) {
		case DATE:
			return dateFormat.format(c.getTime());
		case DATETIME:
			return dateTimeFormat.format(c.getTime());
		case TIME:
			return timeFormat.format(c.getTime());
		}
		return null;
	}

	public String getDateTimeString(String pattern) {
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		return f.format(c.getTime());
	}

	public Calendar getCalendar() {
		return c;
	}

	public DateContainerType getType() {
		return type;
	}

}
