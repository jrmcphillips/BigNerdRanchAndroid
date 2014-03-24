package com.bignerdranch.android.criminalintent;

import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static final CrimeLab sCrimeLab = new CrimeLab();

    private final CrimeList mCrimeList = new CrimeList();

    private CrimeLab() {
    }

    public static CrimeLab get() {
        return sCrimeLab;
    }

    public Crime getCrime(final UUID crimeId) {
        Crime crime = null;
        for (Crime curCrime : mCrimeList) {
            if (curCrime.getId().equals(crimeId)) {
                crime = curCrime;
            }
        }
        return crime;
    }

    public List<Crime> getCrimeList() {
        return mCrimeList;
    }

    public void addCrime(Crime crime) {
        mCrimeList.add(crime);
    }

    public Crime get(int pos) {
        return mCrimeList.get(pos);
    }

    public int size() {
        return mCrimeList.size();
    }

}
