package com.bignerdranch.android.criminalintent;
import android.os.*;
import java.util.*;
import java.lang.reflect.*;

public class ParcelCreator<T extends Parcelable> implements Parcelable.Creator<T>
{

	private Class<T> clazz;

	public ParcelCreator(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	public T createFromParcel(Parcel in)
	{
		T instance;
		try
		{
			instance = clazz.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		return instance;
	}

	public T[] newArray(int size)
	{
		return (T[]) Array.newInstance(clazz, size);
	}
}
