package pt.ipp.isep.cma.preferencesproject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity implements OnClickListener {
	
	public static final String CHECK_PREFERENCE_KEY = "pref_notifications";
	public static final String EDIT_PREFERENCE_KEY = "pref_username";
	public static final String LIST_PREFERENCE_KEY = "pref_notifications_language";
	
	public static final boolean CHECK_PREFERENCE_DEFAULT = true;
	public static final String EDIT_PREFERENCE_DEFAULT = "n/a";
	public static final String LIST_PREFERENCE_DEFAULT = "n/a";

	private TextView txtCheckNotifications;
	private TextView txtEditUsername;
	private TextView txtListLanguage;
	
	private EditText txtUsername;
	private Button btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtCheckNotifications = (TextView) findViewById(R.id.txtCheckNotifications);
		txtEditUsername = (TextView) findViewById(R.id.txtEditUsername);
		txtListLanguage = (TextView) findViewById(R.id.txtListLanguage);
		
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();		
		// Obter a referência para as preferências da aplicação
		SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);
		
		// Preencher as caixas de texto com os valores guardados nas preferências da aplicação
		txtCheckNotifications.setText(mSettings.getBoolean(CHECK_PREFERENCE_KEY, CHECK_PREFERENCE_DEFAULT) + "");
		txtEditUsername.setText(mSettings.getString(EDIT_PREFERENCE_KEY, EDIT_PREFERENCE_DEFAULT));
		txtListLanguage.setText(mSettings.getString(LIST_PREFERENCE_KEY, LIST_PREFERENCE_DEFAULT));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		menu.add(0, 0, 0, R.string.preferences_menu);		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId() == 0)
			startActivity(new Intent(this, PreferencesActivity.class));			

		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnSave && txtUsername.getText()!=null) {
			// Obter a referência para as preferências assim como para o Editor das mesmas.
			SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor mEditor = mSettings.edit();
			
			// Guardar a String com o username na preferência da aplicação respectiva.
			// Não esquecer de invocar o método commit() para guardar o registo.
			mEditor.putString(EDIT_PREFERENCE_KEY, txtUsername.getText().toString());
			mEditor.commit();
			
			txtEditUsername.setText(mSettings.getString(EDIT_PREFERENCE_KEY, EDIT_PREFERENCE_DEFAULT));
		}					
	}
}
 