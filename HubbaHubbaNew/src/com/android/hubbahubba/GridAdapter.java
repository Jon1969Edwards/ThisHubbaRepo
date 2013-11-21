package com.android.hubbahubba;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class GridAdapter extends BaseAdapter /*implements Filterable*/ {
	
	private Context context; 
    
    static class ViewHolder{
		ImageView imgThumbnail;
	}
    
    /*
    public HubbaCursorAdapter (Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.setContext(context);
        this.layout = layout;
    }
    */
    
	public GridAdapter(Context mContext) {
		context = mContext;
		// TODO Auto-generated constructor stub
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public static int convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float Pix = dp * (metrics.densityDpi / 160f);
	    int px = (int) Pix;
	    return px;
	}
	
	 // references to our images
    private Integer[] mThumbIds = {
    		R.drawable.riley1, R.drawable.riley2, R.drawable.riley3,
            R.drawable.connorock , R.drawable.indysunburst,
            R.drawable.nosegrabup , R.drawable.img1,
            R.drawable.heart1, R.drawable.deatroit1, R.drawable.detroit2,

    };

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
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
		
		ImageView view;
		
		//imageLoader.destroy();
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
		
		// Convert the dp value for xml to pixels (casted to int from float)
		int size = convertDpToPixel(80, context);
		
		// Use picasso to load the image into view
		// XXX - THIS MUST STAY CONSISTANT WITH THE SIZE ON SPOT PAGE
		Picasso.with(context)
			   .load(mThumbIds[position])
			   .centerCrop()
			   .resize(size, size)
			   .placeholder(R.drawable.ic_empty)
			   .into(view);
		return view;
	}
}