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

	public static User findUser(String name, String pass){

		//if user is found in database
		//	return new user
		// else 
		return null; //or throw exception
	}

	private User(String name, String pass, int ukey, int akey){
		this.name = name;
		this.pass = pass;
		this.ukey = ukey;
		this.akey = akey;
	}

}
