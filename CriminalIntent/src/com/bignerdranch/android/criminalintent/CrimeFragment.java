package com.bignerdranch.android.criminalintent;

import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.text.*;
import android.widget.CompoundButton.*;
import android.text.format.DateFormat;

public class CrimeFragment extends Fragment {
	private Crime mCrime;
	private EditText mEditText;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCrime = new Crime();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_crime, parent, false);
		mEditText = (EditText) view.findViewById(R.id.crime_title);
		mEditText.addTextChangedListener(new TextWatcher() {

				public void beforeTextChanged(CharSequence c, int start, int before, int after)
				{
					mCrime.setTitle(c.toString());
				}

				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				public void afterTextChanged(Editable p1)
				{
					// TODO: Implement this method
				}

			
		});
		
		mDateButton = (Button) view.findViewById(R.id.crime_date);
		String formattedDate = DateFormat.format("EEEE, MMMM dd, yyyy", mCrime.getDate()).toString();
		mDateButton.setText(formattedDate);
		mDateButton.setEnabled(false);
		
		mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					mCrime.setSolved(isChecked);
				}
		});
		
		return view;
	}
}
