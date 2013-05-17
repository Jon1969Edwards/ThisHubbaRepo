package com.android.hubbahubbanew;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class ListViewFavorites extends SherlockFragment {

	CustomListViewAdapterFavorites mAdapter;
	private View rootView;
	
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                 Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		 rootView =  inflater.inflate(R.layout.activity_list_view_favorites, container, false);

		// create back button
		Button backButton = (Button) rootView.findViewById(R.id.backButton);
		Button addSpotButton = (Button) rootView.findViewById(R.id.AddSpot);

		// back button goes back to the main page
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//Intent intent = new Intent(ListViewFavorites.this, MainActivity.class);
				//startActivity(intent);
				Intent intent = new Intent(getSherlockActivity(), MainActivity.class);
                ListViewFavorites.this.startActivity(intent);
			}

		});

		// login Button takes you to the map view
		addSpotButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//Intent intent = new Intent(ListViewFavorites.this, AddLocation.class);
				//startActivity(intent);
				Intent intent = new Intent(getSherlockActivity(), AddLocation.class);
                ListViewFavorites.this.startActivity(intent);
			}
		});

		ListView lv = (ListView) rootView.findViewById(R.id.listViewFavorites);
		List<ListViewItem> items = new ArrayList<ListViewFavorites.ListViewItem>();

		ListViewItem item1 = new ListViewItem();
		item1.Thumbnail = R.drawable.ic_launcher;
		item1.Title = "Favorite Spot 1";
		item1.OverallRating = 10;
		item1.PoRating = 3;
		item1.DiffRating = 4;
		item1.Distance = 3.4;
		items.add(item1);

		ListViewItem item2 = new ListViewItem();
		item2.Thumbnail = R.drawable.ic_launcher;
		item2.Title = "Favorite Spot 2";
		item2.OverallRating = 9;
		item2.PoRating = 2;
		item2.DiffRating = 7;
		item2.Distance = 2.2;
		items.add(item2);

		mAdapter = new CustomListViewAdapterFavorites(this, items);
		lv.setAdapter(mAdapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			//@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				ListViewItem item = (ListViewItem) mAdapter.getItem(position);
				//Intent intent = new Intent(ListViewFavorites.this, SpotPage.class);
				Intent intent = new Intent(getSherlockActivity(), MainActivity.class);
               

				intent.putExtra("Thumbnail", item.Thumbnail);
				intent.putExtra("Title", item.Title);
				intent.putExtra("OverallRating", item.OverallRating);
				intent.putExtra("PoRating", item.PoRating);
				intent.putExtra("Distance", item.Distance);

				//startActivity(intent);
				ListViewFavorites.this.startActivity(intent);
			}
		});
		
		return rootView;
	}

	//@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSherlockActivity().getMenuInflater().inflate(R.menu.activity_list_view_favorites, menu);

		return true;
	}

	static class ListViewItem {
		public int Thumbnail;
		public String Title;
		public int OverallRating;
		public int PoRating;
		public int DiffRating;
		public double Distance;

		public ListViewItem() {

		}

		public ListViewItem(Intent intent) {
			bindTo(intent);
		}

		public ListViewItem(Cursor cursor) {
			bindTo(cursor);
		}

		public void bindTo(Intent intent) {
			Thumbnail = intent.getIntExtra("Thumbnail", 0);
			Title = intent.getStringExtra("Title");
			OverallRating = intent.getIntExtra("OverallRating", 0);
			PoRating = intent.getIntExtra("PoRating", 0);
			DiffRating = intent.getIntExtra("DiffRating", 0);
			Distance = intent.getIntExtra("Distance", 0);
		}

		public void bindTo(Cursor cursor) {

		}
	}
}