package com.android.hubbahubba;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

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
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        
        try{
        	JSONObject jObject = new JSONObject(result);
			JSONArray photos = jObject.getJSONArray("photos");

            for(int i = 0; i < photos.length(); i++){                     
                HashMap<String, String> photoMap = new HashMap<String, String>();    
                JSONObject photo = photos.getJSONObject(i);
                
                // TODO: get username and rider somehow
                JSONObject user = photo.getJSONObject("user");
                
				String url = photo.getString("url");
                String rider_name = photo.getString("rider");
                String display_name = "";
                if(user != null){
                    display_name = user.getString("display_name");
                }
				//String fb_user_id = user.getString("fb_user_id");
				//String ukey = user.getString("ukey");
				//String uname = photo.getString("uname");
				
				// TODO: GET THE REST FROM THE DB/ FB

				photoMap.put("url", url);
				photoMap.put("display_name", display_name);
                photoMap.put("rider_name", rider_name);
                
                imagesArray.add(photoMap);            
            }       
        }catch(JSONException e)        {
        	Toast.makeText(context, "IMAGES: " + "JSON PROBLEM in grabbing list of images", Toast.LENGTH_LONG).show();
        	e.printStackTrace();
        }
        
        dataAdapter = new HubbaGridAdapter(context, imagesArray);
		
		// Assign adapter to ListView
		gridView.setAdapter(dataAdapter);
		//Toast.makeText(context, "Result: " + result, Toast.LENGTH_LONG).show();
    }
}