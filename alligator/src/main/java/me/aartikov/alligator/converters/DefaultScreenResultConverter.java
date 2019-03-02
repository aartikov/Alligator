package me.aartikov.alligator.converters;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.ScreenResult;

/**
 * Date: 15.09.2018
 * Time: 10:18
 *
 * @author Artur Artikov
 */

/**
 * Creates {@code ActivityResult(Activity.RESULT_OK, data)} (where {@code data} contains a serialized screen result) if a screen result is not {@code null},
 * and {@code ActivityResult(Activity.RESULT_CANCELED, null)} otherwise.
 *
 * @param <ScreenResultT> screen result type
 */

public class DefaultScreenResultConverter<ScreenResultT extends ScreenResult> implements ScreenResultConverter<ScreenResultT> {
	private static final String KEY_SCREEN_RESULT = "me.aartikov.alligator.KEY_SCREEN_RESULT";

	private Class<ScreenResultT> mScreenResultClass;

	public DefaultScreenResultConverter(Class<ScreenResultT> screenResultClass) {
		mScreenResultClass = screenResultClass;
	}

	@Override
	@NonNull
	public ActivityResult createActivityResult(@NonNull ScreenResultT screenResult) {
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

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public ScreenResultT getScreenResult(@NonNull ActivityResult activityResult) {
		if (activityResult.getIntent() == null || activityResult.getResultCode() != Activity.RESULT_OK) {
			return null;
		} else if (Serializable.class.isAssignableFrom(mScreenResultClass)) {
			return (ScreenResultT) activityResult.getIntent().getSerializableExtra(KEY_SCREEN_RESULT);
		} else if (Parcelable.class.isAssignableFrom(mScreenResultClass)) {
			return (ScreenResultT) activityResult.getIntent().getParcelableExtra(KEY_SCREEN_RESULT);
		} else {
			throw new IllegalArgumentException("Screen result " + mScreenResultClass.getCanonicalName() + " should be Serializable or Parcelable.");
		}
	}
}
