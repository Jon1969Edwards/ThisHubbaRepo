package com.android.hubbahubba;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

public class GetSpotTopImageTask_VIEW extends AsyncTask<String, String, String>{
	
	private Context context;
	private View view;
	private Marker marker;
	private int size;
	private ImageView mImage;
	
	//in constructor:
	public GetSpotTopImageTask_VIEW(View view, Context context, Marker marker){
	        
		this.context = context;
        this.view = view;
        this.marker = marker;
	}
	
	byte[] readFully(InputStream in) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024];
	    for (int count; (count = in.read(buffer)) != -1; ) {
	      out.write(buffer, 0, count);
	    }
	    return out.toByteArray();
	}

    @Override
    protected String doInBackground(String... uri) {
    	OkHttpClient client = new OkHttpClient();    	
    	HttpURLConnection connection = null;
    	
		try {
			connection = client.open(new URL(uri[0].toString()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	InputStream in = null;
        try {
        	in = connection.getInputStream();
        	byte[] response = readFully(in);
            
        	return new String(response, "UTF-8");
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
          if (in != null)
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        
        // Get size of image and imageView to load into
        size = Image.convertDpToPixel(90, context);
        mImage = (ImageView) view.findViewById(R.id.info_window_image);
        
    	try{
        	JSONObject jObject = new JSONObject(result);
			JSONObject photo = jObject.getJSONObject("top_photo");
			
            // TODO: get username and rider somehow
            //JSONObject user = photo.getJSONObject("user");
            
			String url = photo.getString("url");
			
			//String display_name = user.getString("display_name");
			//imageMap.put("display_name", display_name);  
			
	        // use picasso to load the image into view
		    Picasso.with(context)
		    	   .load(url)
		    	   .centerCrop()
		    	   .resize(size, size)
		    	   .into(mImage);
		    
        }catch(JSONException e)        {
        	e.printStackTrace();
        	
        	// use picasso to load the image into view
        	// TODO: get a placeholder for this
			Picasso.with(context)
	    	   .load(R.drawable.gettinthere)
	    	   .centerCrop()
	    	   .resize(size, size)
	    	   .placeholder(R.drawable.ic_empty)
	    	   .into(mImage);
        }
    	
    	// TODO: come up with a better time for this
    	Handler handler = new Handler(); 
        handler.postDelayed(new Runnable() { 
             public void run() { 
            	 marker.showInfoWindow();
            	 } 
        }, 500);
    }
}