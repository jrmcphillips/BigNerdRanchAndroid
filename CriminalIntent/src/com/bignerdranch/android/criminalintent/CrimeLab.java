package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
    private static final CrimeLab sCrimeLab = new CrimeLab();

    private Context mContext;

    private final CrimeMap mCrimeMap = new CrimeMap();

    private CrimeLab() {
    }

    public static CrimeLab get(final Context context) {
        if (sCrimeLab.mContext == null) {
            sCrimeLab.mContext = context.getApplicationContext();
        }

        return sCrimeLab;
    }

    public Crime getCrime(final UUID crimeId) {
        return mCrimeMap.get(crimeId);
    }

    public CrimeMap getCrimeMap() {
        return mCrimeMap;
    }

    public List<Crime> getCrimeList() {
        return new ArrayList<Crime>(mCrimeMap.values());
    }

    public void addCrime(Crime crime) {
        mCrimeMap.put(crime.getId(), crime);
    }

}
