package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropMats
{
	public static final String 
		COLUMN_WALLS = "Muros",
		COLUMN_ROOFS = "Techos",
		COLUMN_OPENINGS = "Aberturas",
		COLUMN_ENCLOSURES = "Cerramientos",
		COLUMN_GAS = "Gas",
		COLUMN_POWER = "Electricidad",
		COLUMN_WATER = "Agua",
		COLUMN_SEWER = "Cloacas",
		TABLE_PROPERTY_MATERIALS = "Propiedades_construccion_detalles",
		DATABASE_CREATE = "create table" + TABLE_PROPERTY_MATERIALS + "("
			  + TableProperties.COLUMN_ID_PROPERTY + " integer primary key, " 
			  + COLUMN_WALLS + " text,"
			  + COLUMN_ROOFS + " text,"
			  + COLUMN_OPENINGS + " text,"
			  + COLUMN_ENCLOSURES + " text,"
			  + COLUMN_WATER + " text,"
			  + COLUMN_POWER + " text,"
			  + COLUMN_GAS + " text,"
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
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_MATERIALS);
		
		onCreate(db);
	}
}
