package me.aartikov.alligator.defaultimpementation;

import android.support.annotation.Nullable;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionListener;
import me.aartikov.alligator.TransitionType;

/**
 * Date: 09.05.2017
 * Time: 16:14
 *
 * @author Artur Artikov
 */

/**
 * Transition listener that does nothing.
 */
public class DefaultTransitionListener implements TransitionListener {
	@Override
	public void onScreenTransition(TransitionType transitionType, @Nullable Class<? extends Screen> screenClassFrom, @Nullable Class<? extends Screen> screenClassTo, boolean isActivity) {
	}
}
