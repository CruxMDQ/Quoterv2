package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableExpenses
{
	public static final String 
		TABLE_EXPENSES = "Gastos",
		COLUMN_ID_EXPENSE = "_id_gasto",
		COLUMN_NAME = "Nombre",
		DATABASE_CREATE = "create table " + TABLE_EXPENSES + "("
				+ COLUMN_ID_EXPENSE + " integer primary key autoincrement,"
				+ COLUMN_NAME + " text not null"
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
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
		
		onCreate(db);
	}	
}
