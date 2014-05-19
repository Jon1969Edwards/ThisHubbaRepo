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
import android.widget.Toast;

public class AddCommentTask extends AsyncTask<String, Void, String> 
{       
	private Context context;
	private String uname;
	private String text;
	private String overall;
	private String difficulty;
	private String bust;
	private String ukey;
	private String akey;
	private String url;
	
	//in constructor:
	public AddCommentTask(Context context){
	        this.context = context;
	}

    @Override
    protected String doInBackground(String... params)   
    {
    	/*	PARAMS
    	 *	0	  1		2		3		   4		5	 6	   7
    	 * {url, uname, text, overall, difficulty, bust, ukey, akey}
    	 *
    	 */
        BufferedReader inBuffer = null;
        //String url = "http://35.2.227.254:5000/spots";
        url = params[0];
        String result = "fail";
        try {
        	
        	// Get info from params
        	uname = params[1];
        	text = params[2];
        	overall = params[3];
        	difficulty = params[4];
        	bust = params[5];
        	ukey = params[6];
        	akey = params[7];
        	
        	HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);

            // set header
            String source = ukey+":"+akey;
            request.setHeader("Authorization","Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP));
            
            // set params
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            //postParameters.add(new BasicNameValuePair("uname", uname));
            postParameters.add(new BasicNameValuePair("text", text));
            postParameters.add(new BasicNameValuePair("overall", overall));
            postParameters.add(new BasicNameValuePair("difficulty", difficulty));
            postParameters.add(new BasicNameValuePair("bust", bust));

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
    	// TODO: put this check in with other posts
    	try {
			JSONObject jResponse = new JSONObject(response);
			String error = jResponse.getString("error");
			
			/*
			if(error.contains("authentication failed")){
				Toast.makeText(context, "error = " + error, Toast.LENGTH_LONG).show();
				
		    	// try to log back in if authentication failed
		    	SharedPreferences hubbaprefs = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
				String fb_user_id = hubbaprefs.getString("fb_user_id", "");
				String fb_access_token = hubbaprefs.getString("fb_access_token", "");
				String fb_expire = hubbaprefs.getString("fb_expire", "");
				Toast.makeText(context, "user_id = " + fb_user_id + "\naccess_token = " + fb_access_token
						+ "\nexpire = " + fb_expire, Toast.LENGTH_LONG).show();
				// TODO: do this not so sketch
				if(!fb_access_token.equals("") && !fb_expire.equals("") && !fb_user_id.equals("")){
					Toast.makeText(context, "Attempting to log back in..." + response, Toast.LENGTH_LONG).show();
					User.loginToFacebook(context, fb_user_id, fb_access_token, Integer.parseInt(fb_expire));
					new AddCommentTask(context).execute(new String[] {url, uname, text, overall, difficulty, bust, ukey, akey});
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
		
		// otherwise nothing
        Toast.makeText(context, "response = " + response, Toast.LENGTH_LONG).show();
    }   
}  