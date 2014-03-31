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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

// TODO: For async tasks but currently unused
public class GetSpotInfoTask extends AsyncTask<String, String, String>{
	
	private Context context;
	private Activity activity;
	
	//in constructor:
	public GetSpotInfoTask(Activity activity, Context context){
	        this.context = context;
	        this.activity = activity;
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
			JSONObject spot = new JSONObject(result);
			
		    try {
		        // Pulling items from the array
				String name = spot.getString("name");
				String id = spot.getString("id");
				String overall = spot.getString("overall");
				String difficulty = spot.getString("difficulty");
				String bust = spot.getString("bust");
				String type = spot.getString("type");
				String distance = "11.1 mi";
				String lat = spot.getString("lat");
				String lon = spot.getString("lon");
				Toast.makeText(context, "Name = " + name + ", id = " + id + " overall = " + overall
						+ " bust = " + bust + " diff = " + difficulty + " type = " + type, Toast.LENGTH_LONG).show();
				
				// get views
				TextView Title = (TextView) activity.findViewById(R.id.txtTitle);
				TextView Difficulty = (TextView) activity.findViewById(R.id.txtDiffRating);
				TextView Overall = (TextView) activity.findViewById(R.id.txtOverallRating);
				TextView Bust = (TextView) activity.findViewById(R.id.txtPoRating);
				TextView Distance = (TextView) activity.findViewById(R.id.txtDistance);
				TextView Lat = (TextView) activity.findViewById(R.id.lat);
				TextView Lon = (TextView) activity.findViewById(R.id.lon);
				
				// set texts
				Overall.setText(overall);
				Bust.setText(bust);
				Difficulty.setText(difficulty);
				Distance.setText(distance);
				Title.setText(name);
				Lat.setText(lat);
				Lon.setText(lon);
		    } catch (JSONException e) {
		    	Toast.makeText(context, "OOPS, JSON PROBLEM in array", Toast.LENGTH_LONG).show();
		    }
			
			//Toast.makeText(context, "Name:\n" +
	        //         name, Toast.LENGTH_LONG).show();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "OOPS, JSON PROBLEM IN SPOT", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
        //Do anything with response..
       // Toast.makeText(context, "response:\n" +
        //         result, Toast.LENGTH_LONG).show();
        
        
    }
}