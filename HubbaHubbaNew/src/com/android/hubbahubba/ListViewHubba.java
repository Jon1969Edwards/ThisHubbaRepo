package com.android.hubbahubba;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;


import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ListViewHubba extends SherlockFragment {

	private HubbaAdapter dataAdapter;
	private ListView listView;
	private View rootView;
	private Context context;
	private ArrayList<HashMap<String, String>> SpotsArray;
	Spinner spinner;
	boolean spinnerTouched = false;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
		
		// enable menu at bottom
		setHasOptionsMenu(true);
		
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
		
		spinner = (Spinner) rootView.findViewById(R.id.spotTypeSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_row, R.id.text1, getResources().getStringArray(R.array.showSpotTypes));
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	String type = spinner.getSelectedItem().toString();
		    	
	    		if(spinnerTouched && type.equalsIgnoreCase("Show All")){
		    		//type = "";
	    			// populate spots array
	    			Spot.getListOfSpots(listView, dataAdapter, SpotsArray, context);
		    	}
		    	else if(!type.equalsIgnoreCase("Show All")){
		    		spinnerTouched = true;
		    		// Take off the s
		    		if(type.substring(type.length() - 1).equalsIgnoreCase("s")){
		    			type = type.substring(0, type.length() - 1);
		    		}
		    		// TODO: CLEAR CURRENT ADAPTER
		    		Spot.updateListOfSpots(listView, dataAdapter, SpotsArray, context, type);
		    	}
	    		// else nothing (page loaded)
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    	// TODO: anything?
		    }

		});
		
		
		// Generate ListView from SQLite Database
		displayListView();

		return rootView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void displayListView() {
			
		SpotsArray = new ArrayList<HashMap<String, String>>();
		
		listView = (ListView) rootView.findViewById(R.id.listView);
		listView.setDivider(getResources().getDrawable(R.drawable.list_divider_hubba));
		listView.setDividerHeight(2);
		
		// populate spots array
		Spot.getListOfSpots(listView, dataAdapter, SpotsArray, context);
		
		//short press is to view the spot (SpotPage.java)
		listView.setOnItemClickListener(new OnItemClickListener() {
		
			public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
				Bundle bundleData = new Bundle();
				HashMap<String, String> spot = SpotsArray.get(position);
				
				//bundleData.putInt("keyid", 29);
				//TextView SpotID = (TextView) rootView.findViewById(R.id.spot_id);
				
				String spot_id = spot.get("id");
				
				bundleData.putString("spot_id", spot_id);
				
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
				
				//int clickId;
		
				//Cursor cursor = (Cursor) listView.getItemAtPosition(position);
				//clickId = Integer.valueOf(cursor.getString(cursor
				//		.getColumnIndexOrThrow("_id")));
		
				// Toast.makeText(getApplicationContext(),
				// clickId + " is the ID", Toast.LENGTH_SHORT).show();
		
				Bundle bundleData = new Bundle();
				bundleData.putInt("keyid", 29);
				Intent intent = new Intent(getActivity().getApplicationContext(),
						EditActivity.class);
				intent.putExtras(bundleData);
				startActivity(intent);
			
		      return true;
		    }
		
		});
		
		/*
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
		*/
	}
	
	public void onResume() {
		super.onResume();
		
		// populate spots array
		SpotsArray = null;
		SpotsArray = new ArrayList<HashMap<String, String>>();
		Spot.getListOfSpots(listView, dataAdapter, SpotsArray, context);
	}
	
	/* --- Start menu --- */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater = new MenuInflater(context);
		inflater.inflate(R.menu.list_action_items, menu);

		// set spinner style
		spinner = (Spinner) menu.findItem(R.id.action_filter).getActionView();
		spinner.setBackgroundDrawable(null);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				R.layout.spinner_row, R.id.text1, getResources()
						.getStringArray(R.array.showSpotTypes));

		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	String type = spinner.getSelectedItem().toString();
		    	
	    		if(spinnerTouched && type.equalsIgnoreCase("Show All")){
		    		//type = "";
	    			// populate spots array
	    			Spot.getListOfSpots(listView, dataAdapter, SpotsArray, context);
		    	}
		    	else if(!type.equalsIgnoreCase("Show All")){
		    		spinnerTouched = true;
		    		// Take off the s
		    		if(type.substring(type.length() - 1).equalsIgnoreCase("s")){
		    			type = type.substring(0, type.length() - 1);
		    		}
		    		// TODO: CLEAR CURRENT ADAPTER
		    		Spot.updateListOfSpots(listView, dataAdapter, SpotsArray, context, type);
		    	}
	    		// else nothing (page loaded)
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    	// TODO: anything?
		    }

		});

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_filter:
			// TODO: delete?
			return true;
		case R.id.action_add_spot:
			// TODO: add spot
			return true;
		case R.id.action_search:
			// TODO: search
			return true;
		case R.id.action_refresh:
			refreshList();
			// refresh
			return true;
		case R.id.action_help:
			Intent intent = new Intent(getActivity(), LeaveFeedback.class);
			startActivity(intent);
			// help action
			// TODO: maybe delete this?
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void refreshList() {
		String type = spinner.getSelectedItem().toString();
		if (type.equalsIgnoreCase("Show All")) {
			// type = "";
			Spot.getListOfSpots(listView, dataAdapter, SpotsArray, context);
		} else if (!type.equalsIgnoreCase("Show All")) {
			spinnerTouched = true;
			// Take off the s
			if (type.substring(type.length() - 1).equalsIgnoreCase("s")) {
				type = type.substring(0, type.length() - 1);
			}
			// TODO: CLEAR CURRENT ADAPTER
			Spot.updateListOfSpots(listView, dataAdapter, SpotsArray, context, type);
		}
	}
	
	/* --- end menu --- */
	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		// populate spots array
		SpotsArray = null;
	}
}