package pt.ipp.isep.cma.countrylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "COUNTRYLIST_DATABASE";

    private static final String DATABASE_NAME = "countrylist.db";
    private static final int DATABASE_VERSION = 1;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // invocado quando a base de dados não existe no disco
    // !!! APÓS MODIFICAÇÕES NESTE MÉTODO, É NECESSÁRIO MUDAR O
    // VALOR DE DATABASE_VERSION PARA QUE AS ALTERAÇÕES TENHAM EFEITO
    @Override
    public void onCreate(SQLiteDatabase _db) {
        Log.w(TAG, "Creating Database...");
        _db.execSQL("CREATE TABLE tbl_country (id_country INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(75) NOT NULL, continent VARCHAR(30));");
        init(_db);
    }

    // inicializa a base de dados
    // !!! APÓS MODIFICAÇÕES NESTE MÉTODO, É NECESSÁRIO MUDAR O
    // VALOR DE DATABASE_VERSION PARA QUE AS ALTERAÇÕES TENHAM EFEITO
    protected void init(SQLiteDatabase _db) {
        Log.w(TAG, "Initializing database...");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Portugal', 'Europe');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Spain', 'Europe');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Germany', 'Europe');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Netherlands', 'Europe');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Norway', 'Europe');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Italy', 'Europe');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Greece', 'Europe');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Poland', 'Europe');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('USA', 'America');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Brazil', 'America');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Venezuela', 'America');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Chile', 'America');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Mexico', 'America');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Cuba', 'America');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Argentina', 'America');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Niger', 'Africa');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Angola', 'Africa');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Egypt', 'Africa');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('South Africa', 'Africa');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Mozambique', 'Africa');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Japan', 'Asia');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Indonesia', 'Asia');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('Russia', 'Asia');");
        _db.execSQL("INSERT INTO tbl_country(name, continent) VALUES ('South Korea', 'Asia');");
    }

    // elimina a base de dados
    // !!! APÓS MODIFICAÇÕES NESTE MÉTODO, É NECESSÁRIO MUDAR O
    // VALOR DE DATABASE_VERSION PARA QUE AS ALTERAÇÕES TENHAM EFEITO
    protected void dropAll(SQLiteDatabase _db) {
        Log.w(TAG, "Droping Database...");
        _db.execSQL("DROP TABLE IF EXISTS tbl_country;");
    }

    // invocado quando existe uma base de dados que não corresponde
    // à versão actual
    // !!! APÓS MODIFICAÇÕES NESTE MÉTODO, É NECESSÁRIO MUDAR O
    // VALOR DE DATABASE_VERSION PARA QUE AS ALTERAÇÕES TENHAM EFEITO
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
            int _newVersion) {
        Log.w(TAG, "Upgrading from version " + _oldVersion + " to " 
                + _newVersion + ", which will destroy all old data.");

        dropAll(_db);
        onCreate(_db);
    }
}
