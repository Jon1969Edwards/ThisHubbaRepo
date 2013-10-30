package com.android.hubbahubba;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ImageAdapter extends BaseAdapter {
	
	//ImageLoader imageLoader = ImageLoader.getInstance();

	private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        ImageLoader imageLoader = ImageLoader.getInstance();
        //imageLoader.destroy();
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(-1, -1));
            imageView.setLayoutParams(new GridView.LayoutParams(-1, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
        }
        else {
            imageView = (ImageView) convertView;
        }
        
        //set options
        DisplayImageOptions options = new DisplayImageOptions.Builder()
    		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory()
			.cacheOnDisc()
			.bitmapConfig(Bitmap.Config.RGB_565)
    		.build();
        
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        imageLoader.displayImage("drawable://" + mThumbIds[position], imageView, options);
        
        //imageView.setImageResource(mThumbIds[position]);
        
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
    		R.drawable.riley1, R.drawable.riley2, R.drawable.riley3,
            R.drawable.connorock , R.drawable.indysunburst,
            R.drawable.nosegrabup , R.drawable.img1,
            R.drawable.heart1, R.drawable.deatroit1, R.drawable.detroit2,

    };
}