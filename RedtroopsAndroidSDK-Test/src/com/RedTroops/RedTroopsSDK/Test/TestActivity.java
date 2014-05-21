package com.RedTroops.RedTroopsSDK.Test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.RedTroops.RedTroopsSDK.RedTroopsSDK;
import com.RedTroops.RedTroopsSDK.RedTroopsSDK.initFinishListener;

public class TestActivity extends Activity {
	private Button mBtnMoreApp, mBtnPopup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		mBtnMoreApp = (Button) findViewById(R.id.btnMore);
		mBtnPopup = (Button) findViewById(R.id.btnPopup);

		RedTroopsSDK.getInstance(this).init(initFinishedListener);

		mBtnMoreApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RedTroopsSDK.getInstance(TestActivity.this).showMorePage();
			}
		});

		mBtnPopup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RedTroopsSDK.getInstance(TestActivity.this)
						.showHTML5ImagePopup();
			}
		});
		
		// Optional
		RedTroopsSDK.getInstance(this).setPushNotificationIcon("ic_launcher");

	}

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

	@Override
	protected void onDestroy() {
		RedTroopsSDK.getInstance(this).endSession();

		super.onDestroy();
	}
}
