package com.android.hubbahubba;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewImage extends Activity {

	int mImageID;
	Bitmap mBitmap;
	ImageView imageView;
	int counter;				// TODO from db
	int negCounter;				// TODO from db
	boolean votedOn = false;	// TODO from db
	String rider;				// TODO from db
	String photog;				// TODO from db

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen_image);

		imageView = (ImageView) findViewById(R.id.image);
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			mImageID = bundle.getInt("imageName");

			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 2;
			mBitmap = BitmapFactory.decodeResource(getResources(), mImageID, bfo);
			imageView.setImageBitmap(mBitmap);
		}
		
		//Buttons to incriment/decriment counter
		Button thumbsUpButton= (Button) findViewById(R.id.thumbs_up);
		Button thumbsDownButton= (Button) findViewById(R.id.thumbs_down);
		
		// TODO: TO BE FILLED FROM THE DB
		TextView rider = (EditText) findViewById(R.id.rider_text);
		TextView photog = (EditText) findViewById(R.id.photog_text);
		
		
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
