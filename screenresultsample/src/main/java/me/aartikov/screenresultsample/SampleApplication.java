package me.aartikov.screenresultsample;

import android.app.Application;

import me.aartikov.alligator.AndroidNavigator;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.ScreenResultResolver;

/**
 * Date: 12.03.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class SampleApplication extends Application {
	private static AndroidNavigator sAndroidNavigator;

	@Override
	public void onCreate() {
		super.onCreate();
		sAndroidNavigator = new AndroidNavigator(new SampleNavigationFactory());
	}

	public static Navigator getNavigator() {
		return sAndroidNavigator;
	}

	public static NavigationContextBinder getNavigationContextBinder() {
		return sAndroidNavigator;
	}

	public static ScreenResultResolver getScreenResultResolver() {
		return sAndroidNavigator.getScreenResultResolver();
	}
}
