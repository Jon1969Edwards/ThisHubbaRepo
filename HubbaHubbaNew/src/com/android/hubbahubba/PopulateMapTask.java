package com.android.hubbahubba;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// TODO: For async tasks but currently unused
public class PopulateMapTask extends AsyncTask<String, String, String>{
	
	private Context context;
	private GoogleMap map;
	
	//in constructor:
	public PopulateMapTask(GoogleMap map, Context context){
	        this.context = context;
	        this.map = map;
	}

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
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
        
        try {
        	// convert to json and get spot entries
			JSONObject jObject = new JSONObject(result);
			JSONArray spotsArray = jObject.getJSONArray("spots");
			
			for (int i=0; i < spotsArray.length(); i++)
			{
			    try {
			        JSONObject spot = spotsArray.getJSONObject(i);
			        // Pulling items from the array
			        String id = spot.getString("id");
					String name = spot.getString("name");
					double lat = Double.parseDouble(spot.getString("lat"));
					double lon = Double.parseDouble(spot.getString("lon"));
					
					// Add spots to map
					this.map.addMarker(new MarkerOptions()
    			                                  .position(new LatLng(lat, lon))
    			                                  .title(name)
    			                                  .snippet(id)
    			                                  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
					
			    } catch (JSONException e) {
			    	Toast.makeText(context, "OOPS, JSON PROBLEM in array", Toast.LENGTH_LONG).show();
			    }
			}
			
			//Toast.makeText(context, "Name:\n" +
	        //         name, Toast.LENGTH_LONG).show();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "OOPS, JSON PROBLEM", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
        //Do anything with response..
        /*
        Toast.makeText(context, "response:\n" +
                 result, Toast.LENGTH_LONG).show();
        */
        
    }
}