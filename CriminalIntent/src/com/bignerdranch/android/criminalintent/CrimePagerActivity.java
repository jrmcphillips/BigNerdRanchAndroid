package com.bignerdranch.android.criminalintent;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class CrimePagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private CrimeLab mCrimeLab;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        mCrimeLab = CrimeLab.get();
        mViewPager.setAdapter(new CrimePagerAdapter());
        mViewPager.setOnPageChangeListener(new CrimeOnPageChangeListener());

        final UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.CRIME_KEY);

        for (int i = 0; i < mCrimeLab.size(); i++) {
            Crime curCrime = mCrimeLab.get(i);
            UUID curId = curCrime.getId();
            
            if (curId.equals(crimeId)) {
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
            final Crime crime = mCrimeLab.get(pos);
            return CrimeFragment.newInstance(crime);
        }

        @Override
        public int getCount() {
            return mCrimeLab.size();
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
            final Crime crime = mCrimeLab.get(pos);
            final String crimeTitle = crime.getTitle();
            final String pageTitle = "Crime: " + ((crimeTitle != null) ? crimeTitle : "");
            setTitle(pageTitle);
        }

    }
}
