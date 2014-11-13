![RedTroops Logo](http://redtroops.com/images/logo.png)

#RedTroops SDK 2.0 for Android

**Requirements: Android 2.3.3+ (API 10)**

###Getting Started

RedTroops SDK 2.0 currently features Push Notifications, and Interstitial ads. In order to have the push notifications ready for RedTroops, you must set up Google Cloud Messaging. 

###Setting Up Google Cloud Messaging (Push Notifications)

Please refer to these instructions by Google from [developer.android.com](developer.android.com). Instead of obtaining an Android key, select Server Key (No need to modify any Server Key options):
[http://developer.android.com/google/gcm/gs.html](http://developer.android.com/google/gcm/gs.html)

A Project Number (Sender ID) will be generated which you will use to set up the SDK, store it.

Next, you have to set up Google Play Services. Please refer to these instructions:
[http://developer.android.com/google/play-services/setup.html](http://developer.android.com/google/play-services/setup.html)

You must have now Google Play Services library in your project. You must have a project number. There should be no errors while compiling.

Android Support Library must be added. This can be done by right-clicking on your project → Android Tools → Add Support Library. Android Private Libraries must be checked in Order and Export.

###Setting Up RedTroops SDK 2.0 In Your Project

Follow the steps below to get your RedTroops SDK 2.0 running:

1) Download the SDK from RedTroops' website.

2) Right-click on your project from the Package Explorer in Eclipse → Build Path → Configure Build Path.

3) Click on Add External JARs, and choose RedTroopsSDK.

4) Go to Order and Export tab, and place a check for RedTroopsSDK.

5) Edit your manifest as follows:

Add the following permissions (Mandatory):

```xml
	<!-- Permissions for RedTroops SDK-->
	<uses-permission android:name="android.permission.INTERNET"/>
   <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />

      <!-- GET_ACCOUNTS permission is only needed if the minSdkVersion is lower
		than 14 (4.0.4), you may remove it otherwise -->
	<uses-permission android:name="android.permission.GET_ACCOUNTS"/>
    
	<!-- End of Permissions for RedTroops SDK-->
```

In application tag, add the following activity, receiver, service, and Google Play Services' meta-data (Mandatory). Change <PACKAGE-NAME> into the app's package name:

```xml
        <!-- RedTroops SDK (MANDATORY) -->
        <activity
            android:name="com.RedTroops.RedTroopsSDK.Interstitial"
            android:configChanges="orientation|keyboardHidden|screenLayout"
            android:excludeFromRecents="true"
            android:theme="@android:style/Theme.Black.NoTitleBar" />

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

        <!-- Install Referrer -->
        <receiver
            android:name="com.RedTroops.RedTroopsSDK.ReferrerReceiver"
            android:exported="true" >
            <intent-filter>
                <action android:name="com.android.vending.INSTALL_REFERRER" >
                </action>
            </intent-filter>
        </receiver>


        <!-- End of RedTroops SDK (MANDATORY) -->
```

6) Go to your assets folder, and create a new file. Name it “redtroops.properties”. Add the following keys and their corresponding values. For example:
```
	gcm_sender_id=330435028307
	api_key=OpjFdYiCEaEUsrLL8AAzpNYx19TSAY1d
```

*gcm_sender_id* is the Project Number that you obtained from Google Cloud Console when you created the project.
*api_key* is the Api Key which is listed in the app details in My Apps at RedTroops developer website.

7) In your main activity's `onCreate`, call:
```java
	RedTroops.getInstance(this).init(initFinishedListener);
```
Where `initFinishedListener` is a listener to declare as follows:
```java	
	private initFinishListener initFinishedListener = new initFinishListener() {

		@Override
		public void onSuccess() {
			// TODO Do on init success. Most probably showInterstitialAd() and showBanner();
		}

		@Override
		public void onFail() {
			// TODO Do on init failure
		}
	};
```

8) To show an Interstitial Ad, call:
```java
	RedTroops.getInstance(this).showInterstitialAd(this);
```

It is preferred to call this in `initFinishedListener`'s `onSuccess()` so that it is made sure that the initialization has finished. The parameter passed to `showInterstitialAd` must be an activity.

9) To end your session, add the following to your last activity's `onDestroy`:
```java
	RedTroops.getInstance(this).endSession();
```
This should only be called once on exiting the app.

###More Options

- The Push Notification Icon can be set using `setPushNotificationIcon(resource_name)`, where resource_name is a string of the name of the drawable without the file extension. For example:
```java
	RedTroops.getInstance(this).setPushNotificationIcon("ic_launcher");
```

The icon must be in any drawable folder. If this is not called, or if the icon supplied is invalid, the default icon is the application's icon.

###Important Notes

1) If the advertisements do not refresh, this is because `RedTroops.getInstance(Context).onPause()` and `RedTroops.getInstance(Context).onResume()` are not being called in the activity where the advertisement is showing.

2) When creating a Push Notification, a custom sound can be specified. The custom sound has to be in res/raw folder. The custom sound name should not contain the file extension. For example, a wav file in the RedTroopsSDK-Test app has the path /res/raw/custom_sound.wav, the proper custom sound name to set is "custom_sound". If the custom sound name was invalid, no sound will be played.

3) The default icon for the Push Notification is the application's icon.

4) The user will not be notified if Google Play Services app was not installed on their device.

5) Make sure that you clean and build your project each time you add a library or change settings of Order and Export tab.

6) The test app will show errors if RedTroops SDK was not added. This can be done by changing the Java Build Path as documented in Setting Up RedTroops SDK In Your Project section.

7) `RedTroops.getInstance(Context).init(Context)` must be called before any other method. If `setPushNotificationIcon(resource_name)` was called before `init` then this error will be logged:
```xml
	setPushNotificationIcon: Error setting Push Notification icon
```

8) If another GCM intent service is to be used along side the RedTroops SDK intent service, they can be differentiated by getting the boolean "sdk" which is true for RedTroops SDK push notifications. An example of how to implement this safely:
```java
 	if((extras.containsKey("sdk")&&
	   !Boolean.parseBoolean(extras.getString("sdk")))||
	   !extras.containsKey("sdk"))
	 //Handle the non-sdk intent.

```

9) If this error is logged:
```xml
	init: Running RedTroops SDK without push notification support.
```
Then Google Play Services were not added to your project.

**Refer to the test app RedtroopsSDK-Test that is available alongside the SDK.**
