package com.android.hubbahubba;

import java.io.FileNotFoundException;

import android.annotation.SuppressLint;
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
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

//import android.view.Menu;

//probably delete the @
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class AddSpot extends Activity {

	// WILL BE USED LATER
	int diffClicked = 0;
	int overallClicked = 0;
	int poClicked = 0;
	HubbaDBAdapter dbHelper;
	String mTitle, mType;
	EditText mComment;
	Button browseButton;
	Bitmap spotImage;
	ImageView mImage;
	Uri mSelectedImage = Uri.parse("android.resource://com.segf4ult.test/" + R.drawable.ic_launcher);;
	private static final int SELECT_PHOTO = 1;
	public static final int RESULT_CODE_SPOT_ADDED = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_spot_layout);
		
		//Read in extras passed from AddLocation Activity (i.e. Title and Type )
		mTitle = getIntent().getStringExtra("spotTitle");
		mType = getIntent().getStringExtra("spotType");
		
		//initialize objects in layout
		mComment = (EditText) findViewById(R.id.commentBlock);
		browseButton = (Button) findViewById(R.id.browseButton);
		mImage = (ImageView) findViewById(R.id.showOffImage);
				
		

		/* --------------------------- DIFF BUTTONS ---------------------------- */
		// create buttons
		final Button oneButtonDiff = (Button) findViewById(R.id.oneButtonDiff);
		final Button twoButtonDiff = (Button) findViewById(R.id.twoButtonDiff);
		final Button threeButtonDiff = (Button) findViewById(R.id.threeButtonDiff);
		final Button fourButtonDiff = (Button) findViewById(R.id.fourButtonDiff);
		final Button fiveButtonDiff = (Button) findViewById(R.id.fiveButtonDiff);
		final Button sixButtonDiff = (Button) findViewById(R.id.sixButtonDiff);
		final Button sevenButtonDiff = (Button) findViewById(R.id.sevenButtonDiff);
		final Button eightButtonDiff = (Button) findViewById(R.id.eightButtonDiff);
		final Button nineButtonDiff = (Button) findViewById(R.id.nineButtonDiff);
		final Button tenButtonDiff = (Button) findViewById(R.id.tenButtonDiff);

		final Button oneButtonDanger = (Button) findViewById(R.id.oneButtonDanger);
		final Button twoButtonDanger = (Button) findViewById(R.id.twoButtonDanger);
		final Button threeButtonDanger = (Button) findViewById(R.id.threeButtonDanger);
		final Button fourButtonDanger = (Button) findViewById(R.id.fourButtonDanger);
		final Button fiveButtonDanger = (Button) findViewById(R.id.fiveButtonDanger);
		final Button sixButtonDanger = (Button) findViewById(R.id.sixButtonDanger);
		final Button sevenButtonDanger = (Button) findViewById(R.id.sevenButtonDanger);
		final Button eightButtonDanger = (Button) findViewById(R.id.eightButtonDanger);
		final Button nineButtonDanger = (Button) findViewById(R.id.nineButtonDanger);
		final Button tenButtonDanger = (Button) findViewById(R.id.tenButtonDanger);

		final Button oneButtonOverall = (Button) findViewById(R.id.oneButtonOverall);
		final Button twoButtonOverall = (Button) findViewById(R.id.twoButtonOverall);
		final Button threeButtonOverall = (Button) findViewById(R.id.threeButtonOverall);
		final Button fourButtonOverall = (Button) findViewById(R.id.fourButtonOverall);
		final Button fiveButtonOverall = (Button) findViewById(R.id.fiveButtonOverall);
		final Button sixButtonOverall = (Button) findViewById(R.id.sixButtonOverall);
		final Button sevenButtonOverall = (Button) findViewById(R.id.sevenButtonOverall);
		final Button eightButtonOverall = (Button) findViewById(R.id.eightButtonOverall);
		final Button nineButtonOverall = (Button) findViewById(R.id.nineButtonOverall);
		final Button tenButtonOverall = (Button) findViewById(R.id.tenButtonOverall);

		oneButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 1) {
					oneButtonDiff.setPressed(true);
					twoButtonDiff.setPressed(false);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(false);

					diffClicked = 1;
				}
				return true;
			}
		});

		twoButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 2) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(true);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(false);
					diffClicked = 2;
				}
				return true;
			}
		});

		threeButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 3) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(false);
					threeButtonDiff.setPressed(true);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(false);
					diffClicked = 3;
				}
				return true;
			}
		});

		fourButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 4) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(false);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(true);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(false);
					diffClicked = 4;
				}
				return true;
			}
		});

		fiveButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 5) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(false);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(true);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(false);
					diffClicked = 5;
				}
				return true;
			}
		});

		sixButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 6) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(false);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(true);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(false);
					diffClicked = 6;
				}
				return true;
			}
		});

		sevenButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 7) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(false);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(true);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(false);
					diffClicked = 7;
				}
				return true;
			}
		});

		eightButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 8) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(false);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(true);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(false);
					diffClicked = 8;
				}
				return true;
			}
		});

		nineButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 9) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(false);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(true);
					tenButtonDiff.setPressed(false);
					diffClicked = 9;
				}
				return true;
			}
		});

		tenButtonDiff.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (diffClicked != 10) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(false);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(true);
					diffClicked = 10;
				}
				return true;
			}
		});

		/* ------------------------------- DANGER BUTTONS ----------------------------- */
		oneButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 1) {
					oneButtonDanger.setPressed(true);
					twoButtonDanger.setPressed(false);
					threeButtonDanger.setPressed(false);
					fourButtonDanger.setPressed(false);
					fiveButtonDanger.setPressed(false);
					sixButtonDanger.setPressed(false);
					sevenButtonDanger.setPressed(false);
					eightButtonDanger.setPressed(false);
					nineButtonDanger.setPressed(false);
					tenButtonDanger.setPressed(false);

					poClicked = 1;
				}
				return true;
			}
		});

		twoButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 2) {
					oneButtonDiff.setPressed(false);
					twoButtonDiff.setPressed(true);
					threeButtonDiff.setPressed(false);
					fourButtonDiff.setPressed(false);
					fiveButtonDiff.setPressed(false);
					sixButtonDiff.setPressed(false);
					sevenButtonDiff.setPressed(false);
					eightButtonDiff.setPressed(false);
					nineButtonDiff.setPressed(false);
					tenButtonDiff.setPressed(false);
					poClicked = 2;
				}
				return true;
			}
		});

		threeButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 3) {
					oneButtonDanger.setPressed(false);
					twoButtonDanger.setPressed(false);
					threeButtonDanger.setPressed(true);
					fourButtonDanger.setPressed(false);
					fiveButtonDanger.setPressed(false);
					sixButtonDanger.setPressed(false);
					sevenButtonDanger.setPressed(false);
					eightButtonDanger.setPressed(false);
					nineButtonDanger.setPressed(false);
					tenButtonDanger.setPressed(false);
					poClicked = 3;
				}
				return true;
			}
		});

		fourButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 4) {
					oneButtonDanger.setPressed(false);
					twoButtonDanger.setPressed(false);
					threeButtonDanger.setPressed(false);
					fourButtonDanger.setPressed(true);
					fiveButtonDanger.setPressed(false);
					sixButtonDanger.setPressed(false);
					sevenButtonDanger.setPressed(false);
					eightButtonDanger.setPressed(false);
					nineButtonDanger.setPressed(false);
					tenButtonDanger.setPressed(false);
					poClicked = 4;
				}
				return true;
			}
		});

		fiveButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 5) {
					oneButtonDanger.setPressed(false);
					twoButtonDanger.setPressed(false);
					threeButtonDanger.setPressed(false);
					fourButtonDanger.setPressed(false);
					fiveButtonDanger.setPressed(true);
					sixButtonDanger.setPressed(false);
					sevenButtonDanger.setPressed(false);
					eightButtonDanger.setPressed(false);
					nineButtonDanger.setPressed(false);
					tenButtonDanger.setPressed(false);
					poClicked = 5;
				}
				return true;
			}
		});

		sixButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 6) {
					oneButtonDanger.setPressed(false);
					twoButtonDanger.setPressed(false);
					threeButtonDanger.setPressed(false);
					fourButtonDanger.setPressed(false);
					fiveButtonDanger.setPressed(false);
					sixButtonDanger.setPressed(true);
					sevenButtonDanger.setPressed(false);
					eightButtonDanger.setPressed(false);
					nineButtonDanger.setPressed(false);
					tenButtonDanger.setPressed(false);
					poClicked = 6;
				}
				return true;
			}
		});

		sevenButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 7) {
					oneButtonDanger.setPressed(false);
					twoButtonDanger.setPressed(false);
					threeButtonDanger.setPressed(false);
					fourButtonDanger.setPressed(false);
					fiveButtonDanger.setPressed(false);
					sixButtonDanger.setPressed(false);
					sevenButtonDanger.setPressed(true);
					eightButtonDanger.setPressed(false);
					nineButtonDanger.setPressed(false);
					tenButtonDanger.setPressed(false);
					poClicked = 7;
				}
				return true;
			}
		});

		eightButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 8) {
					oneButtonDanger.setPressed(false);
					twoButtonDanger.setPressed(false);
					threeButtonDanger.setPressed(false);
					fourButtonDanger.setPressed(false);
					fiveButtonDanger.setPressed(false);
					sixButtonDanger.setPressed(false);
					sevenButtonDanger.setPressed(false);
					eightButtonDanger.setPressed(true);
					nineButtonDanger.setPressed(false);
					tenButtonDanger.setPressed(false);
					poClicked = 8;
				}
				return true;
			}
		});

		nineButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 9) {
					oneButtonDanger.setPressed(false);
					twoButtonDanger.setPressed(false);
					threeButtonDanger.setPressed(false);
					fourButtonDanger.setPressed(false);
					fiveButtonDanger.setPressed(false);
					sixButtonDanger.setPressed(false);
					sevenButtonDanger.setPressed(false);
					eightButtonDanger.setPressed(false);
					nineButtonDanger.setPressed(true);
					tenButtonDanger.setPressed(false);
					poClicked = 9;
				}
				return true;
			}
		});

		tenButtonDanger.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (poClicked != 10) {
					oneButtonDanger.setPressed(false);
					twoButtonDanger.setPressed(false);
					threeButtonDanger.setPressed(false);
					fourButtonDanger.setPressed(false);
					fiveButtonDanger.setPressed(false);
					sixButtonDanger.setPressed(false);
					sevenButtonDanger.setPressed(false);
					eightButtonDanger.setPressed(false);
					nineButtonDanger.setPressed(false);
					tenButtonDanger.setPressed(true);
					poClicked = 10;
				}
				return true;
			}
		});

		/*------------------------------ OVERALL BUTTONS ------------------------------ */
		oneButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 1) {
					oneButtonOverall.setPressed(true);
					twoButtonOverall.setPressed(false);
					threeButtonOverall.setPressed(false);
					fourButtonOverall.setPressed(false);
					fiveButtonOverall.setPressed(false);
					sixButtonOverall.setPressed(false);
					sevenButtonOverall.setPressed(false);
					eightButtonOverall.setPressed(false);
					nineButtonOverall.setPressed(false);
					tenButtonOverall.setPressed(false);

					overallClicked = 1;
				}
				return true;
			}
		});

		twoButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 2) {
					oneButtonOverall.setPressed(false);
					twoButtonOverall.setPressed(true);
					threeButtonOverall.setPressed(false);
					fourButtonOverall.setPressed(false);
					fiveButtonOverall.setPressed(false);
					sixButtonOverall.setPressed(false);
					sevenButtonOverall.setPressed(false);
					eightButtonOverall.setPressed(false);
					nineButtonOverall.setPressed(false);
					tenButtonOverall.setPressed(false);
					overallClicked = 2;
				}
				return true;
			}
		});

		threeButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 3) {
					oneButtonOverall.setPressed(false);
					twoButtonOverall.setPressed(false);
					threeButtonOverall.setPressed(true);
					fourButtonOverall.setPressed(false);
					fiveButtonOverall.setPressed(false);
					sixButtonOverall.setPressed(false);
					sevenButtonOverall.setPressed(false);
					eightButtonOverall.setPressed(false);
					nineButtonOverall.setPressed(false);
					tenButtonOverall.setPressed(false);
					overallClicked = 3;
				}
				return true;
			}
		});

		fourButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 4) {
					oneButtonOverall.setPressed(false);
					twoButtonOverall.setPressed(false);
					threeButtonOverall.setPressed(false);
					fourButtonOverall.setPressed(true);
					fiveButtonOverall.setPressed(false);
					sixButtonOverall.setPressed(false);
					sevenButtonOverall.setPressed(false);
					eightButtonOverall.setPressed(false);
					nineButtonOverall.setPressed(false);
					tenButtonOverall.setPressed(false);
					overallClicked = 4;
				}
				return true;
			}
		});

		fiveButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 5) {
					oneButtonOverall.setPressed(false);
					twoButtonOverall.setPressed(false);
					threeButtonOverall.setPressed(false);
					fourButtonOverall.setPressed(false);
					fiveButtonOverall.setPressed(true);
					sixButtonOverall.setPressed(false);
					sevenButtonOverall.setPressed(false);
					eightButtonOverall.setPressed(false);
					nineButtonOverall.setPressed(false);
					tenButtonOverall.setPressed(false);
					overallClicked = 5;
				}
				return true;
			}
		});

		sixButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 6) {
					oneButtonOverall.setPressed(false);
					twoButtonOverall.setPressed(false);
					threeButtonOverall.setPressed(false);
					fourButtonOverall.setPressed(false);
					fiveButtonOverall.setPressed(false);
					sixButtonOverall.setPressed(true);
					sevenButtonOverall.setPressed(false);
					eightButtonOverall.setPressed(false);
					nineButtonOverall.setPressed(false);
					tenButtonOverall.setPressed(false);
					overallClicked = 6;
				}
				return true;
			}
		});

		sevenButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 7) {
					oneButtonOverall.setPressed(false);
					twoButtonOverall.setPressed(false);
					threeButtonOverall.setPressed(false);
					fourButtonOverall.setPressed(false);
					fiveButtonOverall.setPressed(false);
					sixButtonOverall.setPressed(false);
					sevenButtonOverall.setPressed(true);
					eightButtonOverall.setPressed(false);
					nineButtonOverall.setPressed(false);
					tenButtonOverall.setPressed(false);
					overallClicked = 7;
				}
				return true;
			}
		});

		eightButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 8) {
					oneButtonOverall.setPressed(false);
					twoButtonOverall.setPressed(false);
					threeButtonOverall.setPressed(false);
					fourButtonOverall.setPressed(false);
					fiveButtonOverall.setPressed(false);
					sixButtonOverall.setPressed(false);
					sevenButtonOverall.setPressed(false);
					eightButtonOverall.setPressed(true);
					nineButtonOverall.setPressed(false);
					tenButtonOverall.setPressed(false);
					overallClicked = 8;
				}
				return true;
			}
		});

		nineButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 9) {
					oneButtonOverall.setPressed(false);
					twoButtonOverall.setPressed(false);
					threeButtonOverall.setPressed(false);
					fourButtonOverall.setPressed(false);
					fiveButtonOverall.setPressed(false);
					sixButtonOverall.setPressed(false);
					sevenButtonOverall.setPressed(false);
					eightButtonOverall.setPressed(false);
					nineButtonOverall.setPressed(true);
					tenButtonOverall.setPressed(false);
					overallClicked = 9;
				}
				return true;
			}
		});

		tenButtonOverall.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				if (overallClicked != 10) {
					oneButtonOverall.setPressed(false);
					twoButtonOverall.setPressed(false);
					threeButtonOverall.setPressed(false);
					fourButtonOverall.setPressed(false);
					fiveButtonOverall.setPressed(false);
					sixButtonOverall.setPressed(false);
					sevenButtonOverall.setPressed(false);
					eightButtonOverall.setPressed(false);
					nineButtonOverall.setPressed(false);
					tenButtonOverall.setPressed(true);
					overallClicked = 10;
				}
				return true;
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
		
	
				

		dbHelper = new HubbaDBAdapter(this);
		dbHelper.open();

		// by pressing submit, you insert comment into DB
		Button addSpotButton = (Button) findViewById(R.id.addSpotButton);
		addSpotButton.setOnClickListener(new View.OnClickListener() {

			// @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String stringName, stringType, stringComments, selectedImagePath;
				double doubleLat, doubleLong;
				int intRate, intDiff, intLevel;

				//retrieve bitmap image and convert it to byte[] to store in db
				/*
				Bitmap bitmap = ((BitmapDrawable)mImage.getDrawable()).getBitmap();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
				byte[] bArray = bos.toByteArray();
				*/

				//retrieve everything else to store in db
				stringName = mTitle;
				stringType = mType;
				doubleLat = 100.001;
				doubleLong = 100.001;
				intRate = overallClicked;
				intDiff = diffClicked;
				intLevel = poClicked;
				stringComments = mComment.getText().toString();
				selectedImagePath = getRealPathFromUri(mSelectedImage);

				dbHelper.createSpot(stringName, stringType, doubleLat,
						doubleLong, intRate, intDiff, intLevel, stringComments, selectedImagePath);
				// dbHelper.close();
				//finish();
				
				
				//now return to listview
				Intent intent = new Intent(AddSpot.this, ActionBarActivity.class);
				startActivity(intent);
				
				//return true;
			}

		});

	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            mSelectedImage = imageReturnedIntent.getData();
	            
	            
	            // THIS WAS TO SHOW THE IMAGE PATH
	            //Context context = getApplicationContext();
	            //CharSequence text = mSelectedImage.getPath();
	            //int duration = Toast.LENGTH_SHORT;

	            //Toast toast = Toast.makeText(context, text, duration);
	            //toast.show();
	            
	            
	            //InputStream imageStream = getContentResolver().openInputStream(selectedImage);
	            //Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	            try {
					spotImage = decodeUri(mSelectedImage);
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
