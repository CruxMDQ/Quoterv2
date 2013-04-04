package com.callisto.quoter.logic;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;

import com.callisto.quoter.R;
import com.callisto.quoter.contentprovider.QuoterContentProvider;
import com.callisto.quoter.database.QuoterDBHelper;
import com.callisto.quoter.database.TableCities;
import com.callisto.quoter.database.TableNbh;
import com.callisto.quoter.database.TableProperties;

public class PropOverviewActivity extends ListActivity implements
	LoaderManager.LoaderCallbacks<Cursor>
{
	private static final int TABLE_PROPERTIES = 4;
	
	private static final int 
//		ACTIVITY_CREATE = 0,
//		ACTIVITY_EDIT = 1,
		ADD_ID = Menu.FIRST + 1,
		UPDATE_ID = Menu.FIRST + 2,
		DELETE_ID = Menu.FIRST + 3,
		EMAIL_ID = Menu.FIRST + 4;
	
	private SimpleCursorAdapter daAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.property_list);
		
		this.getListView().setDividerHeight(2);

		populateList();
		
		registerForContextMenu(getListView());		
	}

	private void populateList()
	{
		String[] from = new String[] { TableProperties.COLUMN_ADDRESS };
		
		int[] to = new int[] { android.R.id.text1 };

		QuoterDBHelper DAO = new QuoterDBHelper(getApplicationContext());
		
		daAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, 
				DAO.getCursor(TABLE_PROPERTIES), from, to, 0);

		setListAdapter(daAdapter);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(Menu.NONE, ADD_ID, Menu.NONE, "New property quote")
			.setIcon(R.drawable.add)
			.setAlphabeticShortcut('n');
	
		menu.add(Menu.NONE, UPDATE_ID, Menu.NONE, "Update existing quote")
			.setIcon(R.drawable.add)
			.setAlphabeticShortcut('u');
		
		menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete quote")
			.setIcon(R.drawable.add)
			.setAlphabeticShortcut('d');
		
		menu.add(Menu.NONE, EMAIL_ID, Menu.NONE, "Email quote")
			.setIcon(R.drawable.add)
			.setAlphabeticShortcut('e');
			
		return (super.onCreateOptionsMenu(menu));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case ADD_ID:
			Intent intent = new Intent();
			
			intent.setClass(this, PropDetailActivity.class);
			
			QuoterDBHelper DAO = new QuoterDBHelper(this);

			intent.putExtra("propId", DAO.getMaxId(4));

			startActivity(intent);
//			addRoomType();
			return (true);
			
		case DELETE_ID:
//			deleteRoomType();
			return (true);
		}
		
		return (super.onOptionsItemSelected(item));
	}
	
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		
		LinearLayout container = (LinearLayout) findViewById(R.id.dashboard);
		
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			container.setOrientation(LinearLayout.HORIZONTAL);
		}
		else
		{
			container.setOrientation(LinearLayout.VERTICAL);
		}
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		String[] projection = { 
				TableProperties.COLUMN_ID_PROPERTY, 
				TableCities.COLUMN_ID_CITY,
				TableNbh.COLUMN_ID_NBH,
				TableProperties.COLUMN_ADDRESS, 
				TableProperties.COLUMN_DESCRIPTION,
				TableProperties.COLUMN_OWNER_URI,
				TableProperties.COLUMN_PICTURE 
				};
		
		CursorLoader cursorLoader = new CursorLoader (this,
				QuoterContentProvider.CONTENT_URI_PROPERTIES, projection, null, null, null);
		
		return cursorLoader;
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data)
	{
		//daAdapter.swapCursor(data);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		// Data not available anymore -> delete reference
		//daAdapter.swapCursor(null);
	}	
}
