package com.android.hubbahubba;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ListView;

public class ListViewComments extends Activity {
	
	CustomListViewAdapterComments mAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_page_comments);
        
        Button uploadPhotoButton = (Button) findViewById(R.id.uploadPhotoButton);
        
        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(ListViewComments.this, UploadComment.class);
				startActivity(intent);
			}
		});
        
        ListView lv = (ListView) findViewById(R.id.listViewComments);
        List<ListViewCommentsItem> items = new ArrayList<ListViewComments.ListViewCommentsItem>();
        
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
        
        mAdapter = new CustomListViewAdapterComments(this, items);
        lv.setAdapter(mAdapter);
        
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