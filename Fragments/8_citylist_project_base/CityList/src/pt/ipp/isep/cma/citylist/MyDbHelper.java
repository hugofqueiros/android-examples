package pt.ipp.isep.cma.citylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "CITYLIST_DATABASE";
	
    private static final String DATABASE_NAME = "citylist.db";
    private static final int DATABASE_VERSION = 1;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(Global.TAG, MyDbHelper.class.getSimpleName() + ": MyDbHelper");
    }

    // invocado quando a base de dados não existe no disco
    // !!! APÓS MODIFICAÇÕES NESTE MÉTODO, É NECESSÁRIO MUDAR O
    // VALOR DE DATABASE_VERSION PARA QUE AS ALTERAÇÕES TENHAM EFEITO
    @Override
    public void onCreate(SQLiteDatabase _db) {
        Log.w(Global.TAG, MyDbHelper.class.getSimpleName() + ": Creating Database...");
        _db.execSQL("CREATE TABLE tbl_place (id_place INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(75) NOT NULL, latitude FLOAT NOT NULL, longitude FLOAT NOT NULL, country VARCHAR(75) NOT NULL, description TEXT);");
        init(_db);
    }

    // inicializa a base de dados
    // !!! APÓS MODIFICAÇÕES NESTE MÉTODO, É NECESSÁRIO MUDAR O
    // VALOR DE DATABASE_VERSION PARA QUE AS ALTERAÇÕES TENHAM EFEITO
    protected void init(SQLiteDatabase _db) {
        Log.w(Global.TAG, MyDbHelper.class.getSimpleName() + ": Initializing database...");
        
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Setubal', 38.5244, -8.8882, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Santarem', 39.23333, -8.68333, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Portalegre', 39.29379, -7.43122, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Lisbon', 38.71667, -9.13333, 'Portugal', 'Lisbon is the capital city and largest city of Portugal with a population of 547,631 within its administrative limits on a land area of 84.8 km2 (33 sq mi). The urban area of Lisbon extends beyond the administrative city limits with a population of over 3 million on an area of 958 km2 (370 sq mi), making it the 11th most populous urban area in the European Union.')");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Leiria', 39.74362, -8.80705, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Funchal', 32.63333, -16.9, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Faro', 37.01667, -7.93333, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Evora', 38.56667, -7.9, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Castelo Branco', 39.81667, -7.5, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Viseu', 40.65, -7.91667, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Vila Real', 41.3, -7.75, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Vila Nova de Gaia', 41.13333, -8.61667, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Viana do Castelo', 41.7, -8.83333, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Porto', 41.15, -8.61667, 'Portugal', 'Porto, also known as Oporto in English, is the second-largest city in Portugal, after Lisbon, and one of the major urban areas in Southern Europe and the capital of second major great urban area in Portugal.')");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Coimbra', 40.2, -8.41667, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Braganca', 41.81667, -6.75, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Braga', 41.55, -8.43333, 'Portugal', null)");
        _db.execSQL("INSERT INTO tbl_place(name, latitude, longitude, country, description) VALUES ('Aveiro', 40.63333, -8.65, 'Portugal', null)");

    }

    // elimina a base de dados
    // !!! APÓS MODIFICAÇÕES NESTE MÉTODO, É NECESSÁRIO MUDAR O
    // VALOR DE DATABASE_VERSION PARA QUE AS ALTERAÇÕES TENHAM EFEITO
    protected void dropAll(SQLiteDatabase _db) {
        Log.w(Global.TAG, MyDbHelper.class.getSimpleName() + ": Droping Database...");
        _db.execSQL("DROP TABLE IF EXISTS tbl_place;");
    }

    // invocado quando existe uma base de dados que não corresponde
    // à versão actual
    // !!! APÓS MODIFICAÇÕES NESTE MÉTODO, É NECESSÁRIO MUDAR O
    // VALOR DE DATABASE_VERSION PARA QUE AS ALTERAÇÕES TENHAM EFEITO
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
            int _newVersion) {
        Log.w(Global.TAG, MyDbHelper.class.getSimpleName() + ": Upgrading from version " + _oldVersion + " to " 
                + _newVersion + ", which will destroy all old data.");

        dropAll(_db);
        onCreate(_db);
    }
}

