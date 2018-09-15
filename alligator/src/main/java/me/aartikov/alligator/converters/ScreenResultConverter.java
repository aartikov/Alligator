package me.aartikov.alligator.converters;

import android.support.annotation.Nullable;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.ScreenResult;

/**
 * Date: 21.10.2017
 * Time: 12:58
 *
 * @author Artur Artikov
 */

/**
 * Converts {@link ScreenResult} to {@link ActivityResult} and vice versa.
 *
 * @param <ScreenResultT> screen result type
 */
public interface ScreenResultConverter<ScreenResultT extends ScreenResult> {

	ActivityResult createActivityResult(@Nullable ScreenResultT screenResult);

	@Nullable ScreenResultT getScreenResult(ActivityResult activityResult);
}
