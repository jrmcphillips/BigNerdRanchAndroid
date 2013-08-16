package com.bignerdranch.android.criminalintent;

import android.os.*;
import android.support.v4.app.*;

public class CrimeActivity extends SingleFragmentActivity {
	protected Fragment createFragment() {
		
		Crime crime = getIntent().getParcelableExtra(CrimeFragment.CRIME_KEY);
		return CrimeFragment.newInstance(crime);
	}
}

