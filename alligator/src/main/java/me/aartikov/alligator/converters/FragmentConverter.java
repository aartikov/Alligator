package me.aartikov.alligator.converters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.Screen;


/**
 * Converts a screen to a fragment and vice versa.
 *
 * @param <ScreenT> screen type
 */
public interface FragmentConverter<ScreenT extends Screen> {

	@NonNull
	Fragment createFragment(@NonNull ScreenT screen);

	@NonNull
	ScreenT getScreen(@NonNull Fragment fragment);
}
