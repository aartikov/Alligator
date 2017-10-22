package me.aartikov.alligator.navigationfactories;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.functions.ActivityConverter;
import me.aartikov.alligator.functions.DialogFragmentConverter;
import me.aartikov.alligator.functions.FragmentConverter;
import me.aartikov.alligator.functions.ScreenResultConverter;
import me.aartikov.alligator.helpers.ScreenClassHelper;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;

/**
 * Date: 11.02.2017
 * Time: 11:41
 *
 * @author Artur Artikov
 */

/**
 * Navigation factory with screen registration methods.
 */

public class RegistryNavigationFactory implements NavigationFactory {
	private Map<Class<? extends Screen>, ScreenImplementation> mImplementations = new LinkedHashMap<>();
	private ScreenClassHelper mScreenClassHelper = new ScreenClassHelper();
	private int mRequestCode = 1000;

	@Override
	public @Nullable ScreenImplementation getScreenImplementation(Class<? extends Screen> screenClass) {
		return mImplementations.get(screenClass);
	}

	@Override
	public @Nullable Class<? extends Screen> getScreenClass(Activity activity) {
		return mScreenClassHelper.getScreenClass(activity);
	}

	@Override
	public @Nullable Class<? extends Screen> getScreenClass(Fragment fragment) {
		return mScreenClassHelper.getScreenClass(fragment);
	}

	@Override
	public @Nullable Class<? extends Screen> getScreenClass(int requestCode) {
		return mScreenClassHelper.getScreenClass(requestCode);
	}

	@Override
	public @Nullable Class<? extends Screen> getPreviousScreenClass(Activity activity) {
		return mScreenClassHelper.getPreviousScreenClass(activity);
	}

	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, Class<? extends Activity> activityClass, ActivityConverter<ScreenT> converter) {
		registerScreenImplementation(screenClass, new ActivityScreenImplementation(screenClass, activityClass, converter, mScreenClassHelper));
		mScreenClassHelper.addActivityClass(activityClass, screenClass);
	}

	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, Class<? extends Activity> activityClass) {
		registerActivity(screenClass, activityClass, new ActivityConverter<>(screenClass, activityClass));
	}

	public <ScreenT extends Screen, ScreenResultT extends ScreenResult> void registerActivityForResult(Class<ScreenT> screenClass,
	                                                                                                   Class<? extends Activity> activityClass,
	                                                                                                   Class<ScreenResultT> screenResultClass,
	                                                                                                   ActivityConverter<ScreenT> converter,
	                                                                                                   ScreenResultConverter<ScreenResultT> screenResultConverter) {

		registerScreenImplementation(screenClass, new ActivityScreenImplementation(screenClass, activityClass, converter, screenResultClass, screenResultConverter, mRequestCode, mScreenClassHelper));
		mScreenClassHelper.addActivityClass(activityClass, screenClass);
		mScreenClassHelper.addRequestCode(mRequestCode, screenClass);
		mRequestCode++;
	}

	public <ScreenT extends Screen, ScreenResultT extends ScreenResult> void registerActivityForResult(Class<ScreenT> screenClass,
	                                                                                                   Class<? extends Activity> activityClass,
	                                                                                                   Class<ScreenResultT> screenResultClass) {

		registerActivityForResult(screenClass, activityClass, screenResultClass, new ActivityConverter<>(screenClass, activityClass), new ScreenResultConverter<>(screenResultClass));
	}

	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, FragmentConverter<ScreenT> converter) {
		registerScreenImplementation(screenClass, new FragmentScreenImplementation(screenClass, converter, mScreenClassHelper));
	}

	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, Class<? extends Fragment> fragmentClass) {
		registerFragment(screenClass, new FragmentConverter<>(screenClass, fragmentClass));
	}

	public <ScreenT extends Screen> void registerDialogFragment(Class<ScreenT> screenClass, DialogFragmentConverter<ScreenT> converter) {
		registerScreenImplementation(screenClass, new DialogFragmentScreenImplementation(screenClass, converter, mScreenClassHelper));
	}

	public <ScreenT extends Screen> void registerDialogFragment(Class<ScreenT> screenClass, Class<? extends DialogFragment> dialogFragmentClass) {
		registerDialogFragment(screenClass, new DialogFragmentConverter<>(screenClass, dialogFragmentClass));
	}

	protected void registerScreenImplementation(Class<? extends Screen> screenClass, ScreenImplementation screenImplementation) {
		if (getScreenImplementation(screenClass) != null) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is already registered.");
		}
		mImplementations.put(screenClass, screenImplementation);
	}
}
