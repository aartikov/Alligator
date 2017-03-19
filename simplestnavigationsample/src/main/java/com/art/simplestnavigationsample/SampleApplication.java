package com.art.simplestnavigationsample;

import android.app.Application;

import com.art.alligator.NavigationContextBinder;
import com.art.alligator.Navigator;
import com.art.alligator.implementation.AndroidNavigator;
import com.art.alligator.implementation.ScreenResolver;

/**
 * Date: 22.01.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class SampleApplication extends Application {
	private static AndroidNavigator sNavigator;

	@Override
	public void onCreate() {
		super.onCreate();
		sNavigator = new AndroidNavigator(new SampleNavigationFactory());
	}

	public static Navigator getNavigator() {
		return sNavigator;
	}

	public static NavigationContextBinder getNavigationContextBinder() {
		return sNavigator;
	}

	public static ScreenResolver getScreenResolver() {
		return sNavigator.getScreenResolver();
	}
}