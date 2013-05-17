package com.android.hubbahubbanew;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
//import android.view.Menu;
import android.content.Intent;

public class AddLocation extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location);
        
        
      //create back button
        Button backButton = (Button)findViewById(R.id.backButton);
        Button cancelButton = (Button)findViewById(R.id.cancelButton);
        Button locateOnMapButton = (Button)findViewById(R.id.locateOnMap);
        Button continueButton = (Button)findViewById(R.id.continueButton);
        		
        //back button goes back to the main page
        backButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(AddLocation.this, ListViewHubba.class);
        		startActivity(intent);
        	}

        });
        
        //login Button takes you to the map view
        cancelButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		Intent intent = new Intent(AddLocation.this, ListViewHubba.class);
        		startActivity(intent);
        	}
        });
        
        locateOnMapButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(AddLocation.this, ViewMap.class);
        		startActivity(intent);
        	}

        }); 
        
        continueButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(AddLocation.this, AddSpot.class);
        		startActivity(intent);
        	}

        });
        
    }

}
