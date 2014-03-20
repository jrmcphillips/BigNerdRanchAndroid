package com.bignerdranch.android.criminalintent;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class CrimePagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private CrimeLab mCrimeLab;
    private List<Crime> mCrimeList;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        mCrimeLab = CrimeLab.get(this);
        mCrimeList = mCrimeLab.getCrimeList();
        mViewPager.setAdapter(new CrimePagerAdapter());
        mViewPager.setOnPageChangeListener(new CrimeOnPageChangeListener());

        final Crime crime = getIntent().getParcelableExtra(CrimeFragment.CRIME_KEY);

        for (int i = 0; i < mCrimeList.size(); i++) {
            if (mCrimeList.get(i).getId().equals(crime.getId())) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }

    private class CrimePagerAdapter extends FragmentStatePagerAdapter {
        public CrimePagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(final int pos) {
            final Crime crime = mCrimeList.get(pos);
            return CrimeFragment.newInstance(crime);
        }

        @Override
        public int getCount() {
            return mCrimeList.size();
        }

    }

    private class CrimeOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(final int state) {
        }

        @Override
        public void onPageScrolled(final int pos, final float posOffset, final int posOffsetPixels) {
        }

        @Override
        public void onPageSelected(final int pos) {
            final Crime crime = mCrimeList.get(pos);
            final String crimeTitle = crime.getTitle();
            final String pageTitle = "Crime: " + ((crimeTitle != null) ? crimeTitle : "");
            setTitle(pageTitle);
        }

    }
}
