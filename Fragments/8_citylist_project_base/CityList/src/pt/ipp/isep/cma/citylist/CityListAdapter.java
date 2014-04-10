package pt.ipp.isep.cma.citylist;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CityListAdapter extends ArrayAdapter<City> {

    // array com os elementos da lista
    private final ArrayList<City> cities;
    private final Context context;

    public CityListAdapter(Context context, ArrayList<City> objects) {
        super(context, R.layout.row_layout, objects);
        
        Log.i(Global.TAG, CityListAdapter.class.getSimpleName() + ": CityListAdapter : size: "  + objects.size());

        this.cities = objects;
        this.context = context;
    }

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	
        Log.i(Global.TAG, CityListAdapter.class.getSimpleName() + ": getView");

        // preenche a view da cidade com os elementos do layout "row_layout" 
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_layout, null);
        }

        City city = cities.get(position);

        if (city != null) {
            // obtem os elementos da view que contêm o nome e país...
            TextView txtName = (TextView) v.findViewById(R.id.txtCityName);
            TextView txtCountry = (TextView) v
                    .findViewById(R.id.txtCountryName);

            // ... e define os seus valores
            txtName.setText(city.getName());
            txtCountry.setText(city.getCountry());
        }

        return v;
    }
}
