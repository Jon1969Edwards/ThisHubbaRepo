package com.android.hubbahubba;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
	
	// Member variables
	private String name;
	private String pass;
	// private int[] spotsRated;	// ID's of spots the user has rated
	// private int[] imagesRated;	// ID's of images the user has rated
	private int ukey;
	private int akey;
	private int[] favs;
	
	/* Getters */
	public String getName() {
		return name;
	}
	
	public int[] getFavs(){
		return favs;
	}
	
	public int getUkey(){
		return ukey;
	}
	
	public int getAkey(){
		return akey;
	}
	 
	/* SETTER FOR RESETTING PASSWORD */
	public void resetPassword(String uname, String pass, String newPass) {
		// TODO - Check credentials, if authenticated then
		this.pass = newPass;
	}
	
	/* Private constructor */
	private User(String name, String pass, int ukey, int akey, int[] favs){
		this.name = name;
		this.pass = pass;
		this.ukey = ukey;
		this.akey = akey;
		this.favs = favs;
	}
	
	/* 
	 * User Functions
	 */
	
	public static User createUser(String name, String pass){
		// Create user and insert into the DB
		/* TODO - insert user into the DB here */
		return null;
	}
	
	public static User logInUser(String name, String pass){
		// TODO if user is found in database
		// attempt auth
		User user = getUserByName(name);
		
		// Return user on success and null on fail
		return user;
	}
	
	public static User getUserById(int id){
		// TODO if user is found in database
		// return new user, otherwise return null
		return null;	//or throw exception to send error message
	}
	
	public static User getUserByName(String name){

		// TODO if user is found in database
		// return new user and attempt auth
		// else 
		return null; //or throw exception to send error message
	}
	
	private static String IP = "http://10.0.0.44:5000";
    //public static String IP = "http://192.168.1.41:5000";
    //private static String IP = "http://35.2.211.107:5000";
	private static String IPD = "http://hubba-api.herokuapp.com";
	public static final String PREFS_FILE = "com.android.hubbahubba.prefs";
	
	public static void loginToFacebook(Context context, String user_id, String access_token, int expire){
		String url = IPD + "/login/facebook";
		String expire_string = Integer.toString(expire);
		new FacebookLoginTask(context).execute(new String[] {url, user_id, access_token, expire_string}); 
	}
	
	public static void shareSpot(Context context, String spot_id, String fb_user_id){
		
		// get ukey and akey from shared preferences
		SharedPreferences preferences = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);
		
		String ukey = preferences.getString("ukey", "");
		String akey = preferences.getString("akey", "");
				
		String url = IPD + "/spots/" + spot_id + "/share";
		
		// TODO: may not need to pass in the spot_id since it is in the URL
		new ShareSpotTask(context).execute(new String[] {url, ukey, akey, spot_id, fb_user_id});
	}

  public static void checkLoggedIn(Context context, String spot_id) {
    String url = IPD + "/health";

      // get ukey and akey from shared preferences
      SharedPreferences preferences = context.getSharedPreferences(User.PREFS_FILE, Context.MODE_MULTI_PROCESS);

      // TODO FIGURE OUT HOW TO GET USERNAME FROM FB
      String ukey = preferences.getString("ukey", "");
      String akey = preferences.getString("akey", "");

    new CheckLoggedInTask(context, spot_id).execute(new String[] {url, ukey, akey});
  }
}
