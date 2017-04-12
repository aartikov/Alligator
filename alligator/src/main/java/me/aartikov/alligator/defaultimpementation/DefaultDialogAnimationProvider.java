package me.aartikov.alligator.defaultimpementation;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.DialogAnimation;
import me.aartikov.alligator.DialogAnimationProvider;
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
	public DialogAnimation getAnimation(Class<? extends Screen> screenClass, AnimationData animationData) {
		return DialogAnimation.DEFAULT;
	}
}
