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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;

public class ListViewHubba extends SherlockFragment {

	private HubbaDBAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	private ListView listView;
	private View rootView;

	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	 */
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
		
           // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.activity_list_view, container, false);
		dbHelper = new HubbaDBAdapter(getActivity().getBaseContext());
		dbHelper.open();

		Button addSpot = (Button) rootView.findViewById(R.id.AddSpot);
		addSpot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(getSherlockActivity(), AddLocation.class);
				intent.putExtra("FromPage", "ListViewHubba");
				ListViewHubba.this.startActivity(intent);
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

		Cursor cursor = dbHelper.fetchAllSpots();

		// The desired columns to be bound
		String[] columns = new String[] { HubbaDBAdapter.KEY_NAME,
				HubbaDBAdapter.KEY_TYPE,HubbaDBAdapter.KEY_RATING,
				HubbaDBAdapter.KEY_DIFF, HubbaDBAdapter.KEY_LEVEL, HubbaDBAdapter.KEY_IMAGE };

		// the XML defined views which the data will be bound to
		int[] to = new int[] { R.id.txtTitle, R.id.txtType,
				R.id.txtOverallRating, R.id.txtDiffRating, R.id.txtPoRating, R.id.imgThumbnail };

		// create the adapter using the cursor pointing to the desired data
		// as well as the layout information
		dataAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.item_row,
				cursor, columns, to, 0);

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

	}
	
	
	public void onResume() {
		dbHelper.open();
		Cursor c = dbHelper.fetchAllSpots();

		dataAdapter.changeCursor(c);
		super.onResume();
	}

}

/*
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ListViewHubba extends SherlockFragment {
	
	CustomListViewAdapter mAdapter;
	private View rootView;
	
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                 Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		 rootView =  inflater.inflate(R.layout.activity_list_view, container, false);
        
        Button addSpotButton = (Button)rootView.findViewById(R.id.AddSpot);
        		
      //login Button takes you to the map view
        addSpotButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		//Intent intent = new Intent(ListViewHubba.this, AddLocation.class);
        		//startActivity(intent);
        		Intent intent = new Intent(getSherlockActivity(), AddLocation.class);
                ListViewHubba.this.startActivity(intent);
        	}
        });
        
        
        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        List<ListViewItem> items = new ArrayList<ListViewHubba.ListViewItem>();
        
        ListViewItem item1 = new ListViewItem();
        item1.Thumbnail = R.drawable.connorock;
        item1.Title = "Riley Swimming Pool";
        item1.OverallRating = 10;
        item1.PoRating = 3;
        item1.DiffRating = 8;
        item1.Distance = 5.4;
        items.add(item1);
        
        ListViewItem item2 = new ListViewItem();
        item2.Thumbnail = R.drawable.indysunburst;
        item2.Title = "Riley Skate Park";
        item2.OverallRating = 9;
        item2.PoRating = 5;
        item2.DiffRating = 7;
        item2.Distance = 6.2;
        items.add(item2);
        
        mAdapter = new CustomListViewAdapter(this, items);
        lv.setAdapter(mAdapter);
        
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position,
        			long id) {
        		// TODO Auto-generated method stub
        		
        		ListViewItem item = (ListViewItem) mAdapter.getItem(position);
        		//Intent intent = new Intent(ListViewHubba.this, SpotPage.class);
        		
        		Intent intent = new Intent(getSherlockActivity(), SpotPage.class);        		
        		intent.putExtra("Thumbnail", item.Thumbnail);
        		intent.putExtra("Title", item.Title);
        		intent.putExtra("OverallRating", item.OverallRating);
        		intent.putExtra("PoRating", item.PoRating);
        		intent.putExtra("DiffRating", item.DiffRating);
        		intent.putExtra("Distance", item.Distance);
        		
        		//startActivity(intent);
                ListViewHubba.this.startActivity(intent);

        	}
        });
        
        return rootView;

    }
    
    static class ListViewItem{
    	public int Thumbnail;
    	public String Title;
    	public int OverallRating;
    	public int PoRating;
    	public int DiffRating;
    	public double Distance;
    	
    	public ListViewItem(){
    		
    	}
    	
    	public ListViewItem(Intent intent){
    		bindTo(intent);
    	}
    	
    	public ListViewItem(Cursor cursor){
    		bindTo(cursor);
    	}
    	
    	public void bindTo(Intent intent){
    		Thumbnail = intent.getIntExtra("Thumbnail", 0);
    		Title = intent.getStringExtra("Title");
    		OverallRating = intent.getIntExtra("OverallRating", 0);
    		PoRating = intent.getIntExtra("PoRating", 0);
    		DiffRating = intent.getIntExtra("DiffRating", 0);
    		Distance = intent.getDoubleExtra("Distance", 0);
    	}
    	
    	public void bindTo(Cursor cursor){
    		
    	}
    }
}
*/