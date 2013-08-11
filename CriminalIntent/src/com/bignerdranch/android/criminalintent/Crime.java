package com.bignerdranch.android.criminalintent;

import java.util.*;

public class Crime
{
	private final UUID mId;
	private String mTitle;

	public Crime()
	{
		this.mId = UUID.randomUUID();
	}

	public void setMTitle(String mTitle)
	{
		this.mTitle = mTitle;
	}

	public String getMTitle()
	{
		return mTitle;
	}

	public UUID getMId()
	{
		return mId;
	}
	
}
