package com.android.hubbahubba;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddSpotTask extends AsyncTask<String, Void, String> 
{       
	private Context context;
	private String name;
	private String lat;
	private String lon;
	private String type;
	private String ukey;
	private String akey;
	private String is_private;
	private String overall;
	private String difficulty;
	private String bust;
	private String text;
	private String url;
	private String imageURI;
	
	//in constructor:
	public AddSpotTask(Context context){
	        this.context = context;
	}

    @Override
    protected String doInBackground(String... params)   
    {           
    	
    	/*
    	 * 	 0	   1	2	 3	  4 	5	   6		7		   8		9		10		11		12
    	 * {url, name, lat, lon, type, ukey, akey, is_private, overall, difficulty, bust, text, imageURI}
    	 * 
    	 */
    	
        BufferedReader inBuffer = null;
        //String url = "http://35.2.227.254:5000/spots";
        url = params[0];
        String result = "fail";
        try {
        	
        	// Get info from params
        	name = params[1];
        	lat = params[2];
        	lon = params[3];
        	type = params[4];
        	ukey = params[5];
        	akey = params[6];
        	is_private = params[7];
        	overall = params[8];
        	difficulty = params[9];
        	bust = params[10];
        	text = params[11];
        	imageURI = params[12];
        	
        	/*
        	boolean secret = false;
        	if(is_private.equals("True")){
        		secret = true;
        	}
        	*/
        	
        	HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);

            // set header and params
            String source = ukey+":"+akey;
            request.setHeader("Authorization","Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP));
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("name", name));
            postParameters.add(new BasicNameValuePair("lat", lat));
            postParameters.add(new BasicNameValuePair("lon", lon));
            postParameters.add(new BasicNameValuePair("type", type));
            postParameters.add(new BasicNameValuePair("secret", is_private));

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                    postParameters);

            request.setEntity(formEntity);
            HttpResponse response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());

        } catch(Exception e) {
            // Do something about exceptions
            result = e.getMessage();
        } finally {
            if (inBuffer != null) {
                try {
                    inBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  result;
    }

    protected void onPostExecute(String response)
    {       
        //textView.setText(page); 
        //Toast.makeText(context, "Response = " + response, Toast.LENGTH_LONG).show();
        String id;
        try {
			JSONObject spot = new JSONObject(response);
			id = spot.getString("id");

			Spot.addComment(context, text, overall, difficulty, bust, id);
			
			if(!imageURI.equals("")){
				// TODO: Make rider not be empty anymore
				Spot.addImage(context, id, "", imageURI);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// Toast.makeText(context, "couldn't parse response or post comment: " + response, Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
        
        // Re-auth if possible when failed to add comment
        // TODO: Remove race condition
    	try {
			JSONObject jResponse = new JSONObject(response);
			String error = jResponse.getString("error");
			
			if(error.contains("authentication failed")){
				Toast.makeText(context, "error = " + error, Toast.LENGTH_LONG).show();
				
		    	// try to log back in if authentication failed
		    	SharedPreferences hubbaprefs = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
				String fb_user_id = hubbaprefs.getString("fb_user_id", "");
				String fb_access_token = hubbaprefs.getString("fb_access_token", "");
				String fb_expire = hubbaprefs.getString("fb_expire", "");
				//Toast.makeText(context, "user_id = " + fb_user_id + "\naccess_token = " + fb_access_token
				//		+ "\nexpire = " + fb_expire, Toast.LENGTH_LONG).show();
				
				/* TODO: Re-auth
				//Log.i("Our Login:", "is private " + is_private);				
				// TODO: do this not so sketch
				if(!fb_access_token.equals("") && !fb_expire.equals("") && !fb_user_id.equals("")){
					Toast.makeText(context, "Attempting to log back in..." + response, Toast.LENGTH_LONG).show();
					
					// log back in
					User.loginToFacebook(context, fb_user_id, fb_access_token, Integer.parseInt(fb_expire));
					
					// create task, wait and run
					AddSpotTask task = new AddSpotTask(context);
					synchronized(task){
						task.wait(3000);
						task.execute(new String[] {url, name, lat, lon, type, ukey, akey, is_private,
								overall, difficulty, bust, text}); 
					}
					
//					User.loginToFacebook(context, fb_user_id, fb_access_token, Integer.parseInt(fb_expire));
//					new AddSpotTask(context).execute(new String[] {url, name, lat, lon, type, ukey, akey, is_private,
//							overall, difficulty, bust, text}).wait(3000); 
				}
				else{
					Toast.makeText(context, "Please re-authenticate with facebook from the home screen", Toast.LENGTH_LONG).show();
				}
				*/
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// do nothing, most likely worked =)
		}
        //Toast.makeText(context, response, Toast.LENGTH_LONG).show();
    	/*
    	catch (InterruptedException e) {
			// TODO Auto-generated catch block
    		Toast.makeText(context, "sleep failed", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		*/
    }   
}  