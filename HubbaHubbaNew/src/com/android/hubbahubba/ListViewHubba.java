package com.android.hubbahubba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

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
        SpotsArray = new ArrayList<HashMap<String, String>>();

        // get listview and set divider
        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setDivider(getResources().getDrawable(R.drawable.list_divider_hubba));
        listView.setDividerHeight(2);

        // populate spots array
        Spot.getListOfSpots(listView, dataAdapter, SpotsArray, context);

        //short press is to view the spot (SpotPage.java)
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Bundle bundleData = new Bundle();
                //Toast.makeText(getActivity().getApplicationContext(), "size = " +  SpotsArray.size(), Toast.LENGTH_SHORT).show();

                HashMap<String, String> spot = SpotsArray.get(position);

                String spot_id = spot.get("id");
                String url = spot.get("photo");

                bundleData.putString("spot_id", spot_id);
                bundleData.putString("url", url);

                Toast.makeText(getActivity().getApplicationContext(), url, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity().getApplicationContext(),
                        SpotPage.class);

                intent.putExtras(bundleData);
                startActivity(intent);
            }
        });

        // TODO: some type of deletion scheme
        /*
        //long press is to edit the spot (EditActivity.java);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position,long id)
            {
                Bundle bundleData = new Bundle();
                HashMap<String, String> spot = SpotsArray.get(position);
                //int clickId;
                String spot_id = spot.get("id");

                // DELETE spot
                Spot.deleteSpotByID(context, spot_id);

                refreshList();

                return false;
            }

        });
        */

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
        /*
		case R.id.action_search:
			// TODO: search
			return true;
		*/
		case R.id.action_refresh:
			refreshList();
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