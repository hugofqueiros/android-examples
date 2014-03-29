package pt.ipp.isep.cma.homecontrol;


public class Global {
	
	public static final String TAG = "HomeControl";
	
	public static final byte MESSAGE_TYPE_REQUEST	= 0x10;
	public static final byte MESSAGE_TYPE_RESPONSE	= 0x11;
	public static final byte MESSAGE_TYPE_ACTION	= 0x12;
	
	public static final byte SENSOR_TYPE_TEMPERATURE	= 0x10;
	public static final byte SENSOR_TYPE_ACTUATOR		= (byte) 0xF0;
	public static final byte SENSOR_TYPE_RELAY			= (byte) 0xF1;
	
	public static final byte BEDROOM_1_TEMPERATURE_SENSOR 	= 0x01;
	public static final byte BEDROOM_2_TEMPERATURE_SENSOR 	= 0x02;
	public static final byte BEDROOM_3_TEMPERATURE_SENSOR 	= 0x03;
	public static final byte LIVING_ROOM_SHUTTER_SENSOR		= 0x01;
	public static final byte GARDEN_LIGHT_SENSOR			= 0x01;
	
	// intent request codes
	public static final int REQUEST_ENABLE_BT = 2;        
}
