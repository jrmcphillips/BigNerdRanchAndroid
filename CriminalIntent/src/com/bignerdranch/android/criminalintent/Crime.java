package com.bignerdranch.android.criminalintent;

import java.util.*;

public class Crime
{
	private final UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;

	public Crime()
	{
		this.mId = UUID.randomUUID();
		this.mDate = new Date();
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
