package com.android.hubbahubba;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewMapNew extends SherlockFragment {

	//private static final int TYPE_NEW = 0;
	//private static final int TYPE_EXISTING = 1;

	private GoogleMap mMap;
	private UiSettings mUiSettings;
	private boolean spotAdded = false; 
	private View rootView;
	private String text;
	private HubbaDBAdapter dbHelper;

	//@SuppressLint("NewApi")
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {

		rootView =  inflater.inflate(R.layout.activity_view_map, container, false);
		Context context = getActivity().getApplicationContext();
        
        
        Button searchButton = (Button)rootView.findViewById(R.id.searchButton);
        //back button goes back to the main page
        searchButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(getSherlockActivity(), ActionBarActivity.class);
        		ViewMapNew.this.startActivity(intent);
        	}

        });
        
        //get mapFragment
        mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        setUpMapIfNeeded();
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        
        //UI SETTINGS HERE
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setRotateGesturesEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setZoomControlsEnabled(false);
        
        final LatLng Riley = new LatLng(42.4409000, -83.3978000 );
        Marker riley = mMap.addMarker(new MarkerOptions()
                                  .position(Riley)
                                  .title("Riley Skate Park")
                                  .snippet("Skatepark")
                                  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        
	     // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
	     CameraPosition cameraPosition = new CameraPosition.Builder()
	         .target(Riley)      // Sets the center of the map to Mountain View
	         .zoom(17)                   // Sets the zoom
	         .bearing(0)                // Sets the orientation of the camera to north
	         .tilt(30)                   // Sets the tilt of the camera to 30 degrees
	         .build();                   // Creates a CameraPosition from the builder
	     mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

	     // TODO JIMMY: If you could write a function that will give me one lat and lng at a time (looping through all)
	     //	so I can add a marker to the map from the latitude and longitude for all of the spots in the db
		 
		 //========NEW CODE========//
		 dbHelper = new HubbaDBAdapter(context);
		 dbHelper.open();
		 Cursor c = dbHelper.fetchAllSpots();
		 double latitude, longitude;

		 do {
		 	latitude =  Double.parseDouble(c.getString(3) );
		 	longitude =  Double.parseDouble(c.getString(4) );
	
			//To-Do: Parse lat/long into map coordinates
	               
		 }while (c.moveToNext());
		 dbHelper.close();
		 //=======END OF NEW CODE======//

	     //NEW STUFF
	     mMap.setOnMapLongClickListener(new OnMapLongClickListener(
					) {

				@Override
				public void onMapLongClick(LatLng point) {
					if(!spotAdded){

						Marker addSpot = mMap.addMarker(new MarkerOptions()
	                      		.position(point)
	                      		.title("Tap to Add Spot")
	                      		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
						addSpot.hideInfoWindow();

						text = addSpot.getPosition().toString();
						//mMarkerMap.put(addSpot.getPosition(), TYPE_NEW);
						text = text.substring(10, text.length() - 1);

						spotAdded = true;

						// FOR TOAST
						//Context context = getActivity().getApplicationContext();
						//int duration = Toast.LENGTH_LONG;

						//Toast toast = Toast.makeText(context, text, duration);
						//toast.show();



						Intent intent = new Intent(getSherlockActivity(), AddLocation.class);
						intent.putExtra("LatLong", text);
						intent.putExtra("FromPage", "ViewMap");
						//startActivityForResult(intent, 0);
						startActivity(intent);


					}
					/*
					Context context = getActivity().getApplicationContext();
					CharSequence Text = text;
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, Text, duration);
					toast.show();
					*/

					/*
					Intent intent = new Intent(getSherlockActivity(), AddLocation.class);
					intent.putExtra("LatLong", text);
					startActivity(intent);
					*/

					// intent.addextra lat and long
					// in add spot retrieve lat and long
					// when done add lat and long to intent
					// start add spot

				}
	     });

	     mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {

				/*
				int type = mMarkerMap.get(marker.getPosition());
				
				if(type == TYPE_NEW) {
					
					
					// intent.addextra lat and long
					// in add spot retrieve lat and long
					// when done add lat and long to intent
					// start add spot
					
				} else if(type == TYPE_EXISTING) {
					// add lat and long to intent to check in databse
					// launch spot page
				}
        		*/

        		//startActivity(intent);
                //ViewMap.this.startActivity(intent);
                
				//Intent intent = new Intent(getSherlockActivity(), SpotPage.class);
        		//startActivityForResult(intent, 0);

				// Toast.makeText(getApplicationContext(),
				// clickId + " is the ID", Toast.LENGTH_SHORT).show();

				// TODO JIMMY: Instead of just choosing riley this needs to be able to look up the position
				// by the id of the row the info window is displaying... not sure how to do this

				Bundle bundleData = new Bundle();
				bundleData.putInt("keyid", 2);
				Intent intent = new Intent(getActivity().getApplicationContext(),
						SpotPage.class);
				intent.putExtras(bundleData);
				startActivity(intent);		
			}
		});


	     //INFO WINDOW
	     // Setting a custom info window adapter for the google map		
	     mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

			// Use default InfoWindow frame
			@Override
			public View getInfoWindow(Marker arg0) {				
				return null;
			}			

			// Defines the contents of the InfoWindow
			@Override
			public View getInfoContents(Marker arg0) {
				String LatLong = arg0.getPosition().toString();
				//mMarkerMap.put(addSpot.getPosition(), TYPE_NEW);
				LatLong = LatLong.substring(10, LatLong.length() - 1);

				String[] separated = LatLong.split(",");
				String latitude = separated[0];
				String longitude = separated[1];

				// This produces the latitude and longitude from the spot
				double Lat = Double.parseDouble(latitude);
				double Lng = Double.parseDouble(longitude);

				// TODO JIMMY: If you could help me write a function to get all the info from the db
				// to mirror what is happening below here but by looking it up by Lat and Lng defined above


				// Getting view from the layout file info_window_layout
				View v = getSherlockActivity().getLayoutInflater().inflate(R.layout.info_window_layout, null);
				
				
				//=========== NEW CODE==========//
				ImageView imgThumbnail = (ImageView) v.findViewById(R.id.info_window_image);
				TextView txtTitle = (TextView) v.findViewById(R.id.info_window_title);
				TextView txtOverallRating = (TextView) v.findViewById(R.id.info_window_OverallRating);
				TextView txtPoRating = (TextView) v.findViewById(R.id.info_window_txtPoRating);
				TextView txtDiffRating = (TextView)  v.findViewById(R.id.info_window_diffRating);
				TextView txtDistance = (TextView) v.findViewById(R.id.info_window_distance);
				Context context = getActivity().getApplicationContext();
				//String mImagePath;


				dbHelper = new HubbaDBAdapter(context);
				dbHelper.open();
				Cursor cur;
				/*
				//if (latitude != null && longitude != null){
					cur = dbHelper.fetchSpotByLatLong(Lat, Lng);
				//}
				
			    if (cur.moveToFirst()) {
					do {
						txtTitle.setText(cur.getString(1)); //name
						txtOverallRating.setText(cur.getString(5)); //rating
						txtDiffRating.setText(cur.getString(6)); //difficulty
						txtPoRating.setText(cur.getString(7)); //level
						String mImagePath = cur.getString(9); //image URI

						if(mImagePath != null ) {
							Uri imageViewUri = Uri.parse(mImagePath); //parse URI
							imgThumbnail.setImageURI(imageViewUri); //set Image via parsed URI
						}
					} while (cur.moveToNext());
				}
				//========END OF NEW CODE=========//
			  	*/
				/*
		        ImageView imgThumbnail = (ImageView) v.findViewById(R.id.info_window_image);
		        imgThumbnail.setImageResource(R.drawable.indysunburst);
		        TextView txtTitle = (TextView) v.findViewById(R.id.info_window_title);
		        txtTitle.setText("Riley Skate Park");
		        TextView txtOverallRating = (TextView) v.findViewById(R.id.info_window_OverallRating);
		        txtOverallRating.setText("10");
		        TextView txtPoRating = (TextView) v.findViewById(R.id.info_window_txtPoRating);
		        txtPoRating.setText("5");
		        TextView txtDiffRating = (TextView)  v.findViewById(R.id.info_window_diffRating);
		        txtDiffRating.setText("7");
		        TextView txtDistance = (TextView) v.findViewById(R.id.info_window_distance);
		        txtDistance.setText("4.00");
				*/

				// Returning the view containing InfoWindow contents
				return v;

			}

		});
		return rootView;

    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onDestroyView() {
        super.onDestroyView();

        // Do Not Miss this
        try {
            Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));  
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // TODO Possibly delete this
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == 0){
    		if(resultCode == AddSpot.RESULT_CODE_SPOT_ADDED){
    			spotAdded = false;
    		}
    	}
    }
    
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = ((MapFragment) getSherlockActivity().getFragmentManager().findFragmentById(R.id.map))
                                .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // The Map is verified. It is now safe to manipulate the map.

            }
        }
    }
    
}