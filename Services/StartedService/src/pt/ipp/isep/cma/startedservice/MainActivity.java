package pt.ipp.isep.cma.startedservice;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

	public static final String INTENT_EXTRA = "text_to_send";
	
    private Button btnService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnService = (Button) findViewById(R.id.btnService);
		btnService.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnService) {
			// Iniciar o serviço e enviar um extra para o mesmo com um texto.
			Intent mIntent = new Intent(this, MyService.class);
			mIntent.putExtra(INTENT_EXTRA, "TEXTO");
			startService(mIntent);
		}
	}
}
