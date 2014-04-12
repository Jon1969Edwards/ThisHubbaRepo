package com.android.hubbahubba;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
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
        String url = params[0];
        String result = "fail";
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
        Toast.makeText(context, "response = " + response, Toast.LENGTH_LONG).show();
    }   
}  