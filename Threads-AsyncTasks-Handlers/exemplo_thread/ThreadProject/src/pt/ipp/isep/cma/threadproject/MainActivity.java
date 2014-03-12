package pt.ipp.isep.cma.threadproject;

import java.lang.Thread.State;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	private EditText txtMaxNumber;
	private Button btnExecuteTask, btnCancelTask;
	private TextView txtCurrentNumber;
	
	private MyThread mTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtMaxNumber = (EditText) findViewById(R.id.txtMaxNumber);
		
		btnExecuteTask = (Button) findViewById(R.id.btnAction);
		btnExecuteTask.setOnClickListener(this);
		
		btnCancelTask = (Button) findViewById(R.id.btnCancel);
		btnCancelTask.setOnClickListener(this);
		
		txtCurrentNumber = (TextView) findViewById(R.id.txtCurrentNumber);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnAction) {
			mTask = new MyThread(getApplicationContext(), txtCurrentNumber);
			mTask.execute(Integer.valueOf(txtMaxNumber.getText().toString()));
		} else if(v.getId() == R.id.btnCancel) {
			if(mTask != null && mTask.getState() != State.TERMINATED)
				mTask.cancel(true);
		}
	}
}
