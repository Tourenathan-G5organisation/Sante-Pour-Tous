package com.toure.santepourtous;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.MobileAds;
import com.toure.santepourtous.utility.Utility;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = MainActivity.class.getSimpleName();

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    int mCurrentTab;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        mTabLayout = findViewById(R.id.tabs);

        setSupportActionBar(mToolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager);
        if (mViewPager != null) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }

        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
        // Initialise AdMob
        MobileAds.initialize(this, Utility.ADMOB_APP_ID);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public String LOG_TAG = SectionsPagerAdapter.class.getSimpleName();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return appropriate fragment for the given screen.

            switch (position) {
                case 0:
                    return MaladieFragment.newInstance(position);
                case 1:
                    return BienEtreFragment.newInstance(position);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.maladie);
                case 1:
                    return getString(R.string.bienetre);
            }
            return super.getPageTitle(position);
        }
    }
}
