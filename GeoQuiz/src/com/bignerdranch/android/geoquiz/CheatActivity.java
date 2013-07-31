package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends LoggingActivity {
	
	public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
	
	private boolean mAnswerIsTrue;
	private TextView mAnswerTextView;
	private Button mShowAnswerButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

		mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
		mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
		
		mShowAnswerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				logDebug("mAnswerIsTrue: " + mAnswerIsTrue);
				int message = mAnswerIsTrue ? R.string.true_button : R.string.false_button;
				mAnswerTextView.setText(message);
				
				Intent result = new Intent(CheatActivity.this, QuizActivity.class);
				result.putExtra(EXTRA_ANSWER_SHOWN, true);
				setResult(RESULT_OK, result);
			}
		});
		
	}
}
