package pt.ipp.isep.cma.homecontrol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class HomeControlService extends Service {
	public static final String TAG = "HomeControl";

	public static final String DEVICE_EXTRA = "BT_DEVICE";

	public static final UUID DEVICE_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	// Para saber qual o estado da conecção
	public static final int STATE_DISCONECTED = 0;
	public static final int STATE_CONNECTED = 1;
	public static final int STATE_ERROR = 2;

	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_RECEIVED = 2;

	private final IBinder mBinder = new HomeControlServiceBinder();

	private int state;
	private InputStream inStream;
	private OutputStream outStream;
	private ReceiveThread receiveThread;

	private BluetoothDevice device;
	private BluetoothSocket socket;
	private Handler handler;

	public class HomeControlServiceBinder extends Binder {
		public HomeControlService getService() {
			return HomeControlService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {

		device = intent.getExtras().getParcelable(DEVICE_EXTRA);

		if (device == null) {
			Log.e(TAG, HomeControlService.class.getSimpleName()
					+ "Device is null");
			return mBinder;
		}

		connect();

		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		disconnect();
		return true;
	}

	private boolean connect() {
		setState(STATE_DISCONECTED);

		if (device != null) {
			try {
				socket = device.createRfcommSocketToServiceRecord(DEVICE_UUID);

			} catch (IOException e) {
				Log.e(TAG, HomeControlService.class.getSimpleName()
						+ "createRfcommSocketToServiceRecord", e);
				setState(STATE_ERROR);
			}

			try {
				socket.connect();
			} catch (IOException e) {
				Log.e(TAG,
						HomeControlService.class.getSimpleName() + "connect", e);
				setState(STATE_ERROR);
				try {
					socket.close();
				} catch (IOException e1) {
					Log.e(TAG,
							HomeControlService.class.getSimpleName() + "close", e1);
					return false;
				}
			}
			setState(STATE_CONNECTED);
			
			receiveThread = new ReceiveThread(this, inStream);
			receiveThread.start();
		}
		return true;
	}

	private void disconnect() {
		if(receiveThread != null
				&& receiveThread.getState() != Thread.State.TERMINATED
				&& !receiveThread.isCanceled()) {
			receiveThread.cancel();
		}
		
		if(inStream != null)
			try {
				inStream.close();
			} catch (IOException e2) {
				Log.e(TAG,
						HomeControlService.class.getSimpleName() + "inStream close", e2);
			}
		if(outStream != null)
			try {
				outStream.close();
			} catch (IOException e1) {
				Log.e(TAG,
						HomeControlService.class.getSimpleName() + "outStream close", e1);
			}
		
		if (socket != null)
			try {
				socket.close();
			} catch (IOException e) {
				Log.e(TAG,
						HomeControlService.class.getSimpleName() + "outStream close", e);
			}
		
		socket = null;
		receiveThread = null;
		device = null;
	}

	/**
	 * @return the handler
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * @param handler
	 *            the handler to set
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * set the state
	 */
	public void setState(int state) {
		this.state = state;

		if (handler != null) {
			Message msg = handler.obtainMessage(MESSAGE_STATE_CHANGE, state, 0);
			msg.sendToTarget();
		}
	}
}
