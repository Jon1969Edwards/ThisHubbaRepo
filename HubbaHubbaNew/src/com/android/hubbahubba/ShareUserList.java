package com.android.hubbahubba;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ShareUserList extends Activity {

	private HubbaUserAdapter dataAdapter;
	private ListView listView;
	private Context context;
	private ArrayList<HashMap<String, String>> userArray;
	Spinner spinner;
	boolean spinnerTouched = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_user_list_view);
		context = getApplicationContext();
		
		displayListView();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void displayListView() {
			
		userArray = new ArrayList<HashMap<String, String>>();
		
		listView = (ListView) findViewById(R.id.listView);
		
		listView.setDivider(getResources().getDrawable(R.drawable.list_divider_hubba));
		listView.setDividerHeight(2);
		
		
		// populate spots array
		Spot.getListOfUsers(listView, dataAdapter, userArray, context);
		// TODO: get list of users
		
		//short press is to view the spot (SpotPage.java)
		listView.setOnItemClickListener(new OnItemClickListener() {
		
			public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
				HashMap<String, String> user = userArray.get(position);
				
				String fb_user_id = user.get("fb_user_id");
				String spot_id = getIntent().getStringExtra("spot_id");
				
				//Toast.makeText(context, "spot_id = " + spot_id + "\n FBID = " + fb_user_id, Toast.LENGTH_LONG).show();
				
				User.shareSpot(context, spot_id, fb_user_id);
			}
		});
	}
	
	public void onResume() {
		super.onResume();
		
		// populate spots array
		//userArray = null;
		//userArray = new ArrayList<HashMap<String, String>>();
		
		// TODO: get list of all users (probs not all, maybes wait for a search)
		//Spot.getListOfSpots(listView, dataAdapter, userArray, context);
	}
//	
//	/* --- Start menu --- */
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		//inflater = new MenuInflater(context);
//		inflater.inflate(R.menu.list_action_items, menu);
//
//		// set spinner style
//		spinner = (Spinner) menu.findItem(R.id.action_filter).getActionView();
//		spinner.setBackgroundDrawable(null);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//				R.layout.spinner_row, R.id.text1, getResources()
//						.getStringArray(R.array.showSpotTypes));
//
//		spinner.setAdapter(adapter);
//
//		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//		    @Override
//		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//		    	String type = spinner.getSelectedItem().toString();
//		    	
//	    		if(spinnerTouched && type.equalsIgnoreCase("Show All")){
//		    		//type = "";
//	    			// populate spots array
//	    			Spot.getListOfSpots(listView, dataAdapter, userArray, context);
//		    	}
//		    	else if(!type.equalsIgnoreCase("Show All")){
//		    		spinnerTouched = true;
//		    		// Take off the s
//		    		if(type.substring(type.length() - 1).equalsIgnoreCase("s")){
//		    			type = type.substring(0, type.length() - 1);
//		    		}
//		    		// TODO: CLEAR CURRENT ADAPTER
//		    		Spot.updateListOfSpots(listView, dataAdapter, userArray, context, type);
//		    	}
//	    		// else nothing (page loaded)
//		    }
//
//		    @Override
//		    public void onNothingSelected(AdapterView<?> parentView) {
//		        // your code here
//		    	// TODO: anything?
//		    }
//
//		});
//
//		super.onCreateOptionsMenu(menu, inflater);
//	}
//	
	public void refreshList() {
		
		/*
		String type = spinner.getSelectedItem().toString();
		
		// reset array
		userArray = new ArrayList<HashMap<String, String>>();
		if (type.equalsIgnoreCase("Show All")) {
			// type = "";
			Spot.getListOfSpots(listView, dataAdapter, userArray, context);
		} else if (!type.equalsIgnoreCase("Show All")) {
			spinnerTouched = true;
			// Take off the s
			if (type.substring(type.length() - 1).equalsIgnoreCase("s")) {
				type = type.substring(0, type.length() - 1);
			}
			// TODO: CLEAR CURRENT ADAPTER
			Spot.updateListOfSpots(listView, dataAdapter, userArray, context, type);
		}
		*/
		// TODO: refresh list of users
	}
	
	/* --- end menu --- */
	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		// populate spots array
		//userArray = null;
	}
}