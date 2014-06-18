package com.android.hubbahubba;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class AddImageTask extends AsyncTask<String, Void, String> {
    private Context context;
    private String imageURI;
    private String rider;
    private String ukey;
    private String akey;
    private String url;

    //in constructor:
    public AddImageTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        /*	PARAMS
    	 *	0		1		2		3	  4
    	 * {url, rider, imageURI, ukey, akey}
    	 *
    	 */
        BufferedReader inBuffer = null;
        //String url = "http://35.2.227.254:5000/spots";
        url = params[0];
        String result = "fail";
        HttpResponse response = null;
        try {

            // Get info from params
            rider = params[1];
            imageURI = params[2];
            ukey = params[3];
            akey = params[4];

            // create http post request
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);

            // set header
            String source = ukey + ":" + akey;
            request.setHeader("Authorization", "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));

            // create file for image (must be named file)
            File file = new File(imageURI);
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("file", new FileBody(file));
            entity.addPart("rider", new StringBody(rider));

            // add entity to request
            request.setEntity(entity);

            // get response and execute request
            response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
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
        return result;
    }

    protected void onPostExecute(String response) {
        // TODO: Re-auth
        try {
            JSONObject jResponse = new JSONObject(response);
            String error = jResponse.getString("error");

            if (error.contains("authentication failed")) {
                //Toast.makeText(context, "error = " + error, Toast.LENGTH_LONG).show();

                // try to log back in if authentication failed
                SharedPreferences hubbaprefs = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
                String fb_user_id = hubbaprefs.getString("fb_user_id", "");
                String fb_access_token = hubbaprefs.getString("fb_access_token", "");
                String fb_expire = hubbaprefs.getString("fb_expire", "");
				/*
				// TODO: do this not so sketch
				if(!fb_access_token.equals("") && !fb_expire.equals("") && !fb_user_id.equals("")){
					//Toast.makeText(context, "Attempting to log back in..." + response, Toast.LENGTH_LONG).show();
					User.loginToFacebook(context, fb_user_id, fb_access_token, Integer.parseInt(fb_expire));
					new AddImageTask(context).execute(new String[] {url, uname, rider, imageURI, ukey, akey}); 
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
    }
}  