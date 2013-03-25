package com.callisto.quoter.logic;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;

import com.callisto.quoter.contentprovider.QuoterContentProvider;
import com.callisto.quoter.database.TableRoomTypes;
import com.callisto.quoter.R;

@SuppressWarnings("deprecation")
public class QuoterDetailActivity extends TabActivity
	implements LoaderManager.LoaderCallbacks<Cursor>
{
	private TabHost tabHost = null;

	private Spinner mSpinnerType;
	
	private Intent mNewTabIntent;
	
	private ArrayAdapter<String> mSpinnerDataAdapter;
	private String[] mTypes;
	
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
		String[] from = new String[] { TableRoomTypes.COLUMN_NAME };
		
		int[] to = new int[] { R.id.spinnerType };
		
		getLoaderManager().initLoader(0, null, this);
		
		mAdapter = new SimpleCursorAdapter(this, R.layout.quoter_detail, null, from, to, 0);
		
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

//	    if (quoteUri == null)
//	    {
//	    	quoteUri = getContentResolver().insert(QuoterContentProvider.CONTENT_URI_ROOM_TYPES, values);
//	    }
//	    else
//	    {
//	    	getContentResolver().update(quoteUri, values, null, null);
//	    }
				
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
		mAdapter.swapCursor(data);
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

}
