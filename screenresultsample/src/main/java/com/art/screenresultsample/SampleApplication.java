package com.art.screenresultsample;

import android.app.Application;

import com.art.alligator.NavigationContextBinder;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Navigator;
import com.art.alligator.implementation.AndroidNavigator;

/**
 * Date: 12.03.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class SampleApplication extends Application {
	private static AndroidNavigator sNavigator;
	private static NavigationFactory sNavigationFactory;

	@Override
	public void onCreate() {
		super.onCreate();
		sNavigationFactory = new SampleNavigationFactory();
		sNavigator = new AndroidNavigator(sNavigationFactory);
	}

	public static Navigator getNavigator() {
		return sNavigator;
	}

	public static NavigationContextBinder getNavigationContextBinder() {
		return sNavigator;
	}

	public static NavigationFactory getNavigationFactory() {
		return sNavigationFactory;
	}
}
