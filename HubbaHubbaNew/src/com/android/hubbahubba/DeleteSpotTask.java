package com.android.hubbahubba;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DeleteSpotTask extends AsyncTask<String, Void, String> 
{       
	private Context context;
	private String name;
	private String spot_id;
	private String string_url;
	private String imageURI;
	private String ukey;
	private String akey;
	
	//in constructor:
	public DeleteSpotTask(Context context){
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
    	HttpURLConnection httpCon;
    	URL url = null;
    	
        //String url = "http://35.2.227.254:5000/spots";
        string_url = params[0];
        ukey = params[1];
    	akey = params[2];
        	
    	// set header and params
        //String source = ukey+":"+akey;
       // httpCon.setHeader("Authorization","Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP));
    	// Delete TODO: take this out or only for creator
        
        /*
        OutputStreamWriter wr;
		try {
			url = new URL(string_url);
			httpCon = (HttpURLConnection) url.openConnection();
			//httpCon.setDoOutput(true);
			httpCon.setRequestProperty(
			    "Content-Type", "application/x-www-form-urlencoded" );
			httpCon.setRequestMethod("DELETE");
			httpCon.connect();
			wr = new OutputStreamWriter(httpCon.getOutputStream());
			wr.write(data); // data is the post data to send
			wr.flush();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "Bad Url", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "IOE", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} finally{
		
		}
		*/
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete request = new HttpDelete(string_url);
        Log.i("LOG_TAG", "r = " + request);
        
       // Toast.makeText(context, request.toString(), Toast.LENGTH_LONG);
        
        // set header and params
        String source = ukey+":"+akey;
        //request.setHeader("Authorization","Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP));
       
        String result = "";
        //request.setEntity(formEntity);
        HttpResponse response = null;
        String message = null;
        int code = -1;
        
		try {
			if(request == null){
				Log.i("LOG_TAG", "r = null");
			}
			else{
				response = httpClient.execute(request);
				//result = EntityUtils.toString(response.getEntity());
				code = response.getStatusLine().getStatusCode();
			    message = response.getStatusLine().getReasonPhrase();
			    Log.i("LOG_TAG", "HTTP response -- code:" + code + "  message:" + message);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "CPE", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "IOE", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} finally{
			httpClient.getConnectionManager().shutdown();
		}
		
		// TODO?
        return result;
        
    }

    protected void onPostExecute(String response)
    {       
    	//Toast.makeText(context, "Done", Toast.LENGTH_LONG).show();
    }   
}  