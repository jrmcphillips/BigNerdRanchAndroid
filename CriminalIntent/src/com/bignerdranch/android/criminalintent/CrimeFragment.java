package com.bignerdranch.android.criminalintent;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {

    public static final String CRIME_KEY = Crime.class.getCanonicalName();
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Crime mCrime;
    private EditText mEditText;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;
    private CrimeLab mCrimeLab;

    
    public static final CrimeFragment newInstance(final Crime crime) {
        final Bundle args = new Bundle();
        args.putParcelable(CRIME_KEY, crime);

        final CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimeLab = CrimeLab.get(getActivity());
        mCrime = getArguments().getParcelable(CRIME_KEY);
        mCrime = mCrimeLab.getCrime(mCrime.getId());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup parent, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_crime, parent, false);
        mEditText = (EditText) view.findViewById(R.id.crime_title);
        mEditText.setText(mCrime.getTitle());

        mEditText.addTextChangedListener(new CrimeTitleTextWatcher());

        mDateButton = (Button) view.findViewById(R.id.crime_date);
        mTimeButton = (Button) view.findViewById(R.id.crime_time);
        updateDate();
        
        mDateButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }
        });

        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                mCrime.setSolved(isChecked);
                Log.i(this.getClass().getSimpleName(), "setting crime to: " + isChecked);
            }
        });

        mSolvedCheckBox.setChecked(mCrime.isSolved());

        return view;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
        
        if (requestCode == REQUEST_TIME) {
            Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXRA_TIME);
            mCrime.setDate(date);
            updateDate();
        }
    }
    
    private void updateDate() {
        final String formattedDate = DateFormat.format("EEEE, MMMM dd, yyyy", mCrime.getDate()).toString();
        final String formattedTime = DateFormat.format("hh:mm p", mCrime.getDate()).toString();
        mDateButton.setText(formattedDate);
        mTimeButton.setText(formattedTime);
    }

    private class CrimeTitleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(final CharSequence c, final int start, final int before, final int after) {
            mCrime.setTitle(c.toString());
        }

        @Override
        public void onTextChanged(final CharSequence p1, final int p2, final int p3, final int p4) {
        }

        @Override
        public void afterTextChanged(final Editable p1) {
        }
    }
}
