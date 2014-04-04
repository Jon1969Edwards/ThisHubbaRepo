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

import android.content.Context;
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
        String url = params[0];
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
        //textView.setText(page); 
        Toast.makeText(context, "response = " + response, Toast.LENGTH_LONG).show();
    }   
}  