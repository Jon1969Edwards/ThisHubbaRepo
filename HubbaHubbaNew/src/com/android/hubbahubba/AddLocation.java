package com.android.hubbahubba;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddLocation extends Activity {

	//declare Strings to pass through to AddSpot activity
	String mTitle, mType, mAddress, mCity;
	Button cancelButton, locateOnMapButton, continueButton;
	EditText spotTitle, spotAddress, spotCity;
	Spinner typeSpinner;
	String LatLong;
	String FromPage;
	String addressList[] = new String[10];
	double selectedLat;
    double selectedLng;
    
    //for take photo
	String mCurrentPhotoPath;
	ImageView mImage;
	ImageButton takePhotoButton, uploadPhotoButton;
	String mImagePath;
	Uri imageViewUri;
	Uri mSelectedImage = Uri.parse("android.resource://com.segf4ult.test/" + R.drawable.ic_launcher);
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
	//boolean photo_selected = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_location_layout);
		
		FromPage = getIntent().getStringExtra("FromPage");
		
		// FOR TOAST
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;
		String text;
		
		//initialize all needed objects 
		cancelButton = (Button) findViewById(R.id.cancelButton);
		locateOnMapButton = (Button) findViewById(R.id.locateOnMap);
		continueButton = (Button) findViewById(R.id.continueButton);
		spotTitle = (EditText) findViewById(R.id.spotTitle);
		spotAddress = (EditText) findViewById(R.id.address);
		spotCity = (EditText) findViewById(R.id.cityStateZip);
		typeSpinner = (Spinner) findViewById(R.id.spotTypeSpinner);
		takePhotoButton = (ImageButton) findViewById(R.id.takePhotoButton);
		uploadPhotoButton = (ImageButton) findViewById(R.id.uploadPhotoButton);
		
		// why does this not work?
		if(FromPage.equals("ViewMap")){
			// TODO only add the marker to the map if you press the "AddSpotButton" in addspot
			LatLong = getIntent().getStringExtra("LatLong");
			//LatLong = LatLong.substring(1, LatLong.length() - 1);
			
			String[] separated = LatLong.split(",");
			String latitude = separated[0];
			String longitude = separated[1];
			
			text = latitude + "   " + longitude;
			
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
			Address address;
			
			// GET ADDRESS FROM THE LATATUDE AND LONGITUDE
			Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
			try {
				List<Address> List = geocoder.getFromLocation(Double.parseDouble(latitude),Double.parseDouble(longitude),1);
				
				address = List.get(0);
				text = address.toString();
				Toast toast0 = Toast.makeText(context, text, duration);
				toast0.show();
				
				for (int j=0; j<1; j++){
				    Address returnedAddress = List.get(j);
				    StringBuilder strReturnedAddress = new StringBuilder();
				    for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
				     strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				     if(i == 0){
				    	 spotAddress.setText(returnedAddress.getAddressLine(i));
				     }
				     else if(i == 1){
				    	 spotCity.setText(returnedAddress.getAddressLine(i));
				     }
				    }
				    addressList[j] = strReturnedAddress.toString();
				    
				    text = addressList[j];
					Toast toast1 = Toast.makeText(context, text, duration);
					toast1.show();
				 }
				
				
			} catch (NumberFormatException e) {
				
				double D1 = Double.parseDouble(latitude);
				double D2 = Double.parseDouble(longitude);
				
				String lat = Double.toString(D1);
				String lon = Double.toString(D2);
				
				text = lat + " " + lon + " NUMBER_FORMAT_EXCEPTION";
				
				Toast toast0 = Toast.makeText(context, text, duration);
				toast0.show();
				
				e.printStackTrace();
			} catch (IOException e) {
				
				double D1 = Double.parseDouble(latitude);
				double D2 = Double.parseDouble(longitude);
				
				String lat = Double.toString(D1);
				String lon = Double.toString(D2);
				
				text = lat + " " + lon + " IOE_EXCEPTION";
				
				Toast toast0 = Toast.makeText(context, text, duration);
				toast0.show();
				
				e.printStackTrace();
			}
			
			/*
			if(list != null && list.get(0) != null){
				// TODO THIS DOESNT WORK
				address = list.get(0);
					text = address.toString();
					Toast toast0 = Toast.makeText(context, text, duration);
					toast0.show();
			}
			else{
					//TODO GETTING IOE EXCEPTION  
					text = "SHEEEETTTT";
					Toast toast1 = Toast.makeText(context, text, duration);
					toast1.show();
			}*/
		}
		
		
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
				Intent intent = new Intent(AddLocation.this, ActionBarActivity.class);
				setResult(Activity.RESULT_CANCELED, intent);
				finish();
				/*
				Intent intent = new Intent(AddLocation.this,
						MainActivity.class);
				startActivity(intent);
				*/
			}
		});
		
		locateOnMapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(AddLocation.this, ActionBarActivity.class);
				startActivity(intent);
			}

		});

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
					intent.putExtra("mSelectedImage", mSelectedImage.toString());
					
					text = mSelectedImage.toString();
					Toast toaster = Toast.makeText(context, text, duration);
					toaster.show();
					
					FromPage = getIntent().getStringExtra("FromPage");
					
					if(FromPage.equals("ListViewHubba") || FromPage.equals("ListViewFavorites") || FromPage.equals("ViewMap")){
						
						List<Address> address = null; //= new Address(Locale.ENGLISH);
						try {
							address = new Geocoder(getApplicationContext()).getFromLocationName("address here", 1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							text = "IOE";

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
							e.printStackTrace();
						}
						
						//address.setAddressLine(0, mAddress);
						//address.setAddressLine(1, mCity);
						
						try {
							address = new Geocoder(getApplicationContext()).getFromLocationName(mAddress + ' ' + mCity, 1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(address.size() == 0){
							text = "Please Enter a Valid Address";

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
						else if(address.get(0).hasLatitude() && address.get(0).hasLongitude()){
						    selectedLat = address.get(0).getLatitude();
						    selectedLng = address.get(0).getLongitude();
						    
						    String lat = Double.toString(selectedLat);
						    String lng = Double.toString(selectedLng);
						    
						    text = "IT WORKED " + lat + " " + lng;
						    
						    intent.putExtra("Lat", selectedLat);
							intent.putExtra("Lng", selectedLng);

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
							
							startActivity(intent);
						}
						else{
							// TODO Better err message
							text = "Incorrect Address";

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
					}
				}
				/*
			else{
				// TODO better error message
				text = "You came from an illegal page, try again!";

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
			*/
		}


		});
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
	        "HUBBA_HUBBA_IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "HUBBA_HUBBA_VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
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
		            takePhotoButton.setClickable(false);
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
						Toast.makeText(this, "GOT EM" +
			                     data.getData(), Toast.LENGTH_LONG).show();
						//uploadPhotoButton.setBackgroundResource(R.color.abs__background_holo_light);
					} catch (FileNotFoundException e) {
						Toast.makeText(this, "FILE NOT FOUND FUCKER", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
		            uploadPhotoButton.setClickable(false);
		            
		        } else if (resultCode == RESULT_CANCELED) {
		        	Toast.makeText(this, "Image cancelled\n you suck", Toast.LENGTH_LONG).show();
		        } else {
		        	Toast.makeText(this, "Image FAILED\n you REALLY suck", Toast.LENGTH_LONG).show();
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
	        JPEG_FILE_SUFFIX//, 
	        //getAlbumDir()
	    );
	    mCurrentPhotoPath = image.getAbsolutePath();
	    return image;
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.activity_sign_up, menu);
	// return true;
	// }
}
