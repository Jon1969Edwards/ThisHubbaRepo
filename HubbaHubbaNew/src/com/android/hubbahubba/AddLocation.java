package com.android.hubbahubba;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
//import android.view.Menu;

public class AddLocation extends Activity {

	//declare Strings to pass through to AddSpot activity
	String mTitle, mType, mAddress, mCity;
	Button cancelButton, locateOnMapButton, continueButton;
	EditText spotTitle, spotAddress, spotCity;
	Spinner typeSpinner;
	String LatLong;
	String FromPage;
	String addressList[] = new String[10];
	double selectedLat;
    double selectedLng;
	//String ViewMap = "ViewMap";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_location_layout);
		
		FromPage = getIntent().getStringExtra("FromPage");
		
		// FOR TOAST
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		String text;
		
		//initialize all needed objects 
		cancelButton = (Button) findViewById(R.id.cancelButton);
		locateOnMapButton = (Button) findViewById(R.id.locateOnMap);
		continueButton = (Button) findViewById(R.id.continueButton);
		spotTitle = (EditText) findViewById(R.id.spotTitle);
		spotAddress = (EditText) findViewById(R.id.address);
		spotCity = (EditText) findViewById(R.id.cityStateZip);
		typeSpinner = (Spinner) findViewById(R.id.spotTypeSpinner);
		
		// why does this not work?
		if(FromPage.equals("ViewMap")){
			// TODO only add the marker to the map if you press the "AddSpotButton" in addspot
			LatLong = getIntent().getStringExtra("LatLong");
			//LatLong = LatLong.substring(1, LatLong.length() - 1);
			
			String[] separated = LatLong.split(",");
			String latitude = separated[0];
			String longitude = separated[1];
			
			text = latitude + "   " + longitude;
			
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
			List<Address> list;
			Address address;
			
			// GET ADDRESS FROM THE LATATUDE AND LONGITUDE
			Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
			try {
				List<Address> List = geocoder.getFromLocation(Double.parseDouble(latitude),Double.parseDouble(longitude),1);
				
				address = List.get(0);
				text = address.toString();
				Toast toast0 = Toast.makeText(context, text, duration);
				toast0.show();
				
				for (int j=0; j<1; j++){
				    Address returnedAddress = List.get(j);
				    StringBuilder strReturnedAddress = new StringBuilder();
				    for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
				     strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				     if(i == 0){
				    	 spotAddress.setText(returnedAddress.getAddressLine(i));
				     }
				     else if(i == 1){
				    	 spotCity.setText(returnedAddress.getAddressLine(i));
				     }
				    }
				    addressList[j] = strReturnedAddress.toString();
				    
				    text = addressList[j];
					Toast toast1 = Toast.makeText(context, text, duration);
					toast1.show();
				 }
				
				
			} catch (NumberFormatException e) {
				
				double D1 = Double.parseDouble(latitude);
				double D2 = Double.parseDouble(longitude);
				
				String lat = Double.toString(D1);
				String lon = Double.toString(D2);
				
				text = lat + " " + lon + " NUMBER_FORMAT_EXCEPTION";
				
				Toast toast0 = Toast.makeText(context, text, duration);
				toast0.show();
				
				e.printStackTrace();
			} catch (IOException e) {
				
				double D1 = Double.parseDouble(latitude);
				double D2 = Double.parseDouble(longitude);
				
				String lat = Double.toString(D1);
				String lon = Double.toString(D2);
				
				text = lat + " " + lon + " IOE_EXCEPTION";
				
				Toast toast0 = Toast.makeText(context, text, duration);
				toast0.show();
				
				e.printStackTrace();
			}
			
			/*
			if(list != null && list.get(0) != null){
				// TODO THIS DOESNT WORK
				address = list.get(0);
					text = address.toString();
					Toast toast0 = Toast.makeText(context, text, duration);
					toast0.show();
			}
			else{
					//TODO GETTING IOE EXCEPTION  
					text = "SHEEEETTTT";
					Toast toast1 = Toast.makeText(context, text, duration);
					toast1.show();
			}*/
		}
		
		//initialize all needed objects 
		cancelButton = (Button) findViewById(R.id.cancelButton);
		locateOnMapButton = (Button) findViewById(R.id.locateOnMap);
		continueButton = (Button) findViewById(R.id.continueButton);
		spotTitle = (EditText) findViewById(R.id.spotTitle);
		spotAddress = (EditText) findViewById(R.id.address);
		spotCity = (EditText) findViewById(R.id.cityStateZip);
		typeSpinner = (Spinner) findViewById(R.id.spotTypeSpinner);
		
		


		// login Button takes you to the map view (ROB: YOU WILL NEED TO CHANGE THIS BACK TO MAPVIEW, not MainActivity)
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				finish();
				/*
				Intent intent = new Intent(AddLocation.this,
						MainActivity.class);
				startActivity(intent);
				*/
			}
		});
		
		locateOnMapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(AddLocation.this, ActionBarActivity.class);
				startActivity(intent);
			}

		});

		continueButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				mTitle = spotTitle.getText().toString();
				mAddress = spotAddress.getText().toString();
				mCity = spotCity.getText().toString();
				mType = typeSpinner.getSelectedItem().toString();
				
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_LONG;
				CharSequence text;

				if(mTitle == null || mTitle.equals("")){
					text = "Please insert a title for the spot.";
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					
				}
				else if(mAddress == null || mAddress.equals("")){
					text = "Please insert an address for the spot.";
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				else if(mCity == null || mCity.equals("")) {
					text = "Please insert a city, state, and zip for the spot.";
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
				else {
					Intent intent = new Intent(AddLocation.this, AddSpot.class);
					intent.putExtra("spotTitle", mTitle);
					intent.putExtra("spotType", mType);
					intent.putExtra("spotAddress", mAddress);
					intent.putExtra("spotCity", mCity );
					
					FromPage = getIntent().getStringExtra("FromPage");
					
					if(FromPage.equals("ListViewHubba") || FromPage.equals("ListViewFavorites") || FromPage.equals("ViewMap")){
						
						List<Address> address = null; //= new Address(Locale.ENGLISH);
						try {
							address = new Geocoder(getApplicationContext()).getFromLocationName("address here", 1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							text = "IOE";

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
							e.printStackTrace();
						}
						
						//address.setAddressLine(0, mAddress);
						//address.setAddressLine(1, mCity);
						
						try {
							address = new Geocoder(getApplicationContext()).getFromLocationName(mAddress + ' ' + mCity, 1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(address.get(0).hasLatitude() && address.get(0).hasLongitude()){
						    selectedLat = address.get(0).getLatitude();
						    selectedLng = address.get(0).getLongitude();
						    
						    String lat = Double.toString(selectedLat);
						    String lng = Double.toString(selectedLng);
						    
						    text = "IT WORKED " + lat + " " + lng;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
						else{
							// TODO Better err message
							text = "Incorrect Address";

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
					}
					
					intent.putExtra("Lat", selectedLat);
					intent.putExtra("Lng", selectedLng);
					//put extra the doubles
					
					
					
					startActivity(intent);
				}
				/*
			else{
				// TODO better error message
				text = "You came from an illegal page, try again!";

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
			*/
		}


		});

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_sign_up, menu);
	// return true;
	// }
}
