package com.bignerdranch.android.criminalintent;

import android.content.*;
import java.util.*;

public class CrimeLab {
	private static final CrimeLab sCrimeLab = new CrimeLab();
	private Context mContext;
	private final CrimeMap mCrimeMap = new CrimeMap();
	
	private CrimeLab() {}
	
	public static CrimeLab get(Context context) {
		if (sCrimeLab.mContext == null) {
			sCrimeLab.mContext = context.getApplicationContext();
		}
		
		sCrimeLab.loadDummyCrimes();
		
		return sCrimeLab;
	}
	
	void loadDummyCrimes() {
		for (int x = 0; x < 100; x++) {
			Crime crime = new Crime();
			crime.setSolved(x % 2 == 0);
			crime.setTitle("Crime #" + x + ": " + crime.isSolved());
			
			mCrimeMap.put(crime.getId(), crime);
		}
	}
	
	public CrimeMap getCrimeMap() {
		return mCrimeMap;
	}
	
	public Crime getCrime(UUID crimeId) {
		return mCrimeMap.get(crimeId);
	}
}
