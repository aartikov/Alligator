package me.aartikov.alligator.converters;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.Screen;


/**
 * Converts a screen to an activity intent and vice versa.
 *
 * @param <ScreenT> screen type
 */
public interface IntentConverter<ScreenT extends Screen> {

	@NonNull
	Intent createIntent(@NonNull Context context, @NonNull ScreenT screen);

	@Nullable
	ScreenT getScreen(@NonNull Intent intent);
}
