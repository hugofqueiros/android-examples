package pt.ipp.isep.cma.databaseproject;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private EditText txtFirstName;
	private EditText txtLastName;
	private Button btnSavePerson;
	private Spinner spnPersons;
	private Button btnDeletePerson;
	
	private MyDbHelper dbHelper;
	private ArrayList<Person> mList;
	private ArrayAdapter<Person> dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new MyDbHelper(this);
		mList = new ArrayList<Person>();
		// Obtem as pessoas existentes na base de dados e preenche o ArrayList com essa informação
		getPersonNames();
		
		txtFirstName = (EditText) findViewById(R.id.txtFirstName);
		txtLastName = (EditText) findViewById(R.id.txtLastName);
		
		btnSavePerson = (Button) findViewById(R.id.btnSave);
		btnSavePerson.setOnClickListener(this);
		
		spnPersons = (Spinner) findViewById(R.id.spnPersons);
		dataAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_spinner_item, mList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnPersons.setAdapter(dataAdapter);
		
		btnDeletePerson = (Button) findViewById(R.id.btnRemove);
		btnDeletePerson.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {
		case R.id.btnSave:			
			String fName = txtFirstName.getText().toString();
			String lName = txtLastName.getText().toString();
			
			// Se existir algum nome escrito nas caixas de texto
			if(fName.length() > 0 && lName.length() > 0) {				
				SQLiteDatabase db = dbHelper.getWritableDatabase();

				// Criar o ContentValues com os dados a inserir na base de dados
				ContentValues cv = new ContentValues();
				cv.put(MyDbHelper.SECOND_COLUMN, fName);
				cv.put(MyDbHelper.THIRD_COLUMN, lName);
				
				// Inserir os dados na base de dados.
				long rowId = db.insert(MyDbHelper.TABLE_NAME, null, cv);
				if(rowId < 0)
					Toast.makeText(this, getString(R.string.error_insert), Toast.LENGTH_LONG).show();
				
				db.close();
				
				// Atualizar a Spinner com os novos dados
				getPersonNames();
				dataAdapter.notifyDataSetChanged();
			}			
			break;
		case R.id.btnRemove:
			SQLiteDatabase db = dbHelper.getWritableDatabase();
						
			// Obter o ID da pessoa a remover. Este é obtido através da posição que está 
			// seleccionada na Spinner. Através desta posição é obtida a pessoa correspondente no ArrayList
			String[] params = { String.valueOf(mList.get(spnPersons.getSelectedItemPosition()).getId()) };		
			long rowsAffected = db.delete(MyDbHelper.TABLE_NAME, "id_person = ?", params);
			if (rowsAffected < 0)
				Toast.makeText(this, getString(R.string.error_delete), Toast.LENGTH_LONG).show();

			db.close();		
			
			// Atualizar a Spinner com os novos dados
			getPersonNames();
			dataAdapter.notifyDataSetChanged();
			break;
		}		
	}
	
	private void getPersonNames() {		
		// Limpar o ArrayList
		mList.clear();
		
		// Realizar a query à base de dados
		SQLiteDatabase db = dbHelper.getReadableDatabase();		
		Cursor c = db.query(MyDbHelper.TABLE_NAME, MyDbHelper.COLUMNS, null, null, null, null, MyDbHelper.SECOND_COLUMN + " ASC");
		//Cursor c = db.rawQuery("SELECT * FROM " + MyDbHelper.TABLE_NAME, null);
		
		// Caso existam dados retornados pela query, estes são adicionados ao ArrayList.
		if(c!= null && c.moveToFirst()) {
			do {
				mList.add(new Person(c.getInt(0), c.getString(1), c.getString(2)));
			} while(c.moveToNext());
		}
		
		db.close();
	}
}
