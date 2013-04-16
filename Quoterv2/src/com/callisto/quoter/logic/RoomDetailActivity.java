package com.callisto.quoter.logic;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.callisto.quoter.R;
import com.callisto.quoter.contentprovider.QuoterContentProvider;
import com.callisto.quoter.database.QuoterDBHelper;
import com.callisto.quoter.database.TableRoomTypes;

public class RoomDetailActivity extends Activity
	implements LoaderManager.LoaderCallbacks<Cursor>
{
	private static final int TABLE_ROOMTYPES = 16;
	
//	private TabHost tabHost = null;

	private Spinner daSpinnerType;
	
	// "Dis 'ere gubbinz are fer da kamera to do work propa."
	private ImageView daImageView;
	private Uri daURI;
	private String daPhotoPath;

//	private Uri quoteUri;

	private SimpleCursorAdapter daAdapter;

	private static final int
		PICK_IMAGE = 0,
		ADD_ID = Menu.FIRST + 1,
		DELETE_ID = Menu.FIRST + 3;					
//		ADD_TAB = Menu.FIRST + 11;

	private long propId;
	private String initialRoomType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.room_detail);
		
		Bundle extras = getIntent().getExtras();
	    
	    if(extras == null) 
	    {
	        propId = 0;
	    } 
	    else 
	    {
	        propId = extras.getLong("propId");
	        
	        initialRoomType = extras.getString("roomType");
	    }
		
		daSpinnerType = (Spinner) findViewById(R.id.spnPropType);
		
		populateRoomTypes();
		
		daImageView = (ImageView) findViewById(R.id.imgDisplayImage);
		
		daImageView.setOnClickListener(new View.OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				Intent camera = new Intent();
				
				camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				
				camera.putExtra("crop", "true");
				
				//File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				
				daURI = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myFile.jpg"));
				
				camera.putExtra(MediaStore.EXTRA_OUTPUT, daURI);
				
				startActivityForResult(camera, PICK_IMAGE);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		menu.add(Menu.NONE, ADD_ID, Menu.NONE, "New room type")
			.setIcon(R.drawable.add)
			.setAlphabeticShortcut('t');

		return (super.onCreateOptionsMenu(menu));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case ADD_ID:
			addRoomType();
			return (true);
			
		case DELETE_ID:
			deleteRoomType();
			return (true);
		}
		
		return (super.onOptionsItemSelected(item));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == Activity.RESULT_OK)
		{
			if (requestCode == PICK_IMAGE)
			{
				Cursor cursor = getContentResolver().query(
						Media.EXTERNAL_CONTENT_URI, new String[]
								{
									Media.DATA, 
									Media.DATE_ADDED, 
									MediaStore.Images.ImageColumns.ORIENTATION
								}, 
								Media.DATE_ADDED, 
								null, 
								"date_added ASC"
				);
				
				if (cursor != null && cursor.moveToFirst())
				{
					do
					{
						daURI = Uri.parse(cursor.getString(cursor.getColumnIndex(Media.DATA)));
						daPhotoPath = daURI.toString();
					}
					while (cursor.moveToNext());
					cursor.close();
				}
				if (data != null)
				{
					if (data.hasExtra("data"))
					{
						Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
						
						daImageView.setImageBitmap(thumbnail);
					}
					else
					{
						System.out.println("Intent bundle does not have the 'data' Extra");
						
						int width = daImageView.getWidth();
						int height = daImageView.getHeight();
						
						BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
						
						factoryOptions.inJustDecodeBounds = true;
						
						BitmapFactory.decodeFile(/*mURI.getPath()*/ daPhotoPath, factoryOptions);
						
						int imageWidth = factoryOptions.outWidth;
						int imageHeight = factoryOptions.outHeight;
						
						// Determine how much to scale down the image
						int scaleFactor = Math.min(
								imageWidth/width,
								imageHeight/height
								);
						
						// Decode the image file into a Bitmap sized to fill view
						
						factoryOptions.inJustDecodeBounds = false;
						factoryOptions.inSampleSize = scaleFactor;
						factoryOptions.inPurgeable = true;
						
						Bitmap bitmap = BitmapFactory.decodeFile(/*mURI.getPath()*/ daPhotoPath, factoryOptions);
						
						daImageView.setImageBitmap(bitmap);
					}
				}
			}
		}
		else 
		{
			System.out.println("Picture taking activity NOT returning RESULT_OK");
		}
	}

	private void deleteRoomType()
	{
		// TODO Implement this
		
	}

	private void populateRoomTypes()
	{
		String[] from = new String[] { TableRoomTypes.COLUMN_NAME };
		
		int[] to = new int[] { android.R.id.text1 };

		QuoterDBHelper DAO = new QuoterDBHelper(getApplicationContext());
		
		daAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_dropdown_item, 
				DAO.getCursor(TABLE_ROOMTYPES), from, to, 0);
		
		daAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		daSpinnerType.setAdapter(daAdapter);
	}
	
	private void addRoomType()
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		
		View addView = inflater.inflate(R.layout.add_type, null);
		
		final AddRoomTypeDialogWrapper wrapper = new AddRoomTypeDialogWrapper(addView);
		
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
	
	private void processAddType(AddRoomTypeDialogWrapper wrapper) 
	{
	    ContentValues values = new ContentValues();
	    
	    values.put(TableRoomTypes.COLUMN_NAME, wrapper.getName());
	    
    	/*quoteUri = */ getContentResolver().insert(QuoterContentProvider.CONTENT_URI_ROOM_TYPES, values);
				
		populateRoomTypes();
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
		
		daAdapter.swapCursor(mask);
	}
	
	@Override
	public void onLoaderReset(Loader<Cursor> loader)
	{
		// Data not available anymore -> delete reference
		daAdapter.swapCursor(null);
	}

	class AddRoomTypeDialogWrapper
	{
		EditText nameField = null;
		View base = null;
		
		AddRoomTypeDialogWrapper(View base)
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
	
	class AddRoomDialogWrapper
	{
		Spinner spinnerType = null;
		View base = null;
		Object item;
		
		AddRoomDialogWrapper(View base)
		{
			this.base = base;
			spinnerType = (Spinner) base.findViewById(R.id.spnRoomType);
		}

		private Spinner getSpinner()
		{
			if (spinnerType == null)
			{
				spinnerType = (Spinner) base.findViewById(R.id.spnRoomType);
			}
			
			return (spinnerType);
		}
		
		public Object getSelectedItem()
		{
			return item;
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
