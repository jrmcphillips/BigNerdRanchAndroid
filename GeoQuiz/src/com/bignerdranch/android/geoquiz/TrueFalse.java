package com.bignerdranch.android.geoquiz;

import java.io.Serializable;

public final class TrueFalse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int mQuestion;
	private boolean mTrueQuestion;
	private boolean mCheated;
	
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

	public final boolean isCheated() {
		return mCheated;
	}

	public final void setCheated(final boolean cheated) {
		mCheated = cheated;
	}
}
