package com.bignerdranch.android.criminalintent;

import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.text.*;

public class CrimeFragment extends Fragment {
	private Crime mCrime;
	private EditText mEditText;
	
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
					mCrime.setMTitle(c.toString());
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
		
		return view;
	}
}
