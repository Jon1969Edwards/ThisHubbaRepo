package com.android.hubbahubba;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddImage extends Activity {
    Button cancelButton, uploadButton;
    EditText riderName;
    String mCurrentPhotoPath;
    ImageButton takePhotoButton, uploadPhotoButton;
    Uri mSelectedImage = null;
    Bitmap spotImage;
    private static final int SELECT_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
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
                if (f != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } else {
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
                if (mSelectedImage != null /*&& riderName.getText().toString().trim().length() > 0*/ /*&& userName.getText().toString().trim().length() > 0*/) {
                    // get spot id
                    Bundle showData = getIntent().getExtras();
                    String spot_id = showData.getString("spot_id");

                    // If Rider Name is left blank, leave blank in db
                    if(riderName.getText().toString().trim().length() <= 0){
                        riderName.setText("");
                    }

                    //String imageURI = mSelectedImage.getPath();
                    String imageURI = getImagePath(mSelectedImage);
                    //Toast.makeText(context, "spot_id = " + spot_id + "\nimage = " + imageURI, duration).show();

                    // Add image to DB
                    Spot.addImage(getApplicationContext(), spot_id, riderName.getText().toString(), imageURI);

                    // Success, set result to OK
                    Intent data = new Intent();
                    if (getParent() == null) {
                        setResult(Activity.RESULT_OK, data);
                    } else {
                        getParent().setResult(Activity.RESULT_OK, data);
                    }

                    // Return to parent page
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please fill out all fields to continue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    mSelectedImage = data.getData();

                    //Toast.makeText(this, "IMG From:\n" +
                    //         data.getData(), Toast.LENGTH_LONG).show();

                    // Set select photo button to be image selected
                    try {
                        spotImage = decodeUri(mSelectedImage);
                        uploadPhotoButton.setImageBitmap(spotImage);

                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    // Clear other buttons image
                    photo_uploaded = true;
                    if (photo_taken) {
                        takePhotoButton.setImageBitmap(null);
                    }
                }
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                    //Toast.makeText(this, "Image saved to:\n" +
                    //         data.getData(), Toast.LENGTH_LONG).show();
                    mSelectedImage = data.getData();

                    // Set button to display the photo taken
                    try {
                        spotImage = decodeUri(mSelectedImage);
                        takePhotoButton.setImageBitmap(spotImage);
                        //Toast.makeText(this, "GOT EM " +
                        //         data.getData(), Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    // Clear other button's image
                    photo_taken = true;
                    if (photo_uploaded) {
                        uploadPhotoButton.setImageDrawable(null);
                    }

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Image cancelled\n", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Image failed\n", Toast.LENGTH_LONG).show();
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