package com.adams.hubbadb;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

public class Spot {
	
	
	private int mId;
	private String mName;
	private String mType;
	private int mLat;
	private int mLong;
	private int mRating;
	private int mDiff;
	private int mLevel;
	private String mComments;
	private String mImage;
	
	//=====================================================
	//   Getters/Setters
	//=====================================================
	
	/*
	 * @return SpotId of this spot
	 */
	public int getId() {
		return mId;
	}
	
	/*
	 * @param spotId set this spots Id
	 */
	public void setId(int spotId) {
		this.mId = spotId;
	}
	
	/*
	 * @return name of this spot
	 */
	public String getName() {
		return mName;
	}
	
	/*
	 * @param name of this spot
	 */
	public void setName(String spotName) {
		this.mName = spotName;
	}
	
	/*
	 * @return type of spot
	 */
	public String getType() {
		return mType;
	}
	
	/*
	 * @param type of spot
	 */
	public void setType(String type) {
		this.mType = type;
	}
	
	/*
	 * @return the Latitude of the Spot
	 */
	public int getLat() {
		return mLat;
	}
	
	/*
	 * @param Latitude of the spot
	 */
	public void setLat(int latitude){
		this.mLat = latitude;
	}
	
	/*
	 * @return the Longitude of the Spot
	 */
	public int getLong() {
		return mLong;
	}
	
	/*
	 * @param Longitude of the spot
	 */
	public void setLong(int longitude) {
		this.mLong = longitude;
	}
	
	/*
	 * @return the Rating of the spot
	 */
	public int getRating() {
		return mRating;
	}
	
	/*
	 * @param the Rating of the spot
	 */
	public void setRating(int rating) {
		this.mRating = rating;
	}
	
	/*
	 * @return the difficulty of the spot
	 */
	public int getDiff() {
		return mDiff;
	}
	
	/*
	 * @param difficulty of the spot
	 */
	public void setDiff(int diff){
		this.mDiff = diff;
	}
	
	
	/*
	 * @return the 50 level of the spot
	 */
	public int getLevel() {
		return mLevel;
	}
	
	/*
	 * @param level of the spot
	 */
	public void setLevel(int level) {
		this.mLevel = level;
	}
	
	
	/*
	 * @return the comments of the spot
	 */
	public String getComments() {
		return mComments;
	}
	
	/*
	 * @param comments of the spot
	 */
	public void setComments(String comment){
		this.mComments = comment;
	}
	
	/*
	 * @return the imageURI of an image
	 */
	public String getImage() {
		return mImage;
	}
	
	/*
	 * @param imageUri of an image of the spot
	 */
	public void setImage(String image){
		this.mImage = image;
	}

	
	
	//==========================================
	//    BASIC MODEL / OBJECT IMPLEMENTATIONS
	//==========================================
	
	public List<NameValuePair> getRequestParams() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		return params;
	}
	
	/*
	 * Create / update the Model / Object from a JSON object
	 */
	public static void createFromJson() {}
	
	/*
	 * Checks to see if the model/object already exists in the database
	 * based on the unique ID of that model/object class. 
	 * @return True if there is alrady a row in the database that matches
	 * that id. False otherwise.
	 */
	public static boolean alreadyExists(String id){
		return false;
	}
	
	/*
	 * Update an existing Model/object with new data.
	 * @return the Model/Object
	 */
	public static Spot updateModel() {
		return null;
	}
	
	/*
	 * Create a new Model/Object
	 * @return The New Model/Object
	 */
	public static Spot createModel() {
		return null;
	}
	
	
}
