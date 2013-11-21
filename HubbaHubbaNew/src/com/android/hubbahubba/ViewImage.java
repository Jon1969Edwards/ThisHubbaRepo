package com.android.hubbahubba;

import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewImage extends Activity {

	int mImageID;
	Bitmap mBitmap;
	ImageView imageView;
	int counter;				// TODO from db
	int negCounter;				// TODO from db
	boolean votedOn = false;	// TODO from db
	String rider;				// TODO from db
	String photog;				// TODO from db

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen_image);
		Context context = this;

		imageView = (ImageView) findViewById(R.id.image);
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			mImageID = bundle.getInt("imageName");
			/*
			// Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeResource(getResources(), mImageID, o);
	        
	        // Get screen size
	        Display display = getWindowManager().getDefaultDisplay();
	        Point size = new Point();
	        display.getSize(size);
	        int width = size.x;
	        int height = size.y;
	        
	        // The new size we want to scale to
	        final int REQUIRED_WIDTH = width - 50;
	        final int REQUIRED_HEIGHT = height - 50;

	        // Find the correct scale value. It should be the power of 2.
	        int width_tmp = o.outWidth, height_tmp = o.outHeight;
	        int scale = 1;
	        while (true) {
	            if (width_tmp / 2 < REQUIRED_WIDTH
	               || height_tmp / 2 < REQUIRED_HEIGHT) {
	                break;
	            }
	            width_tmp /= 2;
	            height_tmp /= 2;
	            scale *= 2;
	        }
	        // TODO: Delete this
	        //scale = 2;

	        // Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = scale;
	        mBitmap =  BitmapFactory.decodeResource(getResources(), mImageID, o2);
	        
	        // To see if it scaled
	        Context context = getApplicationContext();
	        String text = "Scale: " + Integer.toString(scale) + " Swidth: " + width + " pic width: " + width_tmp;
	        int duration = Toast.LENGTH_SHORT;
			
			
	        Toast toast = Toast.makeText(context, text, duration);
	        toast.show();
	        
	        imageView.setImageBitmap(mBitmap);
		    */
			
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
		    	   .load(mImageID)
		    	   //.centerCrop()
		    	   .centerInside()
		    	   .resize(width, height)
		    	   .placeholder(R.drawable.ic_empty)
		    	   .into(imageView);
			
	        /*
			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 2;
			mBitmap = BitmapFactory.decodeResource(getResources(), mImageID, bfo);
			imageView.setImageBitmap(mBitmap);
			*/
		}
		
		//Buttons to incriment/decriment counter
		Button thumbsUpButton= (Button) findViewById(R.id.thumbs_up);
		Button thumbsDownButton= (Button) findViewById(R.id.thumbs_down);
		
		// TODO: TO BE FILLED FROM THE DB
		TextView rider = (TextView) findViewById(R.id.rider_text);
		TextView photog = (TextView) findViewById(R.id.photog_text);
		
		
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

	};
}
