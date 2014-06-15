ThisHubbaRepo
=============

Android mobile application using the Google Maps API, Picasso, OkHttp, and ActionBarSherlock. Our backend is a python API using mongoDB with mongoengine. If you're interested in working on the iOS version of HubbaHubba email me at robsmall@umich.edu and pull the iOS code to get started!

// TODO For Beta Release:
- Quit activity when returning from mail
- Comment out Delete Endpoint
- Cannot back out of app
- Add correct error messages instead of returning json
- Take out all debug messages

//BUGS:
- ADD SPOT ONLY IF LOGGED IN
- Change type in map view then click
- change spot in listview then click on spot. crashes.
- Eliminate race condition with re-auth on post requests
- Not get /photos so much (firing off too many times)
	- Moved to onResume()... Needs to have a var passed down
	  indicating that a photo has been added to refresh
	  - ** THIS MAY BE DONE **

// WishList
- ADD SPOT ONLY IF LOGGED IN
- *** Dont refresh photos when photo not added***
	- resultcode code there but doesnt work coming from the task
- Dont show users to add to spot if already on access list
- *** ADD TYPE INTO SPOT ROWS ***
- change spinner on listview, go to map view, come back to list view. Spinner reset.
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