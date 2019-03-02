package me.aartikov.alligator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.Nullable;

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
	@Nullable
	private Intent mIntent;

	public ActivityResult(int resultCode, @Nullable Intent intent) {
		mResultCode = resultCode;
		mIntent = intent;
	}

	public int getResultCode() {
		return mResultCode;
	}

	@Nullable
	public Intent getIntent() {
		return mIntent;
	}

	@Nullable
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
