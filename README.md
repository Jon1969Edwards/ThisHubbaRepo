ThisHubbaRepo
=============

Android mobile application using the Google Maps API, Picasso, and ActionBarSherlock. We are in the process of phasing out the SQL lite backend for testing for a real, working backend with a python API. Stay Tuned!

// TODO
- Add menu button to the right of the tabs for the actionbar (logout, login, go home, leave feedback, and eventually profile)
- check the post to /me when resuming activities and if its an error repost to /login/facebook
- Make sure all old code is taken out
- Diff markers for adding a spot
- Quit activity when returning from mail
- USE http://square.github.io/okhttp/ for get requests other than images
- Eliminate race condition with re-auth on post requests
- Add info to arrays when posted so you see it when a refresh happens


// WishList
- Start using 9patch images (from android or fb and color them in photoshop) for things!
- sliders for numbers on ratings for spots
- Redo continue and backwards buttons to look like the top bar but on the bottom bar


// TODO: FOR BACKEND
- Make sure users can only rate photos/spots once
- rider field (user or string)
- add up and down ratings for spots
- videos along with photos?
- Delete endpoints