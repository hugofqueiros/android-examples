package pt.ipp.isep.cma.boundedservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

	// Criar uma classe do tipo Binder para depois obter a refer�ncia para a inst�ncia do servi�o
	// deste modo pode-se aceder �s vari�veis e m�todos do servi�o que foi inst�nciado e assim
	// alterar o seu comportamento.
	public class MyServiceBinder extends Binder {
	    public MyService getService() { 
             return MyService.this; 
         }
	}
	
	// Interface necess�ria para haver comunica��o entre ambas as partes
	private final IBinder mBinder = new MyServiceBinder();	
	
	@Override
	public void onCreate() {
		super.onCreate();		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	// N�o esquecer de retornar sempre a interface de modo ao outro componente Android 
	// (por exemplo, Activity) ter acesso ao Binder
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
		
	@Override
	public boolean onUnbind(Intent intent) {
		return true;
	}
	
	// M�todo que ir� ser invocado pela Activity de modo a mostrar que a conex�o entre ambos est� ativa
	public String testServiceConnectivity() {
		return getString(R.string.service_working);
	}
}
