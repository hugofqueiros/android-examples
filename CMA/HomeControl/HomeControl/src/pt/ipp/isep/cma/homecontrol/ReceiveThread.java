/**
 * 
 */
package pt.ipp.isep.cma.homecontrol;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

/**
 * @author Zhao
 * 
 */
public class ReceiveThread extends Thread {
	public static final String TAG = "HomeControl";

	private HomeControlService service;

	private boolean canceled = false;
	private int errorCount = 0;
	private InputStream mInputStream;

	public ReceiveThread(HomeControlService service, InputStream inputStrem) {
		this.service = service;
		this.mInputStream = inputStrem;
	}

	public void run() {
		byte[] msg = new byte[4];

		while (errorCount < 3 && !canceled) {
			try {

				for (int i = 0; i < 4 && !canceled; ++i) {
					byte b;
					b = (byte) mInputStream.read();
					msg[i] = b;
				}

				if (!canceled) {
					// TODO enviar para o service
				}
				errorCount = 0;
			} catch (IOException e) {
				++errorCount;
				Log.e(TAG, "Error", e);
				// e.printStackTrace();
			}
			
			// TODO notificar o service que acabou a thread
		}
	}

	public void cancel() {
		canceled = true;
	}

	/**
	 * @return the canceled
	 */
	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * @param canceled
	 *            the canceled to set
	 */
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

}
