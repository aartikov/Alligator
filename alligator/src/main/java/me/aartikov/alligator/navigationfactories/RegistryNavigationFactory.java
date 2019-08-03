package me.aartikov.alligator.navigationfactories;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.LinkedHashMap;
import java.util.Map;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.DefaultDialogFragmentConverter;
import me.aartikov.alligator.converters.DefaultFragmentConverter;
import me.aartikov.alligator.converters.DefaultIntentConverter;
import me.aartikov.alligator.converters.DefaultScreenResultConverter;
import me.aartikov.alligator.converters.DialogFragmentConverter;
import me.aartikov.alligator.converters.FragmentConverter;
import me.aartikov.alligator.converters.IntentConverter;
import me.aartikov.alligator.converters.OneWayIntentConverter;
import me.aartikov.alligator.converters.OneWayScreenResultConverter;
import me.aartikov.alligator.converters.ScreenResultConverter;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.Destination;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.helpers.ScreenClassHelper;


/**
 * Navigation factory with screen registration methods.
 */

public class RegistryNavigationFactory implements NavigationFactory {
	private Map<Class<? extends Screen>, Destination> mDestinations = new LinkedHashMap<>();
	private ScreenClassHelper mScreenClassHelper = new ScreenClassHelper();
	private int mRequestCode = 1000;

	@Override
	@Nullable
	public Destination getDestination(@NonNull Class<? extends Screen> screenClass) {
		return mDestinations.get(screenClass);
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
		ActivityDestination destination = new ActivityDestination(screenClass, activityClass, converter, mScreenClassHelper);
		registerDestination(screenClass, destination);
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
	 * Registers a screen represented by an activity using {@link OneWayIntentConverter}. Should be used for external activities only.
	 *
	 * @param screenClass screen class
	 * @param converter   one-way intent converter
	 * @param <ScreenT>   screen type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen> void registerActivity(@NonNull Class<ScreenT> screenClass, @NonNull OneWayIntentConverter<ScreenT> converter) {
		ActivityDestination destination = new ActivityDestination(screenClass, null, converter, mScreenClassHelper);
		registerDestination(screenClass, destination);
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

		ActivityDestination destination = new ActivityDestination(screenClass, activityClass, converter, screenResultClass, screenResultConverter, mRequestCode, mScreenClassHelper);
		registerDestination(screenClass, destination);
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
	 * Registers a screen represented by an activity for result using {@link OneWayIntentConverter} and {@link OneWayScreenResultConverter}. Should be used for external activities only.
	 *
	 * @param screenClass           screen class
	 * @param screenResultClass     screen result class
	 * @param converter             one-way intent converter
	 * @param screenResultConverter one-way screen result converter
	 * @param <ScreenT>             screen type
	 * @param <ScreenResultT>       screen result type
	 * @throws IllegalArgumentException if the screen is already registered
	 */
	public <ScreenT extends Screen, ScreenResultT extends ScreenResult> void registerActivityForResult(@NonNull Class<ScreenT> screenClass,
	                                                                                                   @NonNull Class<ScreenResultT> screenResultClass,
	                                                                                                   @NonNull OneWayIntentConverter<ScreenT> converter,
	                                                                                                   @NonNull OneWayScreenResultConverter<ScreenResultT> screenResultConverter) {

		ActivityDestination destination = new ActivityDestination(screenClass, null, converter, screenResultClass, screenResultConverter, mRequestCode, mScreenClassHelper);
		registerDestination(screenClass, destination);
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
		FragmentDestination destination = new FragmentDestination(screenClass, converter, null, mScreenClassHelper);
		registerDestination(screenClass, destination);
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
		FragmentConverter<ScreenT> converter = new DefaultFragmentConverter<>(screenClass, fragmentClass);
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
		FragmentDestination destination = new FragmentDestination(screenClass, converter, screenResultClass, mScreenClassHelper);
		registerDestination(screenClass, destination);
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
		DialogFragmentDestination destination = new DialogFragmentDestination(screenClass, converter, null, mScreenClassHelper);
		registerDestination(screenClass, destination);
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
		DialogFragmentDestination destination = new DialogFragmentDestination(screenClass, converter, screenResultClass, mScreenClassHelper);
		registerDestination(screenClass, destination);
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


	protected void registerDestination(@NonNull Class<? extends Screen> screenClass, @NonNull Destination destination) {
		if (getDestination(screenClass) != null) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is already registered.");
		}
		mDestinations.put(screenClass, destination);
	}
}
