package me.aartikov.alligator.animations.providers;

import android.support.annotation.Nullable;

import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.DialogAnimation;
import me.aartikov.alligator.animations.providers.DialogAnimationProvider;
import me.aartikov.alligator.Screen;

/**
 * Date: 26.03.2017
 * Time: 12:41
 *
 * @author Artur Artikov
 */

/**
 * Default implementation of {@link DialogAnimationProvider}. Always returns {@code DialogAnimation.DEFAULT}.
 */
public class DefaultDialogAnimationProvider implements DialogAnimationProvider {
	@Override
	public DialogAnimation getAnimation(Class<? extends Screen> screenClass, @Nullable AnimationData animationData) {
		return DialogAnimation.DEFAULT;
	}
}
