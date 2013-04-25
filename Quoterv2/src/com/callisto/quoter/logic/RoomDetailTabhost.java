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
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.callisto.quoter.R;
import com.callisto.quoter.database.QuoterDBHelper;
import com.callisto.quoter.database.TableRoomTypes;
import com.callisto.quoter.logic.AddRoomDialogWrapper;

// TODO Figure how to get text from a spinner linked to a database via an Adapter (note link to solution when done)
// COMPLETED: http://stackoverflow.com/questions/5787809/get-spinner-selected-items-text

// TODO Code data saving to database logic
// TODO Code data retrieval from database logic

/***
 *  * ***** DEPRECATED: getting rooms from DB here is USELESS, actual room info must be grabbed by RoomDetailActivity ******
 * PSEUDOCODE FOR DATABASE RETRIEVAL LOGIC
 * 
 * 
 * void getRoomsFromDB
 * {
 *     Cursor rooms = get rooms from database
 *     {
 *     		cursor getRooms(parameter1: property id, integer)
 *     		{
 *   			Run query: 
				SELECT 
					A._id_room AS _id, 
				    A._id_room_type,
				    A.width_x,
				    A.width_y,
				    A.pisos,
				    A.detalles,
				    A.imagen 
				FROM Ambientes AS A, Propiedades_ambientes AS P
				WHERE P._id_prop = parameter1;
 *     		}
 *     }
 *     
 *     while there are items in rooms
 *     {
 *     		item = get current item on rooms;
 *     
 *     		create new tab using item;
 *     }
 * }
 */

/***
 * PSEUDOCODE FOR DATABASE RETRIEVAL LOGIC, TRY TWO (Purpose: repopulate a previous quote, or create a new one)
 * 
 * 	integer quantity = get room count on database for a given property ID;
 * 
 * 	if (quantity greater than zero (meaning case: previously existing quote))
 * 	{
 * 		cursor rooms = get rooms from database;
 * 
 * 		for (int i = 0; i < quantity; i++)
 * 		{
 * 			integer roomId = get room identifier from cursor rooms for room matching i;
 *  
 *  		create intent for new tab / new room;
 *  
 *  		put extras in intent, containing propId and roomId;
 *  
 *  		call method creating new tab;
 * 		}	
 *  }
 *  else (meaning case: new quote)
 *  {
 *  	create intent for new tab / new room;
 *  
 *  	put extra in intent, containing propId;
 *  
 *  	call method creating new tab;
 *  }
 */

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
		daRoomTypeIdTemporary,	// "Zoggin' bad setup, boss, I know, but I can think a' no better right now."
		daRoomTypeId;
	
	private String daRoomType;
	
	private Spinner daSpinnerRoomTypes;
	
	private SimpleCursorAdapter daRoomTypesAdapter;
	
	Object daSpinnerSelekshun;

	OnItemSelectedListener spinnerListener;

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
	        
	        // DONE Code this on PropDetailActivity
	        daRoomTypeId = extras.getLong("roomTypeId");
	        
	        daRoomType = extras.getString("roomType");
	    }
		
		Intent newTab = new Intent();

		newTab.putExtra("propId", this.daPropId);
		
		// "We'z needin' dis 'ere fing to add up da new room into yer databaze, boss: da type indikator cannot be, um, 'null'."
		newTab.putExtra("roomTypeId", this.daRoomTypeId);
		
		newTab.setClass(this, RoomDetailActivity.class);
		
		tabHost.addTab(
				tabHost.newTabSpec("Main")
				.setIndicator(daRoomType)
				.setContent(newTab)
				);
		
		spinnerListener = new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id)
			{
				daRoomTypeId = id;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				
			}
		};
		
//		/*** TODO Log this: source for retrieval of row id:
//		 * http://stackoverflow.com/questions/11037256/get-the-row-id-of-an-spinner-item-populated-from-database
//		 */ 
//		daSpinnerRoomTypes.setOnItemSelectedListener(new OnItemSelectedListener()
//		{
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view,
//					int pos, long id)
//			{
//				daRoomTypeIdTemporary = id;
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent)
//			{
//				
//			}
//		});
		
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
	
	public long getDaRoomTypeId()
	{
		return daRoomTypeId;
	}

	public void setDaRoomTypeId(long daRoomTypeId)
	{
		this.daRoomTypeId = daRoomTypeId;
	}

	private void spawnTypeRequestDialog()
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		
		View addView = inflater.inflate(R.layout.add_room, null);
		
		final AddRoomDialogWrapper wrapper = new AddRoomDialogWrapper(addView);
		
		daSpinnerRoomTypes = wrapper.getSpinner();
		
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
						// TODO Figure how to get text from a spinner linked to a database via an Adapter (note link to solution when done)
						// COMPLETED: http://stackoverflow.com/questions/5787809/get-spinner-selected-items-text
						
						TextView t = (TextView) daSpinnerRoomTypes.getSelectedView();
						
						daRoomType = t.getText().toString();
						
						daRoomTypeId = daRoomTypeIdTemporary;
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
	
	private void doTabGubbinz()
	{
		spawnTypeRequestDialog();
		
		Intent newTab = new Intent();
		
		newTab.putExtra("propId", this.daPropId);

		/*** "YA GIT, DIS AIN'T GONNA DO DA JOB WITH NEW TAB GUBBINZ! Ya'z havin' to cook up sumfin' elze, or me Squiggof's eatin' tonight!"
		 * (KUFF!) "OWWWW! I hearz ya, boss... methinks somehow tellin' it da new value a' dat room type fing could work..."
		 * Follow-up:
		 * "Boss, we'z tryin' dis up: I've set up one a' dem 'AlertDialog' fings to ask up for da room type, as I did wiv da PropDetailActivity fing."
		 */
		newTab.putExtra("roomTypeId", this.daRoomTypeId);
		
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
		
		daSpinnerRoomTypes.setOnItemSelectedListener(spinnerListener);
		
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
