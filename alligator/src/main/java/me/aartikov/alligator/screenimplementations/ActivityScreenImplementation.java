package me.aartikov.alligator.screenimplementations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.IntentConverter;
import me.aartikov.alligator.converters.ScreenResultConverter;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.helpers.ScreenClassHelper;

/**
 * Date: 15.10.2017
 * Time: 11:21
 *
 * @author Artur Artikov
 */

public class ActivityScreenImplementation implements ScreenImplementation {
	private Class<? extends Screen> mScreenClass;
	private Class<? extends Activity> mActivityClass;
	private IntentConverter<? extends Screen> mIntentConverter;

	private Class<? extends ScreenResult> mScreenResultClass;
	private int mRequestCode;
	private ScreenResultConverter<? extends ScreenResult> mScreenResultConverter;

	private ScreenClassHelper mScreenClassHelper;

	public ActivityScreenImplementation(Class<? extends Screen> screenClass,
	                                    Class<? extends Activity> activityClass,
	                                    IntentConverter<? extends Screen> intentConverter,
	                                    Class<? extends ScreenResult> screenResultClass,
	                                    ScreenResultConverter<? extends ScreenResult> screenResultConverter,
	                                    int requestCode,
	                                    ScreenClassHelper screenClassHelper) {
		mScreenClass = screenClass;
		mActivityClass = activityClass;
		mIntentConverter = intentConverter;
		mScreenResultClass = screenResultClass;
		mScreenResultConverter = screenResultConverter;
		mRequestCode = requestCode;
		mScreenClassHelper = screenClassHelper;
	}

	public ActivityScreenImplementation(Class<? extends Screen> screenClass,
	                                    Class<? extends Activity> activityClass,
	                                    IntentConverter<? extends Screen> intentConverter,
	                                    ScreenClassHelper screenClassHelper) {
		this(screenClass, activityClass, intentConverter, null, null, -1, screenClassHelper);
	}

	@Override
	public <R> R accept(ScreenImplementationVisitor<R> visitor) throws NavigationException {
		return visitor.visit(this);
	}

	@SuppressWarnings("unchecked")
	public Intent createIntent(Context context, Screen screen, @Nullable Class<? extends Screen> previousScreenClass) {
		checkScreenClass(screen.getClass());
		Intent intent = ((IntentConverter<Screen>) mIntentConverter).createIntent(context, screen);
		mScreenClassHelper.putScreenClass(intent, screen.getClass());
		if (previousScreenClass != null) {
			mScreenClassHelper.putPreviousScreenClass(intent, previousScreenClass);
		}
		return intent;
	}

	public @Nullable Intent createEmptyIntent(Context context, Class<? extends Screen> screenClass) {
		if (mActivityClass == null) {
			return null;
		}

		Intent intent = new Intent(context, mActivityClass);
		mScreenClassHelper.putScreenClass(intent, screenClass);
		return intent;
	}

	@SuppressWarnings("unchecked")
	public ActivityResult createActivityResult(ScreenResult screenResult) {
		checkScreenResultClass(screenResult.getClass());
		return ((ScreenResultConverter<ScreenResult>) mScreenResultConverter).createActivityResult(screenResult);
	}

	@SuppressWarnings("unchecked")
	public @Nullable Screen getScreen(Activity activity) {
		return mIntentConverter.getScreen(activity.getIntent());
	}

	public @Nullable Class<? extends ScreenResult> getScreenResultClass() {
		return mScreenResultClass;
	}

	public int getRequestCode() {
		return mRequestCode;
	}

	@SuppressWarnings("unchecked")
	public @Nullable ScreenResult getScreenResult(ActivityResult activityResult) {
		return ((ScreenResultConverter<ScreenResult>) mScreenResultConverter).getScreenResult(activityResult);
	}

	private void checkScreenClass(Class<? extends Screen> screenClass) {
		if (!mScreenClass.isAssignableFrom(screenClass)) {
			throw new IllegalArgumentException("Invalid screen class " + screenClass.getSimpleName() + ". Expected " + mScreenClass.getSimpleName());
		}
	}

	private void checkScreenResultClass(Class<? extends ScreenResult> screenResultClass) {
		if (!mScreenResultClass.isAssignableFrom(screenResultClass)) {
			throw new IllegalArgumentException("Invalid screen result class " + screenResultClass.getCanonicalName() + ". Expected " + mScreenResultClass.getCanonicalName());
		}
	}
}
