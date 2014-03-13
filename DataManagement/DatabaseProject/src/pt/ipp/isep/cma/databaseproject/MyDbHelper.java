package pt.ipp.isep.cma.databaseproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    // Nome e versão da base de dados
    private static final String DATABASE_NAME = "databaseproject.db";
    private static final int DATABASE_VERSION = 1;
    
    // Campos e nome da tabela
    public static final String TABLE_NAME = "tbl_person";
    public static final String FIRST_COLUMN = "id_person";
    public static final String SECOND_COLUMN = "fname";
    public static final String THIRD_COLUMN = "lname";    
    public static final String[] COLUMNS = {FIRST_COLUMN, SECOND_COLUMN, THIRD_COLUMN};

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    
    @Override
    public void onCreate(SQLiteDatabase _db) {
        // Criar a tabela na base de dados e inserir duas pessoas
        _db.execSQL("CREATE TABLE tbl_person (id_person INTEGER PRIMARY KEY AUTOINCREMENT, fname VARCHAR(20) NOT NULL, lname VARCHAR(20) NOT NULL);");
        _db.execSQL("INSERT INTO tbl_person(fname, lname) VALUES ('John', 'Doe');");
        _db.execSQL("INSERT INTO tbl_person(fname, lname) VALUES ('Alex', 'Maxi');");
    }

    protected void dropAll(SQLiteDatabase _db) {
    	// Remove a tabela da base de dados
        _db.execSQL("DROP TABLE IF EXISTS tbl_person;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
    	// Invocar o método que elimina as tabelas da base de dados
        dropAll(_db);
        // Invocar o método que cria as tabelas na base de dados e inicializa os dados
        onCreate(_db);
    }
}
