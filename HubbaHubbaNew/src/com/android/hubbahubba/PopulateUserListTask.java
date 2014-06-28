package com.android.hubbahubba;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PopulateUserListTask extends AsyncTask<String, String, String>{
	
	private Context context;
	private ArrayList<HashMap<String, String>> UsersArray;
	private HubbaUserAdapter dataAdapter;
	private ListView listView;
	
	//in constructor:
	public PopulateUserListTask(ListView listView, HubbaUserAdapter dataAdapter,
			ArrayList<HashMap<String, String>> UsersArray, Context context){
	        
		this.context = context;
        this.UsersArray = UsersArray;
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
			JSONArray users = jObject.getJSONArray("users");
			
            for(int i = 0; i < users.length(); i++){
                HashMap<String, String> userMap = new HashMap<String, String>();
                JSONObject user = users.getJSONObject(i);
				
                String display_name = user.getString("display_name");
				String fb_user_id = user.getString("fb_user_id");
				String ukey = user.getString("ukey");
                String picture_url = user.getString("picture_url");

                Log.i("DEBUG", "picture_url: " + picture_url);

                userMap.put("display_name",  display_name);
                userMap.put("fb_user_id", fb_user_id);
                userMap.put("ukey", ukey);
                userMap.put("picture_url", picture_url);

                // Only add users that will show up in the list view
                if(!display_name.equals("") && !(display_name == null)){
                    UsersArray.add(userMap);
                }
            }
        } catch(JSONException e) {
        	Toast.makeText(context, "OOPS, JSON PROBLEM in array", Toast.LENGTH_LONG).show();
        	e.printStackTrace();
        }
        
        dataAdapter = new HubbaUserAdapter(context, UsersArray, R.layout.activity_list_view);
		// create the adapter using the cursor pointing to the desired data
		// as well as the layout information
		//dataAdapter = new HubbaCursorAdapter(getActivity().getApplicationContext(), cursor);
		
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);
    }
}