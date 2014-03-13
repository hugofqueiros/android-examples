package pt.ipp.isep.cma.countrylist;

import java.util.ArrayList;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity implements OnClickListener {
	
	private CountryAdapter mAdapter;
	private ArrayList<Country> countries = new ArrayList<Country>();
	private AlertDialog mDialog;	
	private int selected = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAdapter = new CountryAdapter(this, countries);
		setListAdapter(mAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		loadCountries();
		mAdapter.notifyDataSetChanged();
	}
	
	private void loadCountries() {		
		countries.clear();
	
		countries.add(new Country(0, "Portugal", "Europe"));
		countries.add(new Country(1, "Spain", "Europe"));
		countries.add(new Country(2, "Germany", "Europe"));
		countries.add(new Country(3, "Netherlands", "Europe"));
		countries.add(new Country(4, "Norway", "Europe"));
		countries.add(new Country(5, "Italy", "Europe"));
		countries.add(new Country(6, "Greece", "Europe"));
		countries.add(new Country(7, "Poland", "Europe"));
		countries.add(new Country(8, "USA", "America"));
		countries.add(new Country(9, "Brazil", "America"));
		countries.add(new Country(10, "Venezuela", "America"));
		countries.add(new Country(11, "Chile", "America"));
		countries.add(new Country(12, "Mexico", "America"));
		countries.add(new Country(13, "Cuba", "America"));
		countries.add(new Country(14, "Argentina", "America"));
		countries.add(new Country(15, "Niger", "Africa"));
		countries.add(new Country(16, "Angola", "Africa"));
		countries.add(new Country(17, "Egypt", "Africa"));
		countries.add(new Country(18, "South Africa", "Africa"));
		countries.add(new Country(19, "Mozambique", "Africa"));
		countries.add(new Country(20, "Japan", "Asia"));
		countries.add(new Country(21, "Indonesia", "Asia"));
		countries.add(new Country(22, "Russia", "Asia"));	
		countries.add(new Country(23, "South Korea", "Asia"));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.dialog_message_part1) + " " + countries.get(position).getName() + " " + getString(R.string.dialog_message_part2));
		builder.setTitle(R.string.dialog_title);
		builder.setPositiveButton(R.string.dialog_pos_button, this);
		
		mDialog = builder.create();
		mDialog.show();
		
		selected = position;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {

		if(which == DialogInterface.BUTTON_POSITIVE && selected != -1) {
			countries.remove(selected);
			selected = -1;
			mAdapter.notifyDataSetChanged();
		}
		
		if(which == DialogInterface.BUTTON_NEGATIVE) {
			selected = -1;
			mDialog.dismiss();
		}		
	}
}