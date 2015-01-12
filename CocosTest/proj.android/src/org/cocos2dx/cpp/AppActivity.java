/****************************************************************************
Copyright (c) 2008-2010 Ricardo Quesada
Copyright (c) 2010-2012 cocos2d-x.org
Copyright (c) 2011      Zynga Inc.
Copyright (c) 2013-2014 Chukong Technologies Inc.

http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ****************************************************************************/
package org.cocos2dx.cpp;


import org.cocos2dx.lib.Cocos2dxActivity;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;

import com.RedTroops.RedTroopsSDK.RedTroops;

public class AppActivity extends Cocos2dxActivity {

	static Activity mContext;

	@Override
	protected void onResume() {
		mContext = this;
		RedTroops.getInstance(mContext).onResume();
		RedTroops.getInstance(mContext).init(new RedTroops.initFinishListener() {

			@Override
			public void onSuccess() {
				showBannerAd();
			}

			@Override
			public void onFail() {
				// TODO Auto-generated method stub

			}
		});
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		RedTroops.getInstance(mContext).onPause();
		super.onPause();
	}
	
	static void showInterstitialAd(){
		mContext.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				RedTroops.getInstance(mContext).showInterstitialAd(mContext);

			}
		});
	}

	static void showBannerAd(){
		mContext.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				RedTroops.getInstance(mContext).showBanner(mContext, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

			}
		});
	}
}
