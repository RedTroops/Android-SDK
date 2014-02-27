<p align="center">
<img src="http://www.redtroops.com/images/RedTroopsLogo.png" alt="RedTroops" width="150">
</p>

#RedTroops SDK 1.0 for Android

**Requirements: Android 2.3.3+ (API 10)**

###Getting Started

RedTroops SDK 1.0 currently features Push Notifications, HTML5/Image Popups, and More Page - Banners. In order to have the Push Notifications ready for RedTroops, you must set up Google Cloud Messaging. 

###Setting Up Google Cloud Messaging (Push Notifications)

Please refer to these instructions by Google from developer.android.com. Instead of obtaining an Android key, select Server Key (No need to modify any Server Key options):
http://developer.android.com/google/gcm/gs.html

A Project Number (Sender ID) will be generated which you will use to set up the SDK, store it.

Next, you have to set up Google Play Services. Please refer to these instructions:
http://developer.android.com/google/play-services/setup.html

You must have now Google Play Services library in your project. You must have a project number. There should be no errors while compiling.

Android Support Library must be added. This can be done by right-clicking on your project → Android Tools → Add Support Library. Android Private Libraries must be checked in Order and Export.


###Setting Up RedTroops SDK 1.0 In Your Project

Follow the steps below to get your RedTroops SDK 1.0 running:

1) Download the SDK from RedTroops' website.

2) Right-click on your project from the Package Explorer in Eclipse → Build Path → Configure Build Path.

3) Click on Add External JARs, and choose RedTroopsSDK.

4) Go to Order and Export tab, and place a check for RedTroopsSDK.

5) Edit your manifest as follows:

Add the following permissions (Mandatory), change \<PACKAGE-NAME> into your app's package name:

```xml
	<!-- Permissions for RedTroops SDK-->
	<uses-permission android:name="android.permission.INTERNET"/>
  	<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
    
      <!-- GET_ACCOUNTS permission is only needed if the minSdkVersion is lower
		than 14 (4.0.4), you may remove it otherwise -->
	<uses-permission android:name="android.permission.GET_ACCOUNTS"/>
    
	<!-- End of Permissions for RedTroops SDK-->
```

In application tag, add the following activities, receiver, service, and meta-data (Mandatory):

```xml
 	<!-- RedTroops SDK (MANDATORY)-->
       <activity android:name="com.RedTroops.RedTroopsSDK.RedTroopsPopupActivity"
            android:theme="@android:style/Theme.Black.NoTitleBar" />
        <activity android:name="com.RedTroops.RedTroopsSDK.RedTroopsMorePageActivity" 
            android:screenOrientation="portrait"/>
    
        <!-- GCM Receiver for RedTroops SDK -->
        <receiver
            android:name="com.RedTroops.RedTroopsSDK.gcm.GcmBroadcastReceiver"
            android:permission="com.google.android.c2dm.permission.SEND" >
            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />

                <category android:name="<PACKAGE-NAME>" />
            </intent-filter>
        </receiver>
        
        <service android:name="com.RedTroops.RedTroopsSDK.gcm.GcmIntentService" />
        
            <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />
            
        <!-- End of RedTroops SDK (MANDATORY)-->
```

6) Go to your assets folder, and create a new file. Name it “redtroops.properties”. Add the following keys and their corresponding values. For example:
```
	gcm_sender_id=330435028307
	api_key=R0mLGV9uDFq875yT26L4omko27fYZ8nG
	app_id=4
```

gcm_sender_id is the Project Number that you obtained from Google Cloud Console when you created the project.


api_key is the API key that you obtained from RedTroops developer website when you added the application.


app_id is the application id. You can obtain it from the address bar when you are editing the App.


7) In your main activity's OnCreate, call:
```java
	RedtroopsSDK.getInstance(this).init(initFinishedListener);
```
Where initFinishedListener is a listener to declare as follows:
```java	
private initFinishListener initFinishedListener = new initFinishListener() {
	
		@Override
		public void onSuccess() {
			// TODO Do on init success. Most probably showHTML5ImagePopup();
		}
		
		@Override
		public void onFail() {
			// TODO Do on init failure
		}
	};
```
8) Whenever you want to show an HTML5/Image popup, call:
```java
	RedTroopsSDK.getInstance(this).showHTML5ImagePopup();
```
It is preferred to call this in initFinishedListener's onSuccess() so that it is made sure that the initialization has finished.

9) Whenever you want to show the more page, call:
```java
	RedTroopsSDK.getInstance(this).showMorePage();
```
10) To end your session, add the following to your last activity's onDestroy:
```java
	RedTroopsSDK.getInstance(this).endSession();
```
This should only be called once after each app run when the user is no longer using the app.

11) Optional: By default the icon for Push Notification is “ic_launcher”, you may change it by calling:
```java
	RedTroopsSDK.getInstance(this).setPushNotificationIcon("ic_launcher");
```
The icon must be in any drawable folder.

###Important Notes

1. When creating a Push Notification, a custom sound can be specified. The custom sound has to be in res/raw folder. The custom sound name should not contain the file type. For example, the proper custom sound name to set is “c_4” for the RedTroopsSDK-Test app. If the custom sound name was invalid, or if the custom sound was not found, no sound will be played.

2. The default icon for the Push Notification is ic_launcher. If a custom icon was not set using setPushNotificationIcon and ic_launcher was not found, the Push Notification will not appear.

3. The user will not be notified if Google Play Services app was not installed on their device.

4. Make sure that you clean and build your project each time you add a library or change settings of Order and Export tab.

5. The test app will show errors if RedTroops SDK was not added. This can be done by changing the Java Build Path as documented in Setting Up RedTroops SDK In Your Project section.

6. If you were to have an error at android:configChanges="orientation|keyboardHidden|screenSize", set android:targetSdkVersion to at least api level 13. Clean and rebuild. If problem still persists, right-click on your project → Properties → Android → set Project Build Target to at least API level 13. Clean and rebuild.

7. init must be called before any other method. If setPushNotificationIcon was called before init then this error will be logged:
setPushNotificationIcon: Error setting Push Notification icon

8. If another GCM intent service is to be used along side the RedTroops SDK intent service, they can be differentiated by getting the boolean “sdk” which is true for RedTroops SDK push notifications. An example of how to implement this safely:
```java
 	if((extras.containsKey("sdk")&&
	   !Boolean.parseBoolean(extras.getString("sdk")))||
	   !extras.containsKey("sdk"))
	 //Handle the non-sdk intent.

```

**Refer to the test app RedtroopsSDK-Test that is available alongside the SDK.**
