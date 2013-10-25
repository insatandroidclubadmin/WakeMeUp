package iac.challenge.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDB extends SQLiteOpenHelper {

	private static final String TABLE_ALARM = "alarm";
	private static final String COL_ID = "id";
	private static final String COL_HOUR = "hour";
	private static final String COL_MIN = "min";
	private static final String COL_ACTIVE = "active";

	private static final String TABLE_SETTINGS = "settings";
	private static final String COL_MODE = "mode";
	private static final String COL_LIGHT = "light";
	private static final String COL_VOLUME = "volume";
	private static final String COL_SOUND = "sound";

	private static final String CREATE_TABLE_ALARM = "CREATE TABLE "
			+ TABLE_ALARM + " (" + COL_ID + " INTEGER PRIMARY KEY NOT NULL, "
			+ COL_HOUR + " INTEGER NOT NULL, " + COL_MIN
			+ " INTEGER NOT NULL, " + COL_ACTIVE + " INTEGER NOT NULL" + " );";
	private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE "
			+ TABLE_SETTINGS + " (" + COL_ID
			+ " INTEGER PRIMARY KEY NOT NULL, "+ COL_MODE
			+ " INTEGER NOT NULL, " + COL_LIGHT
			+ " INTEGER NOT NULL, " + COL_VOLUME + " INTEGER NOT NULL, "
			+ COL_SOUND + " INTEGER NOT NULL" + " );";

	public AlarmDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// on créé la table à partir de la requête écrite dans la variable
		// CREATE_BDD
		db.execSQL(CREATE_TABLE_ALARM);
		db.execSQL(CREATE_TABLE_SETTINGS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table
		// et de la recréer
		// comme ça lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + TABLE_ALARM + ";");
		db.execSQL("DROP TABLE " + TABLE_SETTINGS + ";");
		onCreate(db);
	}

}