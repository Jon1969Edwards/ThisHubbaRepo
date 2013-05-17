package com.android.hubbahubba;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
  
public class CustomListViewAdapter extends BaseAdapter
{

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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}  
	/*
	LayoutInflater inflater;
	List<ListViewItem> items;
	
	public CustomListViewAdapter(SherlockFragment context, List<ListViewItem> items) {  
        super(); 
        
        this.items = items;
        this.inflater = (LayoutInflater)context.getSherlockActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return items.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        // TODO Auto-generated method stub  
    	
    	ListViewItem item = items.get(position);
    	
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

    	ListViewItem item = items.get(position);
    	
    	View vi=convertView;
        
        if(convertView==null)
            vi = inflater.inflate(R.layout.item_row, null);
        
        ImageView imgThumbnail = (ImageView) vi.findViewById(R.id.imgThumbnail);
        TextView txtTitle = (TextView) vi.findViewById(R.id.txtTitle);
        TextView txtOverallRating = (TextView) vi.findViewById(R.id.txtOverallRating);
        TextView txtPoRating = (TextView) vi.findViewById(R.id.txtPoRating);
        TextView txtDiffRating = (TextView) vi.findViewById(R.id.txtDiffRating);
        TextView txtDistance = (TextView) vi.findViewById(R.id.txtDistance);
        
        imgThumbnail.setImageResource(item.Thumbnail);
        txtTitle.setText(item.Title);
        txtOverallRating.setText(String.valueOf(item.OverallRating));
        txtPoRating.setText(String.valueOf(item.PoRating));
        txtDiffRating.setText(String.valueOf(item.DiffRating));
        txtDistance.setText(String.format("%.2f", item.Distance));
        
        return vi;
    }
    */
}