package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

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

    public static final String EXRA_DATE = CrimeFragment.class.getCanonicalName();
    public static final String CRIME_KEY = Crime.class.getCanonicalName();
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final String DIALOG_CHOICE = "date_time_choice";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = 1;
    public static final int RESULT_DATE_CHOICE = 2;
    public static final int RESULT_TIME_CHOICE = 3;

    private Crime mCrime;
    private EditText mEditText;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private CrimeLab mCrimeLab;

    
    public static final CrimeFragment newInstance(final Crime crime) {
        final Bundle args = new Bundle();
        args.putSerializable(CRIME_KEY, crime.getId());

        final CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimeLab = CrimeLab.get();
        UUID crimeId = (UUID) getArguments().getSerializable(CRIME_KEY);
        mCrime = mCrimeLab.getCrime(crimeId);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup parent, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_crime, parent, false);
        mEditText = (EditText) view.findViewById(R.id.crime_title);
        mEditText.setText(mCrime.getTitle());

        mEditText.addTextChangedListener(new CrimeTitleTextWatcher());

        mDateButton = (Button) view.findViewById(R.id.crime_date);
        updateDate();
        
        mDateButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                 
                DateTimeChoiceFragment dialog = DateTimeChoiceFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_CHOICE);
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
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Date date = (Date)data.getSerializableExtra(EXRA_DATE);
        mCrime.setDate(date);
        updateDate();
        
        switch (resultCode) {
            case RESULT_CANCELED:
                break;
            case RESULT_OK:
                break;
            case RESULT_DATE_CHOICE:
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
                break;
            case RESULT_TIME_CHOICE:
                TimePickerFragment timePickerDialog = TimePickerFragment.newInstance(mCrime.getDate());
                timePickerDialog.setTargetFragment(this, REQUEST_TIME);
                timePickerDialog.show(fm, DIALOG_TIME);
                break;
        }
        
    }
    
    private void updateDate() {
        final String formattedDate = DateFormat.format("hh:mm a EEEE, MMMM dd, yyyy", mCrime.getDate()).toString();
        mDateButton.setText(formattedDate);
    }

    private class CrimeTitleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(final CharSequence c, final int start, final int before, final int after) {
        }

        @Override
        public void onTextChanged(final CharSequence p1, final int p2, final int p3, final int p4) {
            mCrime.setTitle(p1.toString());
        }

        @Override
        public void afterTextChanged(final Editable p1) {
        }
    }
}
