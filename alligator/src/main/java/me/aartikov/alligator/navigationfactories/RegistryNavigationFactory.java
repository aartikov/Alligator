package me.aartikov.alligator.navigationfactories;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.functions.ActivityConverter;
import me.aartikov.alligator.functions.DialogFragmentConverter;
import me.aartikov.alligator.functions.FragmentConverter;
import me.aartikov.alligator.functions.ScreenResultConverter;
import me.aartikov.alligator.helpers.ScreenClassHelper;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;

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

	/**
	 * Registers a screen represented by an activity using a custom {@link ActivityConverter}.
	 *
	 * @param screenClass   screen class
	 * @param activityClass activity class
	 * @param converter     activity converter
	 * @param <ScreenT>     screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, Class<? extends Activity> activityClass, ActivityConverter<ScreenT> converter) {
		registerScreenImplementation(screenClass, new ActivityScreenImplementation(screenClass, activityClass, converter, mScreenClassHelper));
		mScreenClassHelper.addActivityClass(activityClass, screenClass);
	}

	/**
	 * Registers a screen represented by an activity using the default {@link ActivityConverter}.
	 * <p>
	 * The default activity converter creates an intent that starts an activity of the class {@code activityClass}. It also puts a screen to the intent's extra if {@code ScreenT} is {@code Serializable} or {@code Parcelable}.
	 *
	 * @param screenClass   screen class
	 * @param activityClass activity class
	 * @param <ScreenT>     screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerActivity(Class<ScreenT> screenClass, Class<? extends Activity> activityClass) {
		registerActivity(screenClass, activityClass, new ActivityConverter<>(screenClass, activityClass));
	}

	/**
	 * Registers a screen represented by an activity for result using a custom {@link ActivityConverter} and a custom {@link ScreenResultConverter}.
	 *
	 * @param screenClass           screen class
	 * @param activityClass         activity class
	 * @param screenResultClass     screen result class
	 * @param converter             activity converter
	 * @param screenResultConverter screen result converter
	 * @param <ScreenT>             screen type
	 * @param <ScreenResultT>       screen result type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
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

	/**
	 * Registers a screen represented by an activity for result using the default {@link ActivityConverter} and the default {@link ScreenResultConverter}.
	 * <p>
	 * The default activity converter creates an intent that starts an activity of the class {@code activityClass}. It also puts a screen to the intent's extra if {@code ScreenT} is {@code Serializable} or {@code Parcelable}.
	 * The default screen result converter returns {@code ActivityResult(Activity.RESULT_OK, data)} (where {@code data} contains a serialized screen result) if a screen result is not {@code null},
	 * and {@code ActivityResult(Activity.RESULT_CANCELED, null)} otherwise.
	 *
	 * @param screenClass       screen class
	 * @param activityClass     activity class
	 * @param screenResultClass screen result class. {@code ScreenT} must be {@code Serializable} or {@code Parcelable}.
	 * @param <ScreenT>         screen type
	 * @param <ScreenResultT>   screen result type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen, ScreenResultT extends ScreenResult> void registerActivityForResult(Class<ScreenT> screenClass,
	                                                                                                   Class<? extends Activity> activityClass,
	                                                                                                   Class<ScreenResultT> screenResultClass) {

		registerActivityForResult(screenClass, activityClass, screenResultClass, new ActivityConverter<>(screenClass, activityClass), new ScreenResultConverter<>(screenResultClass));
	}

	/**
	 * Registers a screen represented by a fragment using a custom {@link FragmentConverter}.
	 *
	 * @param screenClass screen class
	 * @param converter   fragment converter
	 * @param <ScreenT>   screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, FragmentConverter<ScreenT> converter) {
		registerScreenImplementation(screenClass, new FragmentScreenImplementation(screenClass, converter, null, mScreenClassHelper));
	}

	/**
	 * Registers a screen represented by a fragment using the default {@link FragmentConverter}.
	 * <p>
	 * The default fragment converter creates a fragment of the class {@code fragmentClass}. It also puts a screen to the fragment's arguments if {@code ScreenT} is {@code Serializable} or {@code Parcelable}.
	 *
	 * @param screenClass   screen class
	 * @param fragmentClass fragment class
	 * @param <ScreenT>     screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerFragment(Class<ScreenT> screenClass, Class<? extends Fragment> fragmentClass) {
		registerFragment(screenClass, new FragmentConverter<>(screenClass, fragmentClass));
	}

	/**
	 * Registers a screen represented by a fragment for result using a custom {@link FragmentConverter}.
	 *
	 * @param screenClass       screen class
	 * @param converter         fragment converter
	 * @param screenResultClass screen result class
	 * @param <ScreenT>         screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerFragmentForResult(Class<ScreenT> screenClass, FragmentConverter<ScreenT> converter, Class<? extends ScreenResult> screenResultClass) {
		registerScreenImplementation(screenClass, new FragmentScreenImplementation(screenClass, converter, screenResultClass, mScreenClassHelper));
	}

	/**
	 * Registers a screen represented by a fragment for result using the default {@link FragmentConverter}.
	 * <p>
	 * The default fragment converter creates a fragment of the class {@code fragmentClass}. It also puts a screen to the fragment's arguments if {@code ScreenT} is {@code Serializable} or {@code Parcelable}.
	 *
	 * @param screenClass       screen class
	 * @param fragmentClass     fragment class
	 * @param screenResultClass screen result class
	 * @param <ScreenT>         screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerFragmentForResult(Class<ScreenT> screenClass, Class<? extends Fragment> fragmentClass, Class<? extends ScreenResult> screenResultClass) {
		registerFragmentForResult(screenClass, new FragmentConverter<>(screenClass, fragmentClass), screenResultClass);
	}

	/**
	 * Registers a screen represented by a dialog fragment using a custom {@link FragmentConverter}.
	 *
	 * @param screenClass screen class
	 * @param converter   dialog fragment converter
	 * @param <ScreenT>   screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerDialogFragment(Class<ScreenT> screenClass, DialogFragmentConverter<ScreenT> converter) {
		registerScreenImplementation(screenClass, new DialogFragmentScreenImplementation(screenClass, converter, null, mScreenClassHelper));
	}

	/**
	 * Registers a screen represented by a fragment using the default {@link DialogFragmentConverter}.
	 * <p>
	 * The default dialog fragment converter creates a dialog fragment of the class {@code dialogFragmentClass}. It also puts a screen to the fragment's arguments if {@code ScreenT} is {@code Serializable} or {@code Parcelable}.
	 *
	 * @param screenClass         screen class
	 * @param dialogFragmentClass dialog fragment class
	 * @param <ScreenT>           screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerDialogFragment(Class<ScreenT> screenClass, Class<? extends DialogFragment> dialogFragmentClass) {
		registerDialogFragment(screenClass, new DialogFragmentConverter<>(screenClass, dialogFragmentClass));
	}

	/**
	 * Registers a screen represented by a dialog fragment for result using a custom {@link DialogFragmentConverter}.
	 *
	 * @param screenClass       screen class
	 * @param converter         dialog fragment converter
	 * @param screenResultClass screen result class
	 * @param <ScreenT>         screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerDialogFragmentForResult(Class<ScreenT> screenClass, DialogFragmentConverter<ScreenT> converter, Class<? extends ScreenResult> screenResultClass) {
		registerScreenImplementation(screenClass, new DialogFragmentScreenImplementation(screenClass, converter, screenResultClass, mScreenClassHelper));
	}

	/**
	 * Registers a screen represented by a dialog fragment for result using the default {@link FragmentConverter}.
	 * <p>
	 * The default dialog fragment converter creates a dialog fragment of the class {@code dialogFragmentClass}. It also puts a screen to the fragment's arguments if {@code ScreenT} is {@code Serializable} or {@code Parcelable}.
	 *
	 * @param screenClass         screen class
	 * @param dialogFragmentClass fragment class
	 * @param screenResultClass   screen result class
	 * @param <ScreenT>           screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerDialogFragmentForResult(Class<ScreenT> screenClass, Class<? extends DialogFragment> dialogFragmentClass, Class<? extends ScreenResult> screenResultClass) {
		registerDialogFragmentForResult(screenClass, new DialogFragmentConverter<>(screenClass, dialogFragmentClass), screenResultClass);
	}


	protected void registerScreenImplementation(Class<? extends Screen> screenClass, ScreenImplementation screenImplementation) {
		if (getScreenImplementation(screenClass) != null) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is already registered.");
		}
		mImplementations.put(screenClass, screenImplementation);
	}
}
