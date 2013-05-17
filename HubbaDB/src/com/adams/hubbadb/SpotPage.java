package com.adams.hubbadb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
//import com.nostra13.universalimageloader.utils.L;

public class SpotPage extends Activity {

	// ImageLoader imageLoader = ImageLoader.getInstance();
	private static final String TEST_FILE_NAME = "Universal Image Loader @#&=+-_.,!()~'%20.png";

	HubbaDBAdapter dbHelper;
	int keyValue;
	Cursor c;

	TextView mRating, mLevel, mDifficulty, mTitle, mDist, mComments, mType;
	ImageView mImage;
	String mImagePath;
	Uri imageViewUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spot_page_layout);

		// find the intent keyid passed through the intent.
		// this is used in the DB Query
		Bundle showData = getIntent().getExtras();
		keyValue = showData.getInt("keyid");

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

		// NEW
		/*
		 * File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME); if
		 * (!testImageOnSdCard.exists()) {
		 * copyTestImageToSdCard(testImageOnSdCard); }
		 */

		// ListViewItem item = new ListViewItem(getIntent());

		/*
		 * ImageView imgThumbnail = (ImageView) findViewById(R.id.imgThumbnail);
		 * TextView txtTitle = (TextView) findViewById(R.id.txtTitle); TextView
		 * txtOverallRating = (TextView) findViewById(R.id.txtOverallRating);
		 * TextView txtPoRating = (TextView) findViewById(R.id.txtPoRating);
		 * TextView txtDiffRating = (TextView) findViewById(R.id.txtDiffRating);
		 * TextView txtDistance = (TextView) findViewById(R.id.txtDistance);
		 * 
		 * imgThumbnail.setImageResource(item.Thumbnail);
		 * txtTitle.setText(item.Title);
		 * txtOverallRating.setText(String.valueOf(item.OverallRating));
		 * txtPoRating.setText(String.valueOf(item.PoRating));
		 * txtDiffRating.setText(String.valueOf(item.DiffRating));
		 * txtDistance.setText(String.format("%.2f", item.Distance));
		 * 
		 * // create buttons Button viewMapButton = (Button)
		 * findViewById(R.id.viewMapButton); Button uploadPhotosButton =
		 * (Button) findViewById(R.id.uploadPhotoButton); Button commentsButton
		 * = (Button) findViewById(R.id.commentsButton); Button favoritesButton
		 * = (Button) findViewById(R.id.favoritesButton);
		 * 
		 * viewMapButton.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View view) { Intent intent = new Intent(SpotPage.this,
		 * ActionBarActivity.class); startActivity(intent); }
		 * 
		 * });
		 */
		/*
		 * uploadPhotosButton.setOnClickListener(new View.OnClickListener() {
		 * public void onClick(View view) { Intent intent = new
		 * Intent(SpotPage.this, MainActivity.class); startActivity(intent); }
		 * 
		 * });
		 */
		/*
		 * favoritesButton.setOnClickListener(new View.OnClickListener() {
		 * public void onClick(View view) { Intent intent = new
		 * Intent(SpotPage.this, ActionBarActivity.class);
		 * startActivity(intent); }
		 * 
		 * });
		 */

		/*
		 * commentsButton.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View view) { Intent intent = new
		 * Intent(SpotPage.this,ListViewComments.class); startActivity(intent);
		 * }
		 * 
		 * });
		 */

		/*
		 * GridView gridview = (GridView) findViewById(R.id.gridviewPictures);
		 * gridview.setAdapter(new ImageAdapter(this));
		 * 
		 * 
		 * 
		 * gridview.setOnItemClickListener(new OnItemClickListener() { public
		 * void onItemClick(AdapterView<?> parent, View v, int position, long
		 * id) {
		 * 
		 * Intent intent = new Intent(SpotPage.this, ViewImage.class); int
		 * imageID = (Integer)parent.getAdapter().getItem(position);
		 * intent.putExtra("imageName", imageID); startActivity(intent); } });
		 * 
		 * 
		 * //TO AVOID SCROLLING LAGS boolean pauseOnScroll = false; // or true
		 * boolean pauseOnFling = true; // or false PauseOnScrollListener
		 * listener = new PauseOnScrollListener(imageLoader, pauseOnScroll,
		 * pauseOnFling); gridview.setOnScrollListener(listener);
		 * 
		 * 
		 * }
		 * 
		 * @Override public boolean onCreateOptionsMenu(Menu menu) {
		 * getMenuInflater().inflate(R.menu.activity_list_view, menu);
		 * 
		 * return true; }
		 * 
		 * private void copyTestImageToSdCard(final File testImageOnSdCard) {
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { try { InputStream is =
		 * getAssets().open(TEST_FILE_NAME); FileOutputStream fos = new
		 * FileOutputStream(testImageOnSdCard); byte[] buffer = new byte[8192];
		 * int read; try { while ((read = is.read(buffer)) != -1) {
		 * fos.write(buffer, 0, read); } } finally { fos.flush(); fos.close();
		 * is.close(); } } catch (IOException e) {
		 * L.w("Can't copy test image onto SD card"); } } }) .start(); }
		 */
	}
}