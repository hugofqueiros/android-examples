package pt.ipp.isep.cma.fragmentsproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FirstFragment extends Fragment implements OnClickListener {

	private Button btnFirst;
	private Button btnSecond;
	
	private OnPersonSelectedListener mListener;
	
	public interface OnPersonSelectedListener {
	    public void onPersonSelected(Person p);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnPersonSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnPersonSelectedListener");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View mContentView = inflater.inflate(R.layout.fragment_first, container, false);
		
		btnFirst = (Button) mContentView.findViewById(R.id.btnFirst);
		btnFirst.setOnClickListener(this);
		
		btnSecond = (Button) mContentView.findViewById(R.id.btnSecond);
		btnSecond.setOnClickListener(this);
		
		return mContentView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnFirst:
			mListener.onPersonSelected(new Person(1, "John", "Doe"));
			break;

		case R.id.btnSecond:
			mListener.onPersonSelected(new Person(2, "Alex", "Maxi"));
			break;
		}
	}	
}