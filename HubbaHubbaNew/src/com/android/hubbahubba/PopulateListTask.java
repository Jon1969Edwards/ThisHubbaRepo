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
import android.widget.ListView;
import android.widget.Toast;

public class PopulateListTask extends AsyncTask<String, String, String>{
	
	private Context context;
	private ArrayList<HashMap<String, String>> SpotsArray;
	private HubbaAdapter dataAdapter;
	private ListView listView;
	
	
	//in constructor:
	public PopulateListTask(ListView listView, HubbaAdapter dataAdapter,
			ArrayList<HashMap<String, String>> SpotsArray, Context context){
	        
		this.context = context;
        this.SpotsArray = SpotsArray;
        this.dataAdapter = dataAdapter;
        this.listView = listView;
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
        
        //Toast.makeText(context, "Result From List: " + result, Toast.LENGTH_LONG).show();
        
        try{
        	JSONObject jObject = new JSONObject(result);
			JSONArray spots = jObject.getJSONArray("spots");
			
			//akey, user {ukey}

            for(int i = 0; i < spots.length(); i++){                     
                HashMap<String, String> spotMap = new HashMap<String, String>();    
                JSONObject spot = spots.getJSONObject(i);
				
                String id = spot.getString("id");
				String name = spot.getString("name");
				String lat = spot.getString("lat");
				String lon = spot.getString("lon");
				String difficulty = spot.getString("difficulty");
				String overall = spot.getString("overall");
				String bust = spot.getString("bust");
				String type = spot.getString("type");
				
				// TODO: Figure out distance stuff
				String distance = "10.0 mi";

                spotMap.put("id",  id);
                spotMap.put("name", name);
                spotMap.put("lat", lat);
                spotMap.put("lon",  lon);
                spotMap.put("overall", overall);
                spotMap.put("difficulty", difficulty);
                spotMap.put("bust", bust);
                spotMap.put("distance", distance);
                
                // TODO: Fix this DB side
                if(type!= null){
                	spotMap.put("type", type);
                }
                else{
                	spotMap.put("type", "hubba");
                }
                
                SpotsArray.add(spotMap);            
            }       
        }catch(JSONException e)        {
        	Toast.makeText(context, "OOPS, JSON PROBLEM in array", Toast.LENGTH_LONG).show();
        	e.printStackTrace();
        }
        
        dataAdapter = new HubbaAdapter(context, SpotsArray, R.layout.activity_list_view);
		// create the adapter using the cursor pointing to the desired data
		// as well as the layout information
		//dataAdapter = new HubbaCursorAdapter(getActivity().getApplicationContext(), cursor);
		
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);
    }
}