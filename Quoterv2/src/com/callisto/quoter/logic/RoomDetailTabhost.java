package com.callisto.quoter.logic;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.callisto.quoter.R;
import com.callisto.quoter.database.QuoterDBHelper;
import com.callisto.quoter.database.TableRoomTypes;

// TODO Figure how to get text from a spinner linked to a database via an Adapter (note link to solution when done)
// COMPLETED: http://stackoverflow.com/questions/5787809/get-spinner-selected-items-text

// TODO Code data saving to database logic
// TODO Code data retrieval from database logic

@SuppressWarnings("deprecation")
public class RoomDetailTabhost extends TabActivity
{
	private TabHost tabHost;
	
	private static final int
		ADD_TAB = Menu.FIRST + 11,
		DELETE_TAB = Menu.FIRST + 12,
		TABLE_ROOMTYPES = 16;

	private int z = 0;

	private long 
		daPropId, 
		daRoomTypeId;
	
	private String daRoomType;
	
	private Spinner daSpinnerRoomTypes;
	
	private SimpleCursorAdapter daRoomTypesAdapter;
	
	Object daSpinnerSelekshun;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.room_tabhost);
		
		this.tabHost = getTabHost();

		Bundle extras = getIntent().getExtras();
	    
	    if(extras == null) 
	    {
	        daPropId = 0;
	    } 
	    else 
	    {
	        daPropId = extras.getLong("propId");
	        
	        // TODO Code this on PropDetailActivity
	        daRoomTypeId = extras.getLong("propTypeId");
	        
	        daRoomType = extras.getString("roomType");
	    }
		
		Intent newTab = new Intent();

		newTab.putExtra("propId", this.daPropId);
		
		newTab.setClass(this, RoomDetailActivity.class);
		
		tabHost.addTab(
				tabHost.newTabSpec("Main")
				.setIndicator(daRoomType)
				.setContent(newTab)
				);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		/***
		 * PSEUDOCODE
		 * 
		 * - Get writable database
		 * - Prepare content values
		 * - Write stuff to database (uses daPropId)
		 * - Assign row id to daRoomId field
		 * - Close database
		 */
	}
	
	private void doTabGubbinz()
	{
		Intent newTab = new Intent();
		
		newTab.putExtra("propId", this.daPropId);

		newTab.setClass(this, RoomDetailActivity.class);
		
		tabHost.addTab(
				tabHost.newTabSpec("NewRoomTab")
						.setIndicator(daRoomType)
						.setContent(newTab)
				);

		Log.d("z", Integer.toString(z));
		
		++z;
	}
	
	private void addTab()
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		
		View addView = inflater.inflate(R.layout.add_room, null);
		
		final AddRoomDialogWrapper wrapper = new AddRoomDialogWrapper(addView);
		
		daSpinnerRoomTypes = wrapper.getSpinner();
		
//		daSpinnerRoomTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//		{
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view,
//					int pos, long id)
//			{
//				daSpinnerSelekshun = parent.getItemAtPosition(pos);
//				
//				System.out.println(daSpinnerSelekshun.toString());
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0)
//			{
//				// "Nothing 'appens 'ere, boss."
//			}
//		});
		
		populateRoomTypes();
		
		new AlertDialog.Builder(this)
			.setTitle("Select room type")
			.setView(addView)
			.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						TextView t = (TextView) daSpinnerRoomTypes.getSelectedView();
						
						daRoomType = t.getText().toString();
						
						doTabGubbinz();
						//startRoomsActivity(daPropId, t.getText().toString());
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

	// "Boss, we really cannot delete one a'dem tab fingz, so we hides 'em."
	private void deleteTab() 
	{
		int position = tabHost.getCurrentTab();
		Log.d("Position", Integer.toString(position));
		
		Log.d("Z val in delete()", Integer.toString(z));
		
		tabHost.getCurrentTabView().setVisibility(View.GONE);

		if (position > 0)
		{
			tabHost.setCurrentTab(position + 1);
			
			z -= 1;
			
			if (z < 0)
			{
				z = 0;
			}
		}
		else if (position == 0)
		{
			tabHost.setCurrentTab(position + 1);
			
			z = 0;
		}
		else if (position == z)
		{
			tabHost.setCurrentTab(z - 1);

			Log.d("Z value in final", "lol");
			Log.d("Pos", Integer.toString(position));
			Log.d("Z pos", Integer.toString(z));
		}		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
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
		case ADD_TAB:
			addTab();
			
			return (true);

		case DELETE_TAB:
			deleteTab();
			
			return (true);
		}
		
		return (super.onOptionsItemSelected(item));
	}

	private void populateRoomTypes()
	{
		String[] from = new String[] { TableRoomTypes.COLUMN_NAME };
		
		int[] to = new int[] { android.R.id.text1 };

		QuoterDBHelper DAO = new QuoterDBHelper(getApplicationContext());
		
		daRoomTypesAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, 
				DAO.getCursor(TABLE_ROOMTYPES), from, to, 0);
		
		daRoomTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		daSpinnerRoomTypes.setAdapter(daRoomTypesAdapter);
	}
	
	private void getRoomsOnProperty(int propId)
	{
		/***
		 * PSEUDOCODE
		 * 
		 * - Declare an array to store room identifiers
		 * - Run query on DB
		 * - 
		 */
	}
}
