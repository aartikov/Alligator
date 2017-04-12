package me.aartikov.advancedscreenswitchersample;

import android.app.Application;

import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.NavigationFactory;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.AndroidNavigator;
import me.aartikov.alligator.ScreenResolver;

/**
 * Date: 21.01.2016
 * Time: 23:30
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

	public static NavigationFactory getNavigationFactory() {
		return sNavigator.getNavigationFactory();
	}
}
