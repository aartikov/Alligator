package com.art.screenresultsample;

import android.app.Application;

import com.art.alligator.NavigationContextBinder;
import com.art.alligator.Navigator;
import com.art.alligator.implementation.AndroidNavigator;
import com.art.alligator.implementation.ScreenResultResolver;

/**
 * Date: 12.03.2016
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

	public static ScreenResultResolver getScreenResultResolver() {
		return sNavigator.getScreenResultResolver();
	}
}
