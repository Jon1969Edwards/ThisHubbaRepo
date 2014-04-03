package com.android.hubbahubba;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewComments extends Activity {
	
	HubbaCommentAdapter mAdapter;
	private HubbaCommentAdapter dataAdapter;
	String spot_id;
	private ListView listView;
	private View rootView;
	private Context context;
	private ArrayList<HashMap<String, String>> commentsArray;
	private Bundle showData;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_page_comments);
        
        // get spot id
        showData = getIntent().getExtras();
		spot_id = showData.getString("spot_id");
        
        Button uploadCommentButton = (Button) findViewById(R.id.uploadCommentButton);
        
        uploadCommentButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(ListViewComments.this, AddComment.class);
				intent.putExtras(showData);
				startActivity(intent);
			}
		});
        // get list view
        listView = (ListView) findViewById(R.id.listViewComments);
        //List<ListViewCommentsItem> items = new ArrayList<ListViewComments.ListViewCommentsItem>();
        commentsArray = new ArrayList<HashMap<String, String>>();
        
        // populate spots array
     	Spot.getCommentsBySpotID(listView, dataAdapter, commentsArray, getApplicationContext(), spot_id);
     	
        /*
        ListViewCommentsItem item1 = new ListViewCommentsItem();
        item1.Username = "robsmall";
        item1.OverallRating = 10;
        item1.PoRating = 3;
        item1.DiffRating = 8;
        item1.CommentText = "This spot is a lot of fun. The ground could be better but the rail is smooth and waxed.";
        items.add(item1);
        
        ListViewCommentsItem item2 = new ListViewCommentsItem();
        item2.Username = "Jadams";
        item2.OverallRating = 3;
        item2.PoRating = 2;
        item2.DiffRating = 3;
        item2.CommentText = "I get kicked out of this spot every time I'm here and all there is is a small ledge for beginners...";
        items.add(item2);
        
        mAdapter = new HubbaCommentAdapter(this, items);
        lv.setAdapter(mAdapter);
        */
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spot_page_comments, menu);
        
        return true;
    }
    
    static class ListViewCommentsItem{
    	public String Username;
    	public int OverallRating;
    	public int PoRating;
    	public int DiffRating;
    	public String CommentText;
    	
    	public ListViewCommentsItem(){
    		
    	}
    	
    	public ListViewCommentsItem(Intent intent){
    		bindTo(intent);
    	}
    	
    	public ListViewCommentsItem(Cursor cursor){
    		bindTo(cursor);
    	}
    	
    	public void bindTo(Intent intent){
    		Username = intent.getStringExtra("Username");
    		OverallRating = intent.getIntExtra("OverallRating", 0);
    		PoRating = intent.getIntExtra("PoRating", 0);
    		DiffRating = intent.getIntExtra("DiffRating", 0);
    		CommentText = intent.getStringExtra("CommentText");
    	}
    	
    	public void bindTo(Cursor cursor){
    		
    	}
    }
}