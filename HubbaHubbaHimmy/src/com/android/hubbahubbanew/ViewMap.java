package com.android.hubbahubbanew;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;


public class ViewMap extends SherlockFragment {


	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	        return inflater.inflate(R.layout.dummy_layout, container, false);
	    }
}


/*
public class ViewMap extends SherlockFragmentActivity implements TabListener, OnPageChangeListener {
    ViewPager  mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new MyPagerAdapter(this, getSupportFragmentManager()));
        
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.addTab(bar.newTab().setText("Map").setTabListener(this));
        bar.addTab(bar.newTab().setText("Add Spot").setTabListener(this));
        bar.addTab(bar.newTab().setText("My Spots").setTabListener(this));        
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
				f = new MapFragment();
				break;
			case 1:
				f = new AddSpotFragment();
				break;
			case 2:
				f = new MySpotsFragment();
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
*/