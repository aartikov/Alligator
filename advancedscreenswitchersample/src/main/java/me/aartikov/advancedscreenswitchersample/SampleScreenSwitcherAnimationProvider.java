package me.aartikov.advancedscreenswitchersample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.SimpleTransitionAnimation;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher;

/**
 * Date: 5/15/2017
 * Time: 15:02
 *
 * @author Artur Artikov
 */
public class SampleScreenSwitcherAnimationProvider implements FragmentScreenSwitcher.AnimationProvider {
	private List<Screen> mTabScreens;

	public SampleScreenSwitcherAnimationProvider(List<Screen> tabScreens) {
		mTabScreens = tabScreens;
	}

	@Override
	@NonNull
	public TransitionAnimation getAnimation(@NonNull Screen screenFrom, @NonNull Screen screenTo, @Nullable AnimationData animationData) {
		int indexFrom = mTabScreens.indexOf(screenFrom);
		int indexTo = mTabScreens.indexOf(screenTo);
		if (indexTo > indexFrom) {
			return new SimpleTransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left);
		} else {
			return new SimpleTransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right);
		}
	}
}
