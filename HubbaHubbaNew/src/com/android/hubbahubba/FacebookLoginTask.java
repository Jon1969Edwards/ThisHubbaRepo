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
import android.util.Log;
import android.widget.Toast;

public class FacebookLoginTask extends AsyncTask<String, Void, String> {
	private Context context;
	private String user_id;
	private String access_token;
	private String expire;

	public FacebookLoginTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		BufferedReader inBuffer = null;
		// String url = "http://35.2.227.254:5000/spots";
		String url = params[0];
		String result = "fail";
		try {

			// Get info from params
			user_id = params[1];
			access_token = params[2];
			expire = params[3];

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("user_id", user_id));
			postParameters.add(new BasicNameValuePair("access_token",
					access_token));
			postParameters.add(new BasicNameValuePair("expires", expire));

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);

			request.setEntity(formEntity);
			HttpResponse r = httpClient.execute(request);

			result = EntityUtils.toString(r.getEntity());

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
		//Toast.makeText(context, "RESPONSE = " + response, Toast.LENGTH_SHORT)
		//		.show();
		// Toast.makeText(context, "user_id = " + user_id + " access_t = " +
		// access_token + " exp = " + expire, Toast.LENGTH_LONG).show();
		JSONObject resp;
		JSONObject user;
		String akey = "";
		String ukey = "";
		SharedPreferences hubbaprefs = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
		SharedPreferences.Editor hubbaprefsEditor = hubbaprefs.edit();
		try {
			resp = new JSONObject(response);
			user = resp.getJSONObject("user");

			akey = resp.getString("akey");
			ukey = user.getString("ukey");
			//hubbaprefsEditor.clear();
			
			// Save our keys
			hubbaprefsEditor.putString("akey", akey);
			hubbaprefsEditor.putString("ukey", ukey);
			hubbaprefsEditor.commit();
			
			Log.i("Facebook Login:", "uid = " + hubbaprefs.getString("fb_user_id", "") +
					"\naccess_token = " + hubbaprefs.getString("fb_access_token", "") +
					"\nexpire = " + hubbaprefs.getString("fb_expire", ""));
			Log.i("Our Login:", "ukey = " + hubbaprefs.getString("ukey", "") + "\nakey = " + hubbaprefs.getString("akey", ""));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "Error with akey/ukey in json, mkay", Toast.LENGTH_LONG).show();
		}

		// Toast.makeText(context, "user_id = " + user_id + " access_t = " +
		// access_token + " exp = " + expire, Toast.LENGTH_LONG).show();
		
		/*
		// Sanity check TODO delete this
		ukey = hubbaprefs.getString("ukey", "");
		akey = hubbaprefs.getString("akey", "");
		Toast.makeText(context, "FB LOGIN: ukey = " + ukey + "\nakey = " +
				akey, Toast.LENGTH_LONG).show();
		Log.i("SHEEE", "ukey = " + hubbaprefs.getString("ukey", "") + "\nakey = " + hubbaprefs.getString("akey", ""));
		*/
		
		 Toast.makeText(context, "logged in", Toast.LENGTH_LONG).show();
	}
}