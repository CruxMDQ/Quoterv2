package com.callisto.quoter.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropSurfaces
{
	public static final String 
		TABLE_PROPERTY_SURFACES = "Propiedades_superficies",
		COLUMN_SURFACE_BUILT = "Construida",
		COLUMN_SURFACE_COVERED = "Cubierta",
		COLUMN_SURFACE_SEMICOVERED = "Semicubierta",
		COLUMN_SURFACE_NOT_BUILT = "Descubierta",
		DATABASE_CREATE = "create table if not exists "
				+ TABLE_PROPERTY_SURFACES + "("
				+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key, "
				+ COLUMN_SURFACE_BUILT + " text,"
				+ COLUMN_SURFACE_COVERED + " text,"
				+ COLUMN_SURFACE_SEMICOVERED + " text,"
				+ COLUMN_SURFACE_NOT_BUILT + " text,"
				+ "FOREIGN KEY(" + TableProperties.COLUMN_ID_PROPERTY + ") REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + ")"
				+ ");";
	
	public static final String DATABASE_SELECT = 
			"SELECT " + TableProperties.COLUMN_ID_PROPERTY + " AS _id, "
			+ COLUMN_SURFACE_BUILT + ", "
			+ COLUMN_SURFACE_COVERED + ", "
			+ COLUMN_SURFACE_SEMICOVERED + ", "
			+ COLUMN_SURFACE_NOT_BUILT + ", "
			+ " FROM " + TABLE_PROPERTY_SURFACES + ";";

	public static void onCreate(SQLiteDatabase db)
	{
		try
		{
			db.execSQL(DATABASE_CREATE);
		} catch (SQLException e)
		{
			System.out.println("Exception on table creation step: " + TABLE_PROPERTY_SURFACES);
			System.out.println(e.getMessage());
		}
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableCities.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_SURFACES);
		
		onCreate(db);
	}
}
