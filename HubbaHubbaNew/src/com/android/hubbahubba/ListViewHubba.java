package com.android.hubbahubba;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;

public class ListViewHubba extends SherlockFragment {

	private HubbaDBAdapter dbHelper;
	//private SimpleCursorAdapter dataAdapter;
	private HubbaCursorAdapter dataAdapter;
	private ListView listView;
	private View rootView;
	private Context context;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
		
        // Inflate the layout for this fragment
		context = getActivity().getBaseContext();
        rootView =  inflater.inflate(R.layout.activity_list_view, container, false);

		Button addSpot = (Button) rootView.findViewById(R.id.AddSpot);
		addSpot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(getSherlockActivity(), AddLocation.class);
				intent.putExtra("FromPage", "ListViewHubba");
				ListViewHubba.this.startActivity(intent);
				//startActivity(intent);
			}
		});
		
		Spinner spinner = (Spinner) rootView.findViewById(R.id.spotTypeSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_row, R.id.text1, getResources().getStringArray(R.array.showSpotTypes));
		spinner.setAdapter(adapter);
		
		// Clean all data
		//dbHelper.deleteAllSpots();
		// Add some data
		/*
		dbHelper = new HubbaDBAdapter(context);
		dbHelper.open();
		dbHelper.insertSomeSpots();
		dbHelper.close();
		*/
		
		// Generate ListView from SQLite Database
		displayListView();
		return rootView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void displayListView() {
		
		dbHelper = new HubbaDBAdapter(context);
		dbHelper.open();

		Cursor cursor = dbHelper.fetchAllSpots();
		try{
			
			// The desired columns to be bound -- THESE ARE JUST HERE TO SEE, deprocated 
			/*
			String[] columns = new String[] { HubbaDBAdapter.KEY_NAME,
					HubbaDBAdapter.KEY_TYPE,HubbaDBAdapter.KEY_RATING,
					HubbaDBAdapter.KEY_DIFF, HubbaDBAdapter.KEY_LEVEL, HubbaDBAdapter.KEY_IMAGE };
	
			// the XML defined views which the data will be bound to
			int[] to = new int[] { R.id.txtTitle, R.id.txtType,
					R.id.txtOverallRating, R.id.txtDiffRating, R.id.txtPoRating, R.id.imgThumbnail };
			*/
			
			// create the adapter using the cursor pointing to the desired data
			// as well as the layout information
			dataAdapter = new HubbaCursorAdapter(getActivity().getApplicationContext(), cursor);
	
			listView = (ListView) rootView.findViewById(R.id.listView);
			// Assign adapter to ListView
			listView.setAdapter(dataAdapter);
	
			//short press is to view the spot (SpotPage.java)
			listView.setOnItemClickListener(new OnItemClickListener() {
	
				// @Override
				public void onItemClick(AdapterView<?> listView, View view,
						int position, long id) {
						//to-do open viewSpot
					
					int clickId;
					
					// TODO toaster
					//int duration = Toast.LENGTH_LONG;
					//Context context = getActivity().getBaseContext();
					
					Cursor cursor = (Cursor) listView.getItemAtPosition(position);
					clickId = Integer.valueOf(cursor.getString(cursor
							.getColumnIndexOrThrow("_id")));
					/*
					Toast toaster = Toast.makeText(context, "Name: " + String.valueOf(clickId), duration);
					toaster.show();
					*/
					/*
					 Toast.makeText(getSherlockActivity().getApplicationContext(),
					 clickId + " is the ID", Toast.LENGTH_SHORT).show();
					*/
					
					Bundle bundleData = new Bundle();
					bundleData.putInt("keyid", clickId);
					Intent intent = new Intent(getActivity().getApplicationContext(),
							SpotPage.class);
					intent.putExtras(bundleData);
					cursor.close();
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
					Intent intent = new Intent(getActivity().getApplicationContext(),
							EditActivity.class);
					intent.putExtras(bundleData);
					cursor.close();
					startActivity(intent);
				
			      return true;
			    }
			
			});
				
			
			EditText myFilter = (EditText) rootView.findViewById(R.id.SearchBar);
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
					return dbHelper.fetchSpotByName(constraint.toString());
				}
			});
			
		}finally{
			if(cursor != null){
	            cursor.close();
			}
		}
		dbHelper.close();
	}
	
	public void onResume() {
		dbHelper.open();
		Cursor c = dbHelper.fetchAllSpots();

		dataAdapter.changeCursor(c);
		super.onResume();
		//dbHelper.close();
		//c.close();
	}
	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		dbHelper.close();
	}
}