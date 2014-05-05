package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private static final String FILE_NAME = "crimes.json";

    private List<Crime> mCrimeList = new ArrayList<Crime>();
    private Jsonizer<Crime> jsonizer = new Jsonizer<Crime>();

    private CrimeLab() {
    }

    public static CrimeLab get(Context context)  {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab();
            try {
                sCrimeLab.loadCrimes(context);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        
        return sCrimeLab;
    }

    public void loadCrimes(Context context) {
        mCrimeList = jsonizer.unJsonize(Crime.class, context, FILE_NAME);
    }

    public void saveCrimes(Context context)  {
        jsonizer.jsonize(mCrimeList, context, FILE_NAME);
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
