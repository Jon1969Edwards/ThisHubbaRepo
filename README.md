ThisHubbaRepo
=============

Android mobile application using the Google Maps API, Picasso, OkHttp, and ActionBarSherlock. Our backend is a python API using mongoDB with mongoengine. If you're interested in working on the iOS version of HubbaHubba email me at robsmall@umich.edu and pull the iOS code and to started! 

// TODO For Beta Release:
- images rider field (user or string)
- Make sure users can only rate photos/spots and comments once
- ** Add login to menu button
- Quit activity when returning from mail
- ** when spot added ratings appear to be 0 until refresh **
- videos along with photos?
- Delete endpoints Only for Super Users
- Move code around to be more easily accessible
- Photo needs to be returned with the spot info
- Better loading image for placeholders/no images in spot


// TODO
- check the post to /me when resuming activities and if its an error repost to /login/facebook
- Make sure all old code is taken out
- USE http://square.github.io/okhttp/ for get requests other than images

//BUGS:
- ** when spot added ratings appear to be 0 until refresh **
- Make sure photo is posted when adding a spot (s3 error?)
- Eliminate race condition with re-auth on post requests

// WishList
- Start using 9patch images (from android or fb and color them in photoshop) for things!
- sliders for numbers on ratings for spots
- Redo continue and backwards buttons to look like the top bar but on the bottom bar
- Have flag for allowing others to also share it (Cred: freeBurg)


// TODO: FOR BACKEND
- Make sure users can only rate photos/spots once
- rider field (user or string)
- add up and down ratings for images
- videos along with photos?
- Delete endpoints Only for Super Users

// Sharing Notes - 
- Might need usernames to be able to share with eachother. Search by name.
Just list of picture / name username
- May need to add a friends list to make this easier
- user pages