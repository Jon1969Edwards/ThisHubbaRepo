package com.android.hubbahubba;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class ShareSpotTask extends AsyncTask<String, Void, String> 
{       
	private Context context;
	private String spot_id;
	private String fb_user_id;
	private String url;
	private String ukey;
	private String akey;
	
	//in constructor:
	public ShareSpotTask(Context context){
	        this.context = context;
	}

    @Override
    protected String doInBackground(String... params)   
    {           
    	
    	/*	TODO: May not need the spot_id here
    	 * 	 0	   1	2	 	3	  		4		
    	 * {url, ukey, akey, spot_id, fb_user_id}
    	 * 
    	 */
    	
        BufferedReader inBuffer = null;
        url = params[0];
        ukey = params[1];
        akey = params[2];
        spot_id = params[3];
        fb_user_id = params[4];
        String result = "fail";
        
        try {
        	HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);

            // set header and params
            String source = ukey+":"+akey;
            request.setHeader("Authorization","Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP));
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("fb_user_id", fb_user_id));
            postParameters.add(new BasicNameValuePair("spot_id", spot_id));

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
        // Re-auth if possible when failed to add comment
        // TODO: Remove race condition
    	try {
			JSONObject jResponse = new JSONObject(response);
			String error = jResponse.getString("error");
			
			Toast.makeText(context, error, Toast.LENGTH_LONG).show();
			/*
			if(error.contains("authentication failed")){
				Toast.makeText(context, "error = " + error, Toast.LENGTH_LONG).show();
				
		    	// try to log back in if authentication failed
		    	SharedPreferences hubbaprefs = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
				String fb_user_id = hubbaprefs.getString("fb_user_id", "");
				String fb_access_token = hubbaprefs.getString("fb_access_token", "");
				String fb_expire = hubbaprefs.getString("fb_expire", "");
				
				//Log.i("Our Login:", "is private " + is_private);				
				// TODO: do this not so sketch
				if(!fb_access_token.equals("") && !fb_expire.equals("") && !fb_user_id.equals("")){
					Toast.makeText(context, "Attempting to log back in..." + response, Toast.LENGTH_LONG).show();
					
					// log back in
					User.loginToFacebook(context, fb_user_id, fb_access_token, Integer.parseInt(fb_expire));
					
					// create task, wait and run
					ShareSpotTask task = new ShareSpotTask(context);
					synchronized(task){
						task.wait(3000);
						task.execute(new String[] {url, ukey, akey, spot_id, fb_user_id}); 
					}
				}
				else{
					Toast.makeText(context, "Please re-authenticate with facebook from the home screen", Toast.LENGTH_LONG).show();
				}
			}
			*/
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// do nothing, most likely worked =)
		}
    	/*
        //Toast.makeText(context, response, Toast.LENGTH_LONG).show();
    	catch (InterruptedException e) {
			// TODO Auto-generated catch block
    		Toast.makeText(context, "sleep failed", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		*/
    }   
}  