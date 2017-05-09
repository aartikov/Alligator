package me.aartikov.alligator.commands;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.Command;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionAnimation;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.internal.ActivityHelper;
import me.aartikov.alligator.internal.DialogFragmentHelper;
import me.aartikov.alligator.internal.FragmentStack;
import me.aartikov.alligator.internal.ScreenClassUtils;

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
		if (DialogFragmentHelper.from(navigationContext).isDialogVisible()) {
			DialogFragmentHelper.from(navigationContext).hideDialog();
			return true;
		} else if (navigationContext.hasContainerId() && FragmentStack.from(navigationContext).getFragmentCount() > 1) {
			FragmentStack fragmentStack = FragmentStack.from(navigationContext);
			List<Fragment> fragments = fragmentStack.getFragments();
			Fragment currentFragment = fragments.get(fragments.size() - 1);
			Fragment previousFragment = fragments.get(fragments.size() - 2);

			Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(currentFragment);
			Class<? extends Screen> screenClassTo = ScreenClassUtils.getScreenClass(previousFragment);
			TransitionAnimation animation = TransitionAnimation.DEFAULT;
			if (screenClassFrom != null && screenClassTo != null) {
				animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, false, mAnimationData);
			}

			fragmentStack.pop(animation);
			navigationContext.getTransitionListener().onScreenTransition(TransitionType.BACK, screenClassFrom, screenClassTo, false);
			return true;
		} else {
			Activity activity = navigationContext.getActivity();

			Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(activity, navigationFactory);
			Class<? extends Screen> screenClassTo = ScreenClassUtils.getPreviousScreenClass(activity);
			TransitionAnimation animation = TransitionAnimation.DEFAULT;
			if (screenClassFrom != null && screenClassTo != null) {
				animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
			}

			ActivityHelper activityHelper = ActivityHelper.from(navigationContext);
			activityHelper.finish(animation);
			navigationContext.getTransitionListener().onScreenTransition(TransitionType.BACK, screenClassFrom, screenClassTo, true);
			return false;
		}
	}
}
