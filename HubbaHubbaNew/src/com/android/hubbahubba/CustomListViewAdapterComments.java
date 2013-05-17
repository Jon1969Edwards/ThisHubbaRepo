package com.android.hubbahubba;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hubbahubba.ListViewComments.ListViewCommentsItem;

public class CustomListViewAdapterComments extends BaseAdapter
{  
	
	LayoutInflater inflater;
	List<ListViewCommentsItem> items;
	
    public CustomListViewAdapterComments(Activity context, List<ListViewCommentsItem> items) {  
        super();
		
        this.items = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return items.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        // TODO Auto-generated method stub  
    	
    	ListViewCommentsItem item = items.get(position);
        return item;  
    }  
  
    @Override  
    public long getItemId(int position) {  
        // TODO Auto-generated method stub  
        return 0;  
    }
      
    @Override  
    public View getView(final int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  

    	ListViewCommentsItem item = items.get(position);
    	
    	View vi=convertView;
        
        if(convertView==null)
            vi = inflater.inflate(R.layout.comment_item_row, null);
            
        TextView txtUsername = (TextView) vi.findViewById(R.id.txtUsername);
        TextView txtOverallRating = (TextView) vi.findViewById(R.id.txtOverallRating);
        TextView txtPoRating = (TextView) vi.findViewById(R.id.txtPoRating);
        TextView txtDiffRating = (TextView) vi.findViewById(R.id.txtDiffRating);
        TextView txtCommentText = (TextView) vi.findViewById(R.id.txtCommentText);
        
        txtUsername.setText(item.Username);
        txtOverallRating.setText(String.valueOf(item.OverallRating));
        txtPoRating.setText(String.valueOf(item.PoRating));
        txtDiffRating.setText(String.valueOf(item.DiffRating));
        txtCommentText.setText(item.CommentText);
        
        return vi;
    }
}