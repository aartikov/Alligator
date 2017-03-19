package com.art.alligator.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.art.alligator.ActivityResult;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.ScreenResult;


import static com.art.alligator.implementation.DefaultConvertingFunctions.*;

/**
 * Date: 11.02.2017
 * Time: 11:41
 *
 * @author Artur Artikov
 */

public class RegistryNavigationFactory implements NavigationFactory {
	private Map<Class<? extends Screen>, ActivityScreenElement> mActivityScreenRegistry = new HashMap<>();
	private Map<Class<? extends Screen>, FragmentScreenElement> mFragmentScreenRegistry = new HashMap<>();
	private Map<Class<? extends Screen>, ScreenForResultElement> mScreenForResultRegistry = new HashMap<>();
	private List<Class<? extends Screen>> mScreenClasses = new ArrayList<>();

	public interface IntentCreationFunction<ScreenT extends Screen> {
		Intent call(Context context, ScreenT screen);
	}

	public interface ActivityScreenGettingFunction<ScreenT extends Screen> {
		ScreenT call(Intent intent);
	}

	public interface FragmentCreationFunction<ScreenT extends Screen> {
		Fragment call(ScreenT screen);
	}

	public interface FragmentScreenGettingFunction<ScreenT extends Screen> {
		ScreenT call(Fragment fragment);
	}

	public interface ActivityResultCreationFunction<ScreenResultT extends ScreenResult> {
		ActivityResult call(ScreenResultT screenResult);
	}

	public interface ScreenResultGettingFunction<ScreenResultT extends ScreenResult> {
		ScreenResultT call(ActivityResult activityResult);
	}

	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, Class<? extends Activity> activityClass,
	                                                      IntentCreationFunction<ScreenT> intentCreationFunction, ActivityScreenGettingFunction<ScreenT> screenGettingFunction) {
		checkThatNotRegistered(screenClass);
		mActivityScreenRegistry.put(screenClass, new ActivityScreenElement(activityClass, intentCreationFunction, screenGettingFunction));
		mScreenClasses.add(screenClass);
	}

	public <ScreenT extends Screen> void registerActivity(final Class<ScreenT> screenClass, Class<? extends Activity> activityClass, IntentCreationFunction<ScreenT> intentCreationFunction) {
		registerActivity(screenClass, activityClass, intentCreationFunction, getNotImplementedActivityScreenGettingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerActivity(final Class<ScreenT> screenClass, final Class<? extends Activity> activityClass) {
		registerActivity(screenClass, activityClass, getDefaultIntentCreationFunction(screenClass, activityClass), getDefaultActivityScreenGettingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, FragmentCreationFunction<ScreenT> fragmentCreationFunction,
	                                                      FragmentScreenGettingFunction<ScreenT> screenGettingFunction) {
		checkThatNotRegistered(screenClass);
		mFragmentScreenRegistry.put(screenClass, new FragmentScreenElement(fragmentCreationFunction, screenGettingFunction));
		mScreenClasses.add(screenClass);
	}

	public <ScreenT extends Screen> void registerFragment(final Class<ScreenT> screenClass, FragmentCreationFunction<ScreenT> fragmentCreationFunction) {
		registerFragment(screenClass, fragmentCreationFunction, getNotImplementedFragmentScreenGettingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerFragment(final Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		registerFragment(screenClass, getDefaultFragmentCreationFunction(screenClass, fragmentClass), getDefaultFragmentScreenGetting(screenClass));
	}

	public <ScreenResultT extends ScreenResult> void registerScreenForResult(Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass,
	                                                                         ActivityResultCreationFunction<ScreenResultT> activityResultCreationFunction,
	                                                                         ScreenResultGettingFunction<ScreenResultT> screenResultGettingFunction) {
		checkThatCanBeRegisteredForResult(screenClass);
		int requestCode = mScreenForResultRegistry.size() + 1;
		mScreenForResultRegistry.put(screenClass, new ScreenForResultElement(requestCode, screenResultClass, activityResultCreationFunction, screenResultGettingFunction));
	}

	public <ScreenResultT extends ScreenResult> void registerScreenForResult(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass,
	                                                                         ScreenResultGettingFunction<ScreenResultT> screenResultGettingFunction) {
		registerScreenForResult(screenClass, screenResultClass, getNotImplementedActivityResultCreationFunction(screenClass, screenResultClass), screenResultGettingFunction);
	}

	public <ScreenResultT extends ScreenResult> void registerScreenForResult(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass) {
		registerScreenForResult(screenClass, screenResultClass, getDefaultActivityResultCreationFunction(screenResultClass), getDefaultScreenResultGettingFunction(screenResultClass));
	}

	@Override
	public boolean isActivityScreen(Class<? extends Screen> screenClass) {
		return mActivityScreenRegistry.containsKey(screenClass);
	}

	@Override
	public Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass) {
		checkThatActivityScreen(screenClass);
		ActivityScreenElement element = mActivityScreenRegistry.get(screenClass);
		return element.getActivityClass();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Intent createIntent(Context context, Screen screen) {
		checkThatActivityScreen(screen.getClass());
		ActivityScreenElement element = mActivityScreenRegistry.get(screen.getClass());
		return element.getIntentCreationFunction().call(context, screen);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(Intent intent, Class<ScreenT> screenClass) {
		checkThatActivityScreen(screenClass);
		ActivityScreenElement element = mActivityScreenRegistry.get(screenClass);
		return (ScreenT) element.getScreenGettingFunction().call(intent);
	}

	@Override
	public boolean isFragmentScreen(Class<? extends Screen> screenClass) {
		return mFragmentScreenRegistry.containsKey(screenClass);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Fragment createFragment(Screen screen) {
		checkThatFragmentScreen(screen.getClass());
		FragmentScreenElement element = mFragmentScreenRegistry.get(screen.getClass());
		return element.getFragmentCreationFunction().call(screen);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass) {
		checkThatFragmentScreen(screenClass);
		FragmentScreenElement element = mFragmentScreenRegistry.get(screenClass);
		return (ScreenT) element.getScreenGettingFunction().call(fragment);
	}

	@Override
	public boolean isScreenForResult(Class<? extends Screen> screenClass) {
		return mScreenForResultRegistry.containsKey(screenClass);
	}

	@Override
	public int getRequestCode(Class<? extends Screen> screenClass) {
		checkThatScreenForResult(screenClass);
		ScreenForResultElement element = mScreenForResultRegistry.get(screenClass);
		return element.getRequestCode();
	}

	@Override
	public Class<? extends ScreenResult> getScreenResultClass(Class<? extends Screen> screenClass) {
		checkThatScreenForResult(screenClass);
		ScreenForResultElement element = mScreenForResultRegistry.get(screenClass);
		return element.getScreenResultClass();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ActivityResult createActivityResult(Class<? extends Screen> screenClass, ScreenResult screenResult) {
		checkThatScreenForResult(screenClass);
		ScreenForResultElement element = mScreenForResultRegistry.get(screenClass);
		return element.getActivityResultCreationFunction().call(screenResult);
	}

	@Override
	public ScreenResult getScreenResult(Class<? extends Screen> screenClass, ActivityResult activityResult) {
		checkThatScreenForResult(screenClass);
		ScreenForResultElement element = mScreenForResultRegistry.get(screenClass);
		return element.getScreenResultGettingFunction().call(activityResult);
	}

	@Override
	public Collection<Class<? extends Screen>> getScreenClasses() {
		return mScreenClasses;
	}

	private void checkThatNotRegistered(Class<? extends Screen> screenClass) {
		if (mScreenClasses.contains(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is already registered.");
		}
	}

	private void checkThatActivityScreen(Class<? extends Screen> screenClass) {
		if (!isActivityScreen(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not registered as activity screen.");
		}
	}

	private void checkThatFragmentScreen(Class<? extends Screen> screenClass) {
		if (!isFragmentScreen(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not registered as fragment screen.");
		}
	}

	private void checkThatScreenForResult(Class<? extends Screen> screenClass) {
		if (!isScreenForResult(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not registered as screen for result.");
		}
	}

	private void checkThatCanBeRegisteredForResult(Class<? extends Screen> screenClass) {
		if (mScreenForResultRegistry.containsKey(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is already registered as screen for result.");
		}
		if (!isActivityScreen(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be registered as activity screen before registering it for result.");
		}
	}

	private static class ActivityScreenElement {
		private Class<? extends Activity> mActivityClass;
		private IntentCreationFunction mIntentCreationFunction;
		private ActivityScreenGettingFunction mScreenGettingFunction;

		ActivityScreenElement(Class<? extends Activity> activityClass, IntentCreationFunction intentCreationFunction, ActivityScreenGettingFunction screenGettingFunction) {
			mActivityClass = activityClass;
			mIntentCreationFunction = intentCreationFunction;
			mScreenGettingFunction = screenGettingFunction;
		}

		Class<? extends Activity> getActivityClass() {
			return mActivityClass;
		}

		IntentCreationFunction getIntentCreationFunction() {
			return mIntentCreationFunction;
		}

		ActivityScreenGettingFunction getScreenGettingFunction() {
			return mScreenGettingFunction;
		}
	}

	private static class FragmentScreenElement {
		private FragmentCreationFunction mFragmentCreationFunction;
		private FragmentScreenGettingFunction mScreenGettingFunction;

		FragmentScreenElement(FragmentCreationFunction fragmentCreationFunction, FragmentScreenGettingFunction screenGettingFunction) {
			mFragmentCreationFunction = fragmentCreationFunction;
			mScreenGettingFunction = screenGettingFunction;
		}

		FragmentCreationFunction getFragmentCreationFunction() {
			return mFragmentCreationFunction;
		}

		FragmentScreenGettingFunction getScreenGettingFunction() {
			return mScreenGettingFunction;
		}
	}

	private static class ScreenForResultElement {
		private int mRequestCode = -1;
		private Class<? extends ScreenResult> mScreenResultClass;
		private ActivityResultCreationFunction mActivityResultCreationFunction;
		private ScreenResultGettingFunction mScreenResultGettingFunction;

		ScreenForResultElement(int requestCode, Class<? extends ScreenResult> screenResultClass, ActivityResultCreationFunction activityResultCreationFunction, ScreenResultGettingFunction screenResultGettingFunction) {
			mRequestCode = requestCode;
			mScreenResultClass = screenResultClass;
			mActivityResultCreationFunction = activityResultCreationFunction;
			mScreenResultGettingFunction = screenResultGettingFunction;
		}

		int getRequestCode() {
			return mRequestCode;
		}

		Class<? extends ScreenResult> getScreenResultClass() {
			return mScreenResultClass;
		}

		ActivityResultCreationFunction getActivityResultCreationFunction() {
			return mActivityResultCreationFunction;
		}

		ScreenResultGettingFunction getScreenResultGettingFunction() {
			return mScreenResultGettingFunction;
		}
	}
}
