package com.zhy.stickynavlayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import 	androidx.viewpager2.adapter.FragmentStateAdapter;

import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import com.zhy.stickynavlayout.view.SimpleViewPagerIndicator;


public class MainActivity extends FragmentActivity {
	private String[] mTitles = new String[] { "南湖", "秋水", "夜无烟" };
	private SimpleViewPagerIndicator mIndicator;
	private ViewPager2 mViewPager;
	private FragmentStateAdapter mAdapter;
	private TabFragment[] mFragments = new TabFragment[mTitles.length];

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		initDatas();
		initEvents();
	}

	private void initEvents() {
		mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				mIndicator.scroll(position, positionOffset);
			}
		});
	}

	private void initDatas(){
		mIndicator.setTitles(mTitles);

		for (int i = 0; i < mTitles.length; i++){
			mFragments[i] = (TabFragment) TabFragment.newInstance(mTitles[i]);
		}
		mAdapter = new FragmentStateAdapter(this) {

			// 每一页，具体的内容
			@NonNull
			@Override
			public Fragment createFragment(int position) {
				return mFragments[position];
			}

			// the number of pages
			// 有几页
			@Override
			public int getItemCount() {
				return mTitles.length;
			}
		};

		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
	}

	private void initViews()
	{
		mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
		mViewPager = (ViewPager2) findViewById(R.id.id_stickynavlayout_viewpager);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}








