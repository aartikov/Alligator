package me.aartikov.alligator.converters;

import androidx.annotation.NonNull;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.ScreenResult;


/**
 * {@link ScreenResultConverter} that doesn't require to implement {@code createActivityResult} method. Should be used for external activities only.
 *
 * @param <ScreenResultT> screen result type
 */
public abstract class OneWayScreenResultConverter<ScreenResultT extends ScreenResult> implements ScreenResultConverter<ScreenResultT> {

	@NonNull
	public ActivityResult createActivityResult(@NonNull ScreenResultT screenResult) {
		throw new UnsupportedOperationException();
	}
}
