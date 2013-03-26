package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropRooms
{
	public static final String
		TABLE_PROPERTY_ROOMS = "Propiedades_ambientes",
		DATABASE_CREATE = "create table " + TABLE_PROPERTY_ROOMS + "("
			+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key,"
			+ TableRooms.COLUMN_ID_ROOM + " integer not null,"
			+ " FOREIGN KEY" + "(" + TableProperties.COLUMN_ID_PROPERTY + ")" + " REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + "),"
			+ " FOREIGN KEY" + "(" + TableRooms.COLUMN_ID_ROOM + ")" + " REFERENCES " + TableRooms.TABLE_ROOMS + "(" + TableRooms.COLUMN_ID_ROOM + ")"
			+ ");";

	public static final String DATABASE_SELECT = 
			"SELECT " + TableProperties.COLUMN_ID_PROPERTY + " AS _id, "
			+ TableRooms.COLUMN_ID_ROOM + ", "
			+ " FROM " + TABLE_PROPERTY_ROOMS + ";";
	
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableCities.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_ROOMS);
		
		onCreate(db);
	}

}
