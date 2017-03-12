package com.art.alligator.implementation;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.art.alligator.ActivityResult;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.ScreenResult;

/**
 * Date: 11.02.2017
 * Time: 11:41
 *
 * @author Artur Artikov
 */

public class RegistryNavigationFactory implements NavigationFactory {
	private int mRequestCodeCounter = 1;
	private Map<Class<? extends Screen>, ScreenRegistryElement> mScreenRegistry = new LinkedHashMap<>();
	private Map<Class<? extends Screen>, ScreenResultCreationFunction> mScreenResultCreationFunctions = new HashMap<>();
	private Map<Class<? extends ScreenResult>, ActivityResultCreationFunction> mActivityResultCreationFunctions = new HashMap<>();

	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, Class<? extends Activity> activityClass, IntentCreationFunction<ScreenT> intentCreationFunction) {
		checkIfScreenAlreadyRegistered(screenClass);
		mScreenRegistry.put(screenClass, new ScreenRegistryElement(activityClass, mRequestCodeCounter, intentCreationFunction));
		mRequestCodeCounter++;
	}

	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, FragmentCreationFunction<ScreenT> fragmentCreationFunction) {
		checkIfScreenAlreadyRegistered(screenClass);
		mScreenRegistry.put(screenClass, new ScreenRegistryElement(fragmentCreationFunction));
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

	public <ScreenResultT extends ScreenResult> void registerScreenResult(Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass,
	                                                                      ScreenResultCreationFunction<ScreenResultT> screenResultCreationFunction,
	                                                                      ActivityResultCreationFunction<ScreenResultT> activityResultCreationFunction) {

		checkIfScreenResultAlreadyRegistered(screenResultClass);
		mScreenResultCreationFunctions.put(screenClass, screenResultCreationFunction);
		mActivityResultCreationFunctions.put(screenResultClass, activityResultCreationFunction);
	}

	public <ScreenResultT extends ScreenResult> void registerScreenResult(Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass,
	                                                                      ScreenResultCreationFunction<ScreenResultT> screenResultCreationFunction) {

		registerScreenResult(screenClass, screenResultClass, screenResultCreationFunction, new ActivityResultCreationFunction<ScreenResultT>() {
			@Override
			public ActivityResult create(ScreenResultT screenResult) {
				throw new RuntimeException("ActivityResultCreationFunction is not implemented for screen result " + screenResult.getClass().getSimpleName());
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public Intent createActivityIntent(Context context, Screen screen) {
		ScreenRegistryElement element = mScreenRegistry.get(screen.getClass());
		if (element != null && element.getIntentCreationFunction() != null) {
			return element.getIntentCreationFunction().create(context, screen);
		} else {
			return null;
		}
	}

	@Override
	public Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass) {
		ScreenRegistryElement element = mScreenRegistry.get(screenClass);
		if (element != null) {
			return element.getActivityClass();
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Fragment createFragment(Screen screen) {
		ScreenRegistryElement element = mScreenRegistry.get(screen.getClass());
		if (element != null && element.getFragmentCreationFunction() != null) {
			return element.getFragmentCreationFunction().create(screen);
		} else {
			return null;
		}
	}

	@Override
	public int getRequestCode(Class<? extends Screen> screenClass) {
		ScreenRegistryElement element = mScreenRegistry.get(screenClass);
		if (element != null) {
			return element.getRequestCode();
		} else {
			return -1;
		}
	}

	@Override
	public ScreenResult createScreenResult(Class<? extends Screen> screenClass, ActivityResult activityResult) {
		ScreenResultCreationFunction screenResultCreationFunction = mScreenResultCreationFunctions.get(screenClass);
		if (screenResultCreationFunction != null) {
			return screenResultCreationFunction.create(activityResult);
		} else {
			return ScreenResultUtils.createScreenResult(activityResult);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public ActivityResult createActivityResult(ScreenResult screenResult) {
		ActivityResultCreationFunction activityResultCreationFunctionation = mActivityResultCreationFunctions.get(screenResult.getClass());
		if (activityResultCreationFunctionation != null) {
			return activityResultCreationFunctionation.create(screenResult);
		} else {
			return ScreenResultUtils.createActivityResult(screenResult);
		}
	}

	@Override
	public Collection<Class<? extends Screen>> getScreenClasses() {
		return mScreenRegistry.keySet();
	}

	private void checkIfScreenAlreadyRegistered(Class<? extends Screen> screenClass) {
		if (mScreenRegistry.get(screenClass) != null) {
			throw new IllegalStateException("Screen " + screenClass.getSimpleName() + " is already registered.");
		}
	}

	private void checkIfScreenResultAlreadyRegistered(Class<? extends ScreenResult> screenResultClass) {
		if (mActivityResultCreationFunctions.get(screenResultClass) != null) {
			throw new IllegalStateException("Screen result " + screenResultClass.getSimpleName() + " is already registered.");
		}
	}

	public interface IntentCreationFunction<ScreenT extends Screen> {
		Intent create(Context context, ScreenT screen);
	}

	public interface FragmentCreationFunction<ScreenT extends Screen> {
		Fragment create(ScreenT screen);
	}

	private static class ScreenRegistryElement {
		private Class<? extends Activity> mActivityClass;
		private int mRequestCode = -1;
		private IntentCreationFunction mIntentCreationFunction;
		private FragmentCreationFunction mFragmentCreationFunction;

		ScreenRegistryElement(Class<? extends Activity> activityClass, int requestCode, IntentCreationFunction intentCreationFunction) {
			mActivityClass = activityClass;
			mRequestCode = requestCode;
			mIntentCreationFunction = intentCreationFunction;
		}

		ScreenRegistryElement(FragmentCreationFunction fragmentCreationFunction) {
			mFragmentCreationFunction = fragmentCreationFunction;
		}

		Class<? extends Activity> getActivityClass() {
			return mActivityClass;
		}

		int getRequestCode() {
			return mRequestCode;
		}

		IntentCreationFunction getIntentCreationFunction() {
			return mIntentCreationFunction;
		}

		FragmentCreationFunction getFragmentCreationFunction() {
			return mFragmentCreationFunction;
		}
	}

	public interface ScreenResultCreationFunction<ScreenResultT extends ScreenResult> {
		ScreenResultT create(ActivityResult activityResult);
	}

	public interface ActivityResultCreationFunction<ScreenResultT extends ScreenResult> {
		ActivityResult create(ScreenResultT screenResult);
	}
}
