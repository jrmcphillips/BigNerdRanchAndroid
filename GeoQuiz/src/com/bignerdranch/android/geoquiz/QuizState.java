package com.bignerdranch.android.geoquiz;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class QuizState implements Parcelable {

	public static final String QUIZ_STATE_KEY = QuizState.class.getCanonicalName();

	private ArrayList<TrueFalse> mQuestionBank;
	private int mCurrentIndex = 0;
    
    public QuizState() {
		mQuestionBank = new ArrayList(Arrays.asList(new TrueFalse(R.string.question_oceans,
				true), new TrueFalse(R.string.question_mideast, false),
				new TrueFalse(R.string.question_africas, false), new TrueFalse(
						R.string.question_americas, true), new TrueFalse(
						R.string.question_asia, true)));
    }
        
    private QuizState(Parcel in) {
    	mQuestionBank = (ArrayList<TrueFalse>) in.readSerializable();
    	mCurrentIndex = in.readInt();
    }
	
	public static final Parcelable.Creator<QuizState> CREATOR
    = new Parcelable.Creator<QuizState>() {
        public QuizState createFromParcel(Parcel in) {
            return new QuizState(in);
        }

        public QuizState[] newArray(int size) {
            return new QuizState[size];
        }
    };

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeSerializable(mQuestionBank);
		out.writeInt(mCurrentIndex);
	}

	public final ArrayList<TrueFalse> getQuestionBank() {
		return mQuestionBank;
	}

	public final void setQuestionBank(ArrayList<TrueFalse> questionBank) {
		mQuestionBank = questionBank;
	}

	public final TrueFalse getCurrentQuestion() {
		return mQuestionBank.get(mCurrentIndex);
	}

	public void setCurrentQuestion(TrueFalse question) {
		mQuestionBank.set(mCurrentIndex, question);
	}

	public final int getCurrentIndex() {
		return mCurrentIndex;
	}

	public final void setCurrentIndex(int currentIndex) {
		mCurrentIndex = currentIndex;
	}

}
