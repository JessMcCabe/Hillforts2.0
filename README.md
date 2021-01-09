# Hillforts2.0

### Version 1 (Hillforts Repo) 
Application Concept. 

Develop an application supporting an exploration of Hillforts. 
Users are archaeology students. 
App should contain a list of Hillforts they have been assigned to visit. 
Users visit site, take photos + notes which augment the information on the illfort provided by the app. 
Students can add additional Hillforts - which may be uncharted.

### Version 2
Advance on Version 1.

Persistence and Authentication using Firebase Realtime Database, Storage and Authentication
Refactor to MVP Architecture
Advanced Features to include - 

- Hillfort Rating 
- Mark as Favourite
- Favourites View
- Current Location Detection
- Navigator
- Firebase Authentication
- Firebase Database


# Setup requirements.
Once you have imported the code to android developer studio you will need to update googlemapsapi.xml with your own API Key. YOUR_KEY_HERE

You will also need to connect to firebase using your own app/google-services.json

Connecting To Firebase:

> Go to the firebase console https://firebase.google.com/ and sign in
> Visit your firebase console and select Add Project. Use the default settings.
> Next, choose the Authentication tab and then the Sign-In Method and Enable Email/Password

> In Android Studio - Select Tools, Firebase -> which gets you the Firebase Assistant Menu
> Choose Authentication->Email and Password Then, Connect to Firebase
> Choose the Project you created earlier
> Next, Choose Add Firebase Authentication, Accept the changes

> Next, in the Firebase Assistant Menu, select Realtime Database, and select Save and Retreive Data
> Select the project you created and the select Add the Realtime Database to your app, accept the changes. 


> Next, in the Firebase Assistant Menu, select Storage, and select Upload and download a file with Cloud Storage
> Select the project you created and the select Add Cloud Storage to your app, accept the changes. 

> Retreive your app/google-services.json from Firebase and add it to the App directory of your project.




### Built on: 
Honor 20 Lite Android Physical Device

### Persistence

Firebase
