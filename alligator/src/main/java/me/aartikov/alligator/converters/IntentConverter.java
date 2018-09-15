package me.aartikov.alligator.converters;

import android.content.Context;
import android.content.Intent;

import me.aartikov.alligator.Screen;

/**
 * Date: 15.10.2017
 * Time: 11:37
 *
 * @author Artur Artikov
 */

/**
 * Converts a screen to an activity intent and vice versa.
 *
 * @param <ScreenT> screen type
 */
public interface IntentConverter<ScreenT extends Screen> {

	<T extends ScreenT> Intent createIntent(Context context, T screen);

	ScreenT getScreen(Intent intent);
}
