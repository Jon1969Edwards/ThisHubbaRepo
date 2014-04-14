package com.android.hubbahubba;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AddImage extends Activity{

	//declare Strings to pass through to AddSpot activity
	Button cancelButton, locateOnMapButton, uploadButton;
	EditText riderName;
	//EditText userName;
    
    //for take photo
	String mCurrentPhotoPath;
	ImageView mImage;
	ImageButton takePhotoButton, uploadPhotoButton;
	String mImagePath;
	Uri imageViewUri;
	Uri mSelectedImage = null;//Uri.parse("android.resource://com.segf4ult.test/" + R.drawable.ic_launcher);
	Bitmap spotImage;
	Bitmap mImageBitmap;
	ImageView mImageView;
	private static final int SELECT_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    public static final int MEDIA_TYPE_IMAGE = 3;
	public static final int MEDIA_TYPE_VIDEO = 4;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private static final String JPEG_FILE_SUFFIX = "Hubba_Hubba";
	private static final String JPEG_FILE_PREFIX = "Hubba_Hubba";
	String rider;
	String user;
	boolean photo_taken = false;
	boolean photo_uploaded = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_image);
		
		
		//initialize all needed objects 
		cancelButton = (Button) findViewById(R.id.cancelButton);
		uploadButton = (Button) findViewById(R.id.uploadButton);
		takePhotoButton = (ImageButton) findViewById(R.id.takePhotoButton);
		uploadPhotoButton = (ImageButton) findViewById(R.id.uploadPhotoButton);
		riderName = (EditText) findViewById(R.id.rider);
		//userName = (EditText) findViewById(R.id.user);
		
		
		uploadPhotoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				uploadPhotoButton.setPressed(true);
				
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);
			}
		});
		
		takePhotoButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//TODO take picture
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, TAKE_PHOTO);
				
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
			}
		});
		
		// login Button takes you to the map view (ROB: YOU WILL NEED TO CHANGE THIS BACK TO MAPVIEW, not MainActivity)
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(AddImage.this, ActionBarActivity.class);
				setResult(Activity.RESULT_CANCELED, intent);
				finish();
			}
		});

		uploadButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_LONG;
				CharSequence text;
				if(mSelectedImage != null && riderName.getText().toString().trim().length() > 0 /*&& userName.getText().toString().trim().length() > 0*/){
					
					// get spot id
			        Bundle showData = getIntent().getExtras();
					String spot_id = showData.getString("spot_id");
					
					// TODO: delete this -- get info for debugging
					//String imageURI = mSelectedImage.getPath();
					String imageURI = getImagePath(mSelectedImage);
					Toast.makeText(context, "spot_id = " + spot_id + "\nimage = " + imageURI, duration).show();
					
					// Add image to DB
					Spot.addImage(getApplicationContext(), spot_id, riderName.getText().toString(), imageURI);
					
					// TODO: this should go back to the spot page and reload
					//Intent intent = new Intent(UploadImage.this, ListViewHubba.class);
					
					//startActivity(intent);
					finish();
				}
				else{
					text = "Please fill out all fields to continue";
					Toast toaster = Toast.makeText(context, text, duration);
					toaster.show();
				}
			}
		});
	}
	
	public String getImagePath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		startManagingCursor(cursor);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch(requestCode) {
		    case SELECT_PHOTO:
		        if(resultCode == RESULT_OK){  
		            mSelectedImage = data.getData();
		            
		            Toast.makeText(this, "IMG From:\n" +
		                     data.getData(), Toast.LENGTH_LONG).show();
		            try {
						spotImage = decodeUri(mSelectedImage);
						uploadPhotoButton.setImageBitmap(spotImage);
						//uploadPhotoButton.setBackgroundResource(R.color.abs__background_holo_light);
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            //takePhotoButton.setClickable(false);
		            photo_uploaded = true;
		            if(photo_taken){
		            	takePhotoButton.setImageBitmap(null);
		            }
		        }
		        break;
		        
		    //case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
		    case TAKE_PHOTO:
		        if (resultCode == RESULT_OK) {
		            // Image captured and saved to fileUri specified in the Intent
		            Toast.makeText(this, "Image saved to:\n" +
		                     data.getData(), Toast.LENGTH_LONG).show();
		            mSelectedImage = data.getData();
		            
		            //TODO THE BUG IS SOMEWHERE IN THE TRY CATCH BLOCK
		            
		            try {
						spotImage = decodeUri(mSelectedImage);
						takePhotoButton.setImageBitmap(spotImage);
						Toast.makeText(this, "GOT EM " +
			                     data.getData(), Toast.LENGTH_LONG).show();
					} catch (FileNotFoundException e) {
						Toast.makeText(this, "FILE NOT FOUND FUCKER", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
		            //uploadPhotoButton.setClickable(false);
		            photo_taken = true;
		            if(photo_uploaded){
		            	uploadPhotoButton.setImageDrawable(null);
		            }
		            
		        } else if (resultCode == RESULT_CANCELED) {
		        	Toast.makeText(this, "Image cancelled\n", Toast.LENGTH_LONG).show();
		        } else {
		        	Toast.makeText(this, "Image failed\n", Toast.LENGTH_LONG).show();
		        }
		        
	            break;
	            
		    case CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE:
		        if (resultCode == RESULT_OK) {
		            // Video captured and saved to fileUri specified in the Intent
		            Toast.makeText(this, "Video saved to:\n" +
		                     data.getData(), Toast.LENGTH_LONG).show();
		        } else if (resultCode == RESULT_CANCELED) {
		            // User cancelled the video capture
		        } else {
		            // Video capture failed, advise user
		        }
		        break;
	    }
	}
	
	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
               || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }
	
	@SuppressLint("SimpleDateFormat")
	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = 
	        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
	    File image = File.createTempFile(
	        imageFileName, 
	        JPEG_FILE_SUFFIX
	    );
	    mCurrentPhotoPath = image.getAbsolutePath();
	    return image;
	}
}