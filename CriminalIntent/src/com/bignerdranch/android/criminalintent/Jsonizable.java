package com.bignerdranch.android.criminalintent;

import org.json.JSONObject;

public interface Jsonizable<T> {
    JSONObject toJson();
    void fromJson(JSONObject jsonObject);
}
