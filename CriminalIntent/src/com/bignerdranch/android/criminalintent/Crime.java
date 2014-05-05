package com.bignerdranch.android.criminalintent;

import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.*;

public class Crime implements Parcelable, Jsonizable<Crime> {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    private static final String JSON_SOLVED = "solved";

    private UUID mId;
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

    public static final Parcelable.Creator<Crime> CREATOR = new Parcelable.Creator<Crime>() {
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

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setId(UUID id) {
        this.mId = id;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new JSONObject();
            jsonObject.put(JSON_ID, getId().toString());
            jsonObject.put(JSON_TITLE, getTitle() != null ? getTitle() : "");
            jsonObject.put(JSON_DATE, getDate().getTime());
            jsonObject.put(JSON_SOLVED, isSolved());
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }

        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        try {
            setId(UUID.fromString(jsonObject.getString(JSON_ID)));
            setTitle(jsonObject.getString(JSON_TITLE));
            setDate(new Date(jsonObject.getLong(JSON_DATE)));
            setSolved(jsonObject.getBoolean(JSON_SOLVED));
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}
