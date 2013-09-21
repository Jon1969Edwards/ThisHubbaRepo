package com.android.hubbahubba;

import java.text.DecimalFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class HubbaDBAdapter {
 
	 public static final String KEY_ROWID = "_id";
	 public static final String KEY_NAME = "name";
	 public static final String KEY_TYPE = "type";
	 public static final String KEY_LAT = "latitude";
	 public static final String KEY_LONG = "longitude";
	 public static final String KEY_RATING = "rating";
	 public static final String KEY_DIFF = "difficulty";
	 public static final String KEY_LEVEL = "level";
	 public static final String KEY_COMMENTS = "comments";
	 public static final String KEY_IMAGE = "image";
	 
	 private static final String TAG = "HubbaDBAdapter";
	 private DatabaseHelper mDbHelper;
	 private SQLiteDatabase mDb;
	 
	 private static final String DATABASE_NAME = "spots_DB";
	 private static final String SQLITE_TABLE = "Spots";
	 private static final int DATABASE_VERSION = 1;
	 
	 private final Context mCtx;
 
	 private static final String DATABASE_CREATE =
		  "CREATE TABLE if not exists " + SQLITE_TABLE 
		  + " (" 
		  + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
		  + KEY_NAME + " TEXT NOT NULL,"
		  + KEY_TYPE + " TEXT NOT NULL,"
		  + KEY_LAT + " REAL," 
		  + KEY_LONG + " REAL,"
		  + KEY_RATING + " INTEGER," 
		  + KEY_DIFF + " INTEGER," 
		  + KEY_LEVEL + " INTEGER," 
		  + KEY_COMMENTS + " TEXT,"
		  + KEY_IMAGE + " TEXT"
		   +")";
		 
	 private static class DatabaseHelper extends SQLiteOpenHelper {
 
		 DatabaseHelper(Context context) {
			 super(context, DATABASE_NAME, null, DATABASE_VERSION);
		 }
 
 
		 @Override
		 public void onCreate(SQLiteDatabase db) {
			 Log.w(TAG, DATABASE_CREATE);
			 db.execSQL(DATABASE_CREATE);
		 }
 
		 @Override
	 	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 	Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
		 			+ newVersion + ", which will destroy all old data");
		 	db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
		 	onCreate(db);
	 	}
		 
	 }
 
 	public HubbaDBAdapter(Context ctx) {
 		this.mCtx = ctx;
 	}
 
 	public HubbaDBAdapter open() throws SQLException {
 		mDbHelper = new DatabaseHelper(mCtx);
 		mDb = mDbHelper.getWritableDatabase();
 		return this;
 	}
 
 	public void close() {
 		if (mDbHelper != null) {
 			mDbHelper.close();
 		}
 	}
 
 	public long createSpot(String name, String type, double latitude, 
 		 	double longitude, int rating, int difficulty, int level, String comments, String imageURI) {
 		 		
 		 		
 		 	//latitude
 		 	DecimalFormat df = new DecimalFormat("##.######");
 		 	 latitude = Double.parseDouble(df.format(latitude));
 		 	 	
 		 	 //longitude
 		 	 DecimalFormat df2 = new DecimalFormat("###.######");
 		 	 longitude = Double.parseDouble(df2.format(longitude));
 		 
 		 	ContentValues initialValues = new ContentValues();
 		 	initialValues.put(KEY_NAME, name);
 		 	initialValues.put(KEY_TYPE, type);
 		 	initialValues.put(KEY_LAT, latitude);
 		 	initialValues.put(KEY_LONG, longitude);
 		 	initialValues.put(KEY_RATING, rating);
 		 	initialValues.put(KEY_DIFF, difficulty);
 		 	initialValues.put(KEY_LEVEL, level);
 		 	initialValues.put(KEY_COMMENTS, comments);
 		 	initialValues.put(KEY_IMAGE, imageURI);
 		 
 		 	return mDb.insert(SQLITE_TABLE, null, initialValues);
 	}
 
 
 	public boolean deleteAllSpots() {
 
 		int doneDelete = 0;
 		doneDelete = mDb.delete(SQLITE_TABLE, null , null);
 		Log.w(TAG, Integer.toString(doneDelete));
 		
 		return doneDelete > 0;
 
 	}
 
 	public Cursor fetchSpotByName(String inputText) throws SQLException {
 		Log.w(TAG, inputText);
 		Cursor mCursor = null;
  
 		if (inputText == null  ||  inputText.length () == 0)  {
 			mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_TYPE, 
 						KEY_LAT, KEY_LONG, KEY_RATING, KEY_DIFF, KEY_LEVEL, KEY_COMMENTS, KEY_IMAGE}, 
 					null, null, null, null, null);
 
 		}
 		else {
 			mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_TYPE,  
 						KEY_LAT, KEY_LONG, KEY_RATING, KEY_DIFF, KEY_LEVEL, KEY_COMMENTS, KEY_IMAGE}, 
 					KEY_NAME + " like '%" + inputText + "%'", null,
 					null, null, null, null);
 		}
  
 		if (mCursor != null) {
 			mCursor.moveToFirst();
 		}
 		
 		
 		return mCursor;
 	}
 
 	public Cursor fetchAllSpots() {
 
 		Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_TYPE, 
 						KEY_LAT, KEY_LONG, KEY_RATING, KEY_DIFF, KEY_LEVEL, KEY_COMMENTS, KEY_IMAGE}, 
 				null, null, null, null, null);
 
 		if (mCursor != null) {
 			mCursor.moveToFirst();
 		}
  
 		return mCursor;
 	}
 	
 	public Cursor queryAll(int nameId) {
 		
 		System.out.println(nameId + " is the ID inputted");
        String[] cols = {KEY_ROWID, KEY_NAME, KEY_TYPE, KEY_LAT, KEY_LONG, KEY_RATING, 
        		KEY_DIFF, KEY_LEVEL, KEY_COMMENTS, KEY_IMAGE};
        Cursor mCursor;
        
        /*if ( nameId == 0)  {
 			mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
 					KEY_NAME, KEY_TYPE, KEY_LAT, KEY_LONG, KEY_RATING, KEY_DIFF, KEY_LEVEL}, 
 					null, null, null, null, null);
 
 		}
        else {*/
        
        	 mCursor = mDb.query(SQLITE_TABLE, cols,
        			KEY_ROWID + "=" + nameId, null, null, null, null);
        	//mCursor =  mDb.rawQuery("select * from " + SQLITE_TABLE + " where " + KEY_ROWID + "=" + nameId  , null);
            
        //}
        return mCursor;
 
    }
 	
 	public long updateSpot(int rowId, String name, String type, String rating, 
 			String difficulty, String level, String comments, String imageURI) {
        ContentValues contentValues = new ContentValues();
        
        // JIMMY: THESE WERE NEVER USED SO I COMMENTED THEM OUT... WAS THERE A REASON FOR THESE?
        /*
        int intRating, intDiff, intLevel;
        intRating = Integer.valueOf(rating);
        intDiff = Integer.valueOf(difficulty);
        intLevel = Integer.valueOf(level);
        */
        
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_TYPE, type);
        contentValues.put(KEY_RATING, rating);
        contentValues.put(KEY_DIFF, difficulty);
        contentValues.put(KEY_LEVEL, level);
        contentValues.put(KEY_COMMENTS, comments);
        contentValues.put(KEY_IMAGE, imageURI);
        
        
        long val = mDb.update(SQLITE_TABLE, contentValues,
                KEY_ROWID + "=" + rowId, null);
        return val;
    }
 	
 	 public int deleteSpot(int rowId) {

 		 int val = mDb.delete(SQLITE_TABLE, KEY_ROWID + "=" + rowId, null);
         return val;
     }
 	
 	
 	public void insertSomeSpots() {
 		  createSpot("Drake Skate Park", "skate park", 42.535469, -83.399513, 5, 8, 2, "Drake skate park is awesome", null);
 		  createSpot("Riley Skate Park","skate park", 	42.4400061, -83.396546, 8, 2, 5, "This spot is awesome", null);
 		 }
 	/*
 	This function allows you to search the DB by Lat/Long (as doubles) and return 
 	a cursor to the spot in the db. I will give an example of how to use the cursor
 	and close the db after.
 	*/
 	/*
 	public Cursor fetchSpotByLatLong(double latitude, double longitude) throws SQLException {
 	 	Cursor mCursor = null;
 	  
 	 	if (latitude !=0 && longitude!=0)  {
 	 	mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_TYPE,  
 	 	KEY_LAT, KEY_LONG, KEY_RATING, KEY_DIFF, KEY_LEVEL, KEY_COMMENTS, KEY_IMAGE}, 
 	 	KEY_LAT + " like '%" + latitude + "%' and " + KEY_LONG + " like '%" + longitude + "%'", null,
 	 	null, null, null, null);
 	 	}
 	else
 	 	mCursor.moveToFirst();
 	 	
 	 	
 	 	return mCursor;
 	 	}
 	 */
 	
 		// ======================= THIS GOES IN HubbbaDBAdapter.Java ==================================//
 		/*
 	 	This function allows you to search the DB by Lat/Long (as doubles) and return 
 	 	a cursor to the spot in the db. I will give an example of how to use the cursor
 	 	and close the db after.
 	 	*/
 	 	
 	public Cursor fetchSpotByLatLong(double latitude, double longitude) throws SQLException 
 	{
 	 	 Cursor mCursor = null;

 	 	 //latitude
 	 	 DecimalFormat df = new DecimalFormat("##.######");
 	 	 latitude = Double.parseDouble(df.format(latitude));
 	 	 	
 	 	 //longitude
 	 	 DecimalFormat df2 = new DecimalFormat("###.######");
 	 	 longitude = Double.parseDouble(df2.format(longitude));
 	 	 	
 	 	 if (!Double.isNaN(latitude) && !Double.isNaN(longitude))  
 	 	 {
 		  	mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_TYPE,  
 		  	KEY_LAT, KEY_LONG, KEY_RATING, KEY_DIFF, KEY_LEVEL, KEY_COMMENTS, KEY_IMAGE}, 
 		  	KEY_LAT + " LIKE '%" + latitude + 
 		  	"%' AND " + 
 		  	KEY_LONG + " LIKE '%" + longitude + "%'", null,
 		  	null, null, null, null);
 		 }
 	 	 	
 	 	 return mCursor;
 	 }
}	