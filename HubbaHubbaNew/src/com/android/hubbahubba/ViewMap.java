package com.android.hubbahubba;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
	Marker addSpot;
	Context context;
	boolean isHybrid = false;
	Button HybridButton;
	LinearLayout Footer;

	// @SuppressLint("NewApi")
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
		rootView = inflater.inflate(R.layout.activity_view_map, container,
				false);

		Button searchButton = (Button) rootView.findViewById(R.id.searchButton);
		// back button goes back to the main page
		
		// TODO: Search Button
//		searchButton.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View view) {
//				Intent intent = new Intent(getSherlockActivity(),
//						ActionBarActivity.class);
//				ViewMap.this.startActivity(intent);
//			}
//
//		});
		
		Footer = (LinearLayout) rootView.findViewById(R.id.footer);
		Footer.setVisibility(View.GONE);
		// Button for changing the map style
		HybridButton = (Button) rootView.findViewById(R.id.hybridButton);
		HybridButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if(isHybrid){
					mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					HybridButton.setBackgroundResource(R.drawable.button_map_normal);
					isHybrid = false;
				}
				else{
					mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
					HybridButton.setBackgroundResource(R.drawable.button_map_hybrid);
					isHybrid = true;
				}
			}

		});

		// Set spinner color
		Spinner spinner = (Spinner) rootView.findViewById(R.id.spotTypeSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				R.layout.spinner_row, R.id.text1, getResources()
						.getStringArray(R.array.showSpotTypes));
		spinner.setAdapter(adapter);

		// get mapFragment
		// mMap = ((SupportMapFragment)
		// getFragmentManager().findFragmentById(R.id.map)).getMap();

		setUpMapIfNeeded();
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onDestroyView() {
		super.onDestroyView();
		// Do Not Miss this
		if(!getActivity().isFinishing()){
			try {
				Fragment fragment = (getFragmentManager()
						.findFragmentById(R.id.map));
				FragmentTransaction ft = getActivity().getSupportFragmentManager()
						.beginTransaction();
				ft.remove(fragment);
				ft.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// TODO Possibly delete this
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode != Activity.RESULT_OK) {
				spotAdded = false;
				addSpot.remove();
			}
		}
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				//mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				isHybrid = false;
				mMap.setMyLocationEnabled(true);

				// UI SETTINGS HERE
				mUiSettings = mMap.getUiSettings();
				mUiSettings.setMyLocationButtonEnabled(true);
				mUiSettings.setRotateGesturesEnabled(false);
				mUiSettings.setZoomControlsEnabled(false);

				// Construct a CameraPosition focusing on Mountain View and
				// animate the camera to that position.
				Bundle showData = this.getArguments();
				if (showData != null) {
					try {
						String lat = showData.getString("lat");
						String lon = showData.getString("lon");

						Double dLat = Double.parseDouble(lat);
						Double dLon = Double.parseDouble(lon);

						final LatLng spotLoc = new LatLng(dLat, dLon);
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(spotLoc) // Sets the center of the map
													// to
													// Mountain View
								.zoom(17) // Sets the zoom
								.bearing(0) // Sets the orientation of the
											// camera to
											// north
								.tilt(30) // Sets the tilt of the camera to 30
											// degrees
								.build(); // Creates a CameraPosition from the
											// builder
						mMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					final LatLng Center = new LatLng(42.4409010, -83.3978000);

					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(Center) // Sets the center of the map to
											// Mountain View
							.zoom(17) // Sets the zoom
							.bearing(0) // Sets the orientation of the camera to
										// north
							.tilt(30) // Sets the tilt of the camera to 30
										// degrees
							.build(); // Creates a CameraPosition from the
										// builder
					mMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
				}

				Spot.getAllSpots(mMap, getActivity().getApplicationContext());

				// NEW STUFF
				mMap.setOnMapLongClickListener(new OnMapLongClickListener() {

					@Override
					public void onMapLongClick(LatLng point) {
						if (!spotAdded) {

							addSpot = mMap.addMarker(new MarkerOptions()
									.position(point)
									.title("Tap to Add Spot")
									.icon(BitmapDescriptorFactory
											.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
							addSpot.hideInfoWindow();

							text = addSpot.getPosition().toString();
							// mMarkerMap.put(addSpot.getPosition(), TYPE_NEW);
							text = text.substring(10, text.length() - 1);

							spotAdded = true;

							// Toast toast = Toast.makeText(context, text,
							// Toast.LENGTH_LONG);
							// toast.show();

							Intent intent = new Intent(getSherlockActivity(),
									AddLocation.class);
							intent.putExtra("LatLong", text);
							intent.putExtra("FromPage", "ViewMap");
							// startActivityForResult(intent, 0);
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
						/*
						 * String LatLong = marker.getPosition().toString();
						 * LatLong = LatLong.substring(10, LatLong.length() -
						 * 1);
						 * 
						 * String[] separated = LatLong.split(","); String
						 * latitude = separated[0]; String longitude =
						 * separated[1];
						 */

						Bundle bundleData = new Bundle();

						// TODO delete this
						bundleData.putInt("keyid", 29);

						// get spot id
						String[] snip = marker.getSnippet().split(",");
						String id = snip[0];

						bundleData.putString("spot_id", id);

						Intent intent = new Intent(getActivity()
								.getApplicationContext(), SpotPage.class);

						intent.putExtras(bundleData);
						startActivity(intent);

					}
				});

				// INFO WINDOW
				// Setting a custom info window adapter for the google map
				mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

					// Use default InfoWindow frame
					@Override
					public View getInfoWindow(Marker arg0) {
						String LatLong = arg0.getPosition().toString();
						LatLong = LatLong.substring(10, LatLong.length() - 1);

						String[] separated = LatLong.split(",");
						String latitude = separated[0];
						String longitude = separated[1];

						// This produces the latitude and longitude from the
						// spot
						double Lat = Double.parseDouble(latitude);
						double Lng = Double.parseDouble(longitude);

						Context context = getActivity().getApplicationContext();

						// Getting view from the layout file info_window_layout
						View v = getSherlockActivity().getLayoutInflater()
								.inflate(R.layout.info_window_layout, null);

						// =========== NEW CODE==========//
						ImageView imgThumbnail = (ImageView) v
								.findViewById(R.id.info_window_image);
						TextView txtTitle = (TextView) v
								.findViewById(R.id.info_window_title);
						TextView txtOverallRating = (TextView) v
								.findViewById(R.id.info_window_OverallRating);
						TextView txtPoRating = (TextView) v
								.findViewById(R.id.info_window_txtPoRating);
						TextView txtDiffRating = (TextView) v
								.findViewById(R.id.info_window_diffRating);
						TextView txtDistance = (TextView) v
								.findViewById(R.id.info_window_distance);

						// TODO: WAS AN ELSE
						//Toast.makeText(getActivity().getApplicationContext(),
						//		"Using new DB", Toast.LENGTH_LONG).show();

						// Get spot info
						String spot_title = arg0.getTitle();

						// parse snippit
						String[] snip = arg0.getSnippet().split(",");
						String overall = snip[1];
						String difficulty = snip[2];
						String bust = snip[3];
						// String type = snip[4];

						txtTitle.setText(spot_title); // name
						txtOverallRating.setText(overall); // rating
						txtDiffRating.setText(difficulty); // difficulty
						txtPoRating.setText(bust); // police level
						txtDistance.setText("1.11 mi");
						// String mImagePath = cur.getString(9); //image URI

						// Convert the dp value for xml to pixels (casted to int
						// from float)
						int size = Image.convertDpToPixel(80, context);

						// Use picasso to load the image into view
						Picasso.with(context).load(R.drawable.gettinthere)
								.centerCrop().resize(size, size)
								.placeholder(R.drawable.gettinthere)
								.into(imgThumbnail);

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
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// Do Not Miss this
		if(!getActivity().isFinishing()){
			try {
				Fragment fragment = (getFragmentManager()
						.findFragmentById(R.id.map));
				FragmentTransaction ft = getActivity().getSupportFragmentManager()
						.beginTransaction();
				ft.remove(fragment);
				ft.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}