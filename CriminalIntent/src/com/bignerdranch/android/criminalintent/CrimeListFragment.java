package com.bignerdranch.android.criminalintent;

import android.support.v4.app.*;
import android.os.*;
import android.view.View;
import android.widget.*;
import android.util.*;
import java.util.*;

public class CrimeListFragment extends ListFragment {
	private CrimeLab mCrimeLab;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimeLab = CrimeLab.get(getActivity());
		
		CrimeAdapter crimeAdapter = 
		new CrimeAdapter(
			mCrimeLab.getCrimeMap());
			
		setListAdapter(crimeAdapter);
	}
	
	@Override
	public void onListItemClick(ListView listView, View v, int position, long id) {
		Crime crime = (Crime) getListAdapter().getItem(position);
		Log.i(this.getClass().getSimpleName(), "" + position);
		Toast.makeText(listView.getContext().getApplicationContext(), "item: " + position, Toast.LENGTH_SHORT).show();
		crime.setTitle(crime.isSolved() + "");
	};
	
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		public CrimeAdapter(CrimeMap crimeMap) {
			super(
				getActivity(),
				R.layout.list_item_crime,
				new ArrayList(crimeMap.values()));
		}
	}
}

