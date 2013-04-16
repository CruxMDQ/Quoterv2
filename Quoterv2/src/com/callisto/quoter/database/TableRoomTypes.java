package com.callisto.quoter.database;

import android.content.ContentValues;
import android.database.SQLException;
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

		if (db.rawQuery("SELECT * FROM " + TABLE_ROOMTYPES + " WHERE " + COLUMN_NAME + " = 'Bedroom';", null).getCount() == 0)
		{
			simpleInsert(db, TABLE_ROOMTYPES, COLUMN_NAME, "Bedroom");
			
//			cv.put(COLUMN_NAME, "Bedroom");
//			db.insert(TABLE_ROOMTYPES, COLUMN_NAME, cv);
			  
			simpleInsert(db, TABLE_ROOMTYPES, COLUMN_NAME, "Bathroom");

//			cv.put(COLUMN_NAME, "Bathroom");
//			db.insert(TABLE_ROOMTYPES, COLUMN_NAME, cv);
			  
			simpleInsert(db, TABLE_ROOMTYPES, COLUMN_NAME, "Kitchen");

//			cv.put(COLUMN_NAME, "Kitchen");
//			db.insert(TABLE_ROOMTYPES, COLUMN_NAME, cv);
			  
			simpleInsert(db, TABLE_ROOMTYPES, COLUMN_NAME, "Living room");
			
//			cv.put(COLUMN_NAME, "Living room");
//			db.insert(TABLE_ROOMTYPES, COLUMN_NAME, cv);
	    }
	}
	
	private static void simpleInsert(SQLiteDatabase db, String table, String col, String value)
	{
		try
		{
			ContentValues cv = new ContentValues();
			
			cv.put(col, value);
			db.insert(table, col, cv);
		}
		catch (SQLException e)
		{
			System.out.println("SQL error: cannot perform insert operation on table " + table);
		}
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
