package com.bignerdranch.android.criminalintent;

import java.util.UUID;

import android.content.Context;

public class CrimeLab {
    private static final CrimeLab sCrimeLab = new CrimeLab();

    public static CrimeLab get(final Context context) {
        if (sCrimeLab.mContext == null) {
            sCrimeLab.mContext = context.getApplicationContext();
        }

        if (sCrimeLab.getCrimeMap().isEmpty()) {
            sCrimeLab.loadDummyCrimes();
        }

        return sCrimeLab;
    }

    private Context mContext;

    private final CrimeMap mCrimeMap = new CrimeMap();

    private CrimeLab() {
    }

    public Crime getCrime(final UUID crimeId) {
        return mCrimeMap.get(crimeId);
    }

    public CrimeMap getCrimeMap() {
        return mCrimeMap;
    }

    void loadDummyCrimes() {
        for (int x = 0; x < 100; x++) {
            final Crime crime = new Crime();
            crime.setSolved(x % 2 == 0);
            crime.setTitle("Crime #" + x + ": " + crime.isSolved());

            mCrimeMap.put(crime.getId(), crime);
        }
    }
}
