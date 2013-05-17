package com.adams.hubbadb;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	private HubbaDBAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbHelper = new HubbaDBAdapter(this);
		dbHelper.open();

		Button addSpot = (Button) findViewById(R.id.addspot);
		addSpot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, AddLocation.class);
				startActivity(intent);
			}
		});

		// Clean all data
		//dbHelper.deleteAllSpots();
		// Add some data
		//dbHelper.insertSomeSpots();

		// Generate ListView from SQLite Database
		displayListView();

	}

	private void displayListView() {

		Cursor cursor = dbHelper.fetchAllSpots();

		// The desired columns to be bound
		String[] columns = new String[] { HubbaDBAdapter.KEY_NAME,
				HubbaDBAdapter.KEY_TYPE, HubbaDBAdapter.KEY_RATING,
				HubbaDBAdapter.KEY_DIFF, HubbaDBAdapter.KEY_LEVEL, HubbaDBAdapter.KEY_IMAGE };

		// the XML defined views which the data will be bound to
		int[] to = new int[] { R.id.txtTitle, R.id.txtType,
				R.id.txtOverallRating, R.id.txtDiffRating, R.id.txtPoRating, R.id.imgThumbnail };

		// create the adapter using the cursor pointing to the desired data
		// as well as the layout information
		dataAdapter = new SimpleCursorAdapter(this, R.layout.item_row,
				cursor, columns, to, 0);

		listView = (ListView) findViewById(R.id.listView1);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		/*
		 * listView.setOnItemClickListener(new OnItemClickListener() { //
		 * @Override public void onItemClick(AdapterView<?> listView, View view,
		 * int position, long id) { // Get the cursor, positioned to the
		 * corresponding row in the result set Cursor cursor = (Cursor)
		 * listView.getItemAtPosition(position);
		 * 
		 * // Get the state's capital from this row in the database. String
		 * spotName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
		 * Toast.makeText(getApplicationContext(), spotName,
		 * Toast.LENGTH_SHORT).show();
		 * 
		 * } });
		 */

		
		//short press is to view the spot (SpotPage.java)
		listView.setOnItemClickListener(new OnItemClickListener() {

			// @Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
					//to-do open viewSpot
				
				int clickId;

				Cursor cursor = (Cursor) listView.getItemAtPosition(position);
				clickId = Integer.valueOf(cursor.getString(cursor
						.getColumnIndexOrThrow("_id")));

				// Toast.makeText(getApplicationContext(),
				// clickId + " is the ID", Toast.LENGTH_SHORT).show();

				Bundle bundleData = new Bundle();
				bundleData.putInt("keyid", clickId);
				Intent intent = new Intent(MainActivity.this,
						SpotPage.class);
				intent.putExtras(bundleData);
				startActivity(intent);
			}
		});
		
		
		//long press is to edit the spot (EditActivity.java);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position,long id)
		    {
				
				int clickId;

				Cursor cursor = (Cursor) listView.getItemAtPosition(position);
				clickId = Integer.valueOf(cursor.getString(cursor
						.getColumnIndexOrThrow("_id")));

				// Toast.makeText(getApplicationContext(),
				// clickId + " is the ID", Toast.LENGTH_SHORT).show();

				Bundle bundleData = new Bundle();
				bundleData.putInt("keyid", clickId);
				Intent intent = new Intent(MainActivity.this,
						EditActivity.class);
				intent.putExtras(bundleData);
				startActivity(intent);
			
		      return true;
		    }
		
		});
			
		

		EditText myFilter = (EditText) findViewById(R.id.myFilter);
		myFilter.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				dataAdapter.getFilter().filter(s.toString());
			}
		});

		dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
			public Cursor runQuery(CharSequence constraint) {
				return dbHelper.fetchSpotByType(constraint.toString());
			}
		});

	}

	@Override
	protected void onResume() {
		dbHelper.open();
		Cursor c = dbHelper.fetchAllSpots();

		dataAdapter.changeCursor(c);
		super.onResume();
	}

}