package com.android.hubbahubba;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hubbahubba.HubbaCommentAdapter.ViewHolder;
import com.squareup.picasso.Picasso;

public class HubbaGridAdapter extends BaseAdapter /*implements Filterable*/ {
	
	private Context context; 
	private List<HashMap<String, String>> images;
    
    static class ViewHolder{
		ImageView image;
		TextView rider_text;
		// username
		TextView photog_text;
	}
    
	public HubbaGridAdapter(Context mContext, List<HashMap<String, String>> images) {
		this.context = mContext;
		this.images = images;
		// TODO Auto-generated constructor stub
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//View v = convertView;
	    //ViewHolder vh = null;
		ImageView view;
		HashMap<String, String> image = images.get(position);
		/*
		if (v == null) {
			LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_image, null);
            vh = new ViewHolder();
            v.setTag(vh);
        } else {
            vh = (ViewHolder) v.getTag();
        }
        */
		
		if (convertView == null) {  // if it's not recycled, initialize some attributes
		    view = new ImageView(context);
		    //imageView.setLayoutParams(new GridView.LayoutParams(-1, -1));
		    view.setLayoutParams(new GridView.LayoutParams(-1, 200));
		    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
		    //imageView.setPadding(8, 8, 8, 8);
		}
		else {
		    view = (ImageView) convertView;
		}
		
		/*
		vh.image = (ImageView) v.findViewById(R.id.image);
        vh.photog_text = (TextView) v.findViewById(R.id.photog_text);
        vh.rider_text = (TextView) v.findViewById(R.id.rider_text);
	    
	    //vh.imgThumbnail.
        vh.photog_text.setText("Photog: " + image.get("display_name"));
        vh.rider_text.setText("Rider: " + "Name of rider");
        
        // set params for gridview
        //vh.image.setLayoutParams(new GridView.LayoutParams(-1, 200));
	    //vh.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
		int size = Image.convertDpToPixel(115, context);
		*/
		// Convert the dp value for xml to pixels (casted to int from float)
		int size = Image.convertDpToPixel(100, context);
		
		// Use picasso to load the image into view
		// XXX - THIS MUST STAY CONSISTANT WITH THE SIZE ON SPOT PAGE
		Picasso.with(context)
			   //.load(mThumbIds[position])
			   .load(image.get("url"))
			   .centerCrop()
			   .resize(size, size)
			   .placeholder(R.drawable.ic_empty)
			   //.into(vh.image);
			   .into(view);
		
		return view;
	}
}