package com.callisto.quoter.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableProperties
{
	public static final String TABLE_PROPERTIES = "Propiedades",
			COLUMN_PICTURE = "Imagen",
			COLUMN_ADDRESS = "domicilio",
			COLUMN_DESCRIPTION = "descripcion",
			COLUMN_OWNER_URI = "owner_uri",
			COLUMN_ID_PROPERTY = "_id_prop",
			COLUMN_NAME = "Nombre";
	
	private static final String DATABASE_CREATE = "create table " + TABLE_PROPERTIES + "("
			+ COLUMN_ID_PROPERTY + " integer primary key autoincrement,"
			+ TablePropTypes.COLUMN_ID_PROPERTY_TYPE + " integer not null,"
			+ TableCities.COLUMN_ID_CITY + " integer not null,"
			+ TableNbh.COLUMN_ID_NBH + " integer not null,"
			+ COLUMN_OWNER_URI + " text not null,"
			+ COLUMN_ADDRESS + " text not null,"
			+ COLUMN_DESCRIPTION + " text,"
			+ COLUMN_PICTURE + " blob,"
			+ "FOREIGN KEY(" + TableCities.COLUMN_ID_CITY + ") REFERENCES " + TableCities.TABLE_CITIES + "(" + TableCities.COLUMN_ID_CITY + ")"
			+ "FOREIGN KEY(" + TableNbh.COLUMN_ID_NBH + ") REFERENCES " + TableNbh.TABLE_NBH + "(" + TableNbh.COLUMN_ID_NBH + ")"
			+ "FOREIGN KEY(" + TablePropTypes.COLUMN_ID_PROPERTY_TYPE + ") REFERENCES " + TablePropTypes.TABLE_PROPERTY_TYPES + "(" + TablePropTypes.COLUMN_ID_PROPERTY_TYPE + ")"
			+ ");";

	public static final String DATABASE_SELECT = 
			"SELECT " + TableProperties.COLUMN_ID_PROPERTY + " AS _id, "
			+ TablePropTypes.COLUMN_ID_PROPERTY_TYPE + ", "
			+ TableCities.COLUMN_ID_CITY + ", "
			+ TableNbh.COLUMN_ID_NBH + ", "
			+ COLUMN_OWNER_URI + ", "
			+ COLUMN_ADDRESS + ", "
			+ COLUMN_DESCRIPTION + ", "
			+ COLUMN_PICTURE 
			+ " FROM " + TABLE_PROPERTIES + ";";
	
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(DATABASE_CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(TableNbh.class.getName(), "Upgrading database from version " + oldVersion 
				+ " to " + newVersion
				+ ", which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS " + TablePropTypes.TABLE_PROPERTY_TYPES);
		
		onCreate(db);
	}

}
