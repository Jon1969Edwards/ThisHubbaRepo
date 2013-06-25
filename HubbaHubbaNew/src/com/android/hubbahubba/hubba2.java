package com.android.hubbahubba;

public class hubba2 {
	
	// ======================= THIS GOES IN HubbbaDBAdapter.Java ==================================//
		/*
	 	This function allows you to search the DB by Lat/Long (as doubles) and return 
	 	a cursor to the spot in the db. I will give an example of how to use the cursor
	 	and close the db after.
	 	*/
	 	/*
	 	public Cursor fetchSpotByLatLong(double latitude, double longitude) throws SQLException 
		{
	 	 	Cursor mCursor = null;

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
			
	// ================== THIS IS AN EXAMPLE OF HOW YOU CALL IT==============================//



			//declare this outside of class
			HubbaDBAdapter dbHelper;


			//Use this code inside of the class wherever you need to make the call.
			//My example sets a textView's text as the name of the spot.
			//Use that as an example for what you want to do.
			//It should be pretty easy to use, but if you need any help
			//just let me know.
			
			/*
			c.getString(1) - returns Name
			c.getString(2) - returns Type
			c.getString(3) - returns Latitude
			c.getString(4) - returns Longitude
			c.getString(5) - returns Rating
			c.getString(6) - returns Difficulty
			c.getString(7) - returns police level
			c.getString(8) - returns comment
			c.getString(9) - returns image URI		
			*//*
			dbHelper = new HubbaDBAdapter(this);
			dbHelper.open();
			Cursor c;
			c = dbHelper.fetchSpotByLatLong(100.001, 100.001);
			
			if (c.moveToFirst()) 
			{
				do 
				{
					String latLong = c.getString(3) + " , " + c.getString(4); // this example retrieves the spot's lat and long and makes a string out of it.
					String resultName = c.getString(1); // result name is now equal to the "Name" of the spot.
					mText.setText(resultName);
				} while (c.moveToNext());
			}
			else // this else clause is optional. I just used it for error checking.
			{
				Context context = getApplicationContext();
				CharSequence text = "db call failed.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
			dbHelper.close(); // don't forget to close it after you're done using it. (if you are going to make more calls to Db after, just reopen it.)
		
		}
*/


}
