package com.android.hubbahubba;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PopulateCommentsListTask extends AsyncTask<String, String, String> {

    private Context context;
    private ArrayList<HashMap<String, String>> commentsArray;
    private HubbaCommentAdapter dataAdapter;
    private ListView listView;


    //in constructor:
    public PopulateCommentsListTask(ListView listView, HubbaCommentAdapter dataAdapter,
                                    ArrayList<HashMap<String, String>> commentsArray, Context context) {

        this.context = context;
        this.commentsArray = commentsArray;
        this.dataAdapter = dataAdapter;
        this.listView = listView;
    }

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        HttpGet request = new HttpGet(uri[0]);

        // get ukey and akey from shared preferences
        SharedPreferences hubbaprefs = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
        String ukey = hubbaprefs.getString("ukey", "");
        String akey = hubbaprefs.getString("akey", "");

        //Toast.makeText(context, "ukey = " + ukey + "\nand akey = " + akey, Toast.LENGTH_LONG).show();

        // set header and params
        // set header and params
        String source = ukey + ":" + akey;
        request.setHeader("Authorization", "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));
        try {
            response = httpclient.execute(request);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else {
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
            responseString = "CPE";
        } catch (IOException e) {
            //TODO Handle problems..
            responseString = "IOE";
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        //Toast.makeText(context, "Result From List: " + result, Toast.LENGTH_LONG).show();

        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray comments = jObject.getJSONArray("comments");

            for (int i = 0; i < comments.length(); i++) {
                HashMap<String, String> commentMap = new HashMap<String, String>();
                JSONObject comment = comments.getJSONObject(i);

                JSONObject user = comment.getJSONObject("user");

                String text = comment.getString("text");
                String difficulty = comment.getString("difficulty");
                String overall = comment.getString("overall");
                String bust = comment.getString("bust");

                // TODO: GET THE REST FROM THE DB/ FB

                commentMap.put("uname", user.getString("display_name"));
                commentMap.put("text", text);
                commentMap.put("overall", overall);
                commentMap.put("difficulty", difficulty);
                commentMap.put("bust", bust);

                commentsArray.add(commentMap);
            }
        } catch (JSONException e) {
            Toast.makeText(context, "JSON exception thrown in grabbing comments array", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        dataAdapter = new HubbaCommentAdapter(context, commentsArray, R.layout.spot_page_comments);
        listView.setAdapter(dataAdapter);
    }
}