package com.bignerdranch.android.geoquiz;



import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class CheatActivity extends LoggingActivity
 {
	
	public static final String QUESTION_KEY = TrueFalse.class.getCanonicalName();
	
	private TextView mAnswerTextView;
	private TextView mAndroidVersionTextView;
	private Button mShowAnswerButton;
	
	private TrueFalse mQuestion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mQuestion =((TrueFalse) getIntent().getSerializableExtra(QUESTION_KEY));

		mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
		mAndroidVersionTextView = (TextView) findViewById(R.id.android_version_view);
		mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
		
		mShowAnswerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				logDebug("mAnswerIsTrue: " + mQuestion.isTrueQuestion());
				
				mQuestion.setCheated(true);
				updateDisplay();
				saveState();
			}
		});
		
		updateDisplay();
		
	}
	
	void updateDisplay() {
		if (mQuestion.isCheated()) {
			int message = mQuestion.isTrueQuestion() ? R.string.true_button : R.string.false_button;
			mAnswerTextView.setText(message);
		}
		
		mAndroidVersionTextView.setText("API level " + Build.VERSION.SDK_INT);
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
		updateDisplay();
	}
	
	void saveState() {
		Intent result = new Intent(CheatActivity.this, QuizActivity.class);
		result.putExtra(QUESTION_KEY, mQuestion);
		setResult(RESULT_OK, result);
		logDebug("isCheated: " + mQuestion.isCheated());
	}
}
