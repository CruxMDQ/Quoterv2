package com.callisto.quoter.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableNbh
{
	public static final String TABLE_NBH = "Barrios",
			COLUMN_ID_NBH = "_id_nbh",
			COLUMN_NAME = "Nombre";
	
	private static final String DATABASE_CREATE = "create table if not exists "
			+ TABLE_NBH
			+ "("
			+ COLUMN_ID_NBH + " integer primary key autoincrement, "
			+ COLUMN_NAME
			+ " text not null"
			+ ");";
	
	public static final String DATABASE_SELECT = 
			"SELECT " + COLUMN_ID_NBH + " AS _id, "
			+ COLUMN_NAME 
			+ " FROM " + TABLE_NBH + ";";
	
	public static void onCreate(SQLiteDatabase db)
	{
		try
		{
			db.execSQL(DATABASE_CREATE);
		} 
		catch (SQLException e)
		{
			System.out.println("Exception on table creation step: " + TABLE_NBH);
			System.out.println(e.getMessage());
		}
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableNbh.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NBH);
		
		onCreate(db);
	}
}
