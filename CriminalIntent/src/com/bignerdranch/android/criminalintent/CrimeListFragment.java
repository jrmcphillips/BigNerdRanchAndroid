package com.bignerdranch.android.criminalintent;

import android.support.v4.app.*;
import android.os.*;
import android.view.View;
import android.widget.*;
import android.util.*;
import java.util.*;
import android.view.*;
import android.content.*;

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
		Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
		Log.i(this.getClass().getSimpleName(), "" + position);
		Toast.makeText(getActivity(), "item: " + position, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(getActivity(), CrimeActivity.class);
		intent.putExtra(CrimeFragment.CRIME_KEY, crime);
		startActivity(intent);
	}
	
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		public CrimeAdapter(CrimeMap crimeMap) {
			super(
				getActivity(),
				R.layout.list_item_crime,
				new ArrayList(crimeMap.values()));
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup group) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			}

			Crime crime = getItem(position);
			
			TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_title_text_view);
			titleTextView.setText(crime.getTitle());
			
			TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_date_text_view);
			dateTextView.setText(crime.getDate().toString());
			
			CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solved_check_box);
			solvedCheckBox.setChecked(crime.isSolved());
			
			return convertView;
		}
	}
}

