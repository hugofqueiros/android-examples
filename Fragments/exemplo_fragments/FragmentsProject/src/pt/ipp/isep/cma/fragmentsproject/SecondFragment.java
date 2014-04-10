package pt.ipp.isep.cma.fragmentsproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {

	private TextView txtPersonId;
	private TextView txtPersonFirstName;
	private TextView txtPersonLastname;
	
	private Person mPerson;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,	Bundle savedInstanceState) {

		View mContentView = inflater.inflate(R.layout.fragment_second, container, false);
		
		txtPersonId = (TextView) mContentView.findViewById(R.id.txtPersonId);
		txtPersonFirstName = (TextView) mContentView.findViewById(R.id.txtPersonFirstName);
		txtPersonLastname = (TextView) mContentView.findViewById(R.id.txtPersonLastName);
		
		return mContentView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updatePerson();
	}
	
	public void setPerson(Person p) {
		this.mPerson = p;
		updatePerson();
	}
	
	public void updatePerson() {
		if(txtPersonId != null && txtPersonFirstName != null && txtPersonLastname != null && mPerson != null) {
			txtPersonId.setText(mPerson.getId() + "");
			txtPersonFirstName.setText(mPerson.getFirstName());
			txtPersonLastname.setText(mPerson.getLastName());
		}
	}
}