package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableExpenses
{
	public static final String 
		TABLE_EXPENSES = "Gastos",
		COLUMN_ID_EXPENSE = "_id_gasto",
		COLUMN_NAME = "Nombre";
	
	public static final String DATABASE_CREATE = "create table if not exists "
				+ TABLE_EXPENSES + "("
				+ COLUMN_ID_EXPENSE + " integer primary key autoincrement,"
				+ COLUMN_NAME + " text not null"
				+ ");";

	public static final String DATABASE_SELECT = "SELECT " + COLUMN_ID_EXPENSE + " AS _id, "
			+ COLUMN_NAME + " FROM " + TABLE_EXPENSES + ";";
	
	public static void onCreate(SQLiteDatabase db)
	{
		try
		{
			db.execSQL(DATABASE_CREATE);
		}
		catch (Exception e)
		{
			System.out.println("Exception on table creation step: " + TABLE_EXPENSES);
			System.out.println(e.getMessage());
		}
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
