package com.android.hubbahubba;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class SpotPage extends Activity {
	
	//private static final int SELECT_PHOTO = 1;
	//private static final int TAKE_PHOTO = 2;
	public static final int MEDIA_TYPE_IMAGE = 3;
	public static final int MEDIA_TYPE_VIDEO = 4;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private static final String JPEG_FILE_SUFFIX = "Hubba_Hubba";
	private static final String JPEG_FILE_PREFIX = "Hubba_Hubba";
	//private GridAdapter gridAdapter;


	//ImageLoader imageLoader = ImageLoader.getInstance();
	//private static final String TEST_FILE_NAME = "Universal Image Loader @#&=+-_.,!()~'%20.png";

	HubbaDBAdapter dbHelper;
	int keyValue = -1;
	Cursor c = null;

	TextView mRating, mLevel, mDifficulty, mTitle, mDist, mComments, mType;
	ImageView mImage;
	String mImagePath;
	Uri imageViewUri;
	String mCurrentPhotoPath;
	Context context = this;
	
	//NEW VARS
	Bitmap mImageBitmap;
	ImageView mImageView;
	//private Uri fileUri;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spot_page_layout);
		
		// initialize everything now
		mTitle = (TextView) findViewById(R.id.txtTitle);
		mRating = (TextView) findViewById(R.id.txtOverallRating);
		mLevel = (TextView) findViewById(R.id.txtPoRating);
		mDist = (TextView) findViewById(R.id.txtDistance);
		mDifficulty = (TextView) findViewById(R.id.txtDiffRating);
		mImage = (ImageView) findViewById(R.id.imgThumbnail);

		dbHelper = new HubbaDBAdapter(this);
		dbHelper.open();
		c = dbHelper.queryAll(keyValue);
		try{
			/*if (c.moveToFirst()) {
				
		        do {
		            mTitle.setText(c.getString(1));
		            //mType.setText(c.getString(2));
			        mRating.setText(c.getString(5));
			        mDifficulty.setText(c.getString(6));
			        mLevel.setText(c.getString(7));
			        //mComments.setText(c.getString(8));
			        
			        //now extract the image path
			        mImagePath = c.getString(9);
			        
		        	// Convert the dp value for xml to pixels (casted to int from float)
			        int size = Image.convertDpToPixel(85, context);
			        
			        // Toast.makeText(this, "Image path:\n" +
		            //         mImagePath + "with size: " + size, Toast.LENGTH_LONG).show();
			        
			        // TODO: add if back in but since no db leave out for now
			        if(mImagePath != null ) {
			    	    // use picasso to load the image into view
			    	    Picasso.with(context)
			    	    	   //.load(mImagePath)
			    	    	   .load(R.drawable.gettinthere)
			    	    	   .centerCrop()
			    	    	   .resize(size, size)
			    	    	   .placeholder(R.drawable.gettinthere)
			    	    	   .into(mImage);
			    	    
			        }
			        else{
			        	Picasso.with(context)
		    	    	   .load(R.drawable.gettinthere)
		    	    	   .centerCrop()
		    	    	   .resize(size, size)
		    	    	   .placeholder(R.drawable.ic_empty)
		    	    	   .into(mImage);
			        }
			    }
			    while (c.moveToNext());
			}
			else{
				*/
			
			Bundle showData = getIntent().getExtras();
			String spot_id = showData.getString("spot_id");	
			
			//Toast.makeText(this, "spot id:\n" +
			//                     spot_id, Toast.LENGTH_LONG).show();
			
			// for now just sets the title
			Spot.getSpotInfoByID(this, spot_id, context);
			
    	    int size = Image.convertDpToPixel(90, context);
    	    
    	    // Toast.makeText(this, "Size:\n" +
            //          size, Toast.LENGTH_LONG).show();
    	    
    	    // use picasso to load the image into view
    	    Picasso.with(context)
    	    	   .load(R.drawable.gettinthere)
    	    	   .centerCrop()
    	    	   .resize(size, size)
    	    	   .placeholder(R.drawable.ic_empty)
    	    	   .into(mImage);
	    	    
			//}
		}
		finally {
	        // this gets called even if there is an exception somewhere above
	        if(c != null)
            c.close();
		 }
	  
		// create buttons
		Button viewMapButton = (Button) findViewById(R.id.viewMapButton);
		Button uploadPhotosButton = (Button) findViewById(R.id.uploadPhotoButton);
		Button commentsButton = (Button) findViewById(R.id.commentsButton);
		Button uploadCommentButton = (Button) findViewById(R.id.uploadCommentButton);
		//Button favoritesButton = (Button) findViewById(R.id.favoritesButton);

		viewMapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Bundle bundleData = new Bundle();
				
				TextView Lat = (TextView) findViewById(R.id.lat);
				TextView Lon = (TextView) findViewById(R.id.lon);
				
				String lon = (String) Lon.getText();
				String lat = (String) Lat.getText();
				
				Toast.makeText(context, "lat = " + lat + " and lon = " + lon, Toast.LENGTH_LONG).show();
				
				bundleData.putString("lat", lat);
				bundleData.putString("lon", lon);
				
				Intent intent = new Intent(SpotPage.this, ActionBarActivity.class);
				
				intent.putExtras(bundleData);
				startActivity(intent);
			}

		});
		
		uploadPhotosButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(SpotPage.this, UploadImage.class);
				startActivity(intent);
			}
		});
		
		uploadCommentButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(SpotPage.this, UploadComment.class);
				startActivity(intent);
			}
		});
		
		
		/*
		favoritesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(SpotPage.this, ActionBarActivity.class);
				startActivity(intent);
			}

		});
		 */
		commentsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(SpotPage.this,ListViewComments.class);
				startActivity(intent);
			}

		});
		
		
		GridView gridview = (GridView) findViewById(R.id.gridviewPictures);
		gridview.setAdapter(new GridAdapter(this));
		
		// TODO: call getThumbnail from the Image class on the gridview's position
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Intent intent = new Intent(SpotPage.this, ViewImage.class);
				//int imageID = (Integer)parent.getAdapter().getItem(position);
				int imageID = mThumbIds[position];
				intent.putExtra("imageName", imageID);
				startActivity(intent);
			}
		});
		
		/*
		//TO AVOID SCROLLING LAGS
		boolean pauseOnScroll = false; // or true
		boolean pauseOnFling = true; // or false
		PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);
		gridview.setOnScrollListener(listener);
		*/
			
	}

	/** Create a file Uri for saving an image or video *//*
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}*/
	
	/** Create a File for saving an image or video */
	@SuppressLint("SimpleDateFormat")
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Toast.makeText(this, "Image saved to:\n" +
	                     data.getData(), Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }

	    if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Video captured and saved to fileUri specified in the Intent
	            Toast.makeText(this, "Video saved to:\n" +
	                     data.getData(), Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the video capture
	        } else {
	            // Video capture failed, advise user
	        }
	    }
	}
	
	@SuppressLint("SimpleDateFormat")
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = 
	        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
	    File image = File.createTempFile(
	        imageFileName, 
	        JPEG_FILE_SUFFIX//, 
	        //getAlbumDir()
	    );
	    mCurrentPhotoPath = image.getAbsolutePath();
	    return image;
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//imageLoader.destroy();
		super.onDestroy();
		dbHelper.close();
	}
	
	// references to our images
    private Integer[] mThumbIds = {
    		R.drawable.riley1, R.drawable.riley2, R.drawable.riley3,
            R.drawable.connorock , R.drawable.indysunburst,
            R.drawable.nosegrabup , R.drawable.img1,
            R.drawable.heart1, R.drawable.deatroit1, R.drawable.detroit2,

    };
	
}