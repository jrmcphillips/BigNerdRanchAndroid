package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CrimeListFragment extends ListFragment {

    private CrimeLab mCrimeLab;

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(final CrimeMap crimeMap) {
            super(getActivity(), R.layout.list_item_crime, new ArrayList(crimeMap.values()));
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup group) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            final Crime crime = getItem(position);

            final TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_title_text_view);
            titleTextView.setText(crime.getTitle());

            final TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_date_text_view);
            dateTextView.setText(crime.getDate().toString());

            final CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solved_check_box);
            solvedCheckBox.setChecked(crime.isSolved());

            return convertView;
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);
        mCrimeLab = CrimeLab.get(getActivity());

        final CrimeAdapter crimeAdapter = new CrimeAdapter(mCrimeLab.getCrimeMap());

        setListAdapter(crimeAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(final ListView listView, final View v, final int position, final long id) {
        final Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
        Log.i(this.getClass().getSimpleName(), "" + position);
        Toast.makeText(getActivity(), "item: " + position, Toast.LENGTH_SHORT).show();
        final Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.CRIME_KEY, crime);
        startActivity(intent);
    }
}
