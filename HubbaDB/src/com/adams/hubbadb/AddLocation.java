package com.adams.hubbadb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_location_layout);
		
		//initialize all needed objects 
		cancelButton = (Button) findViewById(R.id.cancelButton);
//		locateOnMapButton = (Button) findViewById(R.id.locateOnMap);
		continueButton = (Button) findViewById(R.id.continueButton);
		spotTitle = (EditText) findViewById(R.id.spotTitle);
		spotAddress = (EditText) findViewById(R.id.address);
		spotCity = (EditText) findViewById(R.id.cityStateZip);
		typeSpinner = (Spinner) findViewById(R.id.spotTypeSpinner);
		
		


		// login Button takes you to the map view (ROB: YOU WILL NEED TO CHANGE THIS BACK TO MAPVIEW, not MainActivity)
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(AddLocation.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

		//ROB: UNCOMMENT THIS. only uncommented for testing purposes
		
		/*locateOnMapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(AddLocation.this, ViewMap.class);
				startActivity(intent);
			}

		});*/

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
					startActivity(intent);
				}
				
				
			}

		});

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_sign_up, menu);
	// return true;
	// }
}
