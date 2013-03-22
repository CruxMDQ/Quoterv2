package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableCities
{
	public static final String TABLE_CITIES = "Ciudades",
			COLUMN_ID_CITY = "_id_city",
			COLUMN_NAME = "Nombre";
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_CITIES
			+ "("
			+ COLUMN_ID_CITY + " integer primary key autoincrement, "
			+ COLUMN_NAME
			+ " text not null"
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
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
		
		onCreate(db);
	}
}
