package com.bignerdranch.android.geoquiz;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	
	private static final String CURRENT_INDEX_KEY = "currentIndex";

	private Button mTrueButton;
	private Button mFalseButton;
	private ImageButton mNextButton;
	private ImageButton mPreviousButton;
	private TextView mQuestionTextView;

	private List<TrueFalse> mQuestionBank;
	private TrueFalse mCurrentQuestion;
	private int mCurrentIndex = 0;

	{
		mQuestionBank = Arrays.asList(
				new TrueFalse(R.string.question_oceans, true), 
				new TrueFalse(R.string.question_mideast, false),
				new TrueFalse(R.string.question_africas, false), 
				new TrueFalse(R.string.question_americas, true), 
				new TrueFalse(R.string.question_asia, true));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logDebug("onCreate()");
		setContentView(R.layout.activity_quiz);

		mTrueButton = (Button) findViewById(R.id.true_button);
		mFalseButton = (Button) findViewById(R.id.false_button);
		mNextButton = (ImageButton) findViewById(R.id.next_button);
		mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

		mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});

		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});

		mPreviousButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				previousQuestion();
			}
		});

		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextQuestion();
			}
		});

		mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextQuestion();
			}
		});

		updateQuestion();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		logDebug("onSaveInstanceState(Bundle)");
		super.onSaveInstanceState(outState);
		logDebug("mCurrentIndex: " + mCurrentIndex);
		outState.putInt(CURRENT_INDEX_KEY, mCurrentIndex);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		logDebug("onRestoreInstanceState(Bundle)");
		super.onRestoreInstanceState(savedInstanceState);
		mCurrentIndex = savedInstanceState.getInt(CURRENT_INDEX_KEY, 0);
		updateQuestion();
	}

	void nextQuestion() {
		mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
		updateQuestion();
	}

	void previousQuestion() {
		mCurrentIndex = (mCurrentIndex + mQuestionBank.size() - 1) % mQuestionBank.size();
		updateQuestion();
	}
	
	void updateQuestion() {
		logDebug("mCurrentIndex: " + mCurrentIndex);
		mCurrentQuestion = mQuestionBank.get(mCurrentIndex);
		mQuestionTextView.setText(mCurrentQuestion.getQuestion());
	}

	void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mCurrentQuestion.isTrueQuestion();
		int messageResId = 0;

		if (userPressedTrue == answerIsTrue) {
			messageResId = R.string.correct_toast;
		} else {
			messageResId = R.string.incorrect_toast;
		}

		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		logDebug("onCreateOptionsMenu(Menu)");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		logDebug("onStart()");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		logDebug("onPause()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		logDebug("onResume()");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		logDebug("onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		logDebug("onDestroy()");
	}
	
	public void logInfo(String message) {
		Log.i(getTag(), message);
	}
	
	public void logDebug(String message) {
		Log.d(getTag(), message);
	}
	
	public String getTag() {
		return getClass().getSimpleName();
	}

}
