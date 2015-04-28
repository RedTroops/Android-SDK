package com.RedTroops.RedTroopsSDK.Test;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.RedTroops.RedTroopsSDK.AdType;
import com.RedTroops.RedTroopsSDK.RedTroops;
import com.RedTroops.RedTroopsSDK.RedTroops.initFinishListener;

public class TestActivity extends FragmentActivity {

	VideoView vv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		getFragmentManager().beginTransaction().replace(R.id.content_frame, new ScrollFragment()).commit();

		RedTroops.getInstance(this).init(initFinishedListener, new AdType().videoAd().bannerAd().audioAd().nativeAd().interstitialAd());

	}
	
	@Override
	protected void onStart() {
		super.onStart();
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

    public void PlayAudioAdvertisement(View v){

        RedTroops.getInstance(this).playAudioAd(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(TestActivity.this, "Media stopped", Toast.LENGTH_SHORT).show();
            }
        });

    }

	public void PlayVideoAdvertisement(View v){

		RedTroops.getInstance(this).playVideoAd(this, new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(TestActivity.this, "Media stopped", Toast.LENGTH_SHORT).show();
			}
		});

	}
	
	@Override
	protected void onPause() {
		RedTroops.getInstance(this).onPause();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
        RedTroops.getInstance(TestActivity.this).showBanner(TestActivity.this);
		RedTroops.getInstance(this).onResume();
		super.onResume();
	}

	@Override
	public void onBackPressed() {

		if(getFragmentManager().getBackStackEntryCount() > 0) {
			getFragmentManager().popBackStack();
		} else {
			super.onBackPressed();
		}
	}
}
