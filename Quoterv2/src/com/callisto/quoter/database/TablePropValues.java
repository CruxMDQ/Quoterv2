package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropValues
{
	public static final String 
			TABLE_PROPERTY_VALUES = "Propiedades_valores",
			COLUMN_VALUE_PARCEL_SQMT = "Valor_lote_metro",
			COLUMN_VALUE_BUILT_SQMT = "Valor_edif_metro",
			COLUMN_VALUE_FINAL = "Valor_final",
			DATABASE_CREATE = "CREATE TABLE " + TABLE_PROPERTY_VALUES + "("
					+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key, "
					+ COLUMN_VALUE_PARCEL_SQMT + " integer,"
					+ COLUMN_VALUE_BUILT_SQMT + " integer,"
					+ COLUMN_VALUE_FINAL + " integer not null,"
					+ "FOREIGN KEY(" + TableProperties.COLUMN_ID_PROPERTY + ") REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + ")"
					+ ");";
	
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableNbh.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_VALUES);
		
		onCreate(db);
	}
	

}
