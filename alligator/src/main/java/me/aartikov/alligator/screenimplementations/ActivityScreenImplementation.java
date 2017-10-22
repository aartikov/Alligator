package me.aartikov.alligator.screenimplementations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.functions.ActivityConverter;
import me.aartikov.alligator.functions.ScreenResultConverter;
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
	private ActivityConverter<? extends Screen> mActivityConverter;

	private Class<? extends ScreenResult> mScreenResultClass;
	private int mRequestCode;
	private ScreenResultConverter<? extends ScreenResult> mScreenResultConverter;

	private ScreenClassHelper mScreenClassHelper;

	public ActivityScreenImplementation(Class<? extends Screen> screenClass,
	                                    Class<? extends Activity> activityClass,
	                                    ActivityConverter<? extends Screen> activityConverter,
	                                    Class<? extends ScreenResult> screenResultClass,
	                                    ScreenResultConverter<? extends ScreenResult> screenResultConverter,
	                                    int requestCode,
	                                    ScreenClassHelper screenClassHelper) {
		mScreenClass = screenClass;
		mActivityClass = activityClass;
		mActivityConverter = activityConverter;
		mScreenResultClass = screenResultClass;
		mScreenResultConverter = screenResultConverter;
		mRequestCode = requestCode;
		mScreenClassHelper = screenClassHelper;
	}

	public ActivityScreenImplementation(Class<? extends Screen> screenClass,
	                                    Class<? extends Activity> activityClass,
	                                    ActivityConverter<? extends Screen> activityConverter,
	                                    ScreenClassHelper screenClassHelper) {
		this(screenClass, activityClass, activityConverter, null, null, -1, screenClassHelper);
	}

	@Override
	public boolean accept(ScreenImplementationVisitor visitor, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		return visitor.execute(this, navigationContext, navigationFactory);
	}

	@SuppressWarnings("unchecked")
	public Intent createIntent(Context context, Screen screen, @Nullable Class<? extends Screen> previousScreenClass) {
		checkScreenClass(screen.getClass());
		Intent intent = ((ActivityConverter<Screen>) mActivityConverter).createIntent(context, screen);
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
	public Screen getScreen(Activity activity) {
		return ((ActivityConverter<Screen>) mActivityConverter).getScreen(activity, mScreenClass);
	}

	public @Nullable Class<? extends ScreenResult> getScreenResultClass() {
		return mScreenResultClass;
	}

	public int getRequestCode() {
		return mRequestCode;
	}

	@SuppressWarnings("unchecked")
	public ScreenResult getScreenResult(ActivityResult activityResult) {
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
