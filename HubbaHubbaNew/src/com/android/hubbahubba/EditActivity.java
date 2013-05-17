package com.android.hubbahubba;

import java.io.FileNotFoundException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
 
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class EditActivity extends Activity {

	HubbaDBAdapter dbHelper;
    int rowId;
    Cursor c;
    EditText mName, mType, mRating, mDifficulty, mLevel, mComments;
    Button editSubmit, btnDelete, browseButton;
    ImageView mImage;
    byte[] blobImage;
    Bitmap mBitmap, spotImage;
    String mImagePath;
    Uri selectedImage, imageViewUri;
    
	private static final int SELECT_PHOTO = 1;
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        mName = (EditText) findViewById(R.id.edit_name);
        mType = (EditText) findViewById(R.id.edit_type);
        mRating = (EditText) findViewById(R.id.edit_rating);
        mDifficulty = (EditText) findViewById(R.id.edit_difficulty);
        mLevel = (EditText) findViewById(R.id.edit_level);
        mComments = (EditText) findViewById(R.id.edit_comments);
        editSubmit = (Button) findViewById(R.id.btn_update);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        mImage = (ImageView) findViewById(R.id.spotImage);
        browseButton= (Button) findViewById(R.id.browseButton);
        
        dbHelper = new HubbaDBAdapter(this);
		dbHelper.open();
		 
 
        Bundle showData = getIntent().getExtras();
        rowId = showData.getInt("keyid");
        // Toast.makeText(getApplicationContext(), Integer.toString(rowId),
        // 500).show();
        
        //Toast.makeText(getApplicationContext(),
				  // rowId + " is the ID", Toast.LENGTH_SHORT).show();
 
        //System.out.println(rowId + " is the current rowId");
        
        c = dbHelper.queryAll(rowId);
        
        //System.out.println(c.getPosition() + " is the current position");
        
        if (c.moveToFirst()) {
            do {
                mName.setText(c.getString(1));
                mType.setText(c.getString(2));
                mRating.setText(c.getString(5));
                mDifficulty.setText(c.getString(6));
                mLevel.setText(c.getString(7));
                mComments.setText(c.getString(8));
                
                //now extract the image path
                mImagePath = c.getString(9);
                if(mImagePath != null ) {
                	//imageViewUri = Uri.parse("MediaStore.Images.Media.EXTERNAL_CONTENT_URI" + mImagePath);
                	imageViewUri = Uri.parse(mImagePath);
                	mImage.setImageURI(imageViewUri);
                }
                
                
                //just for testing
                /*Context context = getApplicationContext();
                CharSequence text = imageViewUri.getPath();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/
                
                /*//now extract the image as well
                ByteArrayInputStream inputStream = new ByteArrayInputStream(c.getBlob(9));
                mBitmap = BitmapFactory.decodeStream(inputStream);
                mImage.setImageBitmap(mBitmap);*/
                
 
            } while (c.moveToNext());
        }
 
        editSubmit.setOnClickListener(new OnClickListener() {
 
            //@Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
            	
            	/*//retrieve bitmap image and convert it to byte[] to store in db
				Bitmap bitmap = ((BitmapDrawable)mImage.getDrawable()).getBitmap();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
				byte[] bArray = bos.toByteArray();*/
            	
            	if(selectedImage != null){
            		mImagePath = getRealPathFromUri(selectedImage);
            	}
            	
            	
                dbHelper.updateSpot(
                		rowId, 
                		mName.getText().toString(),
                		mType.getText().toString(), 
                		mRating.getText().toString(), 
                		mDifficulty.getText().toString(), 
                		mLevel.getText().toString(),
                		mComments.getText().toString(),
                		mImagePath);
                finish();
            }
        });
        btnDelete.setOnClickListener(new OnClickListener() {
 
            //@Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dbHelper.deleteSpot(rowId);
                finish();
            }
        });
        
        
        browseButton.setOnTouchListener(new OnTouchListener() {

			// @Override
			public boolean onTouch(View v, MotionEvent event) {

				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO); 
				
				return true;
			}
			
		});
        
    }
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            selectedImage = imageReturnedIntent.getData();
	            //InputStream imageStream = getContentResolver().openInputStream(selectedImage);
	            //Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	            try {
					spotImage = decodeUri(selectedImage);
					mImage.setImageBitmap(spotImage);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
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
	
	
	private String getRealPathFromUri(Uri contentUri) {
		Context context = getApplicationContext();
	    String[] proj = { MediaStore.Images.Media.DATA };
	    CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
	    Cursor cursor = loader.loadInBackground();
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	}
	
	
	@Override
	protected void onDestroy() {
		dbHelper.close();
		super.onDestroy();
	}
    
}