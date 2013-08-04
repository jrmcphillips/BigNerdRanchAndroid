package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends LoggingActivity {
	
	public static final String QUESTION_KEY = TrueFalse.class.getCanonicalName();
	
	private TextView mAnswerTextView;
	private Button mShowAnswerButton;
	
	private TrueFalse mQuestion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mQuestion =((TrueFalse) getIntent().getSerializableExtra(QUESTION_KEY));

		mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
		mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
		
		mShowAnswerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				logDebug("mAnswerIsTrue: " + mQuestion.isTrueQuestion());
				
				mQuestion.setCheated(true);
				updateAnswer();
				saveState();
			}
		});
		
	}
	
	void updateAnswer() {
		if (mQuestion.isCheated()) {
			int message = mQuestion.isTrueQuestion() ? R.string.true_button : R.string.false_button;
			mAnswerTextView.setText(message);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(QUESTION_KEY, mQuestion);
		saveState();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mQuestion = (TrueFalse) savedInstanceState.getSerializable(QUESTION_KEY);
		logDebug("isCheated: " + mQuestion.isCheated());
		updateAnswer();
	}
	
	void saveState() {
		Intent result = new Intent(CheatActivity.this, QuizActivity.class);
		result.putExtra(QUESTION_KEY, mQuestion);
		setResult(RESULT_OK, result);
		logDebug("isCheated: " + mQuestion.isCheated());
	}
}
