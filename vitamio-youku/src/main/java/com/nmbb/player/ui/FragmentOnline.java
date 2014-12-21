package com.nmbb.player.ui;

import com.nmbb.player.R;
import com.nmbb.player.util.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FragmentOnline extends FragmentBase {

	private WebView mWebView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_online, container, false);
		mWebView = (WebView) v.findViewById(R.id.webview);
		mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		mWebView.getSettings().setJavaScriptEnabled(true);
//		mWebView.getSettings().setPluginsEnabled(true);
		mWebView.loadUrl("http://3g.youku.com");
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
			};

			/** 页面跳转 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (FileUtils.isVideoOrAudio(url)) {
					Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
					intent.putExtra("path", url);
					startActivity(intent);
					return true;
				}
				return false;
			};
		});

		mWebView.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView != null && mWebView.canGoBack()) {
					mWebView.goBack();
					return true;
				}
				return false;
			}
		});
		return v;
	}
}

/*
private boolean loadVideo(final String url) {
	if (StringUtils.isEmpty(url))
		return false;

	mCurrentUrl = url;

	new AsyncTask<Void, Void, OnlineVideo>() {

		@Override
		protected OnlineVideo doInBackground(Void... params) {
			Log.d("Youku", url);
			if (url.startsWith("http://m.youku.com")) {
				return VideoHelper.getYoukuVideo(url);
			}
			return null;
		}

		@Override
		protected void onPostExecute(OnlineVideo result) {
			super.onPostExecute(result);
			if (result != null) {
				Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
				intent.putExtra("path", result.url);
				intent.putExtra("title", result.title);
				startActivity(intent);
			} else {
				mWebView.loadUrl(url);
			}
		}
	}.execute();
	return true;
}*/
