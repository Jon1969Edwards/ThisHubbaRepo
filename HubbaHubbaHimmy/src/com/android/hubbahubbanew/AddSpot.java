package com.android.hubbahubbanew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
//import android.view.Menu;

public class AddSpot extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_spot);
        
        
        //create back button
        Button backButton = (Button)findViewById(R.id.backButton);
        final Button oneButtonDiff = (Button)findViewById(R.id.oneButtonDiff);
        
        
        oneButtonDiff.setOnTouchListener(new OnTouchListener() {

          //  @Override
            public boolean onTouch(View v, MotionEvent event) {
                oneButtonDiff.setPressed(true);
                return true;
            }
        });
        		
        //back button goes back to the main page
        backButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(AddSpot.this, AddLocation.class);
        		startActivity(intent);
        	}

        });
        
        //create back button
        Button addSpotButton = (Button)findViewById(R.id.addSpotButton);
        		
        //back button goes back to the main page
        addSpotButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(AddSpot.this, SpotPage.class);
        		startActivity(intent);
        	}

        });
        
        
    }

}
