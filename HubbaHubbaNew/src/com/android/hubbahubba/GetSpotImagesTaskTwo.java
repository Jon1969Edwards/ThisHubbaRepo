package com.android.hubbahubba;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

public class GetSpotImagesTaskTwo extends AsyncTask<String, String, String>{
	
	private Context context;
	private ArrayList<HashMap<String, String>> imagesArray;
	private HubbaGridAdapter dataAdapter;
	private GridView gridView;
	
	
	//in constructor:
	public GetSpotImagesTaskTwo(GridView gridView, HubbaGridAdapter dataAdapter,
			ArrayList<HashMap<String, String>> imagesArray, Context context){
	        
		this.context = context;
        this.imagesArray = imagesArray;
        this.dataAdapter = dataAdapter;
        this.gridView = gridView;
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
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        
        try{
        	JSONObject jObject = new JSONObject(result);
			JSONArray photos = jObject.getJSONArray("photos");
			
			//akey, user {ukey}

            for(int i = 0; i < photos.length(); i++){                     
                HashMap<String, String> photoMap = new HashMap<String, String>();    
                JSONObject photo = photos.getJSONObject(i);
                
                // TODO: get username and rider somehow
                JSONObject user = photo.getJSONObject("user");
                
				String url = photo.getString("url");
				
				String display_name = user.getString("display_name");
				//String fb_user_id = user.getString("fb_user_id");
				//String ukey = user.getString("ukey");
				//String uname = photo.getString("uname");
				//String rider = photo.getString("rider");
				
				// TODO: GET THE REST FROM THE DB/ FB

				photoMap.put("url", url);
				photoMap.put("display_name", display_name);
                
                imagesArray.add(photoMap);            
            }       
        }catch(JSONException e)        {
        	Toast.makeText(context, "IMAGES: " + "OOPS, JSON PROBLEM in array", Toast.LENGTH_LONG).show();
        	e.printStackTrace();
        }
        
        dataAdapter = new HubbaGridAdapter(context, imagesArray);
		
		// Assign adapter to ListView
		gridView.setAdapter(dataAdapter);
		Toast.makeText(context, "Result: " + result, Toast.LENGTH_LONG).show();
    }
}