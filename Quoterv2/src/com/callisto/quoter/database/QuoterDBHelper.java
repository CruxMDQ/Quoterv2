package com.callisto.quoter.database;

import java.util.ArrayList;

import android.content.ContentValues;
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

	// "Memo t' self: mind dat zoggin' kreayshun order!"
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		TableCities.onCreate(db);
		TableComforts.onCreate(db);
		TableExpenses.onCreate(db);
		TableNbh.onCreate(db);
		TableProperties.onCreate(db);
		TableRooms.onCreate(db);
		TableRoomTypes.onCreate(db);
		TablePropComforts.onCreate(db);
		TablePropExpenses.onCreate(db);
		TablePropLogistics.onCreate(db);
		TablePropMats.onCreate(db);
		TablePropNom.onCreate(db);
		TablePropSurfaces.onCreate(db);
		TablePropTaxes.onCreate(db);
		TablePropTypes.onCreate(db);
		TablePropValues.onCreate(db);
		TablePropRooms.onCreate(db);

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
	
	public long insert(int tableId, ContentValues cv)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		switch(tableId)
		{
		case 0:
			return db.insertOrThrow(TableCities.TABLE_CITIES, null, cv);
			
		case 1:
			return db.insertOrThrow(TableComforts.TABLE_COMFORTS, null, cv);

		case 2:
			return db.insertOrThrow(TableExpenses.TABLE_EXPENSES, null, cv);
		
		case 3:
			return db.insertOrThrow(TableNbh.TABLE_NBH, null, cv);

		case 4:
			return db.insertOrThrow(TableProperties.TABLE_PROPERTIES, null, cv);
			
		case 5:
			return db.insertOrThrow(TablePropComforts.TABLE_PROPERTY_COMFORTS, null, cv);
			
		case 6:
			return db.insertOrThrow(TablePropExpenses.TABLE_PROPERTY_EXPENSES, null, cv);

		case 7:
			return db.insertOrThrow(TablePropLogistics.TABLE_PROPERTY_LOGISTICS, null, cv);
			
		case 8:
			return db.insertOrThrow(TablePropMats.TABLE_PROPERTY_MATERIALS, null, cv);
			
		case 9:
			return db.insertOrThrow(TablePropNom.TABLE_PROPERTY_NOMENCLATURES, null, cv);
		
		case 10:
			return db.insertOrThrow(TablePropRooms.TABLE_PROPERTY_ROOMS, null, cv);
			
		case 11:
			return db.insertOrThrow(TablePropSurfaces.TABLE_PROPERTY_SURFACES, null, cv);
		
		case 12:
			return db.insertOrThrow(TablePropTaxes.TABLE_PROPERTY_TAXES, null, cv);
			
		case 13:
			return db.insertOrThrow(TablePropTypes.TABLE_PROPERTY_TYPES, null, cv);
			
		case 14:
			return db.insertOrThrow(TablePropValues.TABLE_PROPERTY_VALUES, null, cv);
			
		case 15:
			return db.insertOrThrow(TableRooms.TABLE_ROOMS, null, cv);
			
		case 16:
			return db.insertOrThrow(TableRoomTypes.TABLE_ROOMTYPES, null, cv);
			
		default:
			return -1;
		}
	}
	
	public long update(int tableId, ContentValues cv)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		switch(tableId)
		{
		case 0:
			return db.update(TableCities.TABLE_CITIES, cv, null, null);
			
		case 1:
			return db.update(TableComforts.TABLE_COMFORTS, cv, null, null);

		case 2:
			return db.update(TableExpenses.TABLE_EXPENSES, cv, null, null);
		
		case 3:
			return db.update(TableNbh.TABLE_NBH, cv, null, null);

		case 4:
			return db.update(TableProperties.TABLE_PROPERTIES, cv, null, null);
			
		case 5:
			return db.update(TablePropComforts.TABLE_PROPERTY_COMFORTS, cv, null, null);
			
		case 6:
			return db.update(TablePropExpenses.TABLE_PROPERTY_EXPENSES, cv, null, null);

		case 7:
			return db.update(TablePropLogistics.TABLE_PROPERTY_LOGISTICS, cv, null, null);
			
		case 8:
			return db.update(TablePropMats.TABLE_PROPERTY_MATERIALS, cv, null, null);
			
		case 9:
			return db.update(TablePropNom.TABLE_PROPERTY_NOMENCLATURES, cv, null, null);
		
		case 10:
		    return db.update(TablePropRooms.TABLE_PROPERTY_ROOMS, cv, null, null);
			
		case 11:
			return db.update(TablePropSurfaces.TABLE_PROPERTY_SURFACES, cv, null, null);
		
		case 12:
			return db.update(TablePropTaxes.TABLE_PROPERTY_TAXES, cv, null, null);
			
		case 13:
			return db.update(TablePropTypes.TABLE_PROPERTY_TYPES, cv, null, null);
			
		case 14:
			return db.update(TablePropValues.TABLE_PROPERTY_VALUES, cv, null, null);
			
		case 15:
			Cursor T = db.rawQuery("SELECT * FROM " + TableRooms.TABLE_ROOMS, null);
			
			while (T.moveToNext())
			{
				System.out.println(T.getInt(0));
			}
			
			T.close();
			
		    return db.update(TableRooms.TABLE_ROOMS,
		    		cv, 
		    		TableRooms.COLUMN_ID_ROOM + " = ?",
		            new String[] { String.valueOf(cv.getAsInteger(TableRooms.COLUMN_ID_ROOM)) });
		    
			//return db.update(TableRooms.TABLE_ROOMS, cv, null, null);
			
		case 16:
			return db.update(TableRoomTypes.TABLE_ROOMTYPES, cv, null, null);
			
		default:
			return -1;
		}
	}
	
	public long getMaxId(int TableID)
	{
	    long id = 0;     
		
		SQLiteDatabase db = getWritableDatabase();
		
		Cursor cursor = null;
		
		switch(TableID)
		{
		case 4:
			cursor = db.rawQuery(TableProperties.DATABASE_MAX_ID, null);
			
		case 15:
			cursor = db.rawQuery(TableRooms.DATABASE_MAX_ID, null);

		default:
			id = -1;
		}
		
	    if (cursor.moveToFirst())
	    {
	        do
	        {           
	            id = cursor.getLong(0);                  
	        } while(cursor.moveToNext());           
	    }
	    
	    cursor.close();
	    
	    return id;
	}
	
	public ArrayList<Long> getIDs(int TableID)
	{		
		SQLiteDatabase db = getWritableDatabase();
		
		Cursor cursor = null;

		ArrayList<Long> ids = new ArrayList<Long>();
		
		switch(TableID)
		{
		case 4:
			cursor = db.rawQuery(TableProperties.DATABASE_SELECT, null);
			
			while (cursor.moveToNext())
			{
				ids.add(cursor.getLong(cursor.getColumnIndex(TableProperties.COLUMN_ID_PROPERTY)));
			}
			
			return ids;
		case 15:
			cursor = db.rawQuery(TableRooms.DATABASE_SELECT, null);
			
			while (cursor.moveToNext())
			{
				ids.add(cursor.getLong(cursor.getColumnIndex(TableRooms.COLUMN_ID_ROOM)));
			}
			
			return ids;

		default:
			return ids;
		}
	}
	
	public int getRoomCountForProp(int propId)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		Cursor c = 
				db.rawQuery("SELECT COUNT(*) FROM " + TablePropRooms.TABLE_PROPERTY_ROOMS 
				+ " WHERE " + TableProperties.COLUMN_ID_PROPERTY + " = " + propId + ";", null);
		
		return c.getCount();
	}

	/***
	 * This will be used by the room tabhost to get the rooms belonging to a given property.
	 * @param propId The ID of the property being queried.
	 * @return Contents of rooms table on database.
	 * Know-how source: http://rosettacode.org/wiki/Parametrized_SQL_statement
	 * DEPRECATION WARNING: actual room details must be retrieved from the room detail activity, *NOT* from the room tabhost.
	 */
