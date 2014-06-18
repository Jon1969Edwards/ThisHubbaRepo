package com.android.hubbahubba;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewImage extends Activity {

	int mImageID;
	String url;
	String display_name;
	ImageView imageView;
	int counter;				// TODO from db
	int negCounter;				// TODO from db
	boolean votedOn = false;	// TODO from db
	String rider_name;			// TODO from db

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen_image);
		Context context = this;

        // TODO: up down buttons
		//Buttons to incriment/decriment counter
		//Button thumbsUpButton= (Button) findViewById(R.id.thumbs_up);
		//Button thumbsDownButton= (Button) findViewById(R.id.thumbs_down);
		
		// TODO: TO BE FILLED FROM THE DB
		TextView rider = (TextView) findViewById(R.id.rider_text);
		TextView photog = (TextView) findViewById(R.id.photog_text);

		imageView = (ImageView) findViewById(R.id.image);
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			url = bundle.getString("url");
			display_name = bundle.getString("display_name");
            rider_name = bundle.getString("rider_name");


		    // Use picasso to load the image into view
		    // XXX - THIS MUST STAY CONSISTANT WITH THE SIZE ON SPOT PAGE
			Display display = getWindowManager().getDefaultDisplay();
	        Point size = new Point();
	        display.getSize(size);
	        int width = size.x;
	        int height = size.y;
	        
	        // The new size we want to scale to
	        width -= 80;
	        height -= 300;
	        
		    Picasso.with(context)
		    	   .load(url)
		    	   .centerInside()
		    	   .resize(width, height)
		    	   .placeholder(R.drawable.ic_empty)
		    	   .into(imageView);
		    
		    photog.setText("Photographer: " + display_name);
            if(rider_name.trim().length() > 0){
                rider.setText("Rider: " + rider_name);
            }
		    else{
                rider.setVisibility(View.GONE);
            }
		}
		/*
        thumbsDownButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		if(votedOn == false){
        			negCounter++;
        			votedOn = true;
        		}
        	}
        });
        
        thumbsUpButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		if(votedOn == false){
        			counter++;
        			votedOn = true;
        		}
        	}
        });
        */

	};
}
