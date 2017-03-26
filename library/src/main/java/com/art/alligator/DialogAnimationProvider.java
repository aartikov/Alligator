package com.art.alligator;

/**
 * Date: 26.03.2017
 * Time: 12:37
 *
 * @author Artur Artikov
 */

public interface DialogAnimationProvider {
	DialogAnimation getAnimation(Class<? extends Screen> screenClass, AnimationData animationData);
}
