package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {

        final Crime crime = getIntent().getParcelableExtra(
                CrimeFragment.CRIME_KEY);
        return CrimeFragment.newInstance(crime);
    }
}
