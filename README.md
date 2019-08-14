### Coding ###
1. Create an application to use 5 systems broadcasts
2. Create a foreground service with notification. Clicking on the notification will stop the foreground music. (You can just push your changes to your previous “services” homework for this.
3. Create an IntentService to create a list of random objects (The objects should have atleast 4 fields including an image). Populate the recyclerView in the same activity which starts the intent service. Pass the data using a broadcast receiver.
4. Use the AlarmManager to send a notification after 5 secs on clicking each list item. The notification should have the object that was clicked on
5. Complete the work manager code lab:  https://codelabs.developers.google.com/codelabs/android-workmanager/#0

### Research ###
1. What is the difference in an started service and a bound service?
* Started Service:
	* Launched by other application components (such as activity or broadcast receiver).
	* Potentially run indefinitely in the background unless the service is stopped or destroyed by the Android runtime system in order to free up resources.
* Bound Service:
	* Returns results or permits interaction with the component that launched it.
	* Through implementation of interprocess communication, interaction can take place across process boundaries.
	* bindService() called to bind app component to it.
	* Offers client-server interface that allows components to interact with the service.
2. Define what a observer/ subscriber relation is.
	* It is a one-to-many relation where the observer can broadcast messages about changes or actions.
	* Subscribers that are subscribed to specific events will receive messages for those events as the observer sends them.
3. Define what a client/server relation is.
	* A distributed app structure that partitions tasks or workloads between providers of a resource or service, which are the servers, service requesters, which are the clients.
4. Define each of the HTTP verbs
* POST - Create new resources.
* GET - Read or Retrieve a representation of a resource.
* PUT - Updating or replacing a resource.
* PATCH - Updating/Modifying part of a resource.
* DELETE - Deletes a resource.
5. Describe at least one use for each service (Foreground, Background, Intent, Bound)
* Foreground - Performs some operation that is noticable to the user such as playing music in the foreground.
* Background - performs an operation that isn't direction noticed by the user, such as long running queries with a database.
* Intent - used for long tasks that require no communication to the Main Thread such as downloading a big file from the internet.
* Bound - when an app component binds to a service to provide a client-server interface. An example is a music app may want to run indefinitely to play music so when the user leaves the app, the music is still playing.
