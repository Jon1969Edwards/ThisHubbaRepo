package com.android.hubbahubba;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

public class CheckLoggedInTask extends AsyncTask<String, Void, String> {
    private Context context;
    private String url;
    private String ukey;
    private String akey;
    private String spot_id;
    private String req_type;

    //in constructor:
    public CheckLoggedInTask(Context context, String spot_id) {
        this.context = context;
        this.spot_id = spot_id;
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
            req_type = params[3];

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
        //Toast.makeText(context, "result = " + response, Toast.LENGTH_SHORT).show();
        try {
            JSONObject jResponse = new JSONObject(response);
            String result = jResponse.getString("success_result");

            //Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            if (result.equals("Success")) {
                if(req_type.equals("Comment")){
                    Bundle bundleData = new Bundle();
                    bundleData.putString("spot_id", spot_id);
                    Intent intent = new Intent(context, AddComment.class);
                    intent.putExtras(bundleData);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }
                else if(req_type.equals("Image")){
                    Bundle bundleData = new Bundle();
                    bundleData.putString("spot_id", spot_id);
                    Intent intent = new Intent(context, AddImage.class);
                    intent.putExtras(bundleData);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }
                else if(req_type.equals("Marker")){
                    // TODO: fix this -- Do nothing
                }
                else {
                    Toast.makeText(context, "Invalid request type", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context,
                        "Please return to the home screen and log in to continue",
                        Toast.LENGTH_LONG)
                        .show();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            // do nothing, most likely worked =)
            Toast.makeText(context,
                    "Please return to the home screen and log in to continue",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}  