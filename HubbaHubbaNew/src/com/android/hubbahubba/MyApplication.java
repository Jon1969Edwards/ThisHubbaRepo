package com.android.hubbahubba;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        //set options
        DisplayImageOptions options = new DisplayImageOptions.Builder()
    		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
    		.build();
        
        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        	.defaultDisplayImageOptions(options)
            .build();
        
        ImageLoader.getInstance().init(config);
    }
}