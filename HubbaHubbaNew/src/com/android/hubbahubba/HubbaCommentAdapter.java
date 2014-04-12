package com.android.hubbahubba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hubbahubba.ListViewComments.ListViewCommentsItem;

public class HubbaCommentAdapter extends BaseAdapter
{  
	
	LayoutInflater inflater;
	List<ListViewCommentsItem> items;
	ArrayList<HashMap<String, String>> comments;
	Context context;
	/*
    public HubbaCommentAdapter(Activity context, List<ListViewCommentsItem> items) {  
        super();
		
        this.items = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    */
    public HubbaCommentAdapter(Context context,
			ArrayList<HashMap<String, String>> comments, int activityListView) {
		// TODO Auto-generated constructor stub
    	this.setContext(context);
		this.comments = comments;
	}
    
    static class ViewHolder{
    	TextView txtUsername;
        TextView txtOverallRating;
        TextView txtPoRating;
        TextView txtDiffRating;
        TextView txtCommentText;
	}
  //public View newView(Context context, Cursor cursor, ViewGroup parent) {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    View v = convertView;
	    ViewHolder vh = null;
	    HashMap<String, String> comment = comments.get(position);
		
	    if (v == null) {
	    	//Toast.makeText(context, "Populating... " + spot.get("name"), Toast.LENGTH_LONG).show();
	    	
			LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.comment_item_row, null);

            vh = new ViewHolder();
            
            v.setTag(vh);
        } else {
            vh = (ViewHolder) v.getTag();
        }
        
	    vh.txtUsername = (TextView) v.findViewById(R.id.txtUsername);
        vh.txtOverallRating = (TextView) v.findViewById(R.id.txtOverallRating);
        vh.txtPoRating = (TextView) v.findViewById(R.id.txtPoRating);
        vh.txtDiffRating = (TextView) v.findViewById(R.id.txtDiffRating);
        vh.txtCommentText = (TextView) v.findViewById(R.id.txtCommentText);
	    
	    //vh.imgThumbnail.
        vh.txtUsername.setText(comment.get("uname"));
        vh.txtOverallRating.setText(comment.get("overall"));
        vh.txtPoRating.setText(comment.get("bust"));
        vh.txtDiffRating.setText(comment.get("difficulty"));
        vh.txtCommentText.setText(comment.get("text"));
        
        return v;
	}
    
    /*
    @Override  
    public View getView(final int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  

    	//ListViewCommentsItem item = items.get(position);
    	HashMap<String, String> comment = comments.get(position);
    	View vi=convertView;
        
        if(convertView==null)
            vi = inflater.inflate(R.layout.comment_item_row, null);
            
        TextView txtUsername = (TextView) vi.findViewById(R.id.txtUsername);
        TextView txtOverallRating = (TextView) vi.findViewById(R.id.txtOverallRating);
        TextView txtPoRating = (TextView) vi.findViewById(R.id.txtPoRating);
        TextView txtDiffRating = (TextView) vi.findViewById(R.id.txtDiffRating);
        TextView txtCommentText = (TextView) vi.findViewById(R.id.txtCommentText);
        
        //txtUsername.setText(comment.get("u_name"));
        txtUsername.setText("robsmall");
        txtOverallRating.setText(comment.get("overall"));
        txtPoRating.setText(comment.get("bust"));
        txtDiffRating.setText(comment.get("difficulty"));
        txtCommentText.setText(comment.get("text"));
        
        return vi;
    }
	*/

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	/**
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        return comments.size();
    }

    /**
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int position) {
        return comments.get(position);
    }

    /**
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }
}