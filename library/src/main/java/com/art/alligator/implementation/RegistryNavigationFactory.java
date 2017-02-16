package com.art.alligator.implementation;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;

/**
 * Date: 11.02.2017
 * Time: 11:41
 *
 * @author Artur Artikov
 */

public class RegistryNavigationFactory implements NavigationFactory {
	private Map<Class<? extends Screen>, RegistryElement> mRegistry = new HashMap<>();

	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, Class<? extends Activity> activityClass, IntentCreationFunction<ScreenT> intentCreationFunction) {
		checkIfAlreadyRegistered(screenClass);
		mRegistry.put(screenClass, new RegistryElement(activityClass, intentCreationFunction));
	}

	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, FragmentCreationFunction<ScreenT> fragmentCreationFunction) {
		checkIfAlreadyRegistered(screenClass);
		mRegistry.put(screenClass, new RegistryElement(fragmentCreationFunction));
	}

	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, final Class<? extends Activity> activityClass) {
		registerActivity(screenClass, activityClass, new IntentCreationFunction<ScreenT>() {
			@Override
			public Intent create(Context context, ScreenT screen) {
				return ScreenUtils.createActivityIntent(context, activityClass, screen);
			}
		});
	}

	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		registerFragment(screenClass, new FragmentCreationFunction<ScreenT>() {
			@Override
			public Fragment create(ScreenT screen) {
				return ScreenUtils.createFragment(fragmentClass, screen);
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public Intent createActivityIntent(Context context, Screen screen) {
		RegistryElement element = mRegistry.get(screen.getClass());
		if (element != null && element.getIntentCreationFunction() != null) {
			return element.getIntentCreationFunction().create(context, screen);
		} else {
			return null;
		}
	}

	@Override
	public Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass) {
		RegistryElement element = mRegistry.get(screenClass);
		if (element != null) {
			return element.getActivityClass();
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Fragment createFragment(Screen screen) {
		RegistryElement element = mRegistry.get(screen.getClass());
		if (element != null && element.getFragmentCreationFunction() != null) {
			return element.getFragmentCreationFunction().create(screen);
		} else {
			return null;
		}
	}

	private void checkIfAlreadyRegistered(Class<? extends Screen> screenClass) {
		if (mRegistry.get(screenClass) != null) {
			throw new IllegalStateException("Screen " + screenClass.getSimpleName() + "is already regitered.");
		}
	}

	public interface IntentCreationFunction<ScreenT extends Screen> {
		Intent create(Context context, ScreenT screen);
	}

	public interface FragmentCreationFunction<ScreenT extends Screen> {
		Fragment create(ScreenT screen);
	}

	private static class RegistryElement {
		private Class<? extends Activity> mActivityClass;
		private IntentCreationFunction mIntentCreationFunction;
		private FragmentCreationFunction mFragmentCreationFunction;

		RegistryElement(Class<? extends Activity> activityClass, IntentCreationFunction intentCreationFunction) {
			mActivityClass = activityClass;
			mIntentCreationFunction = intentCreationFunction;
		}

		RegistryElement(FragmentCreationFunction fragmentCreationFunction) {
			mFragmentCreationFunction = fragmentCreationFunction;
		}

		Class<? extends Activity> getActivityClass() {
			return mActivityClass;
		}

		IntentCreationFunction getIntentCreationFunction() {
			return mIntentCreationFunction;
		}

		FragmentCreationFunction getFragmentCreationFunction() {
			return mFragmentCreationFunction;
		}
	}
}
