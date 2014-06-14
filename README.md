ThisHubbaRepo
=============

Android mobile application using the Google Maps API, Picasso, OkHttp, and ActionBarSherlock. Our backend is a python API using mongoDB with mongoengine. If you're interested in working on the iOS version of HubbaHubba email me at robsmall@umich.edu and pull the iOS code to get started!

// TODO For Beta Release:
- Add Leave Feedback to menu item (Possibly its own row)
- Quit activity when returning from mail
- Comment out Delete Endpoint
- check the post to /me when resuming activities and if its an error repost to /login/facebook
- Remove search button from menu on listview
- cronjob to keep api awake

//BUGS:
- change spot in listview then click on spot. crashes.
- Eliminate race condition with re-auth on post requests
- Not get /photos so much (firing off too many times)
	- Moved to onResume()... Needs to have a var passed down
	  indicating that a photo has been added to refresh
	  - ** THIS MAY BE DONE **

// WishList
- ** Add login to menu button **
- search in listview and mapview
- add up and down ratings for images
- Make sure all old code is taken out
- Pass "photo" from spot object to spot page activity
	then use it there to load the image. Remove top_photo function.
- Better image placeholder for images
- Move code around to be more easily accessible
- ** when spot added ratings appear to be 0 until refresh **
- USE http://square.github.io/okhttp/ for get requests other than images
- Re-intro delete endpoint and figure out schema for it
- videos along with photos?
- Make images rider field a user object
- Have flag for allowing others to also share it (Cred: freeBurg)
- ** DO SOMETHING WHEN NOT CONNECTED TO THE INTERNET **
    - Don't reload the map info when no internet
    - Don't try to add image, etc...
- Keep map cached
- Animations?
    (android:animateLayoutChanges="true" when setting to gone) 
- Handle re-auth
	- post back to fb, then back to us?
	- button for it?
- Move away from heroku for backend

// Design Changes
- sliders for numbers on ratings for spots
- Redo continue and backwards buttons to look like the top bar but on the bottom bar
- Better buttons/layout
- Woodgrain style?
- Lighter style?

// TODO: FOR BACKEND
- Ratings for images
- add up and down ratings for images
- videos along with photos?
- Comment out Delete endpoints
	- Eventually Only for Super Users (maybe?)

// Sharing Notes - 
- Might need usernames to be able to share with eachother
	- Search by name.
		- Just list of picture / name username
- May need to add a friends list to make this easier
- user pages