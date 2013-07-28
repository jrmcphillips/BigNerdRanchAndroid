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

		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextQuestion();
			}
		});

		mPreviousButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				previousQuestion();
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

	void nextQuestion() {
		mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
		updateQuestion();
	}

	void previousQuestion() {
		mCurrentIndex = (mCurrentIndex + mQuestionBank.size() - 1) % mQuestionBank.size();
		updateQuestion();
	}
	
	void updateQuestion() {
		Log.i(getClass().getSimpleName(), mCurrentIndex + "");
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

}
