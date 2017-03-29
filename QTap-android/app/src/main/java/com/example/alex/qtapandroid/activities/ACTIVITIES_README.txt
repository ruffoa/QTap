# Activities

In this package all of the app's activities can be found. These define the main pages in the application, and are used to control what UI is shown (based on what fragments are used).

## LoginActivity

This activity shows a web view of Solus and allows the user to log into Solus. Upon Solus log in, the URL of the ICS file is found by manually looking for the user's NetID on the page, since the URL contains the NetID. Then, that link i
used to download the ICS file. After this point, the user is logged into the application. The user is given a loading screen to allow for the time to download the file, and parse it into the SQLite database.

## MainTabActivity

