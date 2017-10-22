package me.aartikov.alligator.commands;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.helpers.FragmentStack;

/**
 * Date: 29.12.2016
 * Time: 10:56
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code goBack} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class BackCommand implements Command {
	private AnimationData mAnimationData;

	public BackCommand(AnimationData animationData) {
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		if (navigationContext.getDialogFragmentHelper().isDialogVisible()) {
			navigationContext.getDialogFragmentHelper().hideDialog();
			return true;
		} else if (navigationContext.getFragmentStack() != null && navigationContext.getFragmentStack().getFragmentCount() > 1) {
			FragmentStack fragmentStack = navigationContext.getFragmentStack();
			List<Fragment> fragments = fragmentStack.getFragments();
			Fragment currentFragment = fragments.get(fragments.size() - 1);
			Fragment previousFragment = fragments.get(fragments.size() - 2);

			Class<? extends Screen> screenClassFrom = navigationFactory.getScreenClass(currentFragment);
			Class<? extends Screen> screenClassTo = navigationFactory.getScreenClass(previousFragment);
			TransitionAnimation animation = TransitionAnimation.DEFAULT;
			if (screenClassFrom != null && screenClassTo != null) {
				animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, false, mAnimationData);
			}

			fragmentStack.pop(animation);
			navigationContext.getTransitionListener().onScreenTransition(TransitionType.BACK, screenClassFrom, screenClassTo, false);
			return true;
		} else {
			Activity activity = navigationContext.getActivity();

			Class<? extends Screen> screenClassFrom = navigationFactory.getScreenClass(activity);
			Class<? extends Screen> screenClassTo = navigationFactory.getPreviousScreenClass(activity);
			TransitionAnimation animation = TransitionAnimation.DEFAULT;
			if (screenClassFrom != null && screenClassTo != null) {
				animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
			}

			navigationContext.getActivityHelper().finish(animation);
			navigationContext.getTransitionListener().onScreenTransition(TransitionType.BACK, screenClassFrom, screenClassTo, true);
			return false;
		}
	}
}
