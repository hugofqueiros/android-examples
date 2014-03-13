package pt.ipp.isep.cma.countrylist;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CountryAdapter extends ArrayAdapter<Country> {

	private ArrayList<Country> countries;
	private Context context;
	
	public CountryAdapter(Context mContext, ArrayList<Country> objects) {
		super(mContext,R.layout.row_layout,objects);
		
		this.countries = objects;
		this.context = mContext;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		View v = convertView;
		if(v == null) {
			LayoutInflater vi = (LayoutInflater) 
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_layout, null);
		}
		
		TextView txtname = (TextView) v.findViewById(R.id.txtName);
		TextView txtContinent = (TextView) v.findViewById(R.id.txtContinent);
		
		txtname.setText(countries.get(position).getName());
		txtContinent.setText(countries.get(position).getContinent());
		
		return v;
	}
}













