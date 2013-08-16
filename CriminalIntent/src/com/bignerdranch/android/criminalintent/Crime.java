package com.bignerdranch.android.criminalintent;

import java.util.*;
import android.os.*;

public class Crime implements Parcelable {
	private final UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;

	public Crime() {
		this.mId = UUID.randomUUID();
		this.mDate = new Date();
	}

    private Crime(Parcel in) {
		this.mId = (UUID) in.readSerializable();
		this.mTitle = in.readString();
		this.mDate = (Date) in.readSerializable();
		this.mSolved = (Boolean) in.readSerializable();
    }

	public static final Parcelable.Creator<Crime> CREATOR
    = new Parcelable.Creator<Crime>() {
        public Crime createFromParcel(Parcel in) {
            return new Crime(in);
        }

        public Crime[] newArray(int size) {
            return new Crime[size];
        }
    };

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel parcel, int p2) {
		parcel.writeSerializable(mId);
		parcel.writeString(mTitle);
		parcel.writeSerializable(mDate);
		parcel.writeSerializable(mSolved);
	}

	public void setSolved(boolean solved)
	{
		this.mSolved = solved;
	}

	public boolean isSolved()
	{
		return mSolved;
	}

	public void setDate(Date date)
	{
		this.mDate = date;
	}

	public Date getDate()
	{
		return mDate;
	}

	public void setTitle(String title)
	{
		this.mTitle = title;
	}

	public String getTitle()
	{
		return mTitle;
	}

	public UUID getId()
	{
		return mId;
	}
	
	@Override
	public String toString() {
		return mTitle;
	}
}
