package com.android.hubbahubba;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ListViewHubba extends SherlockFragment {

	private HubbaAdapter dataAdapter;
	private ListView listView;
	private LinearLayout header;
	private View rootView;
	private Context context;
	private ArrayList<HashMap<String, String>> SpotsArray;
	Spinner spinner;
	boolean spinnerTouched = false;
    private boolean clickable = false;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
		
		// enable menu at bottom
		setHasOptionsMenu(true);
		
        // Inflate the layout for this fragment
		context = getActivity().getBaseContext();
        rootView =  inflater.inflate(R.layout.activity_list_view, container, false);

		// Generate ListView from SQLite Database
		//displayListView();

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
                Bundle bundleData = new Bundle();
                HashMap<String, String> spot = SpotsArray.get(position);
                //int clickId;
                String spot_id = spot.get("id");


                //Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                //clickId = Integer.valueOf(cursor.getString(cursor
                //		.getColumnIndexOrThrow("_id")));

                // Toast.makeText(getApplicationContext(),
                // clickId + " is the ID", Toast.LENGTH_SHORT).show();

                // DELETE spot
                Spot.deleteSpotByID(context, spot_id);

                refreshList();
				/*
				bundleData.putInt("keyid", 29);
				Intent intent = new Intent(getActivity().getApplicationContext(),
						EditActivity.class);
				intent.putExtras(bundleData);
				startActivity(intent);
				*/
                return true;
            }

        });

		return rootView;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /*
	public void onResume() {
		super.onResume();
		
		// populate spots array
		SpotsArray = null;
		SpotsArray = new ArrayList<HashMap<String, String>>();
        Log.i("TASK", "RESUME LIST");

        //Spot.getListOfSpots(listView, dataAdapter, SpotsArray, context);
	}
	*/

	/* --- Start menu --- */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//inflater = new MenuInflater(context);
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
                if (spinnerTouched && type.equalsIgnoreCase("Show All")) {
                    refreshList();
                } else if (!type.equalsIgnoreCase("Show All")) {
                    spinnerTouched = true;
                    refreshList();
                }
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
			Intent addintent = new Intent(getSherlockActivity(), AddLocation.class);
			addintent.putExtra("FromPage", "ListViewHubba");
			ListViewHubba.this.startActivity(addintent);
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
		// reset array
		SpotsArray = new ArrayList<HashMap<String, String>>();
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

    public void onDestroyView() {
        super.onDestroyView();
        // clear spots array
        if(SpotsArray != null){
            SpotsArray = null;
        }
    }

	@Override
	public void onDestroy(){
		super.onDestroy();
		// clear spots array
        if(SpotsArray != null) {
            SpotsArray = null;
        }
	}
}