package com.nmbb.player.ui;

import com.nmbb.player.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

public class MainFragmentActivity extends FragmentActivity implements OnCheckedChangeListener {

	private ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_pager);
		mPager = (ViewPager) findViewById(R.id.pager);

		((RadioButton) findViewById(R.id.radio_file)).setOnCheckedChangeListener(this);
		((RadioButton) findViewById(R.id.radio_online)).setOnCheckedChangeListener(this);

		mPager.setAdapter(mAdapter);
	}

	private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

		/** 仅执行一次 */
		@Override
		public Fragment getItem(int position) {
			Fragment result = null;
			switch (position) {
			case 1:
				result = new FragmentOnline();//在线视频
				break;
			case 0:
			default:
				result = new FragmentFile();//本地视频
				break;
			}
			return result;
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.radio_file:
			mPager.setCurrentItem(1);
			break;
		case R.id.radio_online:
			mPager.setCurrentItem(0);
			break;
		}
	}
}
