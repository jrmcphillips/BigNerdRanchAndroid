package com.bignerdranch.android.criminalintent;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CrimeAdapter extends ArrayAdapter<Crime> {
    private Activity activity;

    public CrimeAdapter(List<Crime> crimeList, Activity activity) {
        super(activity, R.layout.list_item_crime, crimeList);
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup group) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_item_crime, null);
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
