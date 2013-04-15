package com.callisto.quoter.logic;

import com.callisto.quoter.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class RoomDetailTabhost extends TabActivity
{
	private TabHost tabHost;
	
	private static final int
		ADD_TAB = Menu.FIRST + 11,
		DELETE_TAB = Menu.FIRST + 12;

	private int z = 0;

	private long propId;
	
	private String roomType;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.room_tabhost);
		
		this.tabHost = getTabHost();

		Bundle extras = getIntent().getExtras();
	    
	    if(extras == null) 
	    {
	        propId = 0;
	    } 
	    else 
	    {
	        propId = extras.getLong("propId");
	        
	        roomType = extras.getString("roomType");
	    }
		
		Intent newTab = new Intent();

		newTab.putExtra("propId", this.propId);
		
		newTab.setClass(this, RoomDetailActivity.class);
		
		tabHost.addTab(
				tabHost.newTabSpec("Main")
				.setIndicator(roomType)
				.setContent(newTab)
				);
	}
	
	private void addTab()
	{
		Intent newTab = new Intent();
		
		newTab.putExtra("propId", this.propId);

		newTab.setClass(this, RoomDetailActivity.class);
		
		tabHost.addTab(
				tabHost.newTabSpec("NewRoomTab")
						.setIndicator("New room")
						.setContent(newTab)
				);

		Log.d("z", Integer.toString(z));
		
		++z;
	}

	// "Boss, we really cannot delete one a'dem tab gubbinz, so we hides 'em."
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
}
