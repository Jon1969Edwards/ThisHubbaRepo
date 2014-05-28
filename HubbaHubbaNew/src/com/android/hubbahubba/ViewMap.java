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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
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
	Spinner spinner;
	boolean spinnerTouched = false;
	private View v;
	private Marker selectedMarker = null;
	private String url;
    private boolean already_shown = false;
    private int loop = 0;

	// @SuppressLint("NewApi")
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, Bundle savedInstanceState) {
		context = getActivity().getApplicationContext();
		rootView = inflater.inflate(R.layout.activity_view_map, container,
				false);

		setHasOptionsMenu(true);

		// Button searchButton = (Button)
		// rootView.findViewById(R.id.searchButton);
		// back button goes back to the main page

		// TODO: Search Button
		// searchButton.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View view) {
		// Intent intent = new Intent(getSherlockActivity(),
		// ActionBarActivity.class);
		// ViewMap.this.startActivity(intent);
		// }
		//
		// });

		// TODO: take this out
		Footer = (LinearLayout) rootView.findViewById(R.id.footer);
		Footer.setVisibility(View.GONE);

		// Button for changing the map style
		HybridButton = (Button) rootView.findViewById(R.id.hybridButton);
		HybridButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (isHybrid) {
					mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					HybridButton
							.setBackgroundResource(R.drawable.button_map_normal);
					isHybrid = false;
				} else {
					mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
					HybridButton
							.setBackgroundResource(R.drawable.button_map_hybrid);
					isHybrid = true;
				}
			}

		});

		// Set spinner color
		spinner = (Spinner) rootView.findViewById(R.id.spotTypeSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				R.layout.spinner_row, R.id.text1, getResources()
						.getStringArray(R.array.showSpotTypes));
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				String type = spinner.getSelectedItem().toString();

				if (spinnerTouched && type.equalsIgnoreCase("Show All")) {
					// type = "";
					Spot.getAllSpots(mMap, getActivity()
							.getApplicationContext());
				} else if (!type.equalsIgnoreCase("Show All")) {
					spinnerTouched = true;
					// Take off the s
					if (type.substring(type.length() - 1).equalsIgnoreCase("s")) {
						type = type.substring(0, type.length() - 1);
					}
					// TODO: CLEAR CURRENT ADAPTER
					Spot.getSpotsByType(mMap, getActivity()
							.getApplicationContext(), type);
				}
				// else nothing (page loaded)
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
				// TODO: anything?
			}

		});

		setUpMapIfNeeded();
		return rootView;
	}

	public void refreshMap() {
		String type = spinner.getSelectedItem().toString();
		if (type.equalsIgnoreCase("Show All")) {
			// type = "";
			Spot.getAllSpots(mMap, getActivity().getApplicationContext());
		} else if (!type.equalsIgnoreCase("Show All")) {
			spinnerTouched = true;
			// Take off the s
			if (type.substring(type.length() - 1).equalsIgnoreCase("s")) {
				type = type.substring(0, type.length() - 1);
			}
			// TODO: CLEAR CURRENT ADAPTER
			Spot.getSpotsByType(mMap, getActivity().getApplicationContext(),
					type);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	public void onDestroyView() {
		super.onDestroyView();
		// Do Not Miss this
		if (!getActivity().isFinishing()) {
			try {
				Fragment fragment = (getFragmentManager()
						.findFragmentById(R.id.map));
				FragmentTransaction ft = getActivity()
						.getSupportFragmentManager().beginTransaction();
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
				// mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
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
								.target(spotLoc)    // sets camera location to spotLoc
								.zoom(17)           // Sets the zoom
								.bearing(0)         // Sets orientation to north
								.tilt(30)           // Sets tilt of the camera to 30 degrees
								.build();           // Creates a CameraPosition from the builder
						mMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
                    // default to riley skatepark
					final LatLng Center = new LatLng(42.4409010, -83.3978000);
					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(Center)
							.zoom(17)
							.bearing(0)
							.tilt(30)
							.build();
					mMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
				}

				Spot.getAllSpots(mMap, getActivity().getApplicationContext());

				// NEW STUFF
				mMap.setOnMapLongClickListener(new OnMapLongClickListener() {

					@Override
					public void onMapLongClick(LatLng point) {
						if (!spotAdded) {

                            // Create the marker
							addSpot = mMap.addMarker(new MarkerOptions()
									.position(point)
									.title("Tap to Add Spot")
									.icon(BitmapDescriptorFactory
											.fromResource(R.drawable.hubba_marker_add)));
							addSpot.hideInfoWindow();

							text = addSpot.getPosition().toString();
							text = text.substring(10, text.length() - 1);

							spotAdded = true;
							Intent intent = new Intent(getSherlockActivity(),
									AddLocation.class);
							intent.putExtra("LatLong", text);
							intent.putExtra("FromPage", "ViewMap");

							startActivityForResult(intent, 0);
						}
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
				
				// Hides the info window if shown, else returns selectedMarker
				mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

		            public boolean onMarkerClick(Marker marker) {

		            	// hide info window if it is shown
		            	if(!(selectedMarker == null) && selectedMarker.equals(marker)){
		            		marker.hideInfoWindow();
		            		selectedMarker = null;
                            v = null;
		            		return true;
		            	}

		            	// else set selectedMarker
		                selectedMarker = marker;
                        already_shown = false;
		                v = null;
		                return false;
		            }
		        });

				// Sets custom info window adapter for mMap
				mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
					// Use default InfoWindow frame
					@Override
					public View getInfoWindow(Marker arg0) {
						if (selectedMarker.isInfoWindowShown()) {
		                    return v;
		                }
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
						v = getSherlockActivity().getLayoutInflater()
								.inflate(R.layout.info_window_layout, null);

						// =========== Get views ==========//
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

						// Get spot info
						String spot_title = arg0.getTitle();

						// parse snippit
						String[] snip = arg0.getSnippet().split(",");
						String spot_id = snip[0];
						String overall = snip[1];
						String difficulty = snip[2];
						String bust = snip[3];
						// String type = snip[4];
						url = snip[5];
						
						//Toast.makeText(context, "URL = " + url, Toast.LENGTH_LONG).show();

						txtTitle.setText(spot_title); // name
						txtOverallRating.setText(overall); // rating
						txtDiffRating.setText(difficulty); // difficulty
						txtPoRating.setText(bust); // police level
						txtDistance.setText("1.11 mi");

						// Convert the dp value for xml to pixels (casted to int
						// from float)
						int size = Image.convertDpToPixel(80, context);
                        if(!already_shown){
                            if(url.equals("lol")){
                                Picasso.with(getActivity().getApplicationContext())
                                        .load(R.drawable.gettinthere)
                                        .centerCrop().resize(size, size)
                                        .placeholder(R.drawable.ic_empty_sec)
                                        .noFade()
                                        .into(imgThumbnail, new InfoWindowRefresher(arg0));
                            }
                            else{
                                Picasso.with(getActivity().getApplicationContext())
                                        .load(url)
                                        .noFade()
                                        .centerCrop().resize(size, size)
                                        .placeholder(R.drawable.ic_empty_sec)
                                        .into(imgThumbnail, new InfoWindowRefresher(arg0));
                            }
                        }
                        else{
                            already_shown = false;
                            if(url.equals("lol")){
                                Picasso.with(getActivity().getApplicationContext())
                                        .load(R.drawable.gettinthere)
                                        .centerCrop().resize(size, size)
                                        .placeholder(R.drawable.ic_empty_sec)
                                        .noFade()
                                        .into(imgThumbnail);
                            }
                            else{
                                Picasso.with(getActivity().getApplicationContext())
                                        .load(url)
                                        .noFade()
                                        .centerCrop().resize(size, size)
                                        .placeholder(R.drawable.ic_empty_sec)
                                        .into(imgThumbnail);
                            }
                        }
                        /*
						// TODO: get a better placeholder
						// Use picasso to load the image into view
						if(url.equals("lol")){
							Picasso.with(getActivity().getApplicationContext())
							.load(R.drawable.gettinthere)
							.centerCrop().resize(size, size)
							.placeholder(R.drawable.ic_empty_sec)
							.noFade()
							.into(imgThumbnail, new InfoWindowRefresher(arg0));
						}
						else{
							Picasso.with(getActivity().getApplicationContext())
									.load(url)
									.noFade()
									.centerCrop().resize(size, size)
									.placeholder(R.drawable.ic_empty_sec)
									.into(imgThumbnail, new InfoWindowRefresher(arg0));
						}
						*/
						return v;
					}

					// Defines the contents of the InfoWindows
					@Override
					public View getInfoContents(Marker arg0) {
						return null;
					}

				});
			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater = new MenuInflater(context);
		inflater.inflate(R.menu.map_action_items, menu);

		// set spinner style
		spinner = (Spinner) menu.findItem(R.id.action_filter).getActionView();
		spinner.setBackgroundDrawable(null);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				R.layout.spinner_row, R.id.text1, getResources()
						.getStringArray(R.array.showSpotTypes));

		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				String type = spinner.getSelectedItem().toString();

				if (spinnerTouched && type.equalsIgnoreCase("Show All")) {
					// type = "";
					Spot.getAllSpots(mMap, getActivity()
							.getApplicationContext());
				} else if (!type.equalsIgnoreCase("Show All")) {
					spinnerTouched = true;
					// Take off the s
					if (type.substring(type.length() - 1).equalsIgnoreCase("s")) {
						type = type.substring(0, type.length() - 1);
					}
					// TODO: CLEAR CURRENT ADAPTER
					Spot.getSpotsByType(mMap, getActivity()
							.getApplicationContext(), type);
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
			return true;
		case R.id.action_add_spot:
			// location found
			Toast.makeText(getActivity().getApplicationContext(),
					"Press and hold on the map\n" +
					"to add a spot at that location", Toast.LENGTH_LONG).show();
			return true;
		case R.id.action_refresh:
			refreshMap();
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

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// Do Not Miss this
		if (!getActivity().isFinishing()) {
			try {
				Fragment fragment = (getFragmentManager()
						.findFragmentById(R.id.map));
				FragmentTransaction ft = getActivity()
						.getSupportFragmentManager().beginTransaction();
				ft.remove(fragment);
				ft.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update_info_window(Marker marker){
		
		ImageView imgThumbnail = (ImageView) v
				.findViewById(R.id.info_window_image);
    	
    	// parse snippit
		String[] snip = marker.getSnippet().split(",");
		
		// Get url for the image
		url = snip[5];
		
		Toast.makeText(context, "URL = " + url, Toast.LENGTH_LONG).show();

		// Convert the dp value for xml to pixels (casted to int
		// from float)
		int size = Image.convertDpToPixel(80, context);
		
		// TODO: get a better placeholder
		// Use picasso to load the image into view
		if(url.equals("lol")){
			Picasso.with(getActivity().getApplicationContext())
			.load(R.drawable.gettinthere)
			.centerCrop().resize(size, size)
			//.placeholder(R.drawable.ic_empty)
			.noFade()
			.into(imgThumbnail);
		}
		else{
			Picasso.with(getActivity().getApplicationContext())
					.load(url)
					.noFade()
					.centerCrop().resize(size, size)
					//.placeholder(R.drawable.ic_empty)
					.into(imgThumbnail);
		}
	}
	
	// Loads the info window
	private class InfoWindowRefresher implements Callback {
		   private Marker markerToRefresh;

		   private InfoWindowRefresher(Marker markerToRefresh) {
		        this.markerToRefresh = markerToRefresh;
		    }

		    @Override
		    public void onSuccess() {
		        markerToRefresh.showInfoWindow();
                //update_info_window(markerToRefresh);
		    }

		    @Override
		    public void onError() {}
	}
}