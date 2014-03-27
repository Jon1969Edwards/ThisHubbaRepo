package com.android.hubbahubba;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AddSpotTask extends AsyncTask<String, Void, String> 
{       
	private Context context;
	//in constructor:
	public AddSpotTask(Context context){
	        this.context = context;
	}

    @Override
    protected String doInBackground(String... params)   
    {           
        BufferedReader inBuffer = null;
        //String url = "http://35.2.227.254:5000/spots";
        String url = params[0];
        String result = "fail";
        try {
        	/*
        	Toast.makeText(context, "name = " + params[0], Toast.LENGTH_SHORT).show();
        	Toast.makeText(context, "lat = " + params[1], Toast.LENGTH_SHORT).show();
        	Toast.makeText(context, "lon = " + params[2], Toast.LENGTH_SHORT).show();
        	*/
        	HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("name", params[1]));
            postParameters.add(new BasicNameValuePair("lat", params[2]));
            postParameters.add(new BasicNameValuePair("lon", params[3]));

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                    postParameters);

            request.setEntity(formEntity);
             httpClient.execute(request);
                    result="got it";

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
        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
    }   
}  