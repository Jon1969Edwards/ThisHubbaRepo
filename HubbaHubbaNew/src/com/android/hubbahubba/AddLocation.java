package com.android.hubbahubba;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddLocation extends Activity {

    //declare Strings to pass through to AddSpot activity
    String mTitle, mType, mAddress, mCity;
    Button cancelButton, locateOnMapButton, continueButton;
    ToggleButton sharedButton;
    EditText spotTitle, spotAddress, spotCity;
    Spinner typeSpinner;
    String LatLong;
    RelativeLayout locateOnMapHolder;
    String latitude;
    String longitude;
    String FromPage;
    String addressList[] = new String[10];
    double selectedLat;
    double selectedLng;

    //for take photo
    String mCurrentPhotoPath;
    ImageButton takePhotoButton, uploadPhotoButton;
    boolean isSecret;
    Uri mSelectedImage = null;
    Bitmap spotImage;
    ArrayAdapter<String> adapter;
    private static final int SELECT_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    private static final String JPEG_FILE_SUFFIX = "Hubba_Hubba";
    private static final String JPEG_FILE_PREFIX = "Hubba_Hubba";
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location_layout);

        context = getApplicationContext();

        FromPage = getIntent().getStringExtra("FromPage");

        //initialize all needed objects
        cancelButton = (Button) findViewById(R.id.cancelButton);
        locateOnMapButton = (Button) findViewById(R.id.locateOnMap);
        locateOnMapHolder = (RelativeLayout) findViewById(R.id.locateOnMapHolder);
        continueButton = (Button) findViewById(R.id.continueButton);
        spotTitle = (EditText) findViewById(R.id.spotTitle);
        spotAddress = (EditText) findViewById(R.id.address);
        spotCity = (EditText) findViewById(R.id.cityStateZip);
        takePhotoButton = (ImageButton) findViewById(R.id.takePhotoButton);
        uploadPhotoButton = (ImageButton) findViewById(R.id.uploadPhotoButton);
        sharedButton = (ToggleButton) findViewById(R.id.sharedButton);

        // Set spinner style
        typeSpinner = (Spinner) findViewById(R.id.spotTypeSpinner);
        adapter = new ArrayAdapter<String>(context, R.layout.spinner_row, R.id.text1, getResources().getStringArray(R.array.spotTypes));
        typeSpinner.setAdapter(adapter);

        // why does this not work?
        if (FromPage.equals("ViewMap")) {
            // TODO only add the marker to the map if you press the "AddSpotButton" in addspot
            LatLong = getIntent().getStringExtra("LatLong");
            //LatLong = LatLong.substring(1, LatLong.length() - 1);
            if (LatLong != null) {
                String[] separated = LatLong.split(",");
                latitude = separated[0];
                longitude = separated[1];
                // GET ADDRESS FROM THE LATATUDE AND LONGITUDE
                Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
                try {
                    List<Address> List = geocoder.getFromLocation(Double.parseDouble(latitude)
                            , Double.parseDouble(longitude), 1);

                    Address returnedAddress = List.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder();
                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                        if (i == 0) {
                            spotAddress.setText(returnedAddress.getAddressLine(i));
                        } else if (i == 1) {
                            spotCity.setText(returnedAddress.getAddressLine(i));
                        }
                    }
                    addressList[0] = strReturnedAddress.toString();
                } catch (NumberFormatException e) {
                    /*
                    double D1 = Double.parseDouble(latitude);
                    double D2 = Double.parseDouble(longitude);
                    String lat = Double.toString(D1);
                    String lon = Double.toString(D2);
                    */
                    Toast.makeText(context, "Error parsing doubles", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                } catch (IOException e) {
                    //double D1 = Double.parseDouble(latitude);
                    //double D2 = Double.parseDouble(longitude);
                    //String lat = Double.toString(D1);
                    //String lon = Double.toString(D2);
                    //Toast.makeText(context, lat + " " + lon + " IOE_EXCEPTION", duration).show();
                    Toast.makeText(context, "Error parsing lat/long into address, restarting your device and try again", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
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

        sharedButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (sharedButton.isChecked()) {
                    sharedButton.setTextColor(Color.RED);
                } else {
                    sharedButton.setTextColor(Color.parseColor("#008000"));
                }
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
                if (f != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                }
            }
        });

        // login Button takes you to the map view (ROB: YOU WILL NEED TO CHANGE THIS BACK TO MAPVIEW, not MainActivity)
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AddLocation.this, ActionBarActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        locateOnMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AddLocation.this, ActionBarActivity.class);
                startActivity(intent);
            }

        });

        if (FromPage.equals("ViewMap")) {
            locateOnMapHolder.setVisibility(View.GONE);
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                mTitle = spotTitle.getText().toString();
                mAddress = spotAddress.getText().toString();
                mCity = spotCity.getText().toString();
                mType = typeSpinner.getSelectedItem().toString();

                if (sharedButton.isChecked() == true) {
                    isSecret = true;
                } else {
                    isSecret = false;
                }

                int duration = Toast.LENGTH_LONG;

                if (mTitle == null || mTitle.equals("")) {
                    Toast.makeText(context, "Please insert a title for the spot.", duration).show();
                } else if (mAddress == null || mAddress.equals("")) {
                    Toast.makeText(context, "Please insert an address for the spot.", duration).show();
                } else if (mCity == null || mCity.equals("")) {
                    Toast.makeText(context, "Please insert a city, state, and zip for the spot.", duration).show();
                } else if (mType.equals("Select Features")) {
                    Toast.makeText(context, "Please select a feature to describe the spot.", duration).show();
                } else {
                    Intent intent = new Intent(AddLocation.this, AddSpot.class);
                    intent.putExtra("isSecret", isSecret);
                    intent.putExtra("spotTitle", mTitle);
                    intent.putExtra("spotType", mType);
                    intent.putExtra("spotAddress", mAddress);
                    intent.putExtra("spotCity", mCity);
                    if (mSelectedImage != null) {
                        intent.putExtra("mSelectedImage", mSelectedImage.toString());
                    }

                    FromPage = getIntent().getStringExtra("FromPage");

                    if (FromPage.equals("ListViewHubba") || FromPage.equals("ListViewFavorites")) {

                        // TODO: pretty sure this should be nothing, test this
                        List<Address> address = null; //= new Address(Locale.ENGLISH);
                        //address.setAddressLine(0, mAddress);
                        //address.setAddressLine(1, mCity);
                        try {
                            address = new Geocoder(getApplicationContext()).getFromLocationName(mAddress + ' ' + mCity, 1);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (address == null || address.size() == 0) {
                            Toast.makeText(context, "Please Enter a Valid Address", duration).show();
                        } else if (address.get(0).hasLatitude() && address.get(0).hasLongitude()) {
                            selectedLat = address.get(0).getLatitude();
                            selectedLng = address.get(0).getLongitude();

                            intent.putExtra("Lat", selectedLat);
                            intent.putExtra("Lng", selectedLng);

                            startActivity(intent);
                        } else {
                            // TODO Better err message
                            Toast.makeText(context, "Incorrect Address", duration).show();
                        }
                    } else if (FromPage.equals("ViewMap")) {
                        selectedLat = Double.parseDouble(latitude);
                        selectedLng = Double.parseDouble(longitude);

                        intent.putExtra("Lat", selectedLat);
                        intent.putExtra("Lng", selectedLng);


                        List<Address> address = null; //= new Address(Locale.ENGLISH);
                        //address.setAddressLine(0, mAddress);
                        //address.setAddressLine(1, mCity);
                        try {
                            address = new Geocoder(getApplicationContext()).getFromLocationName(mAddress + ' ' + mCity, 1);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            Toast.makeText(context, "Please Enter a Valid Address", duration).show();
                            e.printStackTrace();
                        }

                        if (address.size() == 0) {
                            Toast.makeText(context, "Please Enter a Valid Address", duration).show();
                        } else if (address.get(0).hasLatitude() && address.get(0).hasLongitude()) {
                            selectedLat = Double.parseDouble(latitude);
                            selectedLng = Double.parseDouble(longitude);

                            intent.putExtra("Lat", selectedLat);
                            intent.putExtra("Lng", selectedLng);
                            startActivity(intent);
                        } else {
                            // TODO Better err message
                            Toast.makeText(context, "Incorrect Address", duration).show();
                        }
                    }
                }
            }
        });
    }

    /**
     * Create a File for saving an image or video
     */
    @SuppressLint("SimpleDateFormat")
    /*
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
	*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    mSelectedImage = data.getData();

                    //Toast.makeText(this, "IMG From:\n" +
                    //         data.getData(), Toast.LENGTH_LONG).show();
                    try {
                        spotImage = decodeUri(mSelectedImage);
                        uploadPhotoButton.setImageBitmap(spotImage);
                        //uploadPhotoButton.setBackgroundResource(R.color.abs__background_holo_light);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //takePhotoButton.setClickable(false);
                    takePhotoButton.setImageBitmap(null);
                }
                break;

            //case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                    //Toast.makeText(this, "Image saved to:\n" +
                    //         data.getData(), Toast.LENGTH_LONG).show();
                    mSelectedImage = data.getData();
                    //TODO THE BUG IS SOMEWHERE IN THE TRY CATCH BLOCK

                    try {
                        spotImage = decodeUri(mSelectedImage);
                        takePhotoButton.setImageBitmap(spotImage);
                        //Toast.makeText(this, "GOT EM" +
                        //         data.getData(), Toast.LENGTH_LONG).show();
                        //uploadPhotoButton.setBackgroundResource(R.color.abs__background_holo_light);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "FILE NOT FOUND FUCKER", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    //uploadPhotoButton.setClickable(false);
                    uploadPhotoButton.setImageBitmap(null);

                } else if (resultCode == RESULT_CANCELED) {
                    // TODO remove this
                    //Toast.makeText(this, "Image cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Image FAILED", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    // TODO: use picasso for this instead
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
}
