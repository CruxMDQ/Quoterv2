package com.callisto.quoter.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuoterDBHelper extends SQLiteOpenHelper
{
	public static final String
		TRIGGER_PROPERTY_EXPENSES = "CREATE TRIGGER IF NOT EXISTS propiedades_gastos_fecha AFTER INSERT ON Propiedades_gastos "
			+ "BEGIN "
			+ "UPDATE Propiedades_gastos "
			+ "SET Fecha = DATETIME('NOW') WHERE rowid = new.rowid; "
			+ "END;";

	private static final String DATABASE_NAME = "Quoter.db";
	private static final int DATABASE_VERSION = 1;
	
	public QuoterDBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		TableCities.onCreate(db);
		TableComforts.onCreate(db);
		TableExpenses.onCreate(db);
		TableNbh.onCreate(db);
		TableProperties.onCreate(db);
		TablePropComforts.onCreate(db);
		TablePropExpenses.onCreate(db);
		TablePropLogistics.onCreate(db);
		TablePropMats.onCreate(db);
		TablePropNom.onCreate(db);
		TablePropSurfaces.onCreate(db);
		TablePropTaxes.onCreate(db);
		TablePropTypes.onCreate(db);
		TablePropValues.onCreate(db);
		TableRooms.onCreate(db);
		TableRoomTypes.onCreate(db);

		db.execSQL(TRIGGER_PROPERTY_EXPENSES);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		TableCities.onUpgrade(db, oldVersion, newVersion);
		TableComforts.onUpgrade(db, oldVersion, newVersion);
		TableExpenses.onUpgrade(db, oldVersion, newVersion);
		TableNbh.onUpgrade(db, oldVersion, newVersion);
		TableProperties.onUpgrade(db, oldVersion, newVersion);
		TablePropComforts.onUpgrade(db, oldVersion, newVersion);
		TablePropExpenses.onUpgrade(db, oldVersion, newVersion);
		TablePropLogistics.onUpgrade(db, oldVersion, newVersion);
		TablePropMats.onUpgrade(db, oldVersion, newVersion);
		TablePropNom.onUpgrade(db, oldVersion, newVersion);
		TablePropSurfaces.onUpgrade(db, oldVersion, newVersion);
		TablePropTaxes.onUpgrade(db, oldVersion, newVersion);
		TablePropTypes.onUpgrade(db, oldVersion, newVersion);
		TablePropValues.onUpgrade(db, oldVersion, newVersion);
		TableRooms.onUpgrade(db, oldVersion, newVersion);
		TableRoomTypes.onUpgrade(db, oldVersion, newVersion);
	}

	public Cursor getCursor(int TableID)
	{
		SQLiteDatabase db = getWritableDatabase();

		switch (TableID)
		{
		case 0:
			return db.rawQuery(TableCities.DATABASE_SELECT, null);
			
		case 1:
			return db.rawQuery(TableComforts.DATABASE_SELECT, null);

		case 2:
			return db.rawQuery(TableExpenses.DATABASE_SELECT, null);
		
		case 3:
			return db.rawQuery(TableNbh.DATABASE_SELECT, null);

		case 4:
			return db.rawQuery(TableProperties.DATABASE_SELECT, null);
			
		case 5:
			return db.rawQuery(TablePropComforts.DATABASE_SELECT, null);
			
		case 6:
			return db.rawQuery(TablePropExpenses.DATABASE_SELECT, null);

		case 7:
			return db.rawQuery(TablePropLogistics.DATABASE_SELECT, null);
			
		case 8:
			return db.rawQuery(TablePropMats.DATABASE_SELECT, null);
			
		case 9:
			return db.rawQuery(TablePropNom.DATABASE_SELECT, null);
		
		case 10:
			return db.rawQuery(TablePropRooms.DATABASE_SELECT, null);
			
		case 11:
			return db.rawQuery(TablePropSurfaces.DATABASE_SELECT, null);
		
		case 12:
			return db.rawQuery(TablePropTaxes.DATABASE_SELECT, null);
			
		case 13:
			return db.rawQuery(TablePropTypes.DATABASE_SELECT, null);
			
		case 14:
			return db.rawQuery(TablePropValues.DATABASE_SELECT, null);
			
		case 15:
			return db.rawQuery(TableRooms.DATABASE_SELECT, null);
			
		case 16:
			return db.rawQuery(TableRoomTypes.DATABASE_SELECT, null);
			
		default:
			return null;
		}
	}
}
