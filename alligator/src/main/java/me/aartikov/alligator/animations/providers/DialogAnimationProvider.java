package me.aartikov.alligator.animations.providers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.DialogAnimation;

/**
 * Provider of a {@link DialogAnimation}.
 */
public interface DialogAnimationProvider {
	/**
	 * Is called when a {@link DialogAnimation} is needed to show a screen represented by a dialog fragment.
	 *
	 * @param screenClass   a class of a shown screen
	 * @param animationData data for an additional animation configuring
	 * @return an animation that will be used to show a dialog fragment
	 */
	@NonNull
	DialogAnimation getAnimation(@NonNull Class<? extends Screen> screenClass, @Nullable AnimationData animationData);
}
