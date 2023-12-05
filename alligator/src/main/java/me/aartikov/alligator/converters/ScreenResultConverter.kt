package me.aartikov.alligator.converters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.ScreenResult;


/**
 * Converts {@link ScreenResult} to {@link ActivityResult} and vice versa.
 *
 * @param <ScreenResultT> screen result type
 */
public interface ScreenResultConverter<ScreenResultT extends ScreenResult> {

	@NonNull
	ActivityResult createActivityResult(@NonNull ScreenResultT screenResult);

	@Nullable
	ScreenResultT getScreenResult(@NonNull ActivityResult activityResult);
}
