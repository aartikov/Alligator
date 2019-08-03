package me.aartikov.simplenavigationsample;

import android.app.Application;

import me.aartikov.alligator.AndroidNavigator;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.ScreenResolver;
import me.aartikov.alligator.navigationfactories.GeneratedNavigationFactory;


public class SampleApplication extends Application {
	private static AndroidNavigator sAndroidNavigator;

	@Override
	public void onCreate() {
		super.onCreate();
		sAndroidNavigator = new AndroidNavigator(new GeneratedNavigationFactory()); // It is ok if GeneratedNavigationFactory is not defined. Just build the project to generate it.
	}

	// In a real application use dependency injection framework to provide these objects.

	public static Navigator getNavigator() {
		return sAndroidNavigator;
	}

	public static NavigationContextBinder getNavigationContextBinder() {
		return sAndroidNavigator;
	}

	public static ScreenResolver getScreenResolver() {
		return sAndroidNavigator.getScreenResolver();
	}
}