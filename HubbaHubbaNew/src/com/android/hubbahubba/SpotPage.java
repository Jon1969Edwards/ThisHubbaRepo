package com.android.hubbahubba;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
//import com.android.hubbahubba.ListViewFavorites.ListViewItem;

//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
//import com.nostra13.universalimageloader.utils.L;

public class SpotPage extends Activity {
	
	private static final int SELECT_PHOTO = 1;
	private static final int TAKE_PHOTO = 2;
	public static final int MEDIA_TYPE_IMAGE = 3;
	public static final int MEDIA_TYPE_VIDEO = 4;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private static final String JPEG_FILE_SUFFIX = "Hubba_Hubba";
	private static final String JPEG_FILE_PREFIX = "Hubba_Hubba";


	ImageLoader imageLoader = ImageLoader.getInstance();
	//private static final String TEST_FILE_NAME = "Universal Image Loader @#&=+-_.,!()~'%20.png";

	HubbaDBAdapter dbHelper;
	int keyValue;
	Cursor c;

	TextView mRating, mLevel, mDifficulty, mTitle, mDist, mComments, mType;
	ImageView mImage;
	String mImagePath;
	Uri imageViewUri;
	String mCurrentPhotoPath;
	
	//NEW VARS
	Bitmap mImageBitmap;
	ImageView mImageView;
	private Uri fileUri;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spot_page_layout);

		// find the intent keyid passed through the intent.
		// this is used in the DB Query
		if(getIntent().getExtras() != null){
			
			Bundle showData = getIntent().getExtras();
			keyValue = showData.getInt("keyid");			 //TODO this crashes the view spot page from the map
		}
		
			
		
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
		
		  if (c.moveToFirst()) {
	            do {
	                mTitle.setText(c.getString(1));
	                //mType.setText(c.getString(2));
	                mRating.setText(c.getString(5));
	                mDifficulty.setText(c.getString(6));
	                mLevel.setText(c.getString(7));
	                //mComments.setText(c.getString(8));
	                
	                //now extract the image path
	                mImagePath = c.getString(9);
	                
	                if(mImagePath != null ) {
	                	imageViewUri = Uri.parse(mImagePath);
	                	mImage.setImageURI(imageViewUri);
	                }
	                
	                
	            } while (c.moveToNext());
	        }

		//NEW
		  /*
			File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
				if (!testImageOnSdCard.exists()) {
					copyTestImageToSdCard(testImageOnSdCard);
				}
			

			ListViewItem item = new ListViewItem(getIntent());

			ImageView imgThumbnail = (ImageView) findViewById(R.id.imgThumbnail);
			TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
			TextView txtOverallRating = (TextView) findViewById(R.id.txtOverallRating);
			TextView txtPoRating = (TextView) findViewById(R.id.txtPoRating);
			TextView txtDiffRating = (TextView) findViewById(R.id.txtDiffRating);
			TextView txtDistance = (TextView) findViewById(R.id.txtDistance);

			imgThumbnail.setImageResource(item.Thumbnail);
			txtTitle.setText(item.Title);
			txtOverallRating.setText(String.valueOf(item.OverallRating));
			txtPoRating.setText(String.valueOf(item.PoRating));
			txtDiffRating.setText(String.valueOf(item.DiffRating));
			txtDistance.setText(String.format("%.2f", item.Distance));
			
			*/
		  
			// create buttons
			Button viewMapButton = (Button) findViewById(R.id.viewMapButton);
			Button uploadPhotosButton = (Button) findViewById(R.id.uploadPhotoButton);
			Button commentsButton = (Button) findViewById(R.id.commentsButton);
			Button favoritesButton = (Button) findViewById(R.id.favoritesButton);
			Button takePhotoButton = (Button) findViewById(R.id.takePhotoButton);
			
			takePhotoButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					//TODO take picture
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					 
					// TAKE PICTURE
					try {
						dispatchTakePictureIntent(TAKE_PHOTO);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// SAVE TO FILE
					File f = null;
					
					try {
						f = createImageFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(f != null){
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					}
					else{
						//TODO output error message
					}
					
					//TODO ADD TO DO
					
					// FOR VIEWING THE PHOTO
					// handleSmallCameraPhoto(takePictureIntent);
					
					/*
					File storageDir = new File(
						    Environment.getExternalStoragePublicDirectory(
						        Environment.DIRECTORY_PICTURES
						    ), 
						    getAlbumName()
						);   
					
					*/
				}

			});

			viewMapButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Intent intent = new Intent(SpotPage.this, ActionBarActivity.class);
					startActivity(intent);
				}

			});
			/*
			uploadPhotosButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Intent intent = new Intent(SpotPage.this, MainActivity.class);
					startActivity(intent);
				}

			});
			*/
			uploadPhotosButton.setOnTouchListener(new OnTouchListener() {

				// @Override
				public boolean onTouch(View v, MotionEvent event) {

					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
					photoPickerIntent.setType("image/*");
					startActivityForResult(photoPickerIntent, SELECT_PHOTO); 
					
					return true;
				}
				
			});
			
			favoritesButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Intent intent = new Intent(SpotPage.this, ActionBarActivity.class);
					startActivity(intent);
				}

			});

			commentsButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Intent intent = new Intent(SpotPage.this,ListViewComments.class);
					startActivity(intent);
				}

			});

			GridView gridview = (GridView) findViewById(R.id.gridviewPictures);
			gridview.setAdapter(new ImageAdapter(this));
			
			
			gridview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

					Intent intent = new Intent(SpotPage.this, ViewImage.class);
					int imageID = (Integer)parent.getAdapter().getItem(position);
					intent.putExtra("imageName", imageID);
					startActivity(intent);
				}
			});
			
			
			//TO AVOID SCROLLING LAGS
			boolean pauseOnScroll = false; // or true
			boolean pauseOnFling = true; // or false
			PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);
			gridview.setOnScrollListener(listener);
			
			
	}
	
	// NEW
	
	private void dispatchTakePictureIntent(final int actionCode) throws IOException {    
	 // create Intent to take a picture and return control to the calling application
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

	    // start the image capture Intent
	    startActivityForResult(intent, actionCode);
	    
	    File f = createImageFile();
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

	}
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
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
	
	// WAS A TESTER FUNCTION
	/*	
	private void copyTestImageToSdCard(final File testImageOnSdCard) {
		new Thread(new Runnable() {
				@Override
				public void run() {
					try {
					InputStream is = getAssets().open(TEST_FILE_NAME);
					FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
					byte[] buffer = new byte[8192];
					int read;
						try {
							while ((read = is.read(buffer)) != -1) {
								fos.write(buffer, 0, read);
							}
						}
						finally {
								fos.flush();
								fos.close();
								is.close();
								}
						}
						catch (IOException e) {
							L.w("Can't copy test image onto SD card");
						}
				}
		})
		.start();
	}
	*/
	/*
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		imageLoader.destroy();
		super.onDestroy();
	}
	*/
}