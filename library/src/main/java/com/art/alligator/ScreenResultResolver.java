package com.art.alligator;

import android.content.Intent;

/**
 * Date: 12.03.2017
 * Time: 11:06
 *
 * @author Artur Artikov
 */

/**
 * Helper class for handling a screen result.
 */
public class ScreenResultResolver {
	private NavigationFactory mNavigationFactory;

	public ScreenResultResolver(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	/**
	 * Handles screen result. This method should be called from {@code onActivityResult} of an activity.
	 *
	 * @param requestCode requestCode passed to {@code onActivityResult}
	 * @param resultCode  resultCode passed to {@code onActivityResult}
	 * @param data        intent passed to {@code onActivityResult}
	 * @param listener    listener that will handle a screen result
	 */
	public void handleActivityResult(int requestCode, int resultCode, Intent data, ScreenResultListener listener) {
		Class<? extends Screen> screenClass = getScreenClass(requestCode);
		if (screenClass != null) {
			ScreenResult screenResult = mNavigationFactory.getScreenResult(screenClass, new ActivityResult(resultCode, data));
			listener.onScreenResult(screenClass, screenResult);
		}
	}

	private Class<? extends Screen> getScreenClass(int requestCode) {
		for (Class<? extends Screen> screenClass : mNavigationFactory.getScreenClasses()) {
			if (mNavigationFactory.isScreenForResult(screenClass) && mNavigationFactory.getRequestCode(screenClass) == requestCode) {
				return screenClass;
			}
		}
		return null;
	}
}
