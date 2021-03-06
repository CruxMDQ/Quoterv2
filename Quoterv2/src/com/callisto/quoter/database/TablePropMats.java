package com.callisto.quoter.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropMats
{
	public static final String 
		TABLE_PROPERTY_MATERIALS = "Propiedades_mats_detalles",
		COLUMN_WALLS = "Muros",
		COLUMN_ROOFS = "Techos",
		COLUMN_OPENINGS = "Aberturas",
		COLUMN_ENCLOSURES = "Cerramientos",
		COLUMN_WATER = "Agua",
		COLUMN_POWER = "Electricidad",
		COLUMN_GAS = "Gas",
		COLUMN_SEWER = "Cloacas",
		DATABASE_CREATE = "create table if not exists " 
				+ TABLE_PROPERTY_MATERIALS + "("
				+ TableProperties.COLUMN_ID_PROPERTY + " integer primary key, " 
				+ COLUMN_WALLS + " text, "
				+ COLUMN_ROOFS + " text, "
				+ COLUMN_OPENINGS + " text, "
				+ COLUMN_ENCLOSURES + " text, "
				+ COLUMN_WATER + " text, "
				+ COLUMN_POWER + " text, "
				+ COLUMN_GAS + " text, "
				+ COLUMN_SEWER + " text, "
				+ "FOREIGN KEY" + "(" + TableProperties.COLUMN_ID_PROPERTY + ")" + "REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + ")"
				+ ");";

	public static final String DATABASE_SELECT = 
			"SELECT " + TableProperties.COLUMN_ID_PROPERTY + " AS _id, "
			+ COLUMN_WALLS + ", "
			+ COLUMN_ROOFS + ", "
			+ COLUMN_OPENINGS + ", "
			+ COLUMN_ENCLOSURES + ", "
			+ COLUMN_WATER + ", "
			+ COLUMN_POWER + ", "
			+ COLUMN_GAS + ", "
			+ COLUMN_SEWER 
			+ " FROM " + TABLE_PROPERTY_MATERIALS + ";";

	public static void onCreate(SQLiteDatabase db)
	{
		try
		{
			db.execSQL(DATABASE_CREATE);
		} catch (SQLException e)
		{
			System.out.println("Exception on table creation step: " + TABLE_PROPERTY_MATERIALS);
			System.out.println(e.getMessage());
		}
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableNbh.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_MATERIALS);
		
		onCreate(db);
	}
}
