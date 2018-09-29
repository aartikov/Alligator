package me.aartikov.alligator.converters;

import android.support.annotation.NonNull;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.ScreenResult;

/**
 * Date: 21.10.2017
 * Time: 12:58
 *
 * @author Artur Artikov
 */

/**
 * {@link ScreenResultConverter} that doesn't require to implement {@code createActivityResult} method.
 *
 * @param <ScreenResultT> screen result type
 */
public abstract class ImplicitScreenResultConverter<ScreenResultT extends ScreenResult> implements ScreenResultConverter<ScreenResultT> {

	@NonNull
	public ActivityResult createActivityResult(@NonNull ScreenResultT screenResult) {
		throw new UnsupportedOperationException();
	}
}
