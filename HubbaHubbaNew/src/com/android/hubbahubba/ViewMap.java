package com.android.hubbahubba;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewMap extends SherlockFragment {

	private GoogleMap mMap;
	private UiSettings mUiSettings;
	private boolean spotAdded = false; 
	private View rootView;
	private String text;
	private HubbaDBAdapter dbHelper;
	Marker addSpot;
	Context context;

	//@SuppressLint("NewApi")
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
		rootView =  inflater.inflate(R.layout.activity_view_map, container, false);
        
        Button searchButton = (Button)rootView.findViewById(R.id.searchButton);
        //back button goes back to the main page
        searchButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(getSherlockActivity(), ActionBarActivity.class);
        		ViewMap.this.startActivity(intent);
        	}

        });
        
        // Set spinner color
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spotTypeSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_row, R.id.text1, getResources().getStringArray(R.array.showSpotTypes));
		spinner.setAdapter(adapter);
        
        //get mapFragment
        //mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        setUpMapIfNeeded();
		return rootView;
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	/*
    public void onDestroyView() {
        super.onDestroyView();
        dbHelper.close();
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
    */
    
    // TODO Possibly delete this
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == 0){
    		if(resultCode != Activity.RESULT_OK){
    			spotAdded = false;
    			addSpot.remove();
    		}
    	}
    }
    
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
        	mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
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
        		 try{
        			 double latitude, longitude;
        			 String title, type;
        	
        			 do {
        			 	latitude =  Double.parseDouble(c.getString(3) );
        			 	longitude =  Double.parseDouble(c.getString(4) );
        			 	title = c.getString(1);
        			 	type = c.getString(2);
        			 	
        			 	// ADDS SPOTS TO MAP	 	
        			 	mMap.addMarker(new MarkerOptions()
        			                                  .position(new LatLng(latitude, longitude))
        			                                  .title(title)
        			                                  .snippet(type)
        			                                  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        			 	
        			 }while (c.moveToNext());
        		 }finally {
        	        // this gets called even if there is an exception somewhere above
        	        if(c != null)
                    c.close();
        		 }
        		 dbHelper.close();
        		 //=======END OF NEW CODE======//
        		 
        	     
        	     //NEW STUFF
        	     mMap.setOnMapLongClickListener(new OnMapLongClickListener() {

        				@Override
        				public void onMapLongClick(LatLng point) {
        					if(!spotAdded){

        						addSpot = mMap.addMarker(new MarkerOptions()
        	                      		.position(point)
        	                      		.title("Tap to Add Spot")
        	                      		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        						addSpot.hideInfoWindow();

        						text = addSpot.getPosition().toString();
        						//mMarkerMap.put(addSpot.getPosition(), TYPE_NEW);
        						text = text.substring(10, text.length() - 1);

        						spotAdded = true;

        						//Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        						//toast.show();

        						Intent intent = new Intent(getSherlockActivity(), AddLocation.class);
        						intent.putExtra("LatLong", text);
        						intent.putExtra("FromPage", "ViewMap");
        						//startActivityForResult(intent, 0);
        						startActivityForResult(intent, 0);
        					}

        					// intent.addextra lat and long
        					// in add spot retrieve lat and long
        					// when done add lat and long to intent
        					// start add spot
        				}
        	     });
        	     
        	     mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

        			@Override
        			public void onInfoWindowClick(Marker marker) {
        				
        				String LatLong = marker.getPosition().toString();
        				LatLong = LatLong.substring(10, LatLong.length() - 1);

        				String[] separated = LatLong.split(",");
        				String latitude = separated[0];
        				String longitude = separated[1];

        				// This produces the latitude and longitude from the spot
        				double Lat = Double.parseDouble(latitude);
        				double Lng = Double.parseDouble(longitude);
        				
        				dbHelper = new HubbaDBAdapter(context);
        				dbHelper.open();
        				Cursor cur = dbHelper.fetchSpotByLatLong(Lat, Lng);
        				try{
        					cur = dbHelper.fetchSpotByLatLong(Lat, Lng);
        					/*
        					int clickId = Integer.valueOf(cur.getString(cur
        							.getColumnIndexOrThrow("_id")));
        	
        					Toast toast = Toast.makeText(getActivity().getApplicationContext(), "ID: " + String.valueOf(clickId), Toast.LENGTH_SHORT);
        					toast.show();
        					*/ 
        					
        					Bundle bundleData = new Bundle();
        					bundleData.putInt("keyid", 29);
        					Intent intent = new Intent(getActivity().getApplicationContext(),
        							SpotPage.class);
        					intent.putExtras(bundleData);
        					startActivity(intent);		
        					dbHelper.close();
        					//cur.close();
        				} finally {
        				    // this gets called even if there is an exception somewhere above
        				    if(cur != null)
        				        cur.close();
        				}
        			}
        		});


        	     //INFO WINDOW
        	     // Setting a custom info window adapter for the google map		
        	     mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

        			// Use default InfoWindow frame
        			@Override
        			public View getInfoWindow(Marker arg0) {	
        				String LatLong = arg0.getPosition().toString();
        				//mMarkerMap.put(addSpot.getPosition(), TYPE_NEW);
        				LatLong = LatLong.substring(10, LatLong.length() - 1);

        				String[] separated = LatLong.split(",");
        				String latitude = separated[0];
        				String longitude = separated[1];

        				// This produces the latitude and longitude from the spot
        				double Lat = Double.parseDouble(latitude);
        				double Lng = Double.parseDouble(longitude);
        				
        				Context context = getActivity().getApplicationContext();


        				// Getting view from the layout file info_window_layout
        				View v = getSherlockActivity().getLayoutInflater().inflate(R.layout.info_window_layout, null);
        				
        				//=========== NEW CODE==========//
        				ImageView imgThumbnail = (ImageView) v.findViewById(R.id.info_window_image);
        				TextView txtTitle = (TextView) v.findViewById(R.id.info_window_title);
        				TextView txtOverallRating = (TextView) v.findViewById(R.id.info_window_OverallRating);
        				TextView txtPoRating = (TextView) v.findViewById(R.id.info_window_txtPoRating);
        				TextView txtDiffRating = (TextView)  v.findViewById(R.id.info_window_diffRating);
        				TextView txtDistance = (TextView) v.findViewById(R.id.info_window_distance);
        				
        				/*
        				int duration = Toast.LENGTH_LONG;
        				
        				Toast toaster = Toast.makeText(context, "Name: ", duration);
        				toaster.show();
        				*/

        				dbHelper = new HubbaDBAdapter(context);
        				dbHelper.open();
        				Cursor cur = dbHelper.fetchSpotByLatLong(Lat, Lng);
        				
        				String lat = Double.toString(Lat);
        			    String lng = Double.toString(Lng);
        			    /*
        			    text = lat + " " + lng;

        				Toast toasted = Toast.makeText(context, text, duration);
        				toasted.show();
        				
        				*/
        			    try{
        					// TODO JIMMY
        				    if (cur.moveToFirst()) {
        						
        						do {
        							txtTitle.setText(cur.getString(1)); //name
        							txtOverallRating.setText(cur.getString(5)); //rating
        							txtDiffRating.setText(cur.getString(6)); //difficulty
        							txtPoRating.setText(cur.getString(7)); //police level
        							String mImagePath = cur.getString(9); //image URI
        	
        							if(mImagePath != null ) {
        								//Uri imageViewUri = Uri.parse(mImagePath); //parse URI
        								
        								// Convert the dp value for xml to pixels (casted to int from float)
        								int size = Image.convertDpToPixel(80, context);
        								
        								Uri imageViewUri = Uri.fromFile(new File(mImagePath));
        								//File f = new File(mImagePath);
        								
        								Toast toast = Toast.makeText(context, "URI: " + imageViewUri + " path: " + mImagePath, Toast.LENGTH_LONG);
        								toast.show();
        								
        								// Use picasso to load the image into view
        								Picasso.with(context)
        									   .load(imageViewUri)
        									   .centerCrop()
        									   .resize(size, size)
        									   .placeholder(R.drawable.gettinthere)
        									   .into(imgThumbnail);
        								
        								//imgThumbnail.setImageURI(imageViewUri); //set Image via parsed URI
        							}
        							/*
        							Toast toast = Toast.makeText(context, "Name: " + cur.getString(1) + "Overall Rating: " + cur.getString(5), duration);
        							toast.show();
        							*/
        							
        							//int duration = Toast.LENGTH_LONG;
        							
        							//Toast toast = Toast.makeText(context, "Name: " + cur.getString(1), duration);
        							//toast.show();
        						} while (cur.moveToNext());
        					}
        			    }finally {
        			        // this gets called even if there is an exception somewhere above
        			        if(cur != null)
        			         cur.close();
        				}
        			    dbHelper.close();
        				//========END OF NEW CODE=========//

        				// Returning the view containing InfoWindow contents
        				return v;
        			}			

        			// Defines the contents of the InfoWindow
        			@Override
        			public View getInfoContents(Marker arg0) {
        				return null;
        			}

        		});
            }
        }
    }
	
	/*
	@Override
	public void onStop() {
	     super.onStop();
	     dbHelper.close();
	}
	*/
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dbHelper.close();
		
		try {
            Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));  
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}