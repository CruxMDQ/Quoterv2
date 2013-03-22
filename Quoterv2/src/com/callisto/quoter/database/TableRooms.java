package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableRooms
{
	public static final String 
		TABLE_ROOMS = "Ambientes",
		COLUMN_ID_ROOM = "_id_room",
		COLUMN_ROOM_WIDTH_X = "width_x",
		COLUMN_ROOM_WIDTH_Y = "width_y",
		COLUMN_FLOORS = "pisos",
		COLUMN_DETAILS = "detalles",
		COLUMN_PICTURE = "Imagen",
		DATABASE_CREATE = "create table " + TABLE_ROOMS + "(" 
				+ COLUMN_ID_ROOM + " integer primary key autoincrement, " 
				+ TableRoomTypes.COLUMN_ID_ROOM_TYPE + " integer not null," 
				+ COLUMN_ROOM_WIDTH_X + " real not null,"
				+ COLUMN_ROOM_WIDTH_Y + " real not null,"
				+ COLUMN_FLOORS + " text not null,"
				+ COLUMN_DETAILS + " text not null,"
				+ COLUMN_PICTURE + " blob,"
				+ "FOREIGN KEY" + "(" + TableRoomTypes.COLUMN_ID_ROOM_TYPE + ")" + " REFERENCES " + TableRoomTypes.TABLE_ROOMTYPES + "(" + TableRoomTypes.COLUMN_ID_ROOM_TYPE + ")"
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
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
		
		onCreate(db);
	}


}
