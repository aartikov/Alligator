package me.aartikov.advancedscreenswitchersample;

import me.aartikov.advancedscreenswitchersample.screens.TabScreen;
import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionAnimation;
import me.aartikov.alligator.animations.transition.SimpleTransitionAnimation;
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher;

/**
 * Date: 5/15/2017
 * Time: 15:02
 *
 * @author Artur Artikov
 */
public class SampleScreenSwitcherAnimationProvider implements FragmentScreenSwitcher.AnimationProvider {
	@Override
	public TransitionAnimation getAnimation(Screen screenFrom, Screen screenTo, AnimationData animationData) {
		int indexFrom = ((TabScreen) screenFrom).ordinal();
		int indexTo = ((TabScreen) screenTo).ordinal();
		if (indexTo > indexFrom) {
			return new SimpleTransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left);
		} else {
			return new SimpleTransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right);
		}
	}
}
