package me.aartikov.alligator;

import android.content.Intent;

/**
 * Date: 12.03.2017
 * Time: 11:06
 *
 * @author Artur Artikov
 */

/**
 * Helper class for handling a screen result. Can be obtained with {@code getScreenResultResolver} method of {@link AndroidNavigator}.
 */
public class ScreenResultResolver {
	private NavigationFactory mNavigationFactory;

	ScreenResultResolver(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	/**
	 * Handles a screen result. This method should be called from {@code onActivityResult} of an activity.
	 *
	 * @param requestCode requestCode passed to {@code onActivityResult}
	 * @param resultCode  resultCode passed to {@code onActivityResult}
	 * @param data        intent passed to {@code onActivityResult}
	 * @param listener    listener that will handle a screen result
	 */
	public void handleActivityResult(int requestCode, int resultCode, Intent data, ScreenResultListener listener) {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(requestCode);
		if (screenClass != null) {
			ScreenResult screenResult = mNavigationFactory.getScreenResult(screenClass, new ActivityResult(resultCode, data));
			listener.onScreenResult(screenClass, screenResult);
		}
	}
}
