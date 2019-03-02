package me.aartikov.alligator.converters;

import android.content.Intent;

import androidx.annotation.NonNull;
import me.aartikov.alligator.Screen;

/**
 * Date: 29.09.2018
 * Time: 13:21
 *
 * @author Artur Artikov
 */

/**
 * {@link IntentConverter} that doesn't require to implement {@code getScreen} method. Should be used for external activities only.
 *
 * @param <ScreenT> screen type
 */
public abstract class OneWayIntentConverter<ScreenT extends Screen> implements IntentConverter<ScreenT> {

	final public ScreenT getScreen(@NonNull Intent intent) {
		throw new UnsupportedOperationException();
	}
}
