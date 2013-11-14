package com.android.hubbahubba;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.actionbarsherlock.app.SherlockFragment;

public class NewListView extends SherlockFragment {

	private HubbaDBAdapter dbHelper;
	private BaseAdapter dataAdapter;
	private ListView listView;
	private View rootView;
	List<String> titles = new ArrayList<String>();
	// List<String> types = new ArrayList<String>();
	// private double[] lats;		Used later for distance away
	// private double[] longs;
	List<String> rats = new ArrayList<String>();
	List<String> difs = new ArrayList<String>();
	List<String> busts = new ArrayList<String>();
	List<String> images = new ArrayList<String>();
	SherlockFragment contextual;
	// List<String> distances = new ArrayList<String>();


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
		
           // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.activity_list_view, container, false);
		dbHelper = new HubbaDBAdapter(getActivity().getBaseContext());
		dbHelper.open();
		contextual = this;

		Button addSpot = (Button) rootView.findViewById(R.id.AddSpot);
		addSpot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(getSherlockActivity(), AddLocation.class);
				intent.putExtra("FromPage", "ListViewHubba");
				NewListView.this.startActivity(intent);
				//startActivity(intent);
			}
		});
		
		/* FOR SPINNER
		Context mContext = getActivity().getApplicationContext();
		Spinner spinner = (Spinner) rootView.findViewById(R.id.spotTypeSpinner);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext, R.layout.spinner_row, R.array.showSpotTypes);
		spinner.setAdapter(adapter);
		*/
		
		// Clean all data
		// dbHelper.deleteAllSpots();
		// Add some data
		// dbHelper.insertSomeSpots();

		// Generate ListView from SQLite Database
		displayListView();
		return rootView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void displayListView() {
		
		 // Fill arrays
		 Context context = getActivity().getApplicationContext();
		 dbHelper = new HubbaDBAdapter(context);
		 dbHelper.open();
		 Cursor c = dbHelper.fetchAllSpots();
		 int counter = 0;

		 do {
		 	//latitude =  Double.parseDouble(c.getString(3) );
		 	//longitude =  Double.parseDouble(c.getString(4) );
			titles.add(c.getString(1));
		 	//types.add(c.getString(2));
		 	rats.add(c.getString(5));
		 	difs.add(c.getString(6));
		 	busts.add(c.getString(7));
		 	images.add(c.getString(7));	 	
	
		 	/*
		 	// FOR TOAST
			Context context1 = getActivity().getApplicationContext();
			// FOR TOAST
		 	String lat = Double.toString(latitude);
		    String lng = Double.toString(longitude);
		    String count = Double.toString(counter);
			int duration = Toast.LENGTH_LONG;
			
			Toast toast = Toast.makeText(context1, lat + ' ' + lng + ' ' + count, duration);
			toast.show();
			*/
		 	
		 	// ADDS SPOTS TO MAP	 	
		 	
	        counter++;
		 }while (c.moveToNext());
		 dbHelper.close();

		// create the adapter using the cursor pointing to the desired data
		// as well as the layout information
		dataAdapter = new NewListViewAdapter(contextual, titles, rats, difs, busts, images);

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

				Cursor cursor = (Cursor) listView.getItemAtPosition(position);
				clickId = Integer.valueOf(cursor.getString(cursor
						.getColumnIndexOrThrow("_id")));
				/*
				 Toast.makeText(getSherlockActivity().getApplicationContext(),
				 clickId + " is the ID", Toast.LENGTH_SHORT).show();
				*/
				
				Bundle bundleData = new Bundle();
				bundleData.putInt("keyid", clickId);
				Intent intent = new Intent(getActivity().getApplicationContext(),
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
				Intent intent = new Intent(getActivity().getApplicationContext(),
						EditActivity.class);
				intent.putExtras(bundleData);
				startActivity(intent);
			
		      return true;
		    }
		
		});
			
		
		
		EditText myFilter = (EditText) rootView.findViewById(R.id.SearchBar);
		/*
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
*/
	}
	
	
	public void onResume() {
		//dbHelper.open();
		//Cursor c = dbHelper.fetchAllSpots();

		//dataAdapter.changeCursor(c);
		super.onResume();
	}

}