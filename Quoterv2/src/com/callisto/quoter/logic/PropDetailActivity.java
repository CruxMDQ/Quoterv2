package com.callisto.quoter.logic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.callisto.quoter.R;
import com.callisto.quoter.contentprovider.QuoterContentProvider;
import com.callisto.quoter.database.QuoterDBHelper;
import com.callisto.quoter.database.TablePropTypes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PropDetailActivity extends Activity implements LocationListener
{
	static final int PICK_REQUEST = 70003;

	private static final int TABLE_PROP_TYPES = 13,
		ADD_ID = Menu.FIRST + 1,
		DELETE_ID = Menu.FIRST + 3;					

	Button btnLocation, btnOwner, btnRooms;
	TextView txtOwner;
	
	Spinner daSpinnerType;
	
	// "Stores info 'bout that zoggin' owner, boss."
	Uri daContact;
	
	private SimpleCursorAdapter daAdapter;

	long daPropId;

	// "We be needing 'em fer da map to do its job, boss."
	private LocationManager daLocationManager;
    private String daProvider;
	private double daCurrentLat, daCurrentLong;
//	private GoogleMap daMap;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prop_detail);
	
		daSpinnerType = (Spinner) findViewById(R.id.spinnerType);
		
		btnLocation = (Button) findViewById(R.id.btnLocation);
		btnRooms = (Button) findViewById(R.id.btnRooms);
		
		txtOwner = (TextView) findViewById(R.id.txtOwner);
		btnOwner = (Button) findViewById(R.id.btnOwner);

		btnRooms.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				startRoomsActivity();
			}
		});
		
		btnOwner.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				pickContact(v);
			}
		});
		
		txtOwner.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				if (txtOwner.getText() != "")
				{
					viewContact(v);
				}
			}
		});
		
		btnLocation.setOnClickListener(new OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				openGps();
			}
		});
		
		populateSpinner();
		
	    Bundle extras = getIntent().getExtras();
	    
	    if(extras == null) 
	    {
	        daPropId = 0;
	    } 
	    else 
	    {
	        daPropId = extras.getLong("propId");
	    }
	    
	    doMapGubbinz();
	}
	
	/***
	 * (Futile) Sources for contact retrieval: 
	 * http://www.stackoverflow.com/questions/10977887/how-to-get-contact-name-number-and-emai-id-from-a-list-of-contacts
	 * http://www.stackoverflow.com/questions/4993063/how-to-call-android-contacts-list-and-select-one-phone-number-from-its-details-s
	 * http://www.stackoverflow.com/questions/866769/how-to-call-android-contacts-list
	 * http://www.stackoverflow.com/questions/1721279/how-to-read-contacts-on-android-2.0
	 * 
	 * Solution:
	 * http://stackoverflow.com/questions/11781618/trying-to-insert-contact-into-edittext-using-contact-picker
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
//				Cursor cursor = null;
				
				try
				{
		            if (data != null) 
		            {
		            	Uri contactData = data.getData();

		                try 
		                {		                         
		                	String id = contactData.getLastPathSegment();
		                	
		                	String[] columns = { 
		                			Phone.DATA, 
		                			Phone.DISPLAY_NAME
		                			};
		                	
		                	Cursor phoneCur = getContentResolver()
	                                 .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                     columns,
                                     ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                         + " = ?", new String[] { id },
                                     null);
		                    
	                        String Name = null;

	                        if (phoneCur.moveToFirst())
	                        {
	                            Name = phoneCur.getString(phoneCur.getColumnIndex(Phone.DISPLAY_NAME));
	                        }

	                        phoneCur.close();

                        	txtOwner.setText(Name);
	                     } 
		                 catch (Exception e) 
		                 {
	                         Log.e("FILES", "Failed to get phone data", e);
	                     }
	                 }				
				}
				catch(Exception e)
				{
					System.out.println("Cannot retrieve contact info");
				}
//				finally
//				{
//					if (cursor != null)
//					{
//						cursor.close();
//					}
//				}
			}
			default:
				break;			
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(Menu.NONE, ADD_ID, Menu.NONE, "New property type")
			.setIcon(R.drawable.add)
			.setAlphabeticShortcut('n');

		return (super.onCreateOptionsMenu(menu));
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case ADD_ID:
			addRoomType();
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
	
	/* Request updates at startup */
	@Override
	protected void onResume() {
	    super.onResume();
	    daLocationManager.requestLocationUpdates(daProvider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
	    super.onPause();
	    daLocationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location)
	{
		daCurrentLat = location.getLatitude();
		daCurrentLong = location.getLongitude();
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{

	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}	

	/**
	 * Research log:
	 * - stackoverflow.com/questions/14659705/android-maps-api-v2-in-dialog [not working: map object not instantiated]
	 * - stackoverflow.com/questions/13733299/initialize-mapfragment-programmatically-with-maps-api-v2
	 * 
	 */
	private void openGps()
	{
		LayoutInflater inflater = LayoutInflater.from(this);

		LatLng here = new LatLng(daCurrentLat, daCurrentLong);

		MapFragment fragment = PreparedMapFragment.newInstance(here);
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		transaction.add(R.id.mapView, fragment)
			.commit();
		
		//daMap = fragment.getMap();

		View gps = inflater.inflate(R.layout.location_gps, null);
		
		new AlertDialog.Builder(this)
			.setTitle("Property location")
			.setView(gps)
			.show();

//		Marker currentLoc = daMap.addMarker(new MarkerOptions().position(here).title("Ubicación actual"));
//		
//		daMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 15));
//		
//		daMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}
	
	private void addRoomType()
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		
		View addView = inflater.inflate(R.layout.add_type, null);
		
		final AddTypeDialogWrapper wrapper = new AddTypeDialogWrapper(addView);
		
		new AlertDialog.Builder(this)
			.setTitle("New property type")
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
	
	private void processAddType(AddTypeDialogWrapper wrapper) 
	{
	    ContentValues values = new ContentValues();
	    
	    values.put(TablePropTypes.COLUMN_NAME, wrapper.getName());
	    
    	/*quoteUri = */ getContentResolver().insert(QuoterContentProvider.CONTENT_URI_PROP_TYPES, values);
				
		populateSpinner();
	}

	private void populateSpinner()
	{
		String[] from = new String[] { TablePropTypes.COLUMN_NAME };
		
		int[] to = new int[] { android.R.id.text1 };

		QuoterDBHelper DAO = new QuoterDBHelper(getApplicationContext());
		
		daAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, 
				DAO.getCursor(TABLE_PROP_TYPES), from, to, 0);
		
		daAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		daSpinnerType.setAdapter(daAdapter);
	}

	public void startRoomsActivity()
	{
		Intent intent = new Intent();
		
		intent.setClass(this, RoomDetailTabhost.class);
		
		intent.putExtra("propId", this.daPropId);

		startActivity(intent);
	}

	public void pickContact(View v)
	{
		Intent i = new Intent(Intent.ACTION_PICK,
				Contacts.CONTENT_URI);
		
		startActivityForResult(i, PICK_REQUEST);
	}
	
	// "Urrr... *AHEM* Dis 'ere be yer gubbinz for managing da map, boss. Ye, AGAIN."
	public void doMapGubbinz()
	{
	    daLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	   
	    Criteria criteria = new Criteria();
	    
	    daProvider = daLocationManager.getBestProvider(criteria, false);
	    
        daLocationManager.requestLocationUpdates(daProvider, 400, 1, this);
        
	    Location location = daLocationManager.getLastKnownLocation(daProvider);		
	    
	    if (location != null) 
	    {
	    	System.out.println("Provider " + daProvider + " has been selected.");
	    	onLocationChanged(location);
	    } 
		daCurrentLat = location.getLatitude();
		daCurrentLong = location.getLongitude();
	}
	
	public void viewContact(View v)
	{
		startActivity(new Intent(Intent.ACTION_VIEW, daContact));
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
	
	static public class PreparedMapFragment extends MapFragment
	{
		static MapFragment frag;
		
		public PreparedMapFragment() 
		{
			super();
		}
		
		public static MapFragment newInstance(LatLng pos)
		{
			frag = new MapFragment();
			
			UiSettings settings = frag.getMap().getUiSettings();
			
			settings.setAllGesturesEnabled(false);
			settings.setMyLocationButtonEnabled(false);
			
			frag.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
			
			frag.getMap().addMarker(new MarkerOptions().position(pos).title("Ubicación actual"));

			frag.getMap().animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

			return frag;
		}
		
//		@Override
//		public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2)
//		{
//			View v = super.onCreateView(arg0, arg1, arg2);
//			initMap();
//			return v;
//		}
//
//		private void initMap()
//		{
//			UiSettings settings = getMap().getUiSettings();
//			
//			settings.setAllGesturesEnabled(false);
//			settings.setMyLocationButtonEnabled(false);
//			
//			getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(daCurrentPos, 16));
//			
//			getMap().addMarker(new MarkerOptions().position(daCurrentPos).title("Ubicación actual"));
//
//			getMap().animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
//		}
	}
}