//	public Cursor getRoomsForProperty(int propId)
//	{
//		SQLiteDatabase db = getWritableDatabase();
//		
//		return db.rawQuery("SELECT " +
//				"A._id_room AS _id, " +
//				"A._id_room_type, " +
//				"A.width_x, " +
//				"A.width_y, " +
//				"A.pisos, " +
//				"A.detalles,  " +
//				"A.imagen" +
//				"FROM " + TableRooms.TABLE_ROOMS + " AS A, " + TablePropRooms.TABLE_PROPERTY_ROOMS + " AS P " +
//				"WHERE P._id_prop = + " + Integer.toString(propId) + ";", null);
//	}
	
	/***
	 * Retrieves info about a specific room. Used by room detail activity.
	 * @param roomId Identifier of the room to look up.
	 * @return A cursor object containing a single row.
	 */
	public Cursor getRoom(int roomId)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		return db.rawQuery("SELECT " +
				"A._id_room AS _id, " +
				"A._id_room_type, " +
				"A.width_x, " +
				"A.width_y, " +
				"A.pisos, " +
				"A.detalles,  " +
				"A.imagen" +
				"FROM " + TableRooms.TABLE_ROOMS + " AS A " +
				"WHERE A._id_room = + " + Integer.toString(roomId) + ";", null);
	}
}
