package me.aartikov.alligator;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Intent;

import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;

/**
 * Date: 12.03.2017
 * Time: 11:06
 *
 * @author Artur Artikov
 */

/**
 * Helper class for handling a screen result.
 */
public class ActivityResultHandler {
	private NavigationFactory mNavigationFactory;
	private ScreenResultListener mScreenResultListener;
	private Queue<ScreenResultPair> mScreenResultQueue = new LinkedList<>();

	ActivityResultHandler(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	void setScreenResultListener(ScreenResultListener screenResultListener) {
		mScreenResultListener = screenResultListener;
		handlePendingScreenResults();
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
	public void handle(final int requestCode, final int resultCode, final Intent data) {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(requestCode);
		if (screenClass != null) {
			ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screenClass);
			if (screenImplementation instanceof ActivityScreenImplementation) {
				ScreenResult screenResult = ((ActivityScreenImplementation) screenImplementation).getScreenResult(new ActivityResult(resultCode, data));
				mScreenResultQueue.add(new ScreenResultPair(screenClass, screenResult));
				handlePendingScreenResults();
			}
		}
	}

	private void handlePendingScreenResults() {
		while (mScreenResultListener != null && !mScreenResultQueue.isEmpty()) {
			ScreenResultPair pair = mScreenResultQueue.remove();
			mScreenResultListener.onScreenResult(pair.mScreenClass, pair.mScreenResult);
		}
	}

	private class ScreenResultPair {
		Class<? extends Screen> mScreenClass;
		ScreenResult mScreenResult;

		ScreenResultPair(Class<? extends Screen> screenClass, ScreenResult screenResult) {
			mScreenClass = screenClass;
			mScreenResult = screenResult;
		}
	}
}