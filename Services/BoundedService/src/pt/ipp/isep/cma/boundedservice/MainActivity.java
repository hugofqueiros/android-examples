package pt.ipp.isep.cma.boundedservice;

import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

public class MainActivity extends Activity implements OnClickListener {

    private MyService mService;
    
    private TextView txtServiceStatus;
    private Button btnService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtServiceStatus = (TextView) findViewById(R.id.txtServiceStatus);
		btnService = (Button) findViewById(R.id.btnService);
		btnService.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// Fazer o bind ao servi�o onde se tem de enviar o objecto mConnection que ser� o respons�vel por 
		// receber notifica��es sobre o estado da conex�o entre a Activity e o Servi�o
		bindService(new Intent(this, MyService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		// N�o esquecer de fazer o unbind do servi�o, sen�o os outros componentes n�o podem
		// fazer bind, enquanto este o estiver a fazer.
		unbindService(mConnection);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {

		// Quando a conex�o ao servi�o � estabelecida este m�todo � invocado
		// o qual envia a Interface IBinder que � retornada pelo m�todo onBind do servi�o
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = ((MyService.MyServiceBinder) service).getService();
			txtServiceStatus.setText(R.string.service_active);
		}

		// Quando a conex�o ao servi�o � terminada este m�todo � invocado.
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
			txtServiceStatus.setText(R.string.service_not_active);
		}		
	};

	@Override
	public void onClick(View v) {

		if(v.getId() == R.id.btnService) {
			if(mService != null) {
				// Invoca��o do m�todo existente no servi�o para obter a String que ele retorna
				String text = mService.testServiceConnectivity();
				Toast.makeText(this, text, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, R.string.service_not_active, Toast.LENGTH_LONG).show();
			}
		}		
	}
}
