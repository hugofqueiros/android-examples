package pt.ipp.isep.cma.homecontrol;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Classe auxiliar para a descoberta de novos dispositivos Bluetooth através do serviço de Device Discovery
 * 
 * @author DAMAN-CMA @ ISEP
 *
 */
public class BluetoothToolkit {
	
	// private static final String TAG = BluetoothToolkit.class.getSimpleName();
	public static final String TAG = "HomeControl";
	
	/**
	 * Interface para a implementação do listener que é notificado quando
	 * o processo de descoberta termina.
	 * O listener é notificado pelo método discoveryComplete, que recebe
	 * uma lista com todos os dispositivos encontrados.
	 */
	public static interface BluetoothToolkitListener {
		
		public void discoveryComplete(List<BluetoothDevice> devices);
	}
	
	private Context context;
	private BluetoothAdapter adapter;
	
	private BluetoothToolkitListener listener;
	
	// lista para dispositivos descobertos
	private List<BluetoothDevice> discoveredDevices = new ArrayList<BluetoothDevice>();
	// lista para dispositivos emparelhados (bonded)
	private List<BluetoothDevice> pairedDevices = new ArrayList<BluetoothDevice>();

	public BluetoothToolkit(Context context, BluetoothAdapter adapter) {
		Log.i(TAG, BluetoothToolkit.class.getSimpleName() + " BluetoothToolkit ");
		
		this.adapter = adapter;
		this.context = context;
		
		discoveryIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		discoveryIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		discoveryIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	}
	
	public BluetoothToolkit(Context context, BluetoothAdapter adapter, BluetoothToolkitListener listener) {
		this(context, adapter);
		
		Log.i(TAG, BluetoothToolkit.class.getSimpleName() + " BluetoothToolkit ");
		
		this.listener = listener;
	}
	
	/**
	 * Atualiza e devolve a lista de dispositivos emparelhados (bonded).
	 * A instância da lista devolvida é sempre a mesma por cada instância de
	 * BluetoothToolkit.
	 * @return lista atualizada de dispositivos emparelhados (bonded)
	 */
	public List<BluetoothDevice> getPairedDevices() {
		Log.i(TAG, BluetoothToolkit.class.getSimpleName() + " getPairedDevices ");
		pairedDevices.clear();
		pairedDevices.addAll(adapter.getBondedDevices());
		return pairedDevices;
	}
	
	/**
	 * Devolve a lista de dispositivos descobertos. NÃO atualiza a lista!
	 * A instância da lista devolvida é sempre a mesma por cada instância de
	 * BluetoothToolkit.
	 * @return lista de dispositivos descobertos
	 */
	public List<BluetoothDevice> getDiscoveredDevices() {
		Log.i(TAG, BluetoothToolkit.class.getSimpleName() + " getDiscoveredDevices ");
		return discoveredDevices;
	}
	
	/**
	 * Inicia um novo processo de Device Discovery que irá atualizar a lista
	 * de dispositivos descobertos, se iniciado com sucesso.
	 * @return false se o BluetoothAdapter estiver desligado; true se o processo foi iniciado com sucesso
	 */
	public boolean startDeviceDiscovery() {
		Log.i(TAG, BluetoothToolkit.class.getSimpleName() + " startDeviceDiscovery ");
		if(!adapter.isEnabled()) {
			Log.e(TAG, "Cannot start device discovery. Bluetooth adapter is disabled!");
			return false;
		}
		
		// cancela o processo de Device Discovery se o mesmo estiver a decorrer
		if(adapter.isDiscovering())
			adapter.cancelDiscovery();
		
		// regista o broadcast receiver que irá receber os intents relacionados
		// com o processo de device discovery
		context.registerReceiver(discoveryReceiver, discoveryIntentFilter);
		
		// inicia o processo de device discovery
		adapter.startDiscovery();
		return true;
	}
	
	/**
	 * Cancela o processo de Device Discovery, se o mesmo estiver a decorrer.
	 */
	public void cancelDeviceDiscovery() {
		Log.i(TAG, BluetoothToolkit.class.getSimpleName() + " cancelDeviceDiscovery ");
		try {
			// primeiro desregista o broadcast receiver (importante!)
			context.unregisterReceiver(discoveryReceiver);
		} catch (Exception e) {}
		adapter.cancelDiscovery();
	}
	
	// intent filter que define (filtra) os intents associados ao processo de device discovery
	// a definição do intent filter encontra-se no construtor da classe
	private final IntentFilter discoveryIntentFilter = new IntentFilter();
	// broadcast receiver que irá receber intents durante o processo de device discovery
	private BroadcastReceiver discoveryReceiver = new BroadcastReceiver() {		
		@Override
		public void onReceive(Context context, Intent intent) {		
			
			Log.i(TAG, BluetoothToolkit.class.getSimpleName() + " onReceive ");
			
			String action = intent.getAction();
						
			if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				// o processo de descoberta começou
				// limpa o array de dispositivos descobertos
				discoveredDevices.clear();
			}
			else if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				// foi encontrado um novo dispositivo
				// adiciona ao array de dispositivos descobertos
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				discoveredDevices.add(device);
			}
			else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				// o processo de descoberta terminou
				// 1) cancela explicitamente o processo de descoberta
				adapter.cancelDiscovery();
				// 2) desregista o broadcast receiver
				context.unregisterReceiver(this);
				// 3) notifica o listener, enviado a lista de dispositivos descobertos
				if(listener != null) listener.discoveryComplete(discoveredDevices);
			}			
		}
	};

	public BluetoothToolkitListener getListener() {
		Log.i(TAG, BluetoothToolkit.class.getSimpleName() + " getListener ");
		return listener;
	}

	public void setListener(BluetoothToolkitListener listener) {
		Log.i(TAG, BluetoothToolkit.class.getSimpleName() + " setListener ");
		this.listener = listener;
	}

}
