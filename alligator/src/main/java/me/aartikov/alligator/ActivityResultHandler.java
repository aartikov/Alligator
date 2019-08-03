package me.aartikov.alligator;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.Destination;
import me.aartikov.alligator.helpers.ScreenResultHelper;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;


/**
 * Helper class for handling a screen result.
 */
public class ActivityResultHandler {
	private NavigationFactory mNavigationFactory;
	private ScreenResultListener mScreenResultListener;
	private ScreenResultPair mPendingScreenResultPair;

	ActivityResultHandler(@NonNull NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	void setScreenResultListener(@NonNull ScreenResultListener screenResultListener) {
		mScreenResultListener = screenResultListener;
		handlePendingScreenResult();
	}

	void resetScreenResultListener() {
		mScreenResultListener = null;
	}

	/**
	 * Handles activity result. This method should be called from {@code onActivityResult} of an activity.
	 *
	 * @param requestCode requestCode passed to {@code onActivityResult}
	 * @param resultCode  resultCode passed to {@code onActivityResult}
	 * @param data        intent passed to {@code onActivityResult}
	 */
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(requestCode);
		if (screenClass != null) {
			Destination destination = mNavigationFactory.getDestination(screenClass);
			if (destination instanceof ActivityDestination) {
				ScreenResult screenResult = ((ActivityDestination) destination).getScreenResult(new ActivityResult(resultCode, data));
				if (mPendingScreenResultPair == null || mPendingScreenResultPair.mScreenResult == null) {
					mPendingScreenResultPair = new ScreenResultPair(screenClass, screenResult);
				}
				handlePendingScreenResult();
			}
		}
	}

	/**
	 * Handles activity result. This method should be called from {@code onNewIntent} of an activity.
	 *
	 * @param intent intent passed to {@code onNewIntent}
	 */
	public void onNewIntent(@NonNull Intent intent) {
		int requestCode = intent.getIntExtra(ScreenResultHelper.KEY_REQUEST_CODE, -1);
		if (requestCode != -1) {
			int resultCode = intent.getIntExtra(ScreenResultHelper.KEY_RESULT_CODE, 0);
			onActivityResult(requestCode, resultCode, intent);
		}
	}

	private void handlePendingScreenResult() {
		if (mScreenResultListener != null && mPendingScreenResultPair != null) {
			mScreenResultListener.onScreenResult(mPendingScreenResultPair.mScreenClass, mPendingScreenResultPair.mScreenResult);
			mPendingScreenResultPair = null;
		}
	}

	private class ScreenResultPair {
		Class<? extends Screen> mScreenClass;
		ScreenResult mScreenResult;

		ScreenResultPair(@NonNull Class<? extends Screen> screenClass, @Nullable ScreenResult screenResult) {
			mScreenClass = screenClass;
			mScreenResult = screenResult;
		}
	}
}