package com.callisto.quoter.contentprovider;

import com.callisto.quoter.database.QuoterDBHelper;
import com.callisto.quoter.database.TableCities;
import com.callisto.quoter.database.TableComforts;
import com.callisto.quoter.database.TableExpenses;
import com.callisto.quoter.database.TableNbh;
import com.callisto.quoter.database.TablePropComforts;
import com.callisto.quoter.database.TablePropExpenses;
import com.callisto.quoter.database.TablePropLogistics;
import com.callisto.quoter.database.TableProperties;
import com.callisto.quoter.database.TableRoomTypes;
import com.callisto.quoter.database.TableRooms;

import android.database.sqlite.SQLiteQueryBuilder;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

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
			ROOMS_ID = 161;
	
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
			
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		return null;
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs)
	{
		// TODO Auto-generated method stub
		return 0;
	}

			
}
