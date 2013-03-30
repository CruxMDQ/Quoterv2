package com.callisto.quoter.logic;

import android.app.Activity;
import android.os.Bundle;

public class PropDetailActivity extends Activity
{
	long propId;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);
		
	    Bundle extras = getIntent().getExtras();
	    
	    if(extras == null) 
	    {
	        propId = 0;
	    } 
	    else 
	    {
	        propId = extras.getLong("propId");
	    }
	}
}
