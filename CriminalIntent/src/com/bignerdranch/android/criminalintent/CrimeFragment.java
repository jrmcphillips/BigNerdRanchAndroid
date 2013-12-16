package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private Crime mCrime;
    private EditText mEditText;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private CrimeLab mCrimeLab;

    public static final String CRIME_KEY = Crime.class.getCanonicalName();

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
        final String formattedDate = DateFormat.format("EEEE, MMMM dd, yyyy", mCrime.getDate()).toString();
        mDateButton.setText(formattedDate);
        mDateButton.setEnabled(false);

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
