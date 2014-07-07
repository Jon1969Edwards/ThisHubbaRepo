package com.android.hubbahubba;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class Spot {

    /**
     * ===========================================
     * Spot Functions
     * ===========================================
     */

    public static boolean alreadyExists(double lat, double lon) {
        // TODO - checks if there is a spot within N feet of the
        // Spot someone is trying to add -- false if none there
        return false;
    }

    // TODO: make these private
    public static String IP = "http://10.0.0.44:5000";
    //public static String IP = "http://192.168.1.41:5000";
    //private static String IP = "http://35.2.211.107:5000";
    public static String IPD = "http://hubba-api.herokuapp.com";

    public static void getListOfSpots(ListView listView, HubbaAdapter dataAdapter,
                                      ArrayList<HashMap<String, String>> SpotsArray, Context c) {
        new PopulateListTask(listView, dataAdapter, SpotsArray, c).execute(IPD + "/spots");
    }

    public static void getListOfUsers(ListView listView, HubbaUserAdapter dataAdapter,
                                      ArrayList<HashMap<String, String>> UsersArray, Context c) {
        new PopulateUserListTask(listView, dataAdapter, UsersArray, c).execute(IPD + "/users");
    }

    public static void updateListOfSpots(ListView listView, HubbaAdapter dataAdapter,
                                         ArrayList<HashMap<String, String>> SpotsArray, Context c, String type) {
        new UpdateListTask(listView, dataAdapter, SpotsArray, c, type).execute(IPD + "/spots");
    }

    public static void getCommentsBySpotID(ListView listView, HubbaCommentAdapter dataAdapter,
                                           ArrayList<HashMap<String, String>> CommentsArray, Context c, String spot_id) {
        new PopulateCommentsListTask(listView, dataAdapter, CommentsArray, c).execute(IPD + "/spots/" + spot_id + "/comments");
    }

    public static void getPhotosBySpotID(GridView gridView, HubbaGridAdapter dataAdapter,
                                         ArrayList<HashMap<String, String>> imagesArray, Context c, String spot_id) {
        new GetSpotImagesTaskTwo(gridView, dataAdapter, imagesArray, c).execute(IPD + "/spots/" + spot_id + "/photos");
    }

    /*
    public static void getTopPhotoBySpotID(Context c, String spot_id, Activity activity){
        new GetSpotTopImageTask(activity, c).execute(IPD + "/spots/" + spot_id + "/top_photo");
    }
    */
    public static void addSpotByLatLon(Context context, String name, String lat,
                                       String lon, String type, boolean isPrivate, String overall, String difficulty,
                                       String bust, String text, String imageURI) {

        // get ukey and akey from shared preferences
        SharedPreferences preferences = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);

        String ukey = preferences.getString("ukey", "");
        String akey = preferences.getString("akey", "");

        String is_private = "False";
        if (isPrivate == true) {
            is_private = "True";
        }

        String url = IPD + "/spots";
        try {
            new AddSpotTask(context).execute(new String[]{url, name, lat, lon, type, ukey, akey, is_private,
                    overall, difficulty, bust, text, imageURI}).get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Toast.makeText(context, "InterruptedException thrown", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (ExecutionException e) {
            Toast.makeText(context, "ExecutionException thrown", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (TimeoutException e) {
            Toast.makeText(context, "TimeoutException thrown", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public static void deleteSpotByID(Context context, String spot_id) {

        // get ukey and akey from shared preferences
        SharedPreferences preferences = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);

        String ukey = preferences.getString("ukey", "");
        String akey = preferences.getString("akey", "");

        String url = IPD + "/spots/" + spot_id;
        new DeleteSpotTask(context).execute(new String[]{url, ukey, akey});
    }

    public static void addComment(Context context, String text, String overall, String difficulty, String bust, String spot_id) {

        // get ukey and akey from shared preferences
        SharedPreferences preferences = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);

        // TODO FIGURE OUT HOW TO GET USERNAME FROM FB
        String uname = "robsmall";
        String ukey = preferences.getString("ukey", "");
        String akey = preferences.getString("akey", "");

        String url = IPD + "/spots/" + spot_id + "/comments";
        new AddCommentTask(context).execute(new String[]{url, uname, text, overall, difficulty, bust, ukey, akey});
    }

    public static void addImage(Context context, String spot_id, String rider, String imageURI) {

        // get ukey and akey from shared preferences
        SharedPreferences preferences = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);

        // TODO FIGURE OUT HOW TO GET USERNAME FROM FB
        String ukey = preferences.getString("ukey", "");
        String akey = preferences.getString("akey", "");

        String url = IPD + "/spots/" + spot_id + "/photos";
        new AddImageTask(context).execute(new String[]{url, rider, imageURI, ukey, akey});
    }

    /*
    public static void getSpotInfoByID(Activity activity, String id, Context c){
        new GetSpotInfoTask(activity, c).execute(IPD + "/spots/" + id);
    }
    */
    public static Spot[] getAllSpots(GoogleMap map, Context c) {
        //new PopulateMapTask(c).execute("http://hubba.david-app.com/spots");
        new PopulateMapTask(map, c).execute(IPD + "/spots");
        return null;
    }

    public static Spot[] getSpotsByType(GoogleMap map, Context c, String type) {
        //new PopulateMapTask(c).execute("http://hubba.david-app.com/spots");
        new UpdateMapTask(map, c, type).execute(IPD + "/spots");
        return null;
    }
}