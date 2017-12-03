package me.aartikov.alligator.functions;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.ScreenResult;

/**
 * Date: 21.10.2017
 * Time: 12:58
 *
 * @author Artur Artikov
 */

public class ScreenResultConverter<ScreenResultT extends ScreenResult> {
	private static final String KEY_SCREEN_RESULT = "me.aartikov.alligator.KEY_SCREEN_RESULT";

	private Function<ActivityResult, ScreenResultT> mScreenResultGettingFunction;
	private Function<ScreenResultT, ActivityResult> mActivityResultCreationFunction;

	public ScreenResultConverter(Function<ActivityResult, ScreenResultT> screenResultGettingFunction, Function<ScreenResultT, ActivityResult> activityResultCreationFunction) {
		mScreenResultGettingFunction = screenResultGettingFunction;
		mActivityResultCreationFunction = activityResultCreationFunction;
	}

	public ScreenResultConverter(Function<ActivityResult, ScreenResultT> screenResultGettingFunction) {
		this(screenResultGettingFunction, null);
	}

	public ScreenResultConverter(Class<ScreenResultT> screenResultClass) {
		this(getDefaultScreenResultGettingFunction(screenResultClass), getDefaultActivityResultCreationFunction(screenResultClass));
	}

	public <T extends ScreenResultT> ScreenResultT getScreenResult(ActivityResult activityResult) {
		return mScreenResultGettingFunction.call(activityResult);
	}

	public ActivityResult createActivityResult(ScreenResultT screenResult) {
		if (mActivityResultCreationFunction == null) {
			throw new RuntimeException("ActivityResult creation function is not implemented for a screen result " + screenResult.getClass().getCanonicalName());
		}
		return mActivityResultCreationFunction.call(screenResult);
	}

	public static <ScreenResultT extends ScreenResult> Function<ActivityResult, ScreenResultT> getDefaultScreenResultGettingFunction(final Class<ScreenResultT> screenResultClass) {
		return new Function<ActivityResult, ScreenResultT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenResultT call(ActivityResult activityResult) {
				if (activityResult.getIntent() == null || activityResult.getResultCode() != Activity.RESULT_OK) {
					return null;
				} else if (Serializable.class.isAssignableFrom(screenResultClass)) {
					return (ScreenResultT) activityResult.getIntent().getSerializableExtra(KEY_SCREEN_RESULT);
				} else if (Parcelable.class.isAssignableFrom(screenResultClass)) {
					return (ScreenResultT) activityResult.getIntent().getParcelableExtra(KEY_SCREEN_RESULT);
				} else {
					throw new IllegalArgumentException("Screen result " + screenResultClass.getCanonicalName() + " should be Serializable or Parcelable.");
				}
			}
		};
	}

	public static <ScreenResultT extends ScreenResult> Function<ScreenResultT, ActivityResult> getDefaultActivityResultCreationFunction(Class<ScreenResultT> screenResultClass) {
		return new Function<ScreenResultT, ActivityResult>() {
			@Override
			public ActivityResult call(ScreenResultT screenResult) {
				if (screenResult == null) {
					return new ActivityResult(Activity.RESULT_CANCELED, null);
				}

				Intent data = new Intent();
				if (screenResult instanceof Serializable) {
					data.putExtra(KEY_SCREEN_RESULT, (Serializable) screenResult);
				} else if (screenResult instanceof Parcelable) {
					data.putExtra(KEY_SCREEN_RESULT, (Parcelable) screenResult);
				} else {
					throw new IllegalArgumentException("Screen result " + screenResult.getClass().getCanonicalName() + " should be Serializable or Parcelable.");
				}
				return new ActivityResult(Activity.RESULT_OK, data);
			}
		};
	}
}
