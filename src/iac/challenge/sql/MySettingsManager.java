package iac.challenge.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MySettingsManager {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "user.db";

	private static final String TABLE_SETTINGS = "settings";
	private static final String COL_ID = "id";
	private static final int NUM_COL_ID = 0;
	private static final String COL_MODE = "mode";
	private static final int NUM_COL_MODE = 1;
	private static final String COL_LIGHT = "light";
	private static final int NUM_COL_LIGHT = 2;
	private static final String COL_VOLUME = "volume";
	private static final int NUM_COL_VOLUME = 3;
	private static final String COL_SOUND = "sound";
	private static final int NUM_COL_SOUND = 4;

	private SQLiteDatabase bdd;

	private AlarmDB maBaseSQLite;

	public MySettingsManager(Context context) {
		// On cr�er la BDD et sa table
		maBaseSQLite = new AlarmDB(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open() {
		// on ouvre la BDD en �criture
		bdd = maBaseSQLite.getWritableDatabase();
	}

	public void close() {
		// on ferme l'acc�s � la BDD

		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public long saveSettings(Setting sets) {
		clear();
		// Cr�ation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		// on lui ajoute une valeur associ� � une cl� (qui est le nom de la
		// colonne dans laquelle on veut mettre la valeur)

		values.put(COL_ID, sets.getId());
		values.put(COL_MODE, sets.getMode());
		values.put(COL_LIGHT, sets.getLight());
		values.put(COL_VOLUME, sets.getVolume());
		values.put(COL_SOUND, sets.getSound());
		// on ins�re l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_SETTINGS, null, values);
	}

	public Setting getSettings() {
		// R�cup�re dans un Cursor les valeur correspondant � un user contenu
		// dans la BDD (ici on s�lectionne le user gr�ce � son login et pass)
		Cursor c = bdd.query(TABLE_SETTINGS, new String[] { COL_ID, COL_MODE,
				COL_LIGHT, COL_VOLUME, COL_SOUND }, null, null, null, null,
				null, null);
		return cursorToSettings(c);
	}

	public void clear() {
		bdd.delete(TABLE_SETTINGS, null, null);
	}

	// Cette m�thode permet de convertir un cursor en un user
	private Setting cursorToSettings(Cursor c) {
		// si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
		if (c.getCount() == 0)
			return null;

		// Sinon on se place sur le premier �l�ment
		c.moveToFirst();
		// On cr�� un user
		Setting a = new Setting();
		// on lui affecte toutes les infos gr�ce aux infos contenues dans le
		// Cursor
		a.setId(c.getInt(NUM_COL_ID));
		a.setMode(c.getInt(NUM_COL_MODE));
		a.setLight(c.getInt(NUM_COL_LIGHT));
		a.setVolume(c.getInt(NUM_COL_VOLUME));
		a.setSound(c.getInt(NUM_COL_SOUND));
		// On ferme le cursor
		c.close();

		// On retourne le user
		return a;
	}

}
