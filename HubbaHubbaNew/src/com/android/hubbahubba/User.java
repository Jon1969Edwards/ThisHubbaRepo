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
	//private static String IP = "http://35.2.211.107:5000";
	private static String IPD = "http://hubba-api.herokuapp.com";
	public static final String PREFS_FILE = "com.android.hubbahubba.prefs";
	
	public static void loginToFacebook(Context context, String user_id, String access_token, int expire){
		String url = IP + "/login/facebook";
		String expire_string = Integer.toString(expire);
		new FacebookLoginTask(context).execute(new String[] {url, user_id, access_token, expire_string}); 
	}
}
