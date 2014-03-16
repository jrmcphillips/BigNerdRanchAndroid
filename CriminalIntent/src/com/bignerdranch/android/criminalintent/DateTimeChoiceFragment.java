package com.bignerdranch.android.criminalintent;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;

public class DateTimeChoiceFragment extends DialogFragment {
    
    private Date mDate;
    
    public static DateTimeChoiceFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(CrimeFragment.EXRA_DATE, date);
        
        DateTimeChoiceFragment fragment = new DateTimeChoiceFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(CrimeFragment.EXRA_DATE);        
        
        View view = getActivity().getLayoutInflater().inflate(R.layout.date_change_choice, null);
        
        Button dateChoiceButton = (Button) view.findViewById(R.id.crime_date_choice_button);
        dateChoiceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                sendResult(CrimeFragment.RESULT_DATE_CHOICE);
            }
        });
        
        Button timeChoiceButton = (Button) view.findViewById(R.id.crime_time_choice_button);
        timeChoiceButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                sendResult(CrimeFragment.RESULT_TIME_CHOICE);
            }
        });
        
        
        return new AlertDialog.Builder(getActivity())
        .setView(view)
        .setTitle(R.string.date_time_choice_picker_title)
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_CANCELED);
            }
        })
        .create();
    }
    
    private void sendResult(int resultCode) {
        if (getTargetFragment() != null) {            
            Intent i = new Intent();
            i.putExtra(CrimeFragment.EXRA_DATE, mDate);
            
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }
    }
}
