package com.RedTroops.RedTroopsSDK.Test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.RedTroops.RedTroopsSDK.RedTroopsSDK;
import com.RedTroops.RedTroopsSDK.RedTroopsSDK.initFinishListener;

public class TestActivity extends Activity {
	private Button mBtnMoreApp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		mBtnMoreApp = (Button) findViewById(R.id.btnMore);
		mBtnMoreApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RedTroopsSDK.getInstance(TestActivity.this).showBannerList();
			}
		});

		RedTroopsSDK.getInstance(this).init(initFinishedListener);
		
		//Optional
		RedTroopsSDK.getInstance(this).setPushNotificationIcon("ic_launcher");

	}

	private initFinishListener initFinishedListener = new initFinishListener() {

		@Override
		public void onSuccess() {
			// TODO Do on init success. Most probably showHTML5ImagePopup();
			RedTroopsSDK.getInstance(TestActivity.this).showHTML5ImagePopup();
			
		}

		@Override
		public void onFail() {
			// TODO Do on init failure
		}
	};

	@Override
	protected void onDestroy(){
		RedTroopsSDK.getInstance(this).endSession();

		super.onDestroy();
	}
}
