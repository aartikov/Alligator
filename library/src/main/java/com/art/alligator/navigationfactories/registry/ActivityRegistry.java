package com.art.alligator.navigationfactories.registry;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.art.alligator.Screen;
import com.art.alligator.functions.Function;
import com.art.alligator.functions.Function2;

/**
 * Date: 25.03.2017
 * Time: 16:52
 *
 * @author Artur Artikov
 */

/**
 * Registry for screens represented by activities.
 */
public class ActivityRegistry {
	private static final String KEY_SCREEN = "com.art.alligator.navigationfactories.registry.ActivityRegistry.KEY_SCREEN";

	private Map<Class<? extends Screen>, Element> mElements = new HashMap<>();

	public <ScreenT extends Screen> void register(Class<ScreenT> screenClass, Class<? extends Activity> activityClass,
	                                              Function2<Context, ScreenT, Intent> intentCreationFunction, Function<Intent, ScreenT> screenGettingFunction) {
		checkThatNotRegistered(screenClass);
		mElements.put(screenClass, new Element(activityClass, intentCreationFunction, screenGettingFunction));
	}

	public boolean isRegistered(Class<? extends Screen> screenClass) {
		return mElements.containsKey(screenClass);
	}

	public Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass) {
		checkThatRegistered(screenClass);
		Element element = mElements.get(screenClass);
		return element.getActivityClass();
	}

	@SuppressWarnings("unchecked")
	public Intent createActivityIntent(Context context, Screen screen) {
		checkThatRegistered(screen.getClass());
		Element element = mElements.get(screen.getClass());
		return ((Function2<Context, Screen, Intent>) element.getIntentCreationFunction()).call(context, screen);
	}

	public <ScreenT extends Screen> ScreenT getScreen(Intent intent, Class<ScreenT> screenClass) {
		checkThatRegistered(screenClass);
		Element element = mElements.get(screenClass);
		return (ScreenT) element.getScreenGettingFunction().call(intent);
	}

	public static <ScreenT extends Screen> Function2<Context, ScreenT, Intent> getDefaultIntentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends Activity> activityClass) {
		return new Function2<Context, ScreenT, Intent>() {
			@Override
			public Intent call(Context context, ScreenT screen) {
				Intent intent = new Intent(context, activityClass);
				if (screen instanceof Serializable) {
					intent.putExtra(KEY_SCREEN, (Serializable) screen);
				}
				return intent;
			}
		};
	}

	public static <ScreenT extends Screen> Function<Intent, ScreenT> getDefaultScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Intent, ScreenT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenT call(Intent intent) {
				if (!Serializable.class.isAssignableFrom(screenClass)) {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable.");
				}
				return (ScreenT) intent.getSerializableExtra(KEY_SCREEN);
			}
		};
	}

	public static <ScreenT extends Screen> Function<Intent, ScreenT> getNotImplementedScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Intent, ScreenT>() {
			@Override
			public ScreenT call(Intent intent) {
				throw new RuntimeException("Screen getting function is not implemented for a screen " + screenClass.getSimpleName());
			}
		};
	}

	private void checkThatNotRegistered(Class<? extends Screen> screenClass) {
		if (isRegistered(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is is already registered.");
		}
	}

	private void checkThatRegistered(Class<? extends Screen> screenClass) {
		if (!isRegistered(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not represented by an activity.");
		}
	}

	private static class Element {
		private Class<? extends Activity> mActivityClass;
		private Function2<Context, ? extends Screen, Intent> mIntentCreationFunction;
		private Function<Intent, ? extends Screen> mScreenGettingFunction;

		Element(Class<? extends Activity> activityClass, Function2<Context, ? extends Screen, Intent> intentCreationFunction, Function<Intent, ? extends Screen> screenGettingFunction) {
			mActivityClass = activityClass;
			mIntentCreationFunction = intentCreationFunction;
			mScreenGettingFunction = screenGettingFunction;
		}

		Class<? extends Activity> getActivityClass() {
			return mActivityClass;
		}

		Function2<Context, ? extends Screen, Intent> getIntentCreationFunction() {
			return mIntentCreationFunction;
		}

		Function<Intent, ? extends Screen> getScreenGettingFunction() {
			return mScreenGettingFunction;
		}
	}
}
