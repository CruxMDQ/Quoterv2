package com.callisto.quoter.logic;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;

import com.callisto.quoter.R;
import com.callisto.quoter.contentprovider.QuoterContentProvider;
import com.callisto.quoter.database.TableRoomTypes;

@SuppressWarnings("deprecation")
public class QuoterDetailActivity extends TabActivity
	implements LoaderManager.LoaderCallbacks<Cursor>
{
	private TabHost tabHost = null;

	private Spinner mSpinnerType;
	
//	private Intent mNewTabIntent;
	
//	private ArrayAdapter<String> mSpinnerDataAdapter;
//	private String[] mTypes;
	
	private Uri quoteUri;

	private SimpleCursorAdapter mAdapter;

	private static final int
		ADD_ID = Menu.FIRST + 1,
		DELETE_ID = Menu.FIRST + 3,					
		ADD_TAB = Menu.FIRST + 11,
		DELETE_TAB = Menu.FIRST + 12;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quoter_detail);
		
		this.tabHost = getTabHost();
		
		mSpinnerType = (Spinner) findViewById(R.id.spinnerType);
		
		populateSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(Menu.NONE, ADD_ID, Menu.NONE, "New room type")
			.setIcon(R.drawable.add)
			.setAlphabeticShortcut('t');
		menu.add(Menu.NONE, ADD_TAB, Menu.NONE, "New room")
			.setIcon(R.drawable.add)
			.setAlphabeticShortcut('a');

		return (super.onCreateOptionsMenu(menu));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case ADD_ID:
			addRoomType();
			
		case DELETE_ID:
			deleteRoomType();
			
		case ADD_TAB:
			addTab();
			
			return (true);
		}
		
		return (super.onOptionsItemSelected(item));
	}


	private void addTab()
	{
		// TODO Auto-generated method stub
		
	}

	private void deleteRoomType()
	{
		// TODO Auto-generated method stub
		
	}

	private void populateSpinner()
	{
		String[] from = new String[] { "_id", TableRoomTypes.COLUMN_NAME };
		
		int[] to = new int[] { android.R.id.text1 };
		
		mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, 
				null, from, to, 0);
		
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		mSpinnerType.setAdapter(mAdapter);
	}
	
	private void addRoomType()
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		
		View addView = inflater.inflate(R.layout.add_type, null);
		
		final AddTypeDialogWrapper wrapper = new AddTypeDialogWrapper(addView);
		
		new AlertDialog.Builder(this)
			.setTitle(R.string.add_type_title)
			.setView(addView)
			.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) {
						processAddType(wrapper); 
					}
				})
			.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which) {
	
					}
				}
			).show();			
	}
	
	private String[] toStringArray(Object[] objectArray)  
	{  
		String[] stringArray = new String[objectArray.length];  
		
		for (int i = 0; i < objectArray.length; i++)  
		{  
			stringArray[i] = objectArray[i].toString();  
		}
		
		return stringArray;
	}

	private void processAddType(AddTypeDialogWrapper wrapper) 
	{
	    ContentValues values = new ContentValues();
	    
	    values.put(TableRoomTypes.COLUMN_NAME, wrapper.getName());
	    
    	quoteUri = getContentResolver().insert(QuoterContentProvider.CONTENT_URI_ROOM_TYPES, values);
				
		populateSpinner();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		String[] projection = { TableRoomTypes.COLUMN_ID_ROOM_TYPE, TableRoomTypes.COLUMN_NAME };
		
		CursorLoader cursorLoader = new CursorLoader (this,
				QuoterContentProvider.CONTENT_URI_ROOM_TYPES, projection, null, null, null);
		
		return cursorLoader;
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data)
	{
		MaskingWrapper mask = new MaskingWrapper(data);
		
		mAdapter.swapCursor(mask);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		// Data not available anymore -> delete reference
		mAdapter.swapCursor(null);
	}

	class AddTypeDialogWrapper
	{
		EditText nameField = null;
		View base = null;
		
		AddTypeDialogWrapper(View base)
		{
			this.base = base;
			nameField = (EditText) base.findViewById(R.id.txtName);
		}
		
		String getName()
		{
			return (getNameField().getText().toString());
		}
		
		private EditText getNameField()
		{
			if (nameField == null)
			{
				nameField = (EditText) base.findViewById(R.id.txtName);
			}
			
			return (nameField);
		}
	}

	// CursorWrapper subclass built to dodge the '_id' requirement for SimpleCursorAdapter
	// (ref.: http://stackoverflow.com/questions/7796345/column-id-does-not-exist-simplecursoradapter-revisited/7796404#7796404)
	class MaskingWrapper extends CursorWrapper
	{
		Cursor maskedCursor;
		
		public MaskingWrapper(Cursor cursor)
		{
			super(cursor);
			maskedCursor = cursor;
		}
		
		@Override
		public int getColumnCount(){
			return super.getColumnCount() + 1;
		}
		
		@Override
		public int getColumnIndex(String columnName)
		{
			if (columnName == "_id")
				return 0;
			else
				return super.getColumnIndex(columnName);
		}

		@Override
		public int getColumnIndexOrThrow(String columnName)
		{
			if (columnName == "_id")
				return 0;
			else
				return super.getColumnIndexOrThrow(columnName);
		}

		@Override
		public double getDouble(int columnIndex)
		{
			if (columnIndex == 0)
				return (double)super.getPosition();
			else
				return super.getDouble(columnIndex);
		}
		
		@Override
		public float getFloat(int columnIndex)
		{
			if (columnIndex == 0)
				return (float)super.getPosition();
			else
				return super.getFloat(columnIndex);
		}
		
		@Override
		public int getType(int columnIndex)
		{
			if (columnIndex == 0)
				return Cursor.FIELD_TYPE_INTEGER;
			else
				return super.getType(columnIndex);
		}
		
		@Override
		public boolean isNull(int columnIndex)
		{
			if (columnIndex == 0)
				return super.isNull(1);
			else
				return super.isNull(columnIndex);
		}
		
	}
}
