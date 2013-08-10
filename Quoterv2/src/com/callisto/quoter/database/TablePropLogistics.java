package com.callisto.quoter.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropLogistics
{
	public static final String 
		TABLE_PROPERTY_LOGISTICS = "Propiedades_Logistica",
		COLUMN_PLACEMENT = "Ubicacion",
		COLUMN_FACING = "Orientacion",
		COLUMN_VIEW = "Vista",
		DATABASE_CREATE = "create table if not exists " 
				+ TABLE_PROPERTY_LOGISTICS + "("
				+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key, "
				+ COLUMN_PLACEMENT + " text,"
				+ COLUMN_FACING + " text,"
				+ COLUMN_VIEW + " text,"
				+ "FOREIGN KEY(" + TableProperties.COLUMN_ID_PROPERTY + ") REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + ")"
				+ ");";
	
	public static final String DATABASE_SELECT = 
			"SELECT " + TableProperties.COLUMN_ID_PROPERTY + " AS _id, "
			+ COLUMN_PLACEMENT + ", "
			+ COLUMN_FACING + ", "
			+ COLUMN_VIEW 
			+ " FROM " + TABLE_PROPERTY_LOGISTICS + ";";

	public static void onCreate(SQLiteDatabase db)
	{
		try
		{
			db.execSQL(DATABASE_CREATE);
		} catch (SQLException e)
		{
			System.out.println("Exception on table creation step: " + TABLE_PROPERTY_LOGISTICS);
			System.out.println(e.getMessage());
		}
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableCities.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_LOGISTICS);
		
		onCreate(db);
	}

}
