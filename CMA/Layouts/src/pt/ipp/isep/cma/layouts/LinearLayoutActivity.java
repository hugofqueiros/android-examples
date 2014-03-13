package pt.ipp.isep.cma.layouts;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class LinearLayoutActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button mButton = (Button) findViewById(R.id.buttonLinearLayout);
		mButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.buttonLinearLayout)
	        startActivity(new Intent(this, RelativeLayoutActivity.class));	
	}
}
