package com.android.hubbahubba;

public class User {
	
	private String name;
	private String pass;
	// private int[] spotsRated;	// ID's of spots the user has rated
	// private int[] imagesRated;	// ID's of images the user has rated
	private int ukey;
	private int akey;
	
	/* Getters */
	public String getName() {
		return name;
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
	private User(String name, String pass, int ukey, int akey){
		this.name = name;
		this.pass = pass;
		this.ukey = ukey;
		this.akey = akey;
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
}
