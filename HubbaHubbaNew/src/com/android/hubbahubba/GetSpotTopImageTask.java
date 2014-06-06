package com.android.hubbahubba;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class GetSpotTopImageTask extends AsyncTask<String, String, String>{
	
	private Context context;
	private Activity activity;
	
	//in constructor:
	public GetSpotTopImageTask(Activity activity, Context context){
	        
		this.context = context;
        this.activity = activity;
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
    	/*
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
        */
        String url = uri[0];

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if(response != null){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        
        // Get size of image and imageView to load into
        int size = Image.convertDpToPixel(90, context);
        ImageView mImage = (ImageView) activity.findViewById(R.id.imgThumbnail);
        
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
		    	   .placeholder(R.drawable.ic_empty)
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
    }
}