package com.android.hubbahubba;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.GridView;
import android.widget.Toast;

public class GetSpotImagesTask extends AsyncTask<String, String, String>{
	
	private Context context;
	private ArrayList<HashMap<String, String>> imagesArray;
	private HubbaGridAdapter dataAdapter;
	private GridView gridView;
	
	
	//in constructor:
	public GetSpotImagesTask(GridView gridView, HubbaGridAdapter dataAdapter,
			ArrayList<HashMap<String, String>> imagesArray, Context context){
	        
		this.context = context;
        this.imagesArray = imagesArray;
        this.dataAdapter = dataAdapter;
        this.gridView = gridView;
	}

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        HttpGet request = new HttpGet(uri[0]);
        
        // get ukey and akey from shared preferences
		SharedPreferences hubbaprefs = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
		String ukey = hubbaprefs.getString("ukey", "");
		String akey = hubbaprefs.getString("akey", "");
		
		//Toast.makeText(context, "ukey = " + ukey + "\nand akey = " + akey, Toast.LENGTH_LONG).show();
     	
		// set header and params
		// set header and params
 		String source = ukey+":"+akey;
        request.setHeader("Authorization","Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP));
        try {
            response = httpclient.execute(request);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        	responseString = "CPE";
        } catch (IOException e) {
            //TODO Handle problems..
        	responseString = "IOE";
        }
        return responseString;
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
		//Toast.makeText(context, "Result: " + result, Toast.LENGTH_LONG).show();
    }
}