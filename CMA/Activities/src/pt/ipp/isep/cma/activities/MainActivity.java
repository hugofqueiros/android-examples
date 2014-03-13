package pt.ipp.isep.cma.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	  private final String TAG = "HELLO_ANDROID";
	  private Button myButtonToShowMessage, myButtonToOpenNewActivity;
	  private EditText myEditText;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_main);

		  // obtém a instância da EditText definida no XML do layout
		  myEditText = (EditText) findViewById(R.id.myEditText);
	    
		  myButtonToShowMessage = (Button) findViewById(R.id.myButtonToShowMessage);
		  myButtonToShowMessage.setOnClickListener(this);

		  myButtonToOpenNewActivity = (Button) findViewById(R.id.myButtonToOpenNewActivity);
		  myButtonToOpenNewActivity.setOnClickListener(this);

		  Log.i(getString(R.string.app_name), TAG + " - onCreate()");
	  }

	  @Override
	  public void onClick(View v) {
		  // executado quando o utilizador clica no myButtonToOpenNewActivity
		  if (v.getId() == R.id.myButtonToOpenNewActivity) {
	    	  Intent intentToOpenNewActivity = new Intent(this, NewActivity.class);
	    	  intentToOpenNewActivity.putExtra("MyText", myEditText.getText().toString());
	          startActivity(intentToOpenNewActivity);
		  }

	      // cria e apresenta a toast message com o texto da myEditText
	      if (v.getId() == R.id.myButtonToShowMessage) {
	    	  Toast toast = Toast.makeText(this, myEditText.getText(), Toast.LENGTH_SHORT);
	          toast.show();
	      }
	  }
}
