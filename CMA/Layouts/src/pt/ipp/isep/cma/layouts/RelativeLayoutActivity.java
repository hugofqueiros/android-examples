package pt.ipp.isep.cma.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RelativeLayoutActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.relative_activity);
	    
		Button mButton = (Button) findViewById(R.id.buttonRelativeLayout);
		mButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.buttonRelativeLayout)
	        startActivity(new Intent(this, TableLayoutActivity.class));	
	}
}
