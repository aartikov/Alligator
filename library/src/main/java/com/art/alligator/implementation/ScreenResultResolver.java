package com.art.alligator.implementation;

import android.content.Intent;

import com.art.alligator.ActivityResult;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.ScreenResult;
import com.art.alligator.ScreenResultListener;

/**
 * Date: 12.03.2017
 * Time: 11:06
 *
 * @author Artur Artikov
 */

public class ScreenResultResolver {
	private NavigationFactory mNavigationFactory;

	public ScreenResultResolver(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	public boolean handleActivityResult(int requestCode, int resultCode, Intent data, ScreenResultListener listener) {
		Class<? extends Screen> screenClass = getScreenClass(requestCode);
		if(screenClass != null) {
			ScreenResult screenResult = mNavigationFactory.getScreenResult(screenClass, new ActivityResult(resultCode, data));
			return listener.onScreenResult(screenClass, screenResult);
		} else {
			return false;
		}
	}

	private Class<? extends Screen> getScreenClass(int requestCode) {
		for(Class<? extends Screen> screenClass: mNavigationFactory.getScreenClasses()) {
			if(mNavigationFactory.isScreenForResult(screenClass) && mNavigationFactory.getRequestCode(screenClass) == requestCode) {
				return screenClass;
			}
		}
		return null;
	}
}
