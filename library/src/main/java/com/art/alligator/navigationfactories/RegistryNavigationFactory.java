package com.art.alligator.navigationfactories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.art.alligator.ActivityResult;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.ScreenResult;
import com.art.alligator.ViewType;


import static com.art.alligator.navigationfactories.DefaultConvertingFunctions.*;

/**
 * Date: 11.02.2017
 * Time: 11:41
 *
 * @author Artur Artikov
 */

public class RegistryNavigationFactory implements NavigationFactory {
	private Map<Class<? extends Screen>, ActivityScreenElement> mActivityScreenRegistry = new HashMap<>();
	private Map<Class<? extends Screen>, FragmentScreenElement> mFragmentScreenRegistry = new HashMap<>();
	private Map<Class<? extends Screen>, DialogFragmentScreenElement> mDialogFragmentScreenRegistry = new HashMap<>();
	private Map<Class<? extends Screen>, ScreenForResultElement> mScreenForResultRegistry = new HashMap<>();
	private List<Class<? extends Screen>> mScreenClasses = new ArrayList<>();

	public interface Function<T, R> {
		R call(T t);
	}

	public interface Function2<T1, T2, R> {
		R call(T1 t1, T2 t2);
	}

	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, Class<? extends Activity> activityClass,
	                                                      Function2<Context, ScreenT, Intent> intentCreationFunction, Function<Intent, ScreenT> screenGettingFunction) {
		checkThatNotRegistered(screenClass);
		mActivityScreenRegistry.put(screenClass, new ActivityScreenElement<>(activityClass, intentCreationFunction, screenGettingFunction));
		mScreenClasses.add(screenClass);
	}

	public <ScreenT extends Screen> void registerActivity(final Class<ScreenT> screenClass, Class<? extends Activity> activityClass, Function2<Context, ScreenT, Intent> intentCreationFunction) {
		registerActivity(screenClass, activityClass, intentCreationFunction, getNotImplementedActivityScreenGettingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerActivity(final Class<ScreenT> screenClass, final Class<? extends Activity> activityClass) {
		registerActivity(screenClass, activityClass, getDefaultIntentCreationFunction(screenClass, activityClass), getDefaultActivityScreenGettingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, Function<ScreenT, Fragment> fragmentCreationFunction,
	                                                      Function<Fragment, ScreenT> screenGettingFunction) {
		checkThatNotRegistered(screenClass);
		mFragmentScreenRegistry.put(screenClass, new FragmentScreenElement<>(fragmentCreationFunction, screenGettingFunction));
		mScreenClasses.add(screenClass);
	}

	public <ScreenT extends Screen> void registerFragment(final Class<ScreenT> screenClass, Function<ScreenT, Fragment> fragmentCreationFunction) {
		registerFragment(screenClass, fragmentCreationFunction, getNotImplementedFragmentScreenGettingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerFragment(final Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		registerFragment(screenClass, getDefaultFragmentCreationFunction(screenClass, fragmentClass), getDefaultFragmentScreenGettingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerDialogFragment(Class<ScreenT> screenClass, Function<ScreenT, DialogFragment> fragmentCreationFunction,
	                                                            Function<DialogFragment, ScreenT> screenGettingFunction) {
		checkThatNotRegistered(screenClass);
		mDialogFragmentScreenRegistry.put(screenClass, new DialogFragmentScreenElement<>(fragmentCreationFunction, screenGettingFunction));
		mScreenClasses.add(screenClass);
	}

	public <ScreenT extends Screen> void registerDialogFragment(final Class<ScreenT> screenClass, Function<ScreenT, DialogFragment> fragmentCreationFunction) {
		registerDialogFragment(screenClass, fragmentCreationFunction, getNotImplementedDialogFragmentScreenGettingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerDialogFragment(final Class<ScreenT> screenClass, final Class<? extends DialogFragment> dialogFragmentClass) {
		registerDialogFragment(screenClass, getDefaultDialogFragmentCreationFunction(screenClass, dialogFragmentClass), getDefaultDialogFragmentScreenGettingFunction(screenClass));
	}

	public <ScreenResultT extends ScreenResult> void registerScreenForResult(Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass,
	                                                                         Function<ScreenResultT, ActivityResult> activityResultCreationFunction,
	                                                                         Function<ActivityResult, ScreenResultT> screenResultGettingFunction) {
		checkThatCanBeRegisteredForResult(screenClass);
		int requestCode = mScreenForResultRegistry.size() + 1;
		mScreenForResultRegistry.put(screenClass, new ScreenForResultElement<>(requestCode, screenResultClass, activityResultCreationFunction, screenResultGettingFunction));
	}

	public <ScreenResultT extends ScreenResult> void registerScreenForResult(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass,
	                                                                         Function<ActivityResult, ScreenResultT> screenResultGettingFunction) {
		registerScreenForResult(screenClass, screenResultClass, getNotImplementedActivityResultCreationFunction(screenClass, screenResultClass), screenResultGettingFunction);
	}

	public <ScreenResultT extends ScreenResult> void registerScreenForResult(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass) {
		registerScreenForResult(screenClass, screenResultClass, getDefaultActivityResultCreationFunction(screenResultClass), getDefaultScreenResultGettingFunction(screenResultClass));
	}

	@Override
	public ViewType getViewType(Class<? extends Screen> screenClass) {
		if (mActivityScreenRegistry.containsKey(screenClass)) {
			return ViewType.ACTIVITY;
		} else if (mFragmentScreenRegistry.containsKey(screenClass)) {
			return ViewType.FRAGMENT;
		} else if (mDialogFragmentScreenRegistry.containsKey(screenClass)) {
			return ViewType.DIALOG_FRAGMENT;
		} else {
			return ViewType.UNKNOWN;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass) {
		checkThatActivityScreen(screenClass);
		ActivityScreenElement<Screen> element = mActivityScreenRegistry.get(screenClass);
		return element.getActivityClass();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Intent createIntent(Context context, Screen screen) {
		checkThatActivityScreen(screen.getClass());
		ActivityScreenElement<Screen> element = mActivityScreenRegistry.get(screen.getClass());
		return element.getIntentCreationFunction().call(context, screen);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(Intent intent, Class<ScreenT> screenClass) {
		checkThatActivityScreen(screenClass);
		ActivityScreenElement<ScreenT> element = mActivityScreenRegistry.get(screenClass);
		return element.getScreenGettingFunction().call(intent);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Fragment createFragment(Screen screen) {
		checkThatFragmentScreen(screen.getClass());
		FragmentScreenElement<Screen> element = mFragmentScreenRegistry.get(screen.getClass());
		return element.getFragmentCreationFunction().call(screen);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass) {
		checkThatFragmentScreen(screenClass);
		FragmentScreenElement<ScreenT> element = mFragmentScreenRegistry.get(screenClass);
		return element.getScreenGettingFunction().call(fragment);
	}

	@Override
	@SuppressWarnings("unchecked")
	public DialogFragment createDialogFragment(Screen screen) {
		checkThatDialogFragmentScreen(screen.getClass());
		DialogFragmentScreenElement<Screen> element = mDialogFragmentScreenRegistry.get(screen.getClass());
		return element.getDialogFragmentCreationFunction().call(screen);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment, Class<ScreenT> screenClass) {
		checkThatDialogFragmentScreen(screenClass);
		DialogFragmentScreenElement<ScreenT> element = mDialogFragmentScreenRegistry.get(screenClass);
		return element.getScreenGettingFunction().call(dialogFragment);
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
	@SuppressWarnings("unchecked")
	public Class<? extends ScreenResult> getScreenResultClass(Class<? extends Screen> screenClass) {
		checkThatScreenForResult(screenClass);
		ScreenForResultElement<ScreenResult> element = mScreenForResultRegistry.get(screenClass);
		return element.getScreenResultClass();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ActivityResult createActivityResult(Class<? extends Screen> screenClass, ScreenResult screenResult) {
		checkThatScreenForResult(screenClass);
		ScreenForResultElement<ScreenResult> element = mScreenForResultRegistry.get(screenClass);
		return element.getActivityResultCreationFunction().call(screenResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ScreenResult getScreenResult(Class<? extends Screen> screenClass, ActivityResult activityResult) {
		checkThatScreenForResult(screenClass);
		ScreenForResultElement<ScreenResult> element = mScreenForResultRegistry.get(screenClass);
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
		if (getViewType(screenClass) != ViewType.ACTIVITY) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not registered as activity screen.");
		}
	}

	private void checkThatFragmentScreen(Class<? extends Screen> screenClass) {
		if (getViewType(screenClass) != ViewType.FRAGMENT) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not registered as fragment screen.");
		}
	}

	private void checkThatDialogFragmentScreen(Class<? extends Screen> screenClass) {
		if (getViewType(screenClass) != ViewType.DIALOG_FRAGMENT) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not registered as dialog fragment screen.");
		}
	}

	private void checkThatScreenForResult(Class<? extends Screen> screenClass) {
		if (!isScreenForResult(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not registered as screen for result.");
		}
	}

	private void checkThatCanBeRegisteredForResult(Class<? extends Screen> screenClass) {
		if (isScreenForResult(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is already registered for result.");
		}

		ViewType viewType = getViewType(screenClass);
		if (viewType == ViewType.UNKNOWN) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be registered as activity screen before registering it for result.");
		}

		if (viewType != ViewType.ACTIVITY) {
			throw new IllegalArgumentException("Can't register screen " + screenClass.getSimpleName() + " for result. Only activity screen can be registered for result.");
		}
	}

	private static class ActivityScreenElement<ScreenT> {
		private Class<? extends Activity> mActivityClass;
		private Function2<Context, ScreenT, Intent> mIntentCreationFunction;
		private Function<Intent, ScreenT> mScreenGettingFunction;

		ActivityScreenElement(Class<? extends Activity> activityClass, Function2<Context, ScreenT, Intent> intentCreationFunction, Function<Intent, ScreenT> screenGettingFunction) {
			mActivityClass = activityClass;
			mIntentCreationFunction = intentCreationFunction;
			mScreenGettingFunction = screenGettingFunction;
		}

		Class<? extends Activity> getActivityClass() {
			return mActivityClass;
		}

		Function2<Context, ScreenT, Intent> getIntentCreationFunction() {
			return mIntentCreationFunction;
		}

		Function<Intent, ScreenT> getScreenGettingFunction() {
			return mScreenGettingFunction;
		}
	}

	private static class FragmentScreenElement<ScreenT> {
		private Function<ScreenT, Fragment> mFragmentCreationFunction;
		private Function<Fragment, ScreenT> mScreenGettingFunction;

		FragmentScreenElement(Function<ScreenT, Fragment> fragmentCreationFunction, Function<Fragment, ScreenT> screenGettingFunction) {
			mFragmentCreationFunction = fragmentCreationFunction;
			mScreenGettingFunction = screenGettingFunction;
		}

		Function<ScreenT, Fragment> getFragmentCreationFunction() {
			return mFragmentCreationFunction;
		}

		Function<Fragment, ScreenT> getScreenGettingFunction() {
			return mScreenGettingFunction;
		}
	}

	private static class DialogFragmentScreenElement<ScreenT> {
		private Function<ScreenT, DialogFragment> mDialogFragmentCreationFunction;
		private Function<DialogFragment, ScreenT> mScreenGettingFunction;

		DialogFragmentScreenElement(Function<ScreenT, DialogFragment> dialogFragmentCreationFunction, Function<DialogFragment, ScreenT> screenGettingFunction) {
			mDialogFragmentCreationFunction = dialogFragmentCreationFunction;
			mScreenGettingFunction = screenGettingFunction;
		}

		Function<ScreenT, DialogFragment> getDialogFragmentCreationFunction() {
			return mDialogFragmentCreationFunction;
		}

		Function<DialogFragment, ScreenT> getScreenGettingFunction() {
			return mScreenGettingFunction;
		}
	}

	private static class ScreenForResultElement<ScreenResultT> {
		private int mRequestCode = -1;
		private Class<ScreenResultT> mScreenResultClass;
		private Function<ScreenResultT, ActivityResult> mActivityResultCreationFunction;
		private Function<ActivityResult, ScreenResultT> mScreenResultGettingFunction;

		ScreenForResultElement(int requestCode, Class<ScreenResultT> screenResultClass, Function<ScreenResultT,
				ActivityResult> activityResultCreationFunction, Function<ActivityResult, ScreenResultT> screenResultGettingFunction) {
			mRequestCode = requestCode;
			mScreenResultClass = screenResultClass;
			mActivityResultCreationFunction = activityResultCreationFunction;
			mScreenResultGettingFunction = screenResultGettingFunction;
		}

		int getRequestCode() {
			return mRequestCode;
		}

		Class<ScreenResultT> getScreenResultClass() {
			return mScreenResultClass;
		}

		Function<ScreenResultT, ActivityResult> getActivityResultCreationFunction() {
			return mActivityResultCreationFunction;
		}

		Function<ActivityResult, ScreenResultT> getScreenResultGettingFunction() {
			return mScreenResultGettingFunction;
		}
	}
}
