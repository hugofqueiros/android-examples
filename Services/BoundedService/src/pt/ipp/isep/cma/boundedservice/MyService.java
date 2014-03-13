package pt.ipp.isep.cma.boundedservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

	// Criar uma classe do tipo Binder para depois obter a referência para a instância do serviço
	// deste modo pode-se aceder às variáveis e métodos do serviço que foi instânciado e assim
	// alterar o seu comportamento.
	public class MyServiceBinder extends Binder {
	    public MyService getService() { 
             return MyService.this; 
         }
	}
	
	// Interface necessária para haver comunicação entre ambas as partes
	private final IBinder mBinder = new MyServiceBinder();	
	
	@Override
	public void onCreate() {
		super.onCreate();		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	// Não esquecer de retornar sempre a interface de modo ao outro componente Android 
	// (por exemplo, Activity) ter acesso ao Binder
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
		
	@Override
	public boolean onUnbind(Intent intent) {
		return true;
	}
	
	// Método que irá ser invocado pela Activity de modo a mostrar que a conexão entre ambos está ativa
	public String testServiceConnectivity() {
		return getString(R.string.service_working);
	}
}
