package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableComforts
{
	public static final String 
		TABLE_COMFORTS = "Comodidades",
		COLUMN_NAME = "Nombre",
		COLUMN_ID_COMFORT = "_id_comfort",
		DATABASE_CREATE = "create table " + TABLE_COMFORTS + "("
				+ COLUMN_ID_COMFORT + " integer primary key autoincrement,"
				+ COLUMN_NAME + " text not null"
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
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMFORTS);
		
		onCreate(db);
	}	

}
