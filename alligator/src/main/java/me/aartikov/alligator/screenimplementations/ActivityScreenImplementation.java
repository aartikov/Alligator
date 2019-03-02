package me.aartikov.alligator.screenimplementations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.IntentConverter;
import me.aartikov.alligator.converters.ScreenResultConverter;
import me.aartikov.alligator.helpers.ScreenClassHelper;

/**
 * Date: 15.10.2017
 * Time: 11:21
 *
 * @author Artur Artikov
 */

public class ActivityScreenImplementation implements ScreenImplementation {
	private Class<? extends Screen> mScreenClass;
	@Nullable
	private Class<? extends Activity> mActivityClass;
	private IntentConverter<? extends Screen> mIntentConverter;

	@Nullable
	private Class<? extends ScreenResult> mScreenResultClass;
	private int mRequestCode;
	@Nullable
	private ScreenResultConverter<? extends ScreenResult> mScreenResultConverter;

	private ScreenClassHelper mScreenClassHelper;

	public ActivityScreenImplementation(@NonNull Class<? extends Screen> screenClass,
	                                    @Nullable Class<? extends Activity> activityClass,
	                                    @NonNull IntentConverter<? extends Screen> intentConverter,
	                                    @Nullable Class<? extends ScreenResult> screenResultClass,
	                                    @Nullable ScreenResultConverter<? extends ScreenResult> screenResultConverter,
	                                    int requestCode,
	                                    @NonNull ScreenClassHelper screenClassHelper) {
		mScreenClass = screenClass;
		mActivityClass = activityClass;
		mIntentConverter = intentConverter;
		mScreenResultClass = screenResultClass;
		mScreenResultConverter = screenResultConverter;
		mRequestCode = requestCode;
		mScreenClassHelper = screenClassHelper;
	}

	public ActivityScreenImplementation(@NonNull Class<? extends Screen> screenClass,
	                                    @Nullable Class<? extends Activity> activityClass,
	                                    @NonNull IntentConverter<? extends Screen> intentConverter,
	                                    @NonNull ScreenClassHelper screenClassHelper) {
		this(screenClass, activityClass, intentConverter, null, null, -1, screenClassHelper);
	}

	@SuppressWarnings("unchecked")
	@NonNull
	public Intent createIntent(@NonNull Context context, @NonNull Screen screen, @Nullable Class<? extends Screen> previousScreenClass) {
		checkScreenClass(screen.getClass());
		Intent intent = ((IntentConverter<Screen>) mIntentConverter).createIntent(context, screen);
		mScreenClassHelper.putScreenClass(intent, screen.getClass());
		if (previousScreenClass != null) {
			mScreenClassHelper.putPreviousScreenClass(intent, previousScreenClass);
		}
		return intent;
	}

	@Nullable
	public Intent createEmptyIntent(@NonNull Context context, @NonNull Class<? extends Screen> screenClass) {
		if (mActivityClass == null) {
			return null;
		}

		Intent intent = new Intent(context, mActivityClass);
		mScreenClassHelper.putScreenClass(intent, screenClass);
		return intent;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	public ActivityResult createActivityResult(ScreenResult screenResult) {
		checkScreenResultClass(screenResult.getClass());
		if (mScreenResultConverter == null) {
			throw new RuntimeException("mScreenResultConverter is null");
		}
		return ((ScreenResultConverter<ScreenResult>) mScreenResultConverter).createActivityResult(screenResult);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public Screen getScreen(Activity activity) {
		return mIntentConverter.getScreen(activity.getIntent());
	}

	@Nullable
	public Class<? extends ScreenResult> getScreenResultClass() {
		return mScreenResultClass;
	}

	public int getRequestCode() {
		return mRequestCode;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public ScreenResult getScreenResult(ActivityResult activityResult) {
		if (mScreenResultConverter == null) {
			throw new RuntimeException("mScreenResultConverter is null");
		}
		return ((ScreenResultConverter<ScreenResult>) mScreenResultConverter).getScreenResult(activityResult);
	}

	private void checkScreenClass(@NonNull Class<? extends Screen> screenClass) {
		if (!mScreenClass.isAssignableFrom(screenClass)) {
			throw new IllegalArgumentException("Invalid screen class " + screenClass.getSimpleName() + ". Expected " + mScreenClass.getSimpleName());
		}
	}

	private void checkScreenResultClass(@NonNull Class<? extends ScreenResult> screenResultClass) {
		if (mScreenResultClass == null) {
			throw new RuntimeException("mScreenResultClass is null");
		}
		if (!mScreenResultClass.isAssignableFrom(screenResultClass)) {
			throw new IllegalArgumentException("Invalid screen result class " + screenResultClass.getCanonicalName() + ". Expected " + mScreenResultClass.getCanonicalName());
		}
	}
}
