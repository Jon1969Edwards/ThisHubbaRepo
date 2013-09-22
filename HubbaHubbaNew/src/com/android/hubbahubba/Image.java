package com.android.hubbahubba;

public class Image {
	private int ups;
	private int id;
	private int downs;
	private String photog;
	private String rider;
	private int spotID;
	
	/* Getters */
	private int getId(){
		return id;
	}
	private int getRating(){
		return ups / (ups + downs);
	}
	
	private int getSpotID(){
		return spotID;
	}
	
	private String getRider(){
		return rider;
	}
	
	private String getPhotog(){
		return photog;
	}
	
	public static Image findImageById(int id){

		// if image is found in database
		// return new image else
		return null; //or throw exception
	}
	
	/* Public constructor */
	private Image(int ups, int id, int downs, String photog, String rider, int spotID){
		this.ups = ups;
		this.id = id;
		this.downs = downs;
		this.photog = photog;
		this.rider = rider;
		this.spotID = spotID;
	}
	
	/*
	 * Image Functions
	 */
	
	public static Image createImage(String photog, String rider, int spotID){
		
		/* TODO - insert image into DB here with ups and downs = 0 */
		return null;
	}
	
	public static Image getImageByID(int id){
		// TODO
		return null;
	}
	
	public static Image getImageByPhotog(String photog){
		// TODO
		return null;
	}
	
	public static Image getImageByRider(String rider){
		// TODO
		return null;
	}
	
	public static Image[] getImagesBySid(int sid){
		// TODO - returns all the images associated with a spot
		return null;
	}
}