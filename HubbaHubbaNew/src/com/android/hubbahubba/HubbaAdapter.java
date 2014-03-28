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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class HubbaAdapter extends BaseAdapter /*implements Filterable*/ {
	
	public HubbaAdapter(Context context, List<HashMap<String, String>> spots, int resource) {
		
		//super(context, spots, resource, from, to);
		this.setContext(context);
		this.spots = spots;
		// TODO Auto-generated constructor stub
	}

	private Context context; 
	private List<HashMap<String, String>> spots;
    
    static class ViewHolder{
		ImageView imgThumbnail;
        TextView txtTitle;
        TextView txtOverallRating;
        TextView txtPoRating;
        TextView txtDiffRating;
        TextView txtDistance;
	}

	//public View newView(Context context, Cursor cursor, ViewGroup parent) {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    View v = convertView;
	    ViewHolder vh = null;
	    HashMap<String, String> spot = spots.get(position);
		
	    if (v == null) {
	    	//Toast.makeText(context, "Populating... " + spot.get("name"), Toast.LENGTH_LONG).show();
	    	
			LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_row, null);

            vh = new ViewHolder();
            
            vh.imgThumbnail = (ImageView) v.findViewById(R.id.imgThumbnail);
            vh.txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            vh.txtOverallRating = (TextView) v.findViewById(R.id.txtOverallRating);
            vh.txtPoRating = (TextView) v.findViewById(R.id.txtPoRating);
            vh.txtDiffRating = (TextView) v.findViewById(R.id.txtDiffRating);
            vh.txtDistance = (TextView) v.findViewById(R.id.txtDistance);
            
            // Convert the dp value for xml to pixels (casted to int from float)
    	    int size = Image.convertDpToPixel(80, context);
    	    
    	    // Use picasso to load the image into view
    	    // XXX - THIS MUST STAY CONSISTANT WITH THE SIZE ON SPOT PAGE
    	    Picasso.with(context)
    	    	   .load(R.drawable.gettinthere)
    	    	   .centerCrop()
    	    	   .resize(size, size)
    	    	   .placeholder(R.drawable.ic_empty)
    	    	   .into(vh.imgThumbnail);
    	    
    	    //vh.imgThumbnail.
            vh.txtTitle.setText(spot.get("name"));
            vh.txtOverallRating.setText(spot.get("overall"));
            vh.txtPoRating.setText(spot.get("bust"));
            vh.txtDiffRating.setText(spot.get("diff"));
            vh.txtDistance.setText(spot.get("distance"));
            
            v.setTag(vh);
        } else {
            vh = (ViewHolder) v.getTag();
        }
        
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
        return spots.size();
    }

    /**
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int position) {
        return spots.get(position);
    }

    /**
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }
}