package com.bignerdranch.android.criminalintent;

import android.os.*;
import android.support.v4.app.*;

public class CrimeActivity extends SingleFragmentActivity {
	protected Fragment createFragment() {
		return new CrimeFragment();
	}
}
