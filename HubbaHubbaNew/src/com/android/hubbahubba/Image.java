package com.android.hubbahubba;

public class Image {
	private int ups;
	private int downs;
	private String photog;
	private String rider;
	private int spotID;
	
	/* Getters */
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
	
	/* Public constructor */
	public Image(int ups, int downs, String photog, String rider, int spotID){
		this.ups = ups;
		this.downs = downs;
		this.photog = photog;
		this.rider = rider;
		this.spotID = spotID;
	}
}
