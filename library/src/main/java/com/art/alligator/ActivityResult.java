package com.art.alligator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Date: 12.03.2017
 * Time: 10:59
 *
 * @author Artur Artikov
 */

/**
 * Wrapper for an activity result. It contains a result code and an intent.
 */
public class ActivityResult {
	private int mResultCode;
	private Intent mIntent;

	public ActivityResult(int resultCode, Intent intent) {
		mResultCode = resultCode;
		mIntent = intent;
	}

	public int getResultCode() {
		return mResultCode;
	}

	public Intent getIntent() {
		return mIntent;
	}

	public Uri getDataUri() {
		return mIntent != null ? mIntent.getData() : null;
	}

	public boolean isOk() {
		return mResultCode == Activity.RESULT_OK;
	}

	public boolean isCanceled() {
		return mResultCode == Activity.RESULT_CANCELED;
	}
}
