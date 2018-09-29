package me.aartikov.alligator.navigationfactories;

import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.DefaultDialogFragmentConverter;
import me.aartikov.alligator.converters.DefaultFragmentConverter;
import me.aartikov.alligator.converters.DefaultIntentConverter;
import me.aartikov.alligator.converters.DefaultScreenResultConverter;
import me.aartikov.alligator.converters.DialogFragmentConverter;
import me.aartikov.alligator.converters.FragmentConverter;
import me.aartikov.alligator.converters.ImplicitIntentConverter;
import me.aartikov.alligator.converters.ImplicitScreenResultConverter;
import me.aartikov.alligator.converters.IntentConverter;
import me.aartikov.alligator.converters.ScreenResultConverter;
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
	@Nullable
	public ScreenImplementation getScreenImplementation(@NonNull Class<? extends Screen> screenClass) {
		return mImplementations.get(screenClass);
	}

	@Override
	@Nullable
	public Class<? extends Screen> getScreenClass(@NonNull Activity activity) {
		return mScreenClassHelper.getScreenClass(activity);
	}

	@Override
	@Nullable
	public Class<? extends Screen> getScreenClass(@NonNull Fragment fragment) {
		return mScreenClassHelper.getScreenClass(fragment);
	}

	@Override
	@Nullable
	public Class<? extends Screen> getScreenClass(int requestCode) {
		return mScreenClassHelper.getScreenClass(requestCode);
	}

	@Override
	@Nullable
	public Class<? extends Screen> getPreviousScreenClass(@NonNull Activity activity) {
		return mScreenClassHelper.getPreviousScreenClass(activity);
	}

	/**
	 * Registers a screen represented by an activity using a custom {@link IntentConverter}.
	 *
	 * @param screenClass   screen class
	 * @param activityClass activity class
	 * @param converter     intent converter
	 * @param <ScreenT>     screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerActivity(@NonNull Class<ScreenT> screenClass, @NonNull Class<? extends Activity> activityClass, @NonNull IntentConverter<ScreenT> converter) {
		ActivityScreenImplementation implementation = new ActivityScreenImplementation(screenClass, activityClass, converter, mScreenClassHelper);
		registerScreenImplementation(screenClass, implementation);
		mScreenClassHelper.addActivityClass(activityClass, screenClass);
	}

	/**
	 * Registers a screen represented by an activity using {@link DefaultIntentConverter}.
	 *
	 * @param screenClass   screen class
	 * @param activityClass activity class
	 * @param <ScreenT>     screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerActivity(@NonNull Class<ScreenT> screenClass, @NonNull Class<? extends Activity> activityClass) {
		IntentConverter<ScreenT> converter = new DefaultIntentConverter<>(screenClass, activityClass);
		registerActivity(screenClass, activityClass, converter);
	}

	/**
	 * Registers a screen represented by an activity using {@link ImplicitIntentConverter}.
	 *
	 * @param screenClass screen class
	 * @param converter   intent converter
	 * @param <ScreenT>   screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerActivity(@NonNull Class<ScreenT> screenClass, @NonNull ImplicitIntentConverter<ScreenT> converter) {
		ActivityScreenImplementation implementation = new ActivityScreenImplementation(screenClass, null, converter, mScreenClassHelper);
		registerScreenImplementation(screenClass, implementation);
	}

	/**
	 * Registers a screen represented by an activity for result using a custom {@link IntentConverter} and a custom {@link ScreenResultConverter}.
	 *
	 * @param screenClass           screen class
	 * @param activityClass         activity class
	 * @param screenResultClass     screen result class
	 * @param converter             intent converter
	 * @param screenResultConverter screen result converter
	 * @param <ScreenT>             screen type
	 * @param <ScreenResultT>       screen result type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen, ScreenResultT extends ScreenResult> void registerActivityForResult(@NonNull Class<ScreenT> screenClass,
	                                                                                                   @NonNull Class<? extends Activity> activityClass,
	                                                                                                   @NonNull Class<ScreenResultT> screenResultClass,
	                                                                                                   @NonNull IntentConverter<ScreenT> converter,
	                                                                                                   @NonNull ScreenResultConverter<ScreenResultT> screenResultConverter) {

		ActivityScreenImplementation implementation = new ActivityScreenImplementation(screenClass, activityClass, converter, screenResultClass, screenResultConverter, mRequestCode, mScreenClassHelper);
		registerScreenImplementation(screenClass, implementation);
		mScreenClassHelper.addActivityClass(activityClass, screenClass);
		mScreenClassHelper.addRequestCode(mRequestCode, screenClass);
		mRequestCode++;
	}

	/**
	 * Registers a screen represented by an activity for result using {@link DefaultIntentConverter} and the default {@link ScreenResultConverter}.
	 *
	 * @param screenClass       screen class
	 * @param activityClass     activity class
	 * @param screenResultClass screen result class. {@code ScreenT} must be {@code Serializable} or {@code Parcelable}.
	 * @param <ScreenT>         screen type
	 * @param <ScreenResultT>   screen result type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen, ScreenResultT extends ScreenResult> void registerActivityForResult(@NonNull Class<ScreenT> screenClass,
	                                                                                                   @NonNull Class<? extends Activity> activityClass,
	                                                                                                   @NonNull Class<ScreenResultT> screenResultClass) {

		IntentConverter<ScreenT> converter = new DefaultIntentConverter<>(screenClass, activityClass);
		ScreenResultConverter<ScreenResultT> screenResultConverter = new DefaultScreenResultConverter<>(screenResultClass);
		registerActivityForResult(screenClass, activityClass, screenResultClass, converter, screenResultConverter);
	}

	/**
	 * Registers a screen represented by an activity for result using {@link ImplicitIntentConverter} and {@link ImplicitScreenResultConverter}.
	 *
	 * @param screenClass           screen class
	 * @param screenResultClass     screen result class
	 * @param converter             implicit intent converter
	 * @param screenResultConverter implicit screen result converter
	 * @param <ScreenT>             screen type
	 * @param <ScreenResultT>       screen result type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen, ScreenResultT extends ScreenResult> void registerActivityForResult(@NonNull Class<ScreenT> screenClass,
	                                                                                                   @NonNull Class<ScreenResultT> screenResultClass,
	                                                                                                   @NonNull ImplicitIntentConverter<ScreenT> converter,
	                                                                                                   @NonNull ImplicitScreenResultConverter<ScreenResultT> screenResultConverter) {

		ActivityScreenImplementation implementation = new ActivityScreenImplementation(screenClass, null, converter, screenResultClass, screenResultConverter, mRequestCode, mScreenClassHelper);
		registerScreenImplementation(screenClass, implementation);
		mScreenClassHelper.addRequestCode(mRequestCode, screenClass);
		mRequestCode++;
	}

	/**
	 * Registers a screen represented by a fragment using a custom {@link FragmentConverter}.
	 *
	 * @param screenClass screen class
	 * @param converter   fragment converter
	 * @param <ScreenT>   screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerFragment(@NonNull Class<ScreenT> screenClass, @NonNull FragmentConverter<ScreenT> converter) {
		FragmentScreenImplementation implementation = new FragmentScreenImplementation(screenClass, converter, null, mScreenClassHelper);
		registerScreenImplementation(screenClass, implementation);
	}

	/**
	 * Registers a screen represented by a fragment using {@link DefaultFragmentConverter}.
	 *
	 * @param screenClass   screen class
	 * @param fragmentClass fragment class
	 * @param <ScreenT>     screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerFragment(@NonNull Class<ScreenT> screenClass, @NonNull Class<? extends Fragment> fragmentClass) {
		FragmentConverter<ScreenT> converter = new DefaultFragmentConverter<ScreenT>(screenClass, fragmentClass);
		registerFragment(screenClass, converter);
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
	public <ScreenT extends Screen> void registerFragmentForResult(@NonNull Class<ScreenT> screenClass, @NonNull FragmentConverter<ScreenT> converter, @NonNull Class<? extends ScreenResult> screenResultClass) {
		FragmentScreenImplementation implementation = new FragmentScreenImplementation(screenClass, converter, screenResultClass, mScreenClassHelper);
		registerScreenImplementation(screenClass, implementation);
	}

	/**
	 * Registers a screen represented by a fragment for result using {@link DefaultFragmentConverter}.
	 *
	 * @param screenClass       screen class
	 * @param fragmentClass     fragment class
	 * @param screenResultClass screen result class
	 * @param <ScreenT>         screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerFragmentForResult(@NonNull Class<ScreenT> screenClass, @NonNull Class<? extends Fragment> fragmentClass, @NonNull Class<? extends ScreenResult> screenResultClass) {
		FragmentConverter<ScreenT> converter = new DefaultFragmentConverter<>(screenClass, fragmentClass);
		registerFragmentForResult(screenClass, converter, screenResultClass);
	}

	/**
	 * Registers a screen represented by a dialog fragment using a custom {@link FragmentConverter}.
	 *
	 * @param screenClass screen class
	 * @param converter   dialog fragment converter
	 * @param <ScreenT>   screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerDialogFragment(@NonNull Class<ScreenT> screenClass, @NonNull DialogFragmentConverter<ScreenT> converter) {
		DialogFragmentScreenImplementation implementation = new DialogFragmentScreenImplementation(screenClass, converter, null, mScreenClassHelper);
		registerScreenImplementation(screenClass, implementation);
	}

	/**
	 * Registers a screen represented by a fragment using {@link DefaultDialogFragmentConverter}.
	 *
	 * @param screenClass         screen class
	 * @param dialogFragmentClass dialog fragment class
	 * @param <ScreenT>           screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerDialogFragment(@NonNull Class<ScreenT> screenClass, @NonNull Class<? extends DialogFragment> dialogFragmentClass) {
		DialogFragmentConverter<ScreenT> converter = new DefaultDialogFragmentConverter<>(screenClass, dialogFragmentClass);
		registerDialogFragment(screenClass, converter);
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
	public <ScreenT extends Screen> void registerDialogFragmentForResult(@NonNull Class<ScreenT> screenClass, @NonNull DialogFragmentConverter<ScreenT> converter, @NonNull Class<? extends ScreenResult> screenResultClass) {
		DialogFragmentScreenImplementation implementation = new DialogFragmentScreenImplementation(screenClass, converter, screenResultClass, mScreenClassHelper);
		registerScreenImplementation(screenClass, implementation);
	}

	/**
	 * Registers a screen represented by a dialog fragment for result using {@link DefaultDialogFragmentConverter}.
	 *
	 * @param screenClass         screen class
	 * @param dialogFragmentClass fragment class
	 * @param screenResultClass   screen result class
	 * @param <ScreenT>           screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerDialogFragmentForResult(@NonNull Class<ScreenT> screenClass, @NonNull Class<? extends DialogFragment> dialogFragmentClass, @NonNull Class<? extends ScreenResult> screenResultClass) {
		DialogFragmentConverter<ScreenT> converter = new DefaultDialogFragmentConverter<ScreenT>(screenClass, dialogFragmentClass);
		registerDialogFragmentForResult(screenClass, converter, screenResultClass);
	}


	protected void registerScreenImplementation(@NonNull Class<? extends Screen> screenClass, @NonNull ScreenImplementation screenImplementation) {
		if (getScreenImplementation(screenClass) != null) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is already registered.");
		}
		mImplementations.put(screenClass, screenImplementation);
	}
}
