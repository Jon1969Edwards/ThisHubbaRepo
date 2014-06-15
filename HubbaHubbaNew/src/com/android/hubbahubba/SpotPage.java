package com.android.hubbahubba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SpotPage extends Activity {
    private ArrayList<HashMap<String, String>> imagesArray;

    TextView mRating, mLevel, mDifficulty, mTitle, mDist;
    ImageView mImage;
    Context context = this;
    String spot_id;
    private HubbaGridAdapter dataAdapter;
    private GridView gridview;

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
        gridview = (GridView) findViewById(R.id.gridviewPictures);

        Bundle showData = getIntent().getExtras();
        spot_id = showData.getString("spot_id");

        // for now just sets the title
        Spot.getSpotInfoByID(this, spot_id, context);

        // Get and populate header photo for the spot
        Spot.getTopPhotoBySpotID(context, spot_id, this);

        // create buttons
        Button viewMapButton = (Button) findViewById(R.id.viewMapButton);
        Button uploadPhotosButton = (Button) findViewById(R.id.uploadPhotoButton);
        Button commentsButton = (Button) findViewById(R.id.commentsButton);
        Button uploadCommentButton = (Button) findViewById(R.id.uploadCommentButton);
        //Button favoritesButton = (Button) findViewById(R.id.favoritesButton);
        Button shareButton = (Button) findViewById(R.id.shareButton);
        TextView isSecret = (TextView) findViewById(R.id.isSecret);


        // If the spot isn't secret then no need to have a share button
        if (isSecret.getText().equals("False") || isSecret.getText().equals("false")) {
            ((ViewManager) shareButton.getParent()).removeView(shareButton);
        } else {
            shareButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Bundle bundleData = new Bundle();
                    bundleData.putString("spot_id", spot_id);
                    Intent intent = new Intent(SpotPage.this, ShareUserList.class);
                    intent.putExtras(bundleData);
                    startActivity(intent);
                }
            });
        }

        viewMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle bundleData = new Bundle();

                TextView Lat = (TextView) findViewById(R.id.lat);
                TextView Lon = (TextView) findViewById(R.id.lon);

                String lon = (String) Lon.getText();
                String lat = (String) Lat.getText();

                //Toast.makeText(context, "lat = " + lat + " and lon = " + lon, Toast.LENGTH_LONG).show();

                bundleData.putString("lat", lat);
                bundleData.putString("lon", lon);

                Intent intent = new Intent(SpotPage.this, ActionBarActivity.class);

                intent.putExtras(bundleData);
                startActivity(intent);
            }

        });

        uploadPhotosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                User.checkLoggedIn(getApplicationContext(), spot_id, "Image");

//                Bundle bundleData = new Bundle();
//                bundleData.putString("spot_id", spot_id);
//                Intent intent = new Intent(SpotPage.this, AddImage.class);
//                intent.putExtras(bundleData);
//                startActivityForResult(intent, 0);
            }
        });

        uploadCommentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                User.checkLoggedIn(getApplicationContext(), spot_id, "Comment");

//                Bundle bundleData = new Bundle();
//                bundleData.putString("spot_id", spot_id);
//
//                Intent intent = new Intent(SpotPage.this, AddComment.class);
//
//                intent.putExtras(bundleData);
//                startActivity(intent);
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
                // add spot_id to the new intent and start it
                Bundle bundleData = new Bundle();
                bundleData.putString("spot_id", spot_id);

                Intent intent = new Intent(SpotPage.this, ListViewComments.class);
                intent.putExtras(bundleData);

                startActivity(intent);
            }

        });

        // Send task to get images
        //imagesArray = new ArrayList<HashMap<String, String>>();
        //Spot.getPhotosBySpotID(gridview, dataAdapter, imagesArray, context, spot_id);

        // Open full screen image
        // TODO: better full screen view
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(SpotPage.this, ViewImage.class);

                // TODO: maybe use the above line?
                HashMap<String, String> image = imagesArray.get(position);
                String url = image.get("url");
                String display_name = image.get("display_name");
                String rider_name = image.get("rider_name");

                Toast.makeText(context, "Rider" + rider_name, Toast.LENGTH_SHORT).show();

                intent.putExtra("url", url);
                intent.putExtra("rider_name", rider_name);
                intent.putExtra("display_name", display_name);
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

    // TODO: Remove these?
    // WORKS FOR NOW THOUGH =)
    @Override
    protected void onResume() {
        super.onResume();

        // Set spot images
        imagesArray = new ArrayList<HashMap<String, String>>();
        Spot.getPhotosBySpotID(gridview, dataAdapter, imagesArray, context, spot_id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // TODO Possibly delete this
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: uncomment and figure out how to refresh from coming back here
        //if (requestCode == 0) {
        //    if (resultCode == Activity.RESULT_OK) {
                // Refresh list because photo was added
                imagesArray = new ArrayList<HashMap<String, String>>();
                Spot.getPhotosBySpotID(gridview, dataAdapter, imagesArray, context, spot_id);
        //    }
        //}
    }
}