package iac.challenge.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyAlarmManager {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "user.db";

	private static final String TABLE_ALARM = "alarm";
	private static final String COL_ID = "id";
	private static final int NUM_COL_ID = 0;
	private static final String COL_HOUR = "hour";
	private static final int NUM_COL_HOUR = 1;
	private static final String COL_MIN = "min";
	private static final int NUM_COL_MIN = 2;
	private static final String COL_ACTIVE = "active";
	private static final int NUM_COL_ACTIVE = 3;

	private SQLiteDatabase bdd;

	private AlarmDB maBaseSQLite;

	public MyAlarmManager(Context context) {
		// On créer la BDD et sa table
		maBaseSQLite = new AlarmDB(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open() {
		// on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}

	public void close() {
		// on ferme l'accès à la BDD
		
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public long saveAlarm(Alarm alarm) {
		clear();
		// Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		// on lui ajoute une valeur associé à une clé (qui est le nom de la
		// colonne dans laquelle on veut mettre la valeur)

		values.put(COL_ID, alarm.getId());
		values.put(COL_HOUR, alarm.getHour());
		values.put(COL_MIN, alarm.getMin());
		values.put(COL_ACTIVE, alarm.getActive());
		// on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_ALARM, null, values);
	}

	public Alarm getAlarm() {
		// Récupère dans un Cursor les valeur correspondant à un user contenu
		// dans la BDD (ici on sélectionne le user grâce à son login et pass)
		Cursor c = bdd.query(TABLE_ALARM, new String[] { COL_ID, COL_HOUR,
				COL_MIN, COL_ACTIVE }, null, null, null, null, null, null);
		return cursorToAlarm(c);
	}

	public void clear() {
		bdd.delete(TABLE_ALARM, null, null);
		}

	// Cette méthode permet de convertir un cursor en un user
	private Alarm cursorToAlarm(Cursor c) {
		// si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;

		// Sinon on se place sur le premier élément
		c.moveToFirst();
		// On créé un user
		Alarm a = new Alarm();
		// on lui affecte toutes les infos grâce aux infos contenues dans le
		// Cursor
		a.setId(c.getInt(NUM_COL_ID));
		a.setHour(c.getInt(NUM_COL_HOUR));
		a.setMin(c.getInt(NUM_COL_MIN));
		a.setActive(c.getInt(NUM_COL_ACTIVE));
		// On ferme le cursor
		c.close();

		// On retourne le user
		return a;
	}

}
