package pt.ipp.isep.cma.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NewActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.new_activity_layout);

	    TextView myNewTextView = (TextView) findViewById(R.id.myNewTextView);
	    myNewTextView.setText(getIntent().getExtras().getString("MyText"));
	}

}
