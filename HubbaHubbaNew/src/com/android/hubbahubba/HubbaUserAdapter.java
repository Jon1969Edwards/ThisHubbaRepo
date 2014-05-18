package com.android.hubbahubba;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HubbaUserAdapter extends BaseAdapter /*implements Filterable*/ {
	
	public HubbaUserAdapter(Context context, List<HashMap<String, String>> users, int resource) {
		
		//super(context, users, resource, from, to);
		this.setContext(context);
		this.users = users;
		// TODO Auto-generated constructor stub
	}

	private Context context; 
	private List<HashMap<String, String>> users;
    
    static class ViewHolder{
		ImageView imgThumbnail;
        TextView displayName;
        //TextView user_id;
	}

	//public View newView(Context context, Cursor cursor, ViewGroup parent) {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    View v = convertView;
	    ViewHolder vh = null;
	    HashMap<String, String> user = users.get(position);
		
	    if (v == null) {
	    	//Toast.makeText(context, "Populating... " + user.get("name"), Toast.LENGTH_LONG).show();
	    	
			LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.user_item_row, null);

            vh = new ViewHolder();
            
            v.setTag(vh);
        } else {
            vh = (ViewHolder) v.getTag();
        }
	    
	    vh.imgThumbnail = (ImageView) v.findViewById(R.id.imgThumbnail);
        vh.displayName = (TextView) v.findViewById(R.id.displayName);
        // Convert the dp value for xml to pixels (casted to int from float)
	    int size = Image.convertDpToPixel(80, context);
	    
	    // Use picasso to load the image into view
	    // XXX - THIS MUST STAY CONSISTANT WITH THE SIZE ON user PAGE
	    Picasso.with(context)
	    	   .load(R.drawable.gettinthere)
	    	   .centerCrop()
	    	   .resize(size, size)
	    	   .placeholder(R.drawable.ic_empty)
	    	   .into(vh.imgThumbnail);
	    
        vh.displayName.setText(user.get("display_name"));
        return v;
	}
	

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
        return users.size();
    }

    /**
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int position) {
        return users.get(position);
    }

    /**
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }
}