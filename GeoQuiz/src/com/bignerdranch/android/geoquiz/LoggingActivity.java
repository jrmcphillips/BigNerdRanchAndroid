package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class LoggingActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logDebug("onCreate()");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		logDebug("onSaveInstanceState(Bundle)");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		logDebug("onRestoreInstanceState(Bundle)");
	}

	@Override
	protected void onStart() {
		super.onStart();
		logDebug("onStart()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		logDebug("onPause()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		logDebug("onResume()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		logDebug("onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		logDebug("onDestroy()");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		logDebug("onActivityResult(int, int, Intent)");
	}

	public void logInfo(String message) {
		Log.i(getTag(), message);
	}

	public void logDebug(String message) {
		Log.d(getTag(), message);
	}

	public String getTag() {
		return getClass().getSimpleName();
	}

}