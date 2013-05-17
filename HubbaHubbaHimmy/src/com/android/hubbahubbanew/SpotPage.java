package com.android.hubbahubbanew;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hubbahubbanew.ListViewHubba.ListViewItem;

public class SpotPage extends Activity {
	
	//private GridView mGridView;

	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_spot_page);
	    
        
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
        
        //create back button
        Button backButton = (Button) findViewById(R.id.backButton);
        Button viewMapButton = (Button) findViewById(R.id.viewMapButton);
        Button uploadPhotosButton = (Button) findViewById(R.id.uploadPhotoButton);
        Button commentsButton = (Button) findViewById(R.id.commentsButton);
        Button favoritesButton = (Button) findViewById(R.id.favoritesButton);
        		
        //back button goes back to the main page
        backButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(SpotPage.this, ListViewHubba.class);
                SpotPage.this.startActivity(intent);
        		//Intent intent = new Intent(SpotPage.this, ListViewHubba.class);
        		//startActivity(intent);
        	}

        });
        
        viewMapButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(SpotPage.this, ListViewHubba.class);
                SpotPage.this.startActivity(intent);
        		//Intent intent = new Intent(SpotPage.this, ListViewHubba.class);
        		//startActivity(intent);
        	}

        });
        
        uploadPhotosButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(SpotPage.this, MainActivity.class);
                SpotPage.this.startActivity(intent);
        		//Intent intent = new Intent(SpotPage.this, MainActivity.class);
        		//startActivity(intent);
        	}

        });
        
        favoritesButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(SpotPage.this, ListViewFavorites.class);
                SpotPage.this.startActivity(intent);
        		//Intent intent = new Intent(SpotPage.this, ListViewFavorites.class);
        		//startActivity(intent);
        	}

        });
        
        commentsButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(SpotPage.this, ListViewComments.class);
                SpotPage.this.startActivity(intent);
        		//Intent intent = new Intent(SpotPage.this, ListViewComments.class);
        		//startActivity(intent);
        	}

        });
        
        GridView gridview = (GridView) findViewById(R.id.gridviewPictures);
        gridview.setAdapter(new ImageAdapter(this));


        	gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(SpotPage.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        
    }
	 	 
	/* @Override
	 public void onActivityCreated(Bundle savedInstanceState)
	 {
	     super.onActivityCreated(savedInstanceState); // Don't forget this!
	     mGridView.setAdapter(new ImageAdapter(SpotPage.this));
	 }*/


}