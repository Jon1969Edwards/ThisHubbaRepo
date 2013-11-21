package com.android.hubbahubba;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HubbaCursorAdapter extends CursorAdapter /*implements Filterable*/ {
	
	private Context context; 
    //private int layout;
    
    static class ViewHolder{
		ImageView imgThumbnail;
        TextView txtTitle;
        TextView txtOverallRating;
        TextView txtPoRating;
        TextView txtDiffRating;
        TextView txtDistance;
	}
    
    /*
    public HubbaCursorAdapter (Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.setContext(context);
        this.layout = layout;
    }
    */
    
	@SuppressWarnings("deprecation")
	public HubbaCursorAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
	}

	public View newView(Context context, Cursor cursor, ViewGroup parent) {
	    LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    /*
		View view = inflater.inflate(R.layout.item_row, null);
	    ViewHolder holder = new ViewHolder();
	    holder.displayName = (TextView) view.findViewById(R.id.name);
	    holder.groupId = (TextView) view.findViewById(R.id.groupId);
	    holder.displayPhoto = (ImageView) view.findViewById(R.id.photo);
	    view.setTag(holder);
	    return view;
	    */
	    
	    View v = inflater.inflate(R.layout.item_row, null);
    	ViewHolder vh = new ViewHolder();
    	
    	v.setTag(vh);
    	
    	vh.imgThumbnail = (ImageView) v.findViewById(R.id.imgThumbnail);
        vh.txtTitle = (TextView) v.findViewById(R.id.txtTitle);
        vh.txtOverallRating = (TextView) v.findViewById(R.id.txtOverallRating);
        vh.txtPoRating = (TextView) v.findViewById(R.id.txtPoRating);
        vh.txtDiffRating = (TextView) v.findViewById(R.id.txtDiffRating);
        vh.txtDistance = (TextView) v.findViewById(R.id.txtDistance);
        
        return v;
	}
	

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	    ViewHolder vh = (ViewHolder) view.getTag();
	    //View v = inflater.inflate(R.layout.item_row, null);
    	
    	//v.setTag(vh);
	    int title = cursor.getColumnIndexOrThrow(HubbaDBAdapter.KEY_NAME);
	    int overall = cursor.getColumnIndexOrThrow(HubbaDBAdapter.KEY_RATING);
	    int bust = cursor.getColumnIndexOrThrow(HubbaDBAdapter.KEY_LEVEL);
	    int diff = cursor.getColumnIndexOrThrow(HubbaDBAdapter.KEY_DIFF);
	    
	    //vh.imgThumbnail.setImageResource(R.drawable.gettinthere);
	    
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
        vh.txtTitle.setText(cursor.getString(title));
        vh.txtOverallRating.setText(cursor.getString(overall));
        vh.txtPoRating.setText(cursor.getString(bust));
        vh.txtDiffRating.setText(cursor.getString(diff));
        vh.txtDistance.setText(String.format("%.2f", 0.00));
	}
	
	/*
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
 
        Cursor c = getCursor();
 
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layout, parent, false);
 
        int nameCol = c.getColumnIndex(People.NAME);
 
        String name = c.getString(nameCol);
 
        */
		/**
         * Next set the name of the entry.
         */    /*
        TextView name_text = (TextView) v.findViewById(R.id.name_entry);
        if (name_text != null) {
            name_text.setText(name);
        }
 
        return v;
    }
 
    @Override
    public void bindView(View v, Context context, Cursor c) {
 
        int nameCol = c.getColumnIndex(People.NAME);
 
        String name = c.getString(nameCol);
 
        */
		/**
         * Next set the name of the entry.
         */  /*  
        TextView name_text = (TextView) v.findViewById(R.id.name_entry);
        if (name_text != null) {
            name_text.setText(name);
        }
    }
   */
	/*
    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if (getFilterQueryProvider() != null) { return getFilterQueryProvider().runQuery(constraint); }
 
        StringBuilder buffer = null;
        String[] args = null;
        if (constraint != null) {
            buffer = new StringBuilder();
            buffer.append("UPPER(");
            buffer.append(People.NAME);
            buffer.append(") GLOB ?");
            args = new String[] { constraint.toString().toUpperCase() + "*" };
        }
 
        return context.getContentResolver().query(People.CONTENT_URI, null,
                buffer == null ? null : buffer.toString(), args, People.NAME + " ASC");
    }
    */
}