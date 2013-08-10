package com.callisto.quoter.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropValues
{
	public static final String 
			TABLE_PROPERTY_VALUES = "Propiedades_valores",
			COLUMN_VALUE_PARCEL_SQMT = "Valor_lote_metro",
			COLUMN_VALUE_BUILT_SQMT = "Valor_edif_metro",
			COLUMN_VALUE_FINAL = "Valor_final",
			DATABASE_CREATE = "create table if not exists "
					+ TABLE_PROPERTY_VALUES + "("
					+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key, "
					+ COLUMN_VALUE_PARCEL_SQMT + " integer,"
					+ COLUMN_VALUE_BUILT_SQMT + " integer,"
					+ COLUMN_VALUE_FINAL + " integer not null,"
					+ "FOREIGN KEY(" + TableProperties.COLUMN_ID_PROPERTY + ") REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + ")"
					+ ");";
	
	public static final String DATABASE_SELECT = 
			"SELECT " + TableProperties.COLUMN_ID_PROPERTY + " AS _id, "
			+ COLUMN_VALUE_PARCEL_SQMT + ", "
			+ COLUMN_VALUE_BUILT_SQMT + ", "
			+ COLUMN_VALUE_FINAL + ", "
			+ " FROM " + TABLE_PROPERTY_VALUES + ";";

	public static void onCreate(SQLiteDatabase db)
	{
		try
		{
			db.execSQL(DATABASE_CREATE);
		} catch (SQLException e)
		{
			System.out.println("Exception on table creation step: " + TABLE_PROPERTY_VALUES);
			System.out.println(e.getMessage());
		}
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableNbh.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_VALUES);
		
		onCreate(db);
	}
}
