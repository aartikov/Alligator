package com.art.alligator.defaultimpementation;

import com.art.alligator.AnimationData;
import com.art.alligator.DialogAnimation;
import com.art.alligator.DialogAnimationProvider;
import com.art.alligator.Screen;

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
