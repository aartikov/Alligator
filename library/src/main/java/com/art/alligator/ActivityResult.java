package com.art.alligator;

import android.content.Intent;

/**
 * Date: 12.03.2017
 * Time: 10:59
 *
 * @author Artur Artikov
 */

public class ActivityResult {
	private int mResultCode;
	private Intent mData;

	public ActivityResult(int resultCode, Intent data) {
		mResultCode = resultCode;
		mData = data;
	}

	public int getResultCode() {
		return mResultCode;
	}

	public Intent getData() {
		return mData;
	}
}
