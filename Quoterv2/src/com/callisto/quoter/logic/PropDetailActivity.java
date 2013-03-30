package com.callisto.quoter.logic;

import com.callisto.quoter.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PropDetailActivity extends Activity
{
	static final int PICK_REQUEST = 70003;

	Button btnOwner;
	TextView txtOwner;
	
	Uri contact;
	
	long propId;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prop_detail);
	
		txtOwner = (TextView) findViewById(R.id.txtOwner);
		btnOwner = (Button) findViewById(R.id.btnOwner);
		
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
	
	public void startRoomsActivity()
	{
		Intent intent = new Intent();
		
		intent.setClass(this, RoomDetailTabhost.class);
		
		intent.putExtra("propId", this.propId);

		startActivity(intent);
	}

	public void pickContact(View v)
	{
		Intent i = new Intent(Intent.ACTION_PICK,
				Contacts.CONTENT_URI);
		
		startActivityForResult(i, PICK_REQUEST);
	}
	
	public void viewContact(View v)
	{
		startActivity(new Intent(Intent.ACTION_VIEW, contact));
	}
	
	/***
	 * Sources for contact retrieval: 
	 * http://www.stackoverflow.com/questions/10977887/how-to-get-contact-name-number-and-emai-id-from-a-list-of-contacts
	 * http://www.stackoverflow.com/questions/4993063/how-to-call-android-contacts-list-and-select-one-phone-number-from-its-details-s
	 */
	@Override
	protected void onActivityResult(
			int requestCode,
			int resultCode,
			Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			switch(requestCode)
			{
			case PICK_REQUEST:
			{
				Cursor cursor = null;
			
				String name = "";
				
				try
				{
					contact = data.getData();
					
					//Log.v(DEBUG_TAG, "Got a contact result : " + contact.toString());

//					String id = contact.getLastPathSegment();

					cursor = getContentResolver().query(contact, new String[] { ContactsContract.Contacts.DISPLAY_NAME }, null, null, null);
					
					int nameId = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
					
					name = cursor.getString(nameId);
				}
				catch(Exception e)
				{
					System.out.println("Cannot retrieve contact info");
				}
				finally
				{
					if (cursor != null)
					{
						cursor.close();
					}
					
					txtOwner.setText(name);
				}
			//	txtOwner.setText(contact.getFragment().)
				
			//	viewButton.setEnabled(true);
			}
			default:
				break;
			
			}
		}
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
}
