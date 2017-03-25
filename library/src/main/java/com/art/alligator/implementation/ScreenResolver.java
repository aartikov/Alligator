package com.art.alligator.implementation;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;

/**
 * Date: 11.02.2017
 * Time: 16:27
 *
 * @author Artur Artikov
 */

public class ScreenResolver {
	private NavigationFactory mNavigationFactory;

	public ScreenResolver(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	public <ScreenT extends Screen> ScreenT getScreen(Activity activity, Class<ScreenT> screenClass) {
		return mNavigationFactory.getScreen(activity.getIntent(), screenClass);
	}

	public <ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass) {
		return mNavigationFactory.getScreen(fragment, screenClass);
	}

	public <ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment, Class<ScreenT> screenClass) {
		return mNavigationFactory.getScreen(dialogFragment, screenClass);
	}
}
