package com.android.hubbahubba;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class CheckLoggedInTask extends AsyncTask<String, Void, String> {
    private Context context;
    private String url;
    private String ukey;
    private String akey;

    //in constructor:
    public CheckLoggedInTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

    	/*	TODO: May not need the spot_id here
    	 * 	 0	   1	2	 	3	  		4		
    	 * {url, ukey, akey, spot_id, fb_user_id}
    	 * 
    	 */

        BufferedReader inBuffer = null;
        url = params[0];
        String result = "fail";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            ukey = params[1];
            akey = params[2];

            // set header and params
            String source = ukey + ":" + akey;

            Log.i("WHATTTT", "Ukey = " + ukey + " Akey = " + akey);

            request.setHeader("Authorization", "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));

            //request.setEntity(formEntity);
            HttpResponse response = httpClient.execute(request);
            result = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
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
        return result;
    }

    protected void onPostExecute(String response) {
        Toast.makeText(context, "result = " + response, Toast.LENGTH_SHORT).show();
        // Re-auth if possible when failed to add comment
        // TODO: Remove race condition
        //try {
        //  JSONObject jResponse = new JSONObject(response);
        //  String error = jResponse.getString("error");
        //
        //  Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        //
        //} catch (JSONException e) {
        //  // TODO Auto-generated catch block
        //  // do nothing, most likely worked =)
        //}
    }
}  