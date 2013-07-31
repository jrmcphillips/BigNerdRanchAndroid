package com.bignerdranch.android.geoquiz;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends LoggingActivity {

	private static final String CURRENT_INDEX_KEY = "currentIndex";

	private Button mTrueButton;
	private Button mFalseButton;
	private Button mCheatButton;
	private ImageButton mNextButton;
	private ImageButton mPreviousButton;
	private TextView mQuestionTextView;

	private List<TrueFalse> mQuestionBank;
	private TrueFalse mCurrentQuestion;
	private int mCurrentIndex = 0;
	
	private boolean mIsCheater = false;

	{
		mQuestionBank = Arrays.asList(new TrueFalse(R.string.question_oceans,
				true), new TrueFalse(R.string.question_mideast, false),
				new TrueFalse(R.string.question_africas, false), new TrueFalse(
						R.string.question_americas, true), new TrueFalse(
						R.string.question_asia, true));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logDebug("onCreate()");
		setContentView(R.layout.activity_quiz);

		mTrueButton = (Button) findViewById(R.id.true_button);
		mFalseButton = (Button) findViewById(R.id.false_button);
		mCheatButton = (Button) findViewById(R.id.cheat_button);
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

		mCheatButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent cheatIntent = new Intent(QuizActivity.this, CheatActivity.class);
				cheatIntent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, mCurrentQuestion.isTrueQuestion());
				startActivityForResult(cheatIntent, 0);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			mIsCheater = data.getExtras().getBoolean(CheatActivity.EXTRA_ANSWER_SHOWN, false);
		}
	}

	void nextQuestion() {
		mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
		updateQuestion();
	}

	void previousQuestion() {
		mCurrentIndex = (mCurrentIndex + mQuestionBank.size() - 1)
				% mQuestionBank.size();
		updateQuestion();
	}

	void updateQuestion() {
		logDebug("mCurrentIndex: " + mCurrentIndex);
		mCurrentQuestion = mQuestionBank.get(mCurrentIndex);
		mQuestionTextView.setText(mCurrentQuestion.getQuestion());
		mIsCheater = false;
	}

	void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mCurrentQuestion.isTrueQuestion();
		int messageResId = 0;

		if (mIsCheater) {
			messageResId = R.string.judgment_toast;
		} else if (userPressedTrue == answerIsTrue) {
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

}
