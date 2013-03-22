package com.callisto.quoter.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuoterDBHelper extends SQLiteOpenHelper
{
	public static final String
		TRIGGER_PROPERTY_EXPENSES = "CREATE TRIGGER IF NOT EXISTS propiedades_gastos_fecha AFTER INSERT ON Propiedades_gastos"
			+ "BEGIN "
			+ "UPDATE Propiedades_gastos "
			+ "SET Fecha = DATETIME('NOW') WHERE rowid = new.rowid; "
			+ "END;";

	private static final String DATABASE_NAME = "todotable.db";
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


}
