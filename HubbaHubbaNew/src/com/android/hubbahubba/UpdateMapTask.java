package com.android.hubbahubba;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: For async tasks but currently unused
public class UpdateMapTask extends AsyncTask<String, String, String>{
	
	private Context context;
	private GoogleMap map;
	private String type;
	
	//in constructor:
	public UpdateMapTask(GoogleMap map, Context context, String type){
	        this.context = context;
	        this.map = map;
	        this.type = type;
	}

    @Override
    protected String doInBackground(String... uri) {
    	// get url
    	String URL = uri[0];
    	
    	// add query params
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        //postParameters.add(new BasicNameValuePair("uname", uname));
        params.add(new BasicNameValuePair("type", type));
        String paramString = URLEncodedUtils.format(params, "utf-8");

        URL += '?' + paramString;
    	
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL);
        HttpResponse response = null;
        String responseString = null;
        
        // get ukey and akey from shared preferences
 		SharedPreferences hubbaprefs = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
        String ukey = hubbaprefs.getString("ukey", "");
 		String akey = hubbaprefs.getString("akey", "");
 		Log.i("GET", "URL == " + URL);
		//Toast.makeText(context, "ukey = " + ukey + "\nand akey = " + akey, Toast.LENGTH_LONG).show();
     	
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
            	//responseString = EntityUtils.toString(response.getEntity());
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
        //Toast.makeText(context, "Result: " + result, Toast.LENGTH_LONG).show();
        try {
        	// convert to json and get spot entries
			JSONObject jObject = new JSONObject(result);
			JSONArray spotsArray = jObject.getJSONArray("spots");
			
			this.map.clear();
			for (int i=0; i < spotsArray.length(); i++)
			{
			    try {
			        JSONObject spot = spotsArray.getJSONObject(i);
			        // Pulling items from the array
			        String snippit = "";
			        String name = spot.getString("name");
					String id = spot.getString("id");
					snippit += (id + ",");
					String overall = spot.getString("overall");
					snippit += (overall + ",");
					String difficulty = spot.getString("difficulty");
					snippit += (difficulty + ",");
					String bust = spot.getString("bust");
					snippit += (bust + ",");
					String type = spot.getString("type");
					snippit += (type + ",");
                    String photo_url = spot.getString("photo");
                    snippit += (photo_url + ",");
					//Toast.makeText(context, snippit, Toast.LENGTH_LONG).show();
					
					double lat = Double.parseDouble(spot.getString("lat"));
					double lon = Double.parseDouble(spot.getString("lon"));
					
					// Add spots to map
					this.map.addMarker(new MarkerOptions()
    			                                  .position(new LatLng(lat, lon))
    			                                  .title(name)
    			                                  .snippet(snippit)
    			                                  .icon(BitmapDescriptorFactory.fromResource(R.drawable.hubba_marker_red_two)));
					
			    } catch (JSONException e) {
			    	Toast.makeText(context, "OOPS, JSON PROBLEM from map", Toast.LENGTH_LONG).show();
			    }
			}
			
			//Toast.makeText(context, "Name:\n" +
	        //         name, Toast.LENGTH_LONG).show();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "OOPS, JSON PROBLEM\nresult = " + result, Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
        //Do anything with response..
        //Toast.makeText(context, "ukey:" + ukey + "\nakey: " + akey, Toast.LENGTH_LONG).show();
    }
}
