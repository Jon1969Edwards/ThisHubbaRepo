package com.android.hubbahubba;

public class DBAdapter {
	
	/* query DB for spot in immediate area, if no spot return false
	 * else return true
	 */
	public boolean spotExists(double lat, double lon){
		return true;
	}
	
	/* creates a spot given the info for the spot */
	public boolean createSpot(String name, String type, double lat,
							  double lon, int rating, int diff, int bust,
							  String comment, String image){
		
		// If there is a spot there (or around), do not create a new one
		if(spotExists(lat, lon)){
			return false;
		}
		
		/* create new array for comments and images*/
		String[] comments = new String[1];
		comments[0] = comment;
		String[] images = new String[1];
		images[0] = image;
		
		/* TODO - Insert Spot into DB here */
		
		return true;
	}
	
	/* creates a spot given the info for the spot */
	public boolean createUser(String name, String pass){
		
		/* TODO - insert user into the BD here */
		return true;
	}
	
	
}
