package com.callisto.quoter.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropNom
{
	public static final String 
			TABLE_PROPERTY_NOMENCLATURES = "Propiedades_catastros",
			COLUMN_CIRC = "Circ",
			COLUMN_SECT = "Sec",
			COLUMN_SQ = "Mz",
			COLUMN_PARCEL = "Parcela",
			COLUMN_SUBPARCEL = "Subparcela",
			COLUMN_POLYGON = "Pol",
			DATABASE_CREATE = "create table if not exists " 
					+ TABLE_PROPERTY_NOMENCLATURES + "("
					+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key, "
					+ COLUMN_CIRC + " text,"
					+ COLUMN_SECT + " text,"
					+ COLUMN_SQ + " text,"
					+ COLUMN_PARCEL + " text,"
					+ COLUMN_SUBPARCEL + " text,"
					+ COLUMN_POLYGON + " text,"
					+ "FOREIGN KEY(" + TableProperties.COLUMN_ID_PROPERTY + ") REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + ")"
					+ ");";

	public static final String DATABASE_SELECT = 
			"SELECT " + TableProperties.COLUMN_ID_PROPERTY + " AS _id, "
			+ COLUMN_CIRC + ", "
			+ COLUMN_SECT + ", "
			+ COLUMN_SQ + ", "
			+ COLUMN_PARCEL + ", "
			+ COLUMN_SUBPARCEL + ", "
			+ COLUMN_POLYGON + ", "
			+ " FROM " + TABLE_PROPERTY_NOMENCLATURES + ";";

	public static void onCreate(SQLiteDatabase db)
	{
		try
		{
			db.execSQL(DATABASE_CREATE);
		} catch (SQLException e)
		{
			System.out.println("Exception on table creation step: " + TABLE_PROPERTY_NOMENCLATURES);
			System.out.println(e.getMessage());
		}
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableCities.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_NOMENCLATURES);
		
		onCreate(db);
	}

}
