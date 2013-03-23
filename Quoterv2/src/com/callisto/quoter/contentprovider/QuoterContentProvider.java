package com.callisto.quoter.contentprovider;

import com.callisto.quoter.database.QuoterDBHelper;
import com.callisto.quoter.database.TableCities;
import com.callisto.quoter.database.TableComforts;
import com.callisto.quoter.database.TableExpenses;
import com.callisto.quoter.database.TableNbh;
import com.callisto.quoter.database.TablePropComforts;
import com.callisto.quoter.database.TablePropExpenses;
import com.callisto.quoter.database.TablePropLogistics;
import com.callisto.quoter.database.TablePropMats;
import com.callisto.quoter.database.TablePropNom;
import com.callisto.quoter.database.TablePropRooms;
import com.callisto.quoter.database.TablePropSurfaces;
import com.callisto.quoter.database.TablePropTaxes;
import com.callisto.quoter.database.TablePropTypes;
import com.callisto.quoter.database.TablePropValues;
import com.callisto.quoter.database.TableProperties;
import com.callisto.quoter.database.TableRoomTypes;
import com.callisto.quoter.database.TableRooms;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class QuoterContentProvider extends ContentProvider
{
	private QuoterDBHelper database;
	
	private static String
			AUTHORITY = "com.callisto.quoter.contentprovider",
			PATH_CITIES = "cities",
			PATH_COMFORTS = "comforts",
			PATH_EXPENSES = "expenses",
			PATH_NBH = "nbhs",
			PATH_PROPERTIES = "properties",
			PATH_ROOMS = "rooms",
			PATH_PROP_COMFORTS = "propcomforts",
			PATH_PROP_EXPENSES = "propexpenses",
			PATH_PROP_LOGISTICS = "proplogistics",
			PATH_PROP_MATS = "propmats",
			PATH_PROP_NOM = "propnom",
			PATH_PROP_ROOMS = "proprooms",
			PATH_PROP_SURFACES = "propsurfaces",
			PATH_PROP_TAXES = "proptaxes",
			PATH_PROP_TYPES = "proptypes",
			PATH_PROP_VALUES = "propvalues",
			PATH_ROOM_TYPES = "roomtypes";
	
	public static final Uri 
			CONTENT_URI_CITIES = Uri.parse("content://" + AUTHORITY + "/" + PATH_CITIES),
			CONTENT_URI_COMFORTS = Uri.parse("content://" + AUTHORITY + "/" + PATH_COMFORTS),
			CONTENT_URI_EXPENSES = Uri.parse("content://" + AUTHORITY + "/" + PATH_EXPENSES),
			CONTENT_URI_NBH = Uri.parse("content://" + AUTHORITY + "/" + PATH_NBH),
			CONTENT_URI_PROPERTIES = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROPERTIES),
			CONTENT_URI_ROOMS = Uri.parse("content://" + AUTHORITY + "/" + PATH_ROOMS),
			CONTENT_URI_PROP_COMFORTS = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_COMFORTS),
			CONTENT_URI_PROP_EXPENSES = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_EXPENSES),
			CONTENT_URI_PROP_LOGISTICS = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_LOGISTICS),
			CONTENT_URI_PROP_MATS = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_MATS),
			CONTENT_URI_PROP_NOM = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_NOM),
			CONTENT_URI_PROP_ROOMS = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_ROOMS),
			CONTENT_URI_PROP_SURFACES = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_SURFACES),
			CONTENT_URI_PROP_TAXES = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_TAXES),
			CONTENT_URI_PROP_TYPES = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_TYPES),
			CONTENT_URI_PROP_VALUES = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROP_VALUES),
			CONTENT_URI_ROOM_TYPES = Uri.parse("content://" + AUTHORITY + "/" + PATH_ROOM_TYPES);

	private static final int 
			CITIES = 10,
			CITIES_ID = 11,
			COMFORTS = 20,
			COMFORTS_ID = 21,
			EXPENSES = 30,
			EXPENSES_ID = 31,
			NBH = 40,
			NBH_ID = 41,
			PROPERTIES = 50,
			PROPERTIES_ID = 51,
			PROP_COMFORTS = 60,
			PROP_COMFORTS_ID = 61,
			PROP_EXPENSES = 70,
			PROP_EXPENSES_ID = 71,
			PROP_LOGISTICS = 80,
			PROP_LOGISTICS_ID = 81,
			PROP_MATS = 90,
			PROP_MATS_ID = 91,
			PROP_NOM = 100,
			PROP_NOM_ID = 101,
			PROP_ROOMS = 110,
			PROP_ROOMS_ID = 111,
			PROP_TAXES = 120,
			PROP_TAXES_ID = 121,
			PROP_TYPES = 130,
			PROP_TYPES_ID = 131,
			PROP_VALUES = 140,
			PROP_VALUES_ID = 141,
			ROOM_TYPES = 150,
			ROOM_TYPES_ID = 151,
			ROOMS = 160,
			ROOMS_ID = 161,
			PROP_SURFACES = 170,
			PROP_SURFACES_ID = 171;
	
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static
	{
		sURIMatcher.addURI(AUTHORITY, PATH_CITIES, CITIES);
		sURIMatcher.addURI(AUTHORITY, PATH_CITIES + "/#", CITIES_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_COMFORTS, COMFORTS);
		sURIMatcher.addURI(AUTHORITY, PATH_COMFORTS + "/#", COMFORTS_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_EXPENSES, EXPENSES);
		sURIMatcher.addURI(AUTHORITY, PATH_EXPENSES + "/#", EXPENSES_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_NBH, NBH);
		sURIMatcher.addURI(AUTHORITY, PATH_NBH + "/#", NBH_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROPERTIES, PROPERTIES);
		sURIMatcher.addURI(AUTHORITY, PATH_PROPERTIES + "/#", PROPERTIES_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_ROOMS, ROOMS);
		sURIMatcher.addURI(AUTHORITY, PATH_ROOMS + "/#", ROOMS_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_ROOM_TYPES, ROOM_TYPES);
		sURIMatcher.addURI(AUTHORITY, PATH_ROOM_TYPES + "/#", ROOM_TYPES_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_COMFORTS, PROP_COMFORTS);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_COMFORTS + "/#", PROP_COMFORTS_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_EXPENSES, PROP_EXPENSES);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_EXPENSES + "/#", PROP_EXPENSES_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_LOGISTICS, PROP_LOGISTICS);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_LOGISTICS + "/#", PROP_LOGISTICS_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_MATS, PROP_MATS);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_MATS + "/#", PROP_MATS_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_NOM, PROP_NOM);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_NOM + "/#", PROP_NOM_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_ROOMS, PROP_ROOMS);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_ROOMS + "/#", PROP_ROOMS_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_TAXES, PROP_TAXES);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_TAXES + "/#", PROP_TAXES_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_TYPES, PROP_TYPES);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_TYPES + "/#", PROP_TYPES_ID);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_VALUES, PROP_VALUES);
		sURIMatcher.addURI(AUTHORITY, PATH_PROP_VALUES + "/#", PROP_VALUES_ID);

	}
	
	@Override
	public boolean onCreate()
	{
		database = new QuoterDBHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder)
	{
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
//		checkColumns(projection);
		
//		queryBuilder.setTables(TodoTable.TABLE_TODO);
		
		int uriType = sURIMatcher.match(uri);
		
		switch (uriType)
		{
		case CITIES_ID:
			queryBuilder.appendWhere(TableCities.COLUMN_ID_CITY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case CITIES:
			queryBuilder.setTables(TableCities.TABLE_CITIES);
			break;
		
		case COMFORTS_ID:
			queryBuilder.appendWhere(TableComforts.COLUMN_ID_COMFORT + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case COMFORTS:
			queryBuilder.setTables(TableComforts.TABLE_COMFORTS);
			break;

		case EXPENSES_ID:
			queryBuilder.appendWhere(TableExpenses.COLUMN_ID_EXPENSE + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case EXPENSES:
			queryBuilder.setTables(TableExpenses.TABLE_EXPENSES);
			break;
			
		case NBH_ID:
			queryBuilder.appendWhere(TableNbh.COLUMN_ID_NBH + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case NBH:
			queryBuilder.setTables(TableNbh.TABLE_NBH);
			break;
			
		case PROPERTIES_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROPERTIES:
			queryBuilder.setTables(TableProperties.TABLE_PROPERTIES);
			break;

		case ROOMS_ID:
			queryBuilder.appendWhere(TableRooms.COLUMN_ID_ROOM + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case ROOMS:
			queryBuilder.setTables(TableRooms.TABLE_ROOMS);
			break;
		
		case ROOM_TYPES_ID:
			queryBuilder.appendWhere(TableRoomTypes.COLUMN_ID_ROOM_TYPE + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case ROOM_TYPES:
			queryBuilder.setTables(TableRoomTypes.TABLE_ROOMTYPES);
			break;
		
		case PROP_COMFORTS_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_COMFORTS:
			queryBuilder.setTables(TablePropComforts.TABLE_PROPERTY_COMFORTS);
			break;

		case PROP_EXPENSES_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_EXPENSES:
			queryBuilder.setTables(TablePropExpenses.TABLE_PROPERTY_EXPENSES);
			break;

		case PROP_LOGISTICS_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_LOGISTICS:
			queryBuilder.setTables(TablePropLogistics.TABLE_PROPERTY_LOGISTICS);
			break;
			
		case PROP_MATS_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_MATS:
			queryBuilder.setTables(TablePropMats.TABLE_PROPERTY_MATERIALS);
			break;
			
		case PROP_NOM_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_NOM:
			queryBuilder.setTables(TablePropNom.TABLE_PROPERTY_NOMENCLATURES);
			break;
			
		case PROP_ROOMS_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_ROOMS:
			queryBuilder.setTables(TablePropRooms.TABLE_PROPERTY_ROOMS);
			break;
			
		case PROP_SURFACES_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_SURFACES:
			queryBuilder.setTables(TablePropSurfaces.TABLE_PROPERTY_SURFACES);
			break;

		case PROP_TAXES_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_TAXES:
			queryBuilder.setTables(TablePropTaxes.TABLE_PROPERTY_TAXES);
			break;
			
		case PROP_TYPES_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_TYPES:
			queryBuilder.setTables(TablePropTypes.TABLE_PROPERTY_TYPES);
			break;

		case PROP_VALUES_ID:
			queryBuilder.appendWhere(TableProperties.COLUMN_ID_PROPERTY + "=" + uri.getLastPathSegment());
			// FALL-THROUGH

		case PROP_VALUES:
			queryBuilder.setTables(TablePropValues.TABLE_PROPERTY_VALUES);
			break;
			
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		SQLiteDatabase db = database.getWritableDatabase();
		
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}
	
	@Override
	public String getType(Uri arg0)
	{
		// Unimplemented? Why?
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		
		Uri R;
		
		switch (uriType)
		{
		case CITIES:
			R = insertOnCase(TableCities.TABLE_CITIES, PATH_CITIES, sqlDB, values);
//			id = sqlDB.insert(TableCities.TABLE_CITIES, null, values);
//			R = Uri.parse(PATH_CITIES + "/" + id);
			
			break;

		case COMFORTS:
			R = insertOnCase(TableComforts.TABLE_COMFORTS, PATH_COMFORTS, sqlDB, values);
//			id = sqlDB.insert(TableComforts.TABLE_COMFORTS, null, values);
//			R = Uri.parse(PATH_COMFORTS + "/" + id);
			
			break;

		case EXPENSES:
			R = insertOnCase(TableExpenses.TABLE_EXPENSES, PATH_EXPENSES, sqlDB, values);
//			id = sqlDB.insert(TableExpenses.TABLE_EXPENSES, null, values);
//			R = Uri.parse(PATH_EXPENSES + "/" + id);
			
			break;
			
		case NBH:
			R = insertOnCase(TableNbh.TABLE_NBH, PATH_NBH, sqlDB, values);
//			id = sqlDB.insert(TableNbh.TABLE_NBH, null, values);
//			R = Uri.parse(PATH_NBH + "/" + id);
			
			break;
			
		case PROPERTIES:
			R = insertOnCase(TableProperties.TABLE_PROPERTIES, PATH_PROPERTIES, sqlDB, values);
//			id = sqlDB.insert(TableProperties.TABLE_PROPERTIES, null, values);
//			R = Uri.parse(PATH_PROPERTIES + "/" + id);
			
			break;

		case ROOMS:
			R = insertOnCase(TableRooms.TABLE_ROOMS, PATH_ROOMS, sqlDB, values);
//			id = sqlDB.insert(TableRooms.TABLE_ROOMS, null, values);
//			R = Uri.parse(PATH_ROOMS + "/" + id);
			
			break;
		
		case ROOM_TYPES:
			R = insertOnCase(TableRoomTypes.TABLE_ROOMTYPES, PATH_ROOM_TYPES, sqlDB, values);
//			id = sqlDB.insert(TableRoomTypes.TABLE_ROOMTYPES, null, values);
//			R = Uri.parse(PATH_ROOM_TYPES + "/" + id);
			
			break;
		
		case PROP_COMFORTS:
			R = insertOnCase(TablePropComforts.TABLE_PROPERTY_COMFORTS, PATH_COMFORTS, sqlDB, values);
//			id = sqlDB.insert(TablePropComforts.TABLE_PROPERTY_COMFORTS, null, values);
//			R = Uri.parse(PATH_PROP_COMFORTS + "/" + id);
			
			break;

		case PROP_EXPENSES:
			R = insertOnCase(TablePropExpenses.TABLE_PROPERTY_EXPENSES, PATH_PROP_EXPENSES, sqlDB, values);
//			id = sqlDB.insert(TablePropExpenses.TABLE_PROPERTY_EXPENSES, null, values);
//			R = Uri.parse(PATH_PROP_EXPENSES + "/" + id);
			
			break;

		case PROP_LOGISTICS:
			R = insertOnCase(TablePropLogistics.TABLE_PROPERTY_LOGISTICS, PATH_PROP_LOGISTICS, sqlDB, values);
//			id = sqlDB.insert(TablePropLogistics.TABLE_PROPERTY_LOGISTICS, null, values);
//			R = Uri.parse(PATH_PROP_LOGISTICS + "/" + id);
			
			break;
			
		case PROP_MATS:
			R = insertOnCase(TablePropMats.TABLE_PROPERTY_MATERIALS, PATH_PROP_MATS, sqlDB, values);
//			id = sqlDB.insert(TablePropMats.TABLE_PROPERTY_MATERIALS, null, values);
//			R = Uri.parse(PATH_PROP_MATS + "/" + id);
			
			break;
			
		case PROP_NOM:
			R = insertOnCase(TablePropNom.TABLE_PROPERTY_NOMENCLATURES, PATH_PROP_NOM, sqlDB, values);
//			id = sqlDB.insert(TablePropNom.TABLE_PROPERTY_NOMENCLATURES, null, values);
//			R = Uri.parse(PATH_PROP_NOM + "/" + id);
			
			break;
			
		case PROP_ROOMS:
			R = insertOnCase(TablePropRooms.TABLE_PROPERTY_ROOMS, PATH_PROP_ROOMS, sqlDB, values);
//			id = sqlDB.insert(TablePropRooms.TABLE_PROPERTY_ROOMS, null, values);
//			R = Uri.parse(PATH_PROP_ROOMS + "/" + id);
			
			break;
			
		case PROP_SURFACES:
			R = insertOnCase(TablePropSurfaces.TABLE_PROPERTY_SURFACES, PATH_PROP_SURFACES, sqlDB, values);
//			id = sqlDB.insert(TablePropSurfaces.TABLE_PROPERTY_SURFACES, null, values);
//			R = Uri.parse(PATH_PROP_SURFACES + "/" + id);
			
			break;

		case PROP_TAXES:
			R = insertOnCase(TablePropTaxes.TABLE_PROPERTY_TAXES, PATH_PROP_TAXES, sqlDB, values);
//			id = sqlDB.insert(TablePropTaxes.TABLE_PROPERTY_TAXES, null, values);
//			R = Uri.parse(PATH_PROP_TAXES + "/" + id);
			
			break;
			
		case PROP_TYPES:
			R = insertOnCase(TablePropTypes.TABLE_PROPERTY_TYPES, PATH_PROP_TYPES, sqlDB, values);
//			id = sqlDB.insert(TablePropTypes.TABLE_PROPERTY_TYPES, null, values);
//			R = Uri.parse(PATH_CITIES + "/" + id);
			
			break;

		case PROP_VALUES:
			R = insertOnCase(TablePropValues.TABLE_PROPERTY_VALUES, PATH_PROP_VALUES, sqlDB, values);
//			id = sqlDB.insert(TablePropValues.TABLE_PROPERTY_VALUES, null, values);
//			R = Uri.parse(PATH_PROP_VALUES + "/" + id);
			
			break;
			
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);

		return R;
	}

	private Uri insertOnCase(String table, String path, SQLiteDatabase db, ContentValues values)
	{	
		Uri R = Uri.parse(path + "/" + db.insert(table, null, values));
		
		return R;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		
		int rowsDeleted = 0;
		
		switch(uriType)
		{
		case CITIES:
			rowsDeleted = sqlDB.delete(TableCities.TABLE_CITIES, selection, selectionArgs);
			break;
			
		case CITIES_ID:
			deleteOnCaseWithId(
					TableCities.TABLE_CITIES,
					TableCities.COLUMN_ID_CITY,
					selection, sqlDB, uri);
			
			break;
			
		case COMFORTS:
			rowsDeleted = sqlDB.delete(TableComforts.TABLE_COMFORTS, selection, selectionArgs);
			break;
			
		case COMFORTS_ID:
			deleteOnCaseWithId(
					TableComforts.TABLE_COMFORTS,
					TableComforts.COLUMN_ID_COMFORT,
					selection, sqlDB, uri);
			
			break;
			
		case EXPENSES:
			rowsDeleted = sqlDB.delete(TableExpenses.TABLE_EXPENSES, selection, selectionArgs);
			break;
			
		case EXPENSES_ID:
			deleteOnCaseWithId(
					TableExpenses.TABLE_EXPENSES,
					TableExpenses.COLUMN_ID_EXPENSE,
					selection, sqlDB, uri);
			
			break;
			
		case NBH:
			rowsDeleted = sqlDB.delete(TableNbh.TABLE_NBH, selection, selectionArgs);
			break;
			
		case NBH_ID:
			deleteOnCaseWithId(
					TableNbh.TABLE_NBH,
					TableNbh.COLUMN_ID_NBH,
					selection, sqlDB, uri);
			
			break;
			
		case PROPERTIES:
			rowsDeleted = sqlDB.delete(TableProperties.TABLE_PROPERTIES, selection, selectionArgs);
			break;
			
		case PROPERTIES_ID:
			deleteOnCaseWithId(
					TableProperties.TABLE_PROPERTIES,
					TableProperties.COLUMN_ID_PROPERTY,
					selection, sqlDB, uri);
			
			break;
			
		case ROOMS:
			rowsDeleted = sqlDB.delete(TableRooms.TABLE_ROOMS, selection, selectionArgs);
			break;
			
		case ROOMS_ID:
			deleteOnCaseWithId(
					TableRooms.TABLE_ROOMS,
					TableRooms.COLUMN_ID_ROOM,
					selection, sqlDB, uri);
			
			break;
		
		case ROOM_TYPES:
			rowsDeleted = sqlDB.delete(TableRoomTypes.TABLE_ROOMTYPES, selection, selectionArgs);
			break;
			
		case ROOM_TYPES_ID:
			deleteOnCaseWithId(
					TableRoomTypes.TABLE_ROOMTYPES,
					TableRoomTypes.COLUMN_ID_ROOM_TYPE,
					selection, sqlDB, uri);
			
			break;

		default:	
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return rowsDeleted;
	}
	
	private int deleteOnCaseWithId(String table, String key, String selection, SQLiteDatabase db, Uri uri)
	{
		int rowsDeleted = 0;
		
		String id = uri.getLastPathSegment();

		if (TextUtils.isEmpty(selection))
		{
			rowsDeleted = db.delete(table, key, null);
		}
		else
		{
			rowsDeleted = db.delete(table, 
					key + "=" + id + " and " + selection,
					null);
		}
	
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs)
	{
		// TODO Auto-generated method stub
		return 0;
	}

			
}
