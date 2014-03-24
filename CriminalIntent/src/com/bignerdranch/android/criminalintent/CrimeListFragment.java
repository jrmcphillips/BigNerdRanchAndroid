package com.bignerdranch.android.criminalintent;

import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {

    private CrimeLab mCrimeLab;

    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(List<Crime> crimeList) {
            super(getActivity(), R.layout.list_item_crime, crimeList);
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
        setHasOptionsMenu(true);
        
        getActivity().setTitle(R.string.crimes_title);
        
        mCrimeLab = CrimeLab.get();
        final CrimeAdapter crimeAdapter = new CrimeAdapter(mCrimeLab.getCrimeList());
        setListAdapter(crimeAdapter);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        
        switch(item.getItemId()) {
        case R.id.menu_item_new_crime:
            Crime crime = new Crime();
            mCrimeLab.addCrime(crime);
            final Intent intent = newCrimePagerIntent(crime.getId());
            startActivityForResult(intent, 0);
            break;
        default:
            result = super.onOptionsItemSelected(item);
            break;
        }
        
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(final ListView listView, final View v, final int position, final long id) {
        final Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
        Log.i(this.getClass().getSimpleName(), "" + position);
        final Intent intent = newCrimePagerIntent(crime.getId());
        startActivity(intent);
    }
    
    Intent newCrimePagerIntent(UUID uuid) {
        final Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
        intent.putExtra(CrimeFragment.CRIME_KEY, uuid);
        
        return intent;
    }
    
    
}
