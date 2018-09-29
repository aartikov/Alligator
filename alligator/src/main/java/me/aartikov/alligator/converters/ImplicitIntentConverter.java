package me.aartikov.alligator.converters;

import android.content.Intent;
import android.support.annotation.NonNull;

import me.aartikov.alligator.Screen;

/**
 * Date: 29.09.2018
 * Time: 13:21
 *
 * @author Artur Artikov
 */

/**
 * {@link IntentConverter} that doesn't require to implement {@code getScreen} method.
 *
 * @param <ScreenT> screen type
 */
public abstract class ImplicitIntentConverter<ScreenT extends Screen> implements IntentConverter<ScreenT> {

	final public ScreenT getScreen(@NonNull Intent intent) {
		throw new UnsupportedOperationException();
	}
}
