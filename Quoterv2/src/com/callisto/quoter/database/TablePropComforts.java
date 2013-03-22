package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropComforts
{
	public static final String 
		TABLE_PROPERTY_COMFORTS = "Propiedades_comodidades",
		DATABASE_CREATE = "create table " + TABLE_PROPERTY_COMFORTS + "("
				+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key, "
				+ TableComforts.COLUMN_ID_COMFORT + " integer not null, "
				+ "FOREIGN KEY(" + TableProperties.COLUMN_ID_PROPERTY + ") REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + "),"
				+ "FOREIGN KEY(" + TableComforts.COLUMN_ID_COMFORT + ") REFERENCES " + TableComforts.TABLE_COMFORTS + "(" + TableComforts.COLUMN_ID_COMFORT + ")"
				+ ");";
		
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableCities.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_COMFORTS);
		
		onCreate(db);
	}

}
