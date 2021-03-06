package com.callisto.quoter.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropTaxes
{
	public static final String 
			TABLE_PROPERTY_TAXES = "Propiedades_reparticiones",
			COLUMN_ARBA = "ARBA",
			COLUMN_MUNICIPAL = "Municipal",
			COLUMN_MATR = "Matricula",
			DATABASE_CREATE = "create table if not exists "
					+ TABLE_PROPERTY_TAXES + "("
					+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key, "
					+ COLUMN_ARBA + " text,"
					+ COLUMN_MUNICIPAL + " text,"
					+ COLUMN_MATR + " text,"
					+ "FOREIGN KEY(" + TableProperties.COLUMN_ID_PROPERTY + ") REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + ")"
					+ ");";		

	public static final String DATABASE_SELECT = 
			"SELECT " + TableProperties.COLUMN_ID_PROPERTY + " AS _id, "
			+ COLUMN_ARBA + " text, "
			+ COLUMN_MUNICIPAL + " text, "
			+ COLUMN_MATR + " text, "
			+ " FROM " + TABLE_PROPERTY_TAXES + ";";

	public static void onCreate(SQLiteDatabase db)
	{
		try
		{
			db.execSQL(DATABASE_CREATE);
		} catch (SQLException e)
		{
			System.out.println("Exception on table creation step: " + TABLE_PROPERTY_TAXES);
			System.out.println(e.getMessage());
		}
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableNbh.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_TAXES);
		
		onCreate(db);
	}
}
