package com.art.alligator.navigationfactories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import com.art.alligator.functions.Function;
import com.art.alligator.functions.Function2;
import com.art.alligator.navigationfactories.registry.ActivityRegistry;
import com.art.alligator.navigationfactories.registry.DialogFragmentRegistry;
import com.art.alligator.navigationfactories.registry.FragmentRegistry;
import com.art.alligator.navigationfactories.registry.ScreenForResultRegistry;

/**
 * Date: 11.02.2017
 * Time: 11:41
 *
 * @author Artur Artikov
 */

public class RegistryNavigationFactory implements NavigationFactory {
	private ActivityRegistry mActivityRegistry = new ActivityRegistry();
	private FragmentRegistry mFragmentRegistry = new FragmentRegistry();
	private DialogFragmentRegistry mDialogFragmentRegistry = new DialogFragmentRegistry();
	private ScreenForResultRegistry mScreenForResultRegistry = new ScreenForResultRegistry();
	private List<Class<? extends Screen>> mScreenClasses = new ArrayList<>();

	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, Class<? extends Activity> activityClass,
	                                                      Function2<Context, ScreenT, Intent> intentCreationFunction, Function<Intent, ScreenT> screenResolvingFunction) {
		checkThatNotRegistered(screenClass);
		mActivityRegistry.register(screenClass, activityClass, intentCreationFunction, screenResolvingFunction);
		mScreenClasses.add(screenClass);
	}

	public <ScreenT extends Screen> void registerActivity(final Class<ScreenT> screenClass, Class<? extends Activity> activityClass, Function2<Context, ScreenT, Intent> intentCreationFunction) {
		registerActivity(screenClass, activityClass, intentCreationFunction, ActivityRegistry.getNotImplementedScreenResolvingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerActivity(final Class<ScreenT> screenClass, final Class<? extends Activity> activityClass) {
		registerActivity(screenClass, activityClass, ActivityRegistry.getDefaultIntentCreationFunction(screenClass, activityClass), ActivityRegistry.getDefaultScreenResolvingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, Function<ScreenT, Fragment> fragmentCreationFunction,
	                                                      Function<Fragment, ScreenT> screenResolvingFunction) {
		checkThatNotRegistered(screenClass);
		mFragmentRegistry.register(screenClass, fragmentCreationFunction, screenResolvingFunction);
		mScreenClasses.add(screenClass);
	}

	public <ScreenT extends Screen> void registerFragment(final Class<ScreenT> screenClass, Function<ScreenT, Fragment> fragmentCreationFunction) {
		registerFragment(screenClass, fragmentCreationFunction, FragmentRegistry.getNotImplementedScreenResolvingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerFragment(final Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		registerFragment(screenClass, FragmentRegistry.getDefaultFragmentCreationFunction(screenClass, fragmentClass), FragmentRegistry.getDefaultScreenResolvingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerDialogFragment(Class<ScreenT> screenClass, Function<ScreenT, DialogFragment> dialogFragmentCreationFunction,
	                                                            Function<DialogFragment, ScreenT> screenResolvingFunction) {
		checkThatNotRegistered(screenClass);
		mDialogFragmentRegistry.register(screenClass, dialogFragmentCreationFunction, screenResolvingFunction);
		mScreenClasses.add(screenClass);
	}

	public <ScreenT extends Screen> void registerDialogFragment(final Class<ScreenT> screenClass, Function<ScreenT, DialogFragment> dialogFragmentCreationFunction) {
		registerDialogFragment(screenClass, dialogFragmentCreationFunction, DialogFragmentRegistry.getNotImplementedScreenResolvingFunction(screenClass));
	}

	public <ScreenT extends Screen> void registerDialogFragment(final Class<ScreenT> screenClass, final Class<? extends DialogFragment> dialogFragmentClass) {
		registerDialogFragment(screenClass, DialogFragmentRegistry.getDefaultDialogFragmentCreationFunction(screenClass, dialogFragmentClass), DialogFragmentRegistry.getDefaultScreenResolvingFunction(screenClass));
	}

	public <ScreenResultT extends ScreenResult> void registerScreenForResult(Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass,
	                                                                         Function<ScreenResultT, ActivityResult> activityResultCreationFunction,
	                                                                         Function<ActivityResult, ScreenResultT> screenResultResolvingFunction) {
		checkThatCanBeRegisteredForResult(screenClass);
		mScreenForResultRegistry.register(screenClass, screenResultClass, activityResultCreationFunction, screenResultResolvingFunction);
	}

	public <ScreenResultT extends ScreenResult> void registerScreenForResult(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass,
	                                                                         Function<ActivityResult, ScreenResultT> screenResultResolvingFunction) {
		registerScreenForResult(screenClass, screenResultClass, ScreenForResultRegistry.getNotImplementedActivityResultCreationFunction(screenClass, screenResultClass), screenResultResolvingFunction);
	}

	public <ScreenResultT extends ScreenResult> void registerScreenForResult(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass) {
		registerScreenForResult(screenClass, screenResultClass, ScreenForResultRegistry.getDefaultActivityResultCreationFunction(screenResultClass), ScreenForResultRegistry.getDefaultScreenResultResolvingFunction(screenResultClass));
	}

	@Override
	public ViewType getViewType(Class<? extends Screen> screenClass) {
		if (mActivityRegistry.isRegistered(screenClass)) {
			return ViewType.ACTIVITY;
		} else if (mFragmentRegistry.isRegistered(screenClass)) {
			return ViewType.FRAGMENT;
		} else if (mDialogFragmentRegistry.isRegistered(screenClass)) {
			return ViewType.DIALOG_FRAGMENT;
		} else {
			return ViewType.UNKNOWN;
		}
	}

	@Override
	public Class<? extends Activity> getActivityClass(Class<? extends Screen> screenClass) {
		return mActivityRegistry.getActivityClass(screenClass);
	}

	@Override
	public Intent createActivityIntent(Context context, Screen screen) {
		return mActivityRegistry.createActivityIntent(context, screen);
	}

	@Override
	public <ScreenT extends Screen> ScreenT getScreen(Intent intent, Class<ScreenT> screenClass) {
		return mActivityRegistry.getScreen(intent, screenClass);
	}

	@Override
	public Fragment createFragment(Screen screen) {
		return mFragmentRegistry.createFragment(screen);
	}

	@Override
	public <ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass) {
		return mFragmentRegistry.getScreen(fragment, screenClass);
	}

	@Override
	public DialogFragment createDialogFragment(Screen screen) {
		return mDialogFragmentRegistry.createDialogFragment(screen);
	}

	@Override
	public <ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment, Class<ScreenT> screenClass) {
		return mDialogFragmentRegistry.getScreen(dialogFragment, screenClass);
	}

	@Override
	public boolean isScreenForResult(Class<? extends Screen> screenClass) {
		return mScreenForResultRegistry.isRegistered(screenClass);
	}

	@Override
	public int getRequestCode(Class<? extends Screen> screenClass) {
		return mScreenForResultRegistry.getRequestCode(screenClass);
	}

	@Override
	public Class<? extends ScreenResult> getScreenResultClass(Class<? extends Screen> screenClass) {
		return mScreenForResultRegistry.getScreenResultClass(screenClass);
	}

	@Override
	public ActivityResult createActivityResult(Class<? extends Screen> screenClass, ScreenResult screenResult) {
		return mScreenForResultRegistry.createActivityResult(screenClass, screenResult);
	}

	@Override
	public ScreenResult getScreenResult(Class<? extends Screen> screenClass, ActivityResult activityResult) {
		return mScreenForResultRegistry.getScreenResult(screenClass, activityResult);
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
}
