package com.bignerdranch.android.geoquiz;

public final class TrueFalse {
	private int mQuestion;
	private boolean mTrueQuestion;
	
	public TrueFalse(final int question, final boolean trueQuestion) {
		mQuestion = question;
		mTrueQuestion = trueQuestion;
	}

	public final int getQuestion() {
		return mQuestion;
	}

	public final void setQuestion(final int question) {
		mQuestion = question;
	}

	public final boolean isTrueQuestion() {
		return mTrueQuestion;
	}

	public final void setTrueQuestion(final boolean trueQuestion) {
		mTrueQuestion = trueQuestion;
	}
}
