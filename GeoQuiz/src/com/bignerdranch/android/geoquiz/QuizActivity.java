package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends LoggingActivity {

	private Button mTrueButton;
	private Button mFalseButton;
	private Button mCheatButton;
	private ImageButton mNextButton;
	private ImageButton mPreviousButton;
	private TextView mQuestionTextView;
	
	private QuizState quizState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		setQuizState(new QuizState());

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
				cheatIntent.putExtra(CheatActivity.QUESTION_KEY, getQuizState().getCurrentQuestion());
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
		super.onSaveInstanceState(outState);
		logDebug("mCurrentIndex: " + getQuizState().getCurrentIndex());
		outState.putParcelable(QuizState.QUIZ_STATE_KEY, getQuizState());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		setQuizState((QuizState) savedInstanceState.getParcelable(QuizState.QUIZ_STATE_KEY));
		updateQuestion();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			quizState.setCurrentQuestion((TrueFalse) data.getExtras().getSerializable(CheatActivity.QUESTION_KEY));
		}
	}

	void nextQuestion() {
		getQuizState().setCurrentIndex((getQuizState().getCurrentIndex() + 1) % getQuizState().getQuestionBank().size());
		updateQuestion();
	}

	void previousQuestion() {
		getQuizState().setCurrentIndex((getQuizState().getCurrentIndex() + getQuizState().getQuestionBank().size() - 1)
				% getQuizState().getQuestionBank().size());
		updateQuestion();
	}

	void updateQuestion() {
		logDebug("mCurrentIndex: " + getQuizState().getCurrentIndex());
		mQuestionTextView.setText(getQuizState().getCurrentQuestion().getQuestion());
	}

	void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = getQuizState().getCurrentQuestion().isTrueQuestion();
		int messageResId = 0;

		if (getQuizState().getCurrentQuestion().isCheated()) {
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

	public QuizState getQuizState() {
		return quizState;
	}

	public void setQuizState(final QuizState quizState) {
		this.quizState = quizState;
	}

}
