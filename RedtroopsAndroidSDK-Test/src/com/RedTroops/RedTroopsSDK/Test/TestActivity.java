package com.RedTroops.RedTroopsSDK.Test;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.RedTroops.RedTroopsSDK.RedTroops;
import com.RedTroops.RedTroopsSDK.RedTroops.initFinishListener;

public class TestActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		RedTroops.getInstance(this).init(initFinishedListener);

	}

	public void ShowInterstitial(View v){
		RedTroops.getInstance(this).showInterstitialAd(this);
	}

	private initFinishListener initFinishedListener = new initFinishListener() {

		@Override
		public void onSuccess() {
			// TODO Do on init success. Most probably showInterstitialAd(Activity);
		}

		@Override
		public void onFail() {
			// TODO Do on init failure. Are you connected to the internet? Check your LogCat.
		}
	};

	@Override
	protected void onDestroy() {
		RedTroops.getInstance(this).endSession();
		super.onDestroy();
	}

	// This is implemented to disable calling onCreate when the screen orientation changes.
	// Not a part of RedTroops SDK.
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}

}
