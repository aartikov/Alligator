package com.art.alligator.implementation;

import java.io.Serializable;

import android.app.Activity;
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

public class ScreenResultUtils {
	private static final String KEY_SCREEN_RESULT = "com.art.alligator.implementation.ScreenResultUtils.KEY_SCREEN_RESULT";

	private ScreenResultUtils() {
	}

	static public boolean handleActivityResult(int requestCode, int resultCode, Intent data, NavigationFactory navigationFactory, ScreenResultListener listener) {
		Class<? extends Screen> screenClass = getScreenClass(requestCode, navigationFactory);
		if(screenClass != null) {
			ScreenResult screenResult = navigationFactory.createScreenResult(new ActivityResult(resultCode, data));
			listener.onScreenResult(screenClass, screenResult);
			return true;
		} else {
			return false;
		}
	}

	static ScreenResult createScreenResult(ActivityResult activityResult) {
		if(activityResult.getData() == null || activityResult.getResultCode() != Activity.RESULT_OK) {
			return null;
		}

		return (ScreenResult) activityResult.getData().getSerializableExtra(KEY_SCREEN_RESULT);
	}

	static ActivityResult createActivityResult(ScreenResult screenResult) {
		if(screenResult == null) {
			return new ActivityResult(Activity.RESULT_CANCELED, null);
		}

		if(!(screenResult instanceof Serializable)) {
			throw new IllegalArgumentException("Screen result must be Serializable");
		}

		Intent data = new Intent();
		data.putExtra(KEY_SCREEN_RESULT, (Serializable) screenResult);
		return new ActivityResult(Activity.RESULT_OK, data);
	}

	private static Class<? extends Screen> getScreenClass(int requestCode, NavigationFactory navigationFactory) {
		for(Class<? extends Screen> screenClass: navigationFactory.getScreenClasses()) {
			if(navigationFactory.getRequestCode(screenClass) == requestCode) {
				return screenClass;
			}
		}
		return null;
	}
}
