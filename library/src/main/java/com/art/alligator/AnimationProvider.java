package com.art.alligator;

/**
 * Date: 24.02.2017
 * Time: 19:00
 *
 * @author Artur Artikov
 */

public interface AnimationProvider {
	TransitionAnimation getAnimation(TransitionType transitionType, boolean isActivity, Class<? extends Screen> screenClass);
}
