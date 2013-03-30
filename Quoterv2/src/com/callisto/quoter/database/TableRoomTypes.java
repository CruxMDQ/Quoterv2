package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableRoomTypes
{
	public static final String 
		TABLE_ROOMTYPES = "Ambientes_tipos",
		COLUMN_ID_ROOM_TYPE = "_id_room_type",
		COLUMN_NAME = "Nombre",
		DATABASE_CREATE = "create table if not exists " 
				+ TABLE_ROOMTYPES + "(" 
				+ COLUMN_ID_ROOM_TYPE + " integer primary key autoincrement, " 
				+ COLUMN_NAME + " text not null"
				+ ");";
	
	public static final String DATABASE_SELECT = 
				"SELECT " + COLUMN_ID_ROOM_TYPE + " AS _id, "
				+ COLUMN_NAME 
				+ " FROM " + TABLE_ROOMTYPES + ";";
	
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableCities.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMTYPES);
		
		onCreate(db);
	}
}
