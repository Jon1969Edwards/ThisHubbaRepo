package com.android.hubbahubba;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
  
public class NewListViewAdapter extends BaseAdapter
{
	
	LayoutInflater inflater;
	List<String> titles;
	List<String> rats;
	List<String> difs;
	List<String> busts;
	List<String> images;
	
	public NewListViewAdapter(SherlockFragment context, List<String> titles,
							  List<String> rats, List<String> difs, List<String> busts,
							  List<String> images) {  
        super(); 
        
        this.titles = titles;
        this.rats = rats;
    	this.difs = difs;
    	this.busts = busts;
    	this.images = images;
        this.inflater = (LayoutInflater)context.getSherlockActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return 0;  
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
    public View getView(final int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  
    	View v = convertView;
        ViewHolder vh = null;
        
        if(convertView != null)
            vh = (ViewHolder) v.getTag();
        else{
        	
        	v = inflater.inflate(R.layout.item_row, null);
        	vh = new ViewHolder();
        	
        	v.setTag(vh);
        	
        	vh.imgThumbnail = (ImageView) v.findViewById(R.id.imgThumbnail);
	        vh.txtTitle = (TextView) v.findViewById(R.id.txtTitle);
	        vh.txtOverallRating = (TextView) v.findViewById(R.id.txtOverallRating);
	        vh.txtPoRating = (TextView) v.findViewById(R.id.txtPoRating);
	        vh.txtDiffRating = (TextView) v.findViewById(R.id.txtDiffRating);
	        vh.txtDistance = (TextView) v.findViewById(R.id.txtDistance);

        }

        vh.imgThumbnail.setImageResource(R.drawable.gettinthere);
        vh.txtTitle.setText(titles.get(position));
        vh.txtOverallRating.setText(rats.get(position));
        vh.txtPoRating.setText(busts.get(position));
        vh.txtDiffRating.setText(difs.get(position));
        vh.txtDistance.setText(String.format("%.2f", 0.00));
        
        return v;
    }
	
	static class ViewHolder{
		ImageView imgThumbnail;
        TextView txtTitle;
        TextView txtOverallRating;
        TextView txtPoRating;
        TextView txtDiffRating;
        TextView txtDistance;
	}
}