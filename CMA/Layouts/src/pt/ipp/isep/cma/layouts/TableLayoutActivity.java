package pt.ipp.isep.cma.layouts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TableLayoutActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.table_activity);
	    
		Button mButton = (Button) findViewById(R.id.buttonTableLayout);
		mButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.buttonTableLayout)
	        TableLayoutActivity.this.finish();	
	}
}
