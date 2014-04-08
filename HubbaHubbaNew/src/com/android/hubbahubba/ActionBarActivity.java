package com.android.hubbahubba;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.WindowManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ActionBarActivity extends SherlockFragmentActivity implements TabListener, OnPageChangeListener {
    ViewPager  mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
        
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new MyPagerAdapter(this, getSupportFragmentManager()));
        
        // Get Actionbar
        ActionBar bar = getSupportActionBar();
        
        // set color to black
        bar.setStackedBackgroundDrawable(new ColorDrawable(android.graphics.Color.BLACK));
        
        // Get tabs
        ActionBar.Tab mapTab = bar.newTab().setIcon(R.drawable.ic_action_map).setTabListener(this);
        ActionBar.Tab listTab = bar.newTab().setIcon(R.drawable.ic_action_view_as_list).setTabListener(this);
        
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.addTab(mapTab);
        bar.addTab(listTab);
        //bar.addTab(bar.newTab().setText("My Spots").setTabListener(this));
        
        // set options for action bar
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayUseLogoEnabled(false);
        bar.setDisplayShowHomeEnabled(false);
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.hubba_header));
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	mViewPager.setOnPageChangeListener(this);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	mViewPager.setOnPageChangeListener(null);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
    	private final String[] mPageTitles; 
    	
		public MyPagerAdapter(Context context, FragmentManager fm) {
			super(fm);
			mPageTitles = context.getResources().getStringArray(R.array.pageTitle);
		}
    	
		@Override
		public CharSequence getPageTitle(int position) {
			return mPageTitles[position];
		}
		
		@Override
		public int getCount() {
			return mPageTitles.length;
		}
		
		@Override
		public Fragment getItem(int position) {
			Fragment f = null;
			switch(position) {
			case 0:
				f = new ViewMap();
				
				// pass through the bundle holding lat/long
				// to move the camera (if present)
				Bundle showData = getIntent().getExtras();
				if(showData != null){
					f.setArguments(showData);
				}
				break;
			case 1:
				//f = new NewListView();
				f = new ListViewHubba();
				break;
			case 2:
				f = new ListViewFavorites();
				break;
			}
			return f;
		}
    }

	public void onPageScrollStateChanged(int arg0) {
		// noop
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// noop
	}

	public void onPageSelected(int position) {
		position = position % getSupportActionBar().getNavigationItemCount();
		getSupportActionBar().setSelectedNavigationItem(position);
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// noop
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// noop
	}
    
    
}