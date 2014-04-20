package com.android.hubbahubba;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

public class AddImageTask extends AsyncTask<String, Void, String> 
{       
	private Context context;
	private String uname;
	private String imageURI;
	private String rider;
	private String ukey;
	private String akey;
	private String url;
	
	//in constructor:
	public AddImageTask(Context context){
	        this.context = context;
	}

    @Override
    protected String doInBackground(String... params)   
    {
    	/*	PARAMS
    	 *	0		1		2		3		4	5
    	 * {url, uname, rider, imageURI, ukey, akey}
    	 *
    	 */
        BufferedReader inBuffer = null;
        //String url = "http://35.2.227.254:5000/spots";
        url = params[0];
        String result = "fail";
        HttpResponse response = null;
        try {
        	
        	// Get info from params
        	uname = params[1];
        	rider = params[2];
        	imageURI = params[3];
        	ukey = params[4];
        	akey = params[5];
        	
        	// create http post request
        	HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);
            
            // set header
            String source = ukey+":"+akey;
            request.setHeader("Authorization","Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP));

            // create file for image (must be named file)
        	File file = new File(imageURI);
        	
        	// create entity for post params
        	MultipartEntity entity = new MultipartEntity();

        	// files[0] = image
        	entity.addPart("file", new FileBody(file));
        	
        	// TODO: add rider name
        	
        	// add entity to request
        	request.setEntity(entity);
        	
            // get response and execute request
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());

        } catch(Exception e) {
            // TODO: find a better way to do this
        	try {
				result = EntityUtils.toString(response.getEntity());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
    	// TODO: Remove race condition
    	try {
			JSONObject jResponse = new JSONObject(response);
			String error = jResponse.getString("error");
			
			if(error.contains("authentication failed")){
				//Toast.makeText(context, "error = " + error, Toast.LENGTH_LONG).show();
				
		    	// try to log back in if authentication failed
		    	SharedPreferences hubbaprefs = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
				String fb_user_id = hubbaprefs.getString("fb_user_id", "");
				String fb_access_token = hubbaprefs.getString("fb_access_token", "");
				String fb_expire = hubbaprefs.getString("fb_expire", "");
				//Toast.makeText(context, "user_id = " + fb_user_id + "\naccess_token = " + fb_access_token
				//		+ "\nexpire = " + fb_expire, Toast.LENGTH_LONG).show();
				
				// TODO: do this not so sketch
				if(!fb_access_token.equals("") && !fb_expire.equals("") && !fb_user_id.equals("")){
					//Toast.makeText(context, "Attempting to log back in..." + response, Toast.LENGTH_LONG).show();
					User.loginToFacebook(context, fb_user_id, fb_access_token, Integer.parseInt(fb_expire));
					new AddImageTask(context).execute(new String[] {url, uname, rider, imageURI, ukey, akey}); 
				}
				else{
					Toast.makeText(context, "Please re-authenticate with facebook from the home screen", Toast.LENGTH_LONG).show();
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// do nothing, most likely worked =)
		}
    	/*
    	// TODO: Success add to array
    	try{
    		JSONObject photo = new JSONObject(response);
			
            JSONObject user = photo.getJSONObject("user");
            
			String url = photo.getString("url");
			
			String display_name = user.getString("display_name");
			//String fb_user_id = user.getString("fb_user_id");
			//String ukey = user.getString("ukey");
			//String uname = photo.getString("uname");
			//String rider = photo.getString("rider");
			
			// TODO: GET THE REST FROM THE DB/ FB
			HashMap<String, String> photoMap = new HashMap<String, String>(); 
			photoMap.put("url", url);
			photoMap.put("display_name", display_name);
            
            imagesArray.add(photoMap);            
        }catch(JSONException e)        {
        	Toast.makeText(context, "IMAGES: " + "JSON PROBLEM in grabbing list of images", Toast.LENGTH_LONG).show();
        	e.printStackTrace();
        }
    	*/
    	
        //textView.setText(page); 
        //Toast.makeText(context, "response = " + response, Toast.LENGTH_LONG).show();
    }   
}  