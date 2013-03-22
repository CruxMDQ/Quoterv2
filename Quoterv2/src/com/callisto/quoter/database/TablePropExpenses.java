package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TablePropExpenses
{
	public static final String 
	//	COLUMN_ID_PROPERTY = "_id_prop",
	//	COLUMN_ID_EXPENSE = "_id_gasto",
		TABLE_PROPERTY_EXPENSES = "Propiedades_gastos",
		COLUMN_AMOUNT = "Monto",
		COLUMN_DATE = "Fecha",
		DATABASE_CREATE = "create table " + TABLE_PROPERTY_EXPENSES + "("
				+ TableProperties.COLUMN_ID_PROPERTY + " integer not null, "
				+ TableExpenses.COLUMN_ID_EXPENSE + " integer not null, "
				+ COLUMN_DATE + " datetime, "
				+ COLUMN_AMOUNT + " real not null, "
				+ "PRIMARY KEY(" + TableProperties.COLUMN_ID_PROPERTY + ", " + TableExpenses.COLUMN_ID_EXPENSE + ", " + COLUMN_DATE + "), "
				+ "FOREIGN KEY(" + TableProperties.COLUMN_ID_PROPERTY + ") REFERENCES " + TableProperties.TABLE_PROPERTIES + "(" + TableProperties.COLUMN_ID_PROPERTY + "), "
				+ "FOREIGN KEY(" + TableExpenses.COLUMN_ID_EXPENSE + ") REFERENCES " + TableExpenses.TABLE_EXPENSES + "(" + TableExpenses.COLUMN_ID_EXPENSE + ")"
				+ ");";
	
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableCities.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTY_EXPENSES);
		
		onCreate(db);
	}	

}
