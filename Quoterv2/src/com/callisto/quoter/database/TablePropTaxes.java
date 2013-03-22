package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropTaxes
{
	public static final String 
			TABLE_PROPERTY_TAXES = "Propiedades_reparticiones",
			COLUMN_ARBA = "ARBA",
			COLUMN_MUNICIPAL = "Municipal",
			COLUMN_MATR = "Matricula",
			DATABASE_CREATE = "CREATE TABLE " + TABLE_PROPERTY_TAXES + "("
					+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key, "
					+ COLUMN_ARBA + " text,"
					+ COLUMN_MUNICIPAL + " text,"
					+ COLUMN_MATR + " text,"
					+ "FOREIGN KEY(" + TableProperties.COLUMN_ID_PROPERTY + ") REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + ")"
					+ ");";		

	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
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
