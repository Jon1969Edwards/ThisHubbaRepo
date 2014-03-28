package com.android.hubbahubba;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;

public class ListViewHubba extends SherlockFragment {

	//private SimpleCursorAdapter dataAdapter;
	private HubbaAdapter dataAdapter;
	private ListView listView;
	private View rootView;
	private Context context;
	private ArrayList<HashMap<String, String>> SpotsArray;

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
		
		// Generate ListView from SQLite Database
		displayListView();
		return rootView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void displayListView() {
			
		SpotsArray = new ArrayList<HashMap<String, String>>();
		
		listView = (ListView) rootView.findViewById(R.id.listView);
		
		// populate spots array
		Spot.getListOfSpots(listView, dataAdapter, SpotsArray, context);
		
		//short press is to view the spot (SpotPage.java)
		listView.setOnItemClickListener(new OnItemClickListener() {
		
			public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
				Bundle bundleData = new Bundle();
				
				bundleData.putInt("keyid", 29);
				
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
		//SpotsArray = new ArrayList<HashMap<String, String>>();
		//Spot.getListOfSpots(listView, dataAdapter, SpotsArray, context);
	}
	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}