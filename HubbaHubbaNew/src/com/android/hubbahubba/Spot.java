package com.android.hubbahubba;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;


public class Spot {
	
	// Member Variables
	private int sid;
	private String name;
	private String type;
	private double lat;
	private double lon;
	private int rating;
	private int diff;
	private int bust;
	private String[] comments;
	// private String[] mImages; -- Search by spotid
	
	//=====================================================
	//   Getters/Setters
	//=====================================================
	
	/*
	 * @return SpotId of this spot
	 */
	public int getId() {
		return sid;
	}
	
	/*
	 * @return name of this spot
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * @param name of this spot
	 */
	public void setName(String spotName) {
		this.name = spotName;
	}
	
	/*
	 * @return type of spot
	 */
	public String getType() {
		return type;
	}
	
	/*
	 * @return the Latitude of the Spot
	 */
	public double getLat() {
		return lat;
	}
	
	/*
	 * @return the Longitude of the Spot
	 */
	public double getLong() {
		return lon;
	}
	
	/*
	 * @return the Rating of the spot
	 */
	public int getRating() {
		return rating;
	}
	
	/*
	 * @param the Rating of the spot
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	/*
	 * @return the difficulty of the spot
	 */
	public int getDiff() {
		return diff;
	}
	
	/*
	 * @param difficulty of the spot
	 */
	public void setDiff(int diff){
		this.diff = diff;
	}
	
	
	/*
	 * @return the 50 level of the spot
	 */
	public int getBust() {
		return bust;
	}
	
	/*
	 * @param level of the spot
	 */
	public void setBust(int level) {
		this.bust = level;
	}
	
	
	/*
	 * @return the comments of the spot
	 */
	public String getComments() {
		return comments[0];
	}
	
	/*
	 * @param comments of the spot
	 */
	public void setComments(String comment){
		this.comments[0] = comment;
	}
	
	/*
	 * @return the imageURI of an image
	 
	public String[] getImages() {
		return mImages;
	}
	*/
	
	/*
	 * @param imageUri of an image of the spot
	 *//*
	public void setImages(String image){
		this.mImages = image;
	}
	*/
	
	/* Public constructor */
	private Spot(int sid, String name, String type,
				 double lat, double lon, int rating,
				 int diff, int bust, String[] comments,
				 String[] images){
		
		this.sid = sid;
		this.name = name;
		this.type = type;
		this.lat = lat;
		this.lon = lon;
		this.rating = rating;
		this.diff = diff;
		this.bust = bust;
		this.comments = comments;
		//this.mImages = images;
	}
	
	/* ===========================================
	 * Spot Functions
	 * ===========================================
	 */
	
	/* query DB for spot in immediate area, if no spot return false
	 * else return true
	 */
	public static boolean spotExists(double lat, double lon){
		return true;
	}
	
	/* creates a spot given the info for the spot */
	public static Spot createSpot(String name, String type, double lat,
							  double lon, int rating, int diff, int bust,
							  String comment, String image){
		
		// If there is a spot there (or around), do not create a new one
		if(spotExists(lat, lon)){
			// TODO - Or throw
			return null;
		}
		
		/* create new array for comments and images*/
		String[] comments = new String[1];
		comments[0] = comment;
		String[] images = new String[1];
		images[0] = image;
		
		/* TODO - Insert Spot into DB here */
		
		return null;
	}
	
	public static Spot getSpotByID(int sid){
		// TODO
		return null;
	}
	
	public static Spot getSpotByName(String name){
		// TODO
		return null;
	}
	
	public static Spot getSpotByType(String type){
		// TODO
		return null;
	}
	
	public static Spot getSpotByLatLong(double lat, double lon){
		// TODO
		return null;
	}
	
	public static boolean alreadyExists(double lat, double lon){
		// TODO - checks if there is a spot within N feet of the
		// Spot someone is trying to add -- false if none there
		return false;
	}
	
	/* 
	 * For returning more than one spot
	 * This could be used for search functions
	 * or for populating with all spots
	 */
	
	public static Spot[] getSpotsByName(String name){
		// TODO
		return null;
	}
	
	public static Spot[] getSpotsByType(String type){
		// TODO
		return null;
	}
	
	private static String IP = "http://10.0.0.44:5000";
	private static String IPD = "http://hubba-api.herokuapp.com";
	
	public static void getListOfSpots(ListView listView, HubbaAdapter dataAdapter,
			ArrayList<HashMap<String, String>> SpotsArray, Context c){
		//new PopulateMapTask(c).execute("http://hubba.david-app.com/spots");
		new PopulateListTask(listView, dataAdapter, SpotsArray, c).execute(IPD + "/spots");
	}
	
	public static void addSpotByLatLon(Context context, String name, String lat, String lon, String type){
		String url = IPD + "/spots";
		new AddSpotTask(context).execute(new String[] {url, name, lat, lon, type}); 
	}
	
	public static void getSpotInfoByID(Activity activity, String id, Context c){
		new GetSpotInfoTask(activity, c).execute(IPD + "/spots/" + id);
	}
	
	public static Spot[] getAllSpots(GoogleMap map, Context c){
		//new PopulateMapTask(c).execute("http://hubba.david-app.com/spots");
		new PopulateMapTask(map, c).execute(IPD + "/spots");
		return null;
	}
}