package com.RedTroops.RedTroopsSDK.Test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.RedTroops.RedTroopsSDK.RedTroops;

/**
 * Created by redtroops on 1/11/15.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_second);
        super.onCreate(savedInstanceState);

        RedTroops.getInstance(this).showBanner(this, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    }

    @Override
    protected void onResume() {
        RedTroops.getInstance(this).onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        RedTroops.getInstance(this).onPause();
        super.onPause();
    }

    public void NextActivity(View v){

        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i);
        finish();

    }
}
