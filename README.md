ThisHubbaRepo
=============

Android mobile application using the Google Maps API, Picasso, OkHttp, and ActionBarSherlock. Our backend is a python API using mongoDB with mongoengine. If you're interested in working on the iOS version of HubbaHubba email me at robsmall@umich.edu and pull the iOS code to get started!

Contributing
------------
// To mention that is getting fixed
- Messages about logging in (easier to log in)
- Add spot without being logged in taking you to the end

// 0.2.0
- Printing to the screen when clicking on image
- Comments now say name of user
- Faster loading spot info from map/list view

// 0.3.0
- Spot title will only be 1 line
- Map more zoomed out
- New default images
- User list cleaned up
	- Users now have photos for sharing
	- No blank users
- Images no longer crash when switched between quickly

To Do for next release
------------
- ADD SPOT ONLY IF LOGGED IN
- Fix the imagesArray problem for real (associated)
- Not get /photos so much (firing off too many times)
	- Moved to onResume()... Needs to have a var passed down
	  indicating that a photo has been added to refresh
- Some type of demo to start the app the first time
- Spot top image doesnt refresh when you post the first image
- List view not sorted, always changes order.

Wish List
------------
- ** PROXIMITY OF ADDING NEW SPOTS
- ** how far away spots are
- *** ADD TYPE INTO SPOT ROWS ***
- ** when spot added ratings appear to be 0 until refresh **
- ** DO SOMETHING WHEN NOT CONNECTED TO THE INTERNET **
    - Don't reload the map info when no internet
    - Don't try to add image, etc...
- ** Add login to menu button **
	- Maybe a drawer or menu tab for it
- *** Cant go back out of the app
- *** Dont refresh photos when photo not added ***
	- resultcode code there but doesnt work coming from the task
- *** Add type into spot info ***

- ** Look into RXjava (https://gist.github.com/staltz/868e7e9bc2a7b8c1f754)
    for things like lists of images, list of spots, etc. to auto refresh
- ** Images for features **
- multiple features per spot
- no 2 spots in the same location
- Possibly want this: when you start the app go to your nearest location
	- ME: check if you have location enabled for this, otherwise default to something.
- Allow separate ratings vs. comments
- Dont show users to add to spot if already on access list
- change spinner on listview, go to map view, come back to list view. Spinner reset.
- search in listview and mapview
- add up and down ratings for images
- Make sure all old code is taken out
- Move code around to be more easily accessible
- USE http://square.github.io/okhttp/ for get requests other than images
- Re-intro delete endpoint and figure out schema for it
- videos along with photos?
- Make images rider field a user object
- Keep map cached
- Animations?
    (android:animateLayoutChanges="true" when setting to gone) 
- Handle re-auth
	- post back to fb, then back to us?
	- button for it?
- Move away from heroku for backend
- Pads required or not if park

Possible Design Changes
------------
- sliders for numbers on ratings for spots
- Redo continue and backwards buttons to look like the top bar but on the bottom bar
- Better buttons/layout
- Woodgrain style?
- Lighter style?

Todo For Backend
------------
- Ratings for images
- add up and down ratings for images
- videos along with photos?
- Figure out delete endpoint


Sharing Notes
------------
- Might need usernames to be able to share with eachother
	- Search by name.
		- Just list of picture / name username
- May need to add a friends list to make this easier
- user pages