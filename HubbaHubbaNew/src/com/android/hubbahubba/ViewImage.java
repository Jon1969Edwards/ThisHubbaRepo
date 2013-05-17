package com.android.hubbahubba;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ViewImage extends Activity {

	int mImageID;
	Bitmap mBitmap;
	ImageView imageView;
	int counter;				//TODO from db
	int negCounter;				//TODO from db
	boolean votedOn = false;	//TODO from db

	// ImageLoader imageLoader = ImageLoader.getInstance();
	// private Context mContext;

	// public ViewImage(Context c) {
	// mContext = c;
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen_image);

		imageView = (ImageView) findViewById(R.id.image);
		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			mImageID = bundle.getInt("imageName");

			// SET IMAGE LOADER
			//imageView.setLayoutParams(new LayoutParams(720, 960));
			//imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

			// DisplayImageOptions options = new DisplayImageOptions.Builder()
			// .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
			// .showImageForEmptyUri(R.drawable.ic_empty)
			// .showImageOnFail(R.drawable.ic_error)
			// .cacheInMemory()
			// .cacheOnDisc()
			// .bitmapConfig(Bitmap.Config.RGB_565)
			// .build();
			//
			// imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
			// imageLoader.displayImage("drawable://" + mImageID, imageView,
			// options);

			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 2;
			mBitmap = BitmapFactory.decodeResource(getResources(), mImageID, bfo);
			imageView.setImageBitmap(mBitmap);
		}
		
		//Buttons to incriment/decriment counter
		Button thumbsUpButton= (Button) findViewById(R.id.thumbs_up);
		Button thumbsDownButton= (Button) findViewById(R.id.thumbs_down);
		
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
