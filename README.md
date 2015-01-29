![RedTroops Logo](http://redtroops.com/images/logo.png)

#RedTroops SDK 2.0 for Android

**Requirements: Android 2.3.3+ (API 10)**

###Getting Started

RedTroops SDK 2.0 currently features Push Notifications, Interstitial, Banner and Native ads. In order to have the push notifications ready for RedTroops, you must set up Google Cloud Messaging. 

###Setting Up Google Cloud Messaging (Push Notifications)

Please refer to these instructions by Google from [developer.android.com](developer.android.com). Instead of obtaining an Android key, select Server Key (No need to modify any Server Key options):
[http://developer.android.com/google/gcm/gs.html](http://developer.android.com/google/gcm/gs.html)

A Project Number (Sender ID) will be generated which you will use to set up the SDK, store it.

Next, you have to set up Google Play Services. Please refer to these instructions:
[http://developer.android.com/google/play-services/setup.html](http://developer.android.com/google/play-services/setup.html)

You must have now Google Play Services library in your project. You must have a project number. There should be no errors while compiling.

Android Support Library must be added. This can be done by right-clicking on your project → Android Tools → Add Support Library. Android Private Libraries must be checked in Order and Export.

###Eclipse - Setting Up RedTroops SDK 2.0 In Your Project

Follow the steps below to get your RedTroops SDK 2.0 running using Eclipse IDE:

1) Download [the SDK](https://github.com/RedTroops/Android-SDK/raw/master/redtroops.jar) from GitHub.

2) Right-click on your project from the Package Explorer in Eclipse → Build Path → Configure Build Path.

3) Click on Add External JARs, and choose RedTroops SDK.

4) Go to Order and Export tab, and place a check for RedTroops SDK.

5) Clean and rebuild your project from Project -> Clean Project for the changes to be applied.

###Android Studio - Setting Up RedTroops SDK 2.0 In Your Project

Follow the steps below to get your RedTroops SDK 2.0 running using Eclipse IDE:

1) Download [the SDK](https://github.com/RedTroops/Android-SDK/raw/master/redtroops.jar) from GitHub.

2) Navigate to your project folder using your file explorer. Open the module folder and copy the SDK to your "libs" folder. Create the folder if it doesn't exist.

> Note libs folder is at the root of the module next to the folders "build" and "src".

> You will not need to do step 3 if you have `{dir=libs, include=[*.jar]} Compile` in your dependencies.

3) Open your module settings by right-clicking your module and choosing "Open Module Settings". Navigate to Dependencies tab. Press the + button to add a new dependency and choose File dependency. The SDK should be under your libs folder according to step 2.

4) Sync your gradle files.

###Adding SDK configuration to the manifest

Edit your manifest as follows:

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

In application tag, add the following activity, receiver, service, and Google Play Services' meta-data (Mandatory). Change PACKAGE-NAME into the app's package name:

```xml
<!-- RedTroops SDK (MANDATORY) -->
<activity
android:name="com.RedTroops.RedTroopsSDK.Interstitial"
android:configChanges="orientation|keyboardHidden|screenLayout"
android:excludeFromRecents="true"
android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" />

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

<!-- Set the value to the Api Key obtained from developer.redtroops.com -->
<meta-data android:name="api_key" android:value="your_api_key"/>
<!-- Set the value to gcm followed by your sender ID you obtained from your Google Cloud Console project -->
<meta-data android:name="gcm_sender_id" android:value="gcm[your_gcm_sender_id]"/>
<!-- End of RedTroops SDK (MANDATORY) -->
```

*gcm_sender_id* is the Project Number that you obtained from Google Cloud Console when you created the project plus the word "gcm" added to its beginning. Example:
`<meta-data android:name="gcm_sender_id" android:value="gcm330435028307"/>`

*api_key* is the Api Key which is listed in the app details in My Apps at RedTroops' Developer website. Example: 
`<meta-data android:name="api_key" android:value="OpjFdYiCEaEUsrLL8AAzpNYx19TSAY1d"/>`

> Make sure you have added the meta-data needed for Google Play Services to enable Push Notification support `<meta-data android:name="com.google.android.gms.version" android:value="@integer/google_play_services_version" />`

####Editing The Source Code

1) In every activity's `onCreate` which you want to show ads in, call:
```java
RedTroops.getInstance(this).init(initFinishedListener);
```
Where `initFinishedListener` is a listener to declare as follows:
```java	
private initFinishListener initFinishedListener = new initFinishListener() {

@Override
public void onSuccess() {
// TODO Do on init success. Most probably showInterstitialAd();
}

@Override
public void onFail() {
// TODO Do on init failure
}
};
```

2) Add this to your onPause() on every activity (including ones you do not show ads in):
```java
@Override
protected void onPause() {
RedTroops.getInstance(this).onPause();
super.onPause();
}
```

3) Add this to your onResume() on every activity (including ones you do not show ads in):
```java
@Override
protected void onResume() {
RedTroops.getInstance(this).onResume();
super.onResume();
}
```

> Steps 2 and 3 are mandatory to ensure proper SDK function and session time calculation.


4) To show an Interstitial Ad, call:
```java
RedTroops.getInstance(this).showInterstitialAd(this);
```

You may call this in `initFinishedListener`'s `onSuccess()` so that it is made sure that the initialization has finished.

> Parameter passed to `showInterstitialAd` must be an activity, such as YourActivity.this. You may not use getApplicationContext() or any of its derivatives.

5) To show a Banner ad, call:
```java
RedTroops.getInstance(this).showBanner(this);
```

You may call this in `initFinishedListener`'s `onSuccess()` so that it is made sure that the initialization has finished.

> Parameter passed to `showBanner` must be an activity, such as YourActivity.this. You may not use getApplicationContext() or any of its derivatives.

To ensure the banner does not block any views under it, add a padding of at least 75dp to your Activity.

6) To show a Native ad, in your layout (design), add the view using:
```xml
<com.RedTroops.RedTroopsSDK.NativeAd
android:layout_width="200dp"
android:layout_height="200dp"/>
```

The aspect ratios accepted are 1:1, 1:6 and 6:1. For example you can set the following sizes: 200dp:200dp, 50dp:300dp, and 300dp:50dp. If a wrong size is placed, an error will be shown.

A Native Ad can also be added using `RedTroops.getInstance(this).getNativeAd(this)`. Example:
```java
NativeAd nAd = RedTroops.getInstance(this).getNativeAd(this);
LinearLayout.LayoutParams nAdparams = new LinearLayout.LayoutParams(600, 600);
nAdparams.gravity = Gravity.CENTER_HORIZONTAL;
nAd.setLayoutParams(nAdparams);
mainView.addView(nAd);
```

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

**Refer to the test app [RedtroopsSDK-Test(Eclipse)](https://github.com/RedTroops/Android-SDK/tree/master/RedtroopsAndroidSDK-Test) or [Test(Android Studio)](https://github.com/RedTroops/Android-SDK/tree/master/Test) that is available alongside the SDK.**