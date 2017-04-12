package me.aartikov.alligator.commands;

import java.util.List;

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
		if(DialogFragmentHelper.from(navigationContext).isDialogVisible()) {
			DialogFragmentHelper.from(navigationContext).hideDialog();
			return true;
		} else if(navigationContext.hasContainerId() && FragmentStack.from(navigationContext).getFragmentCount() > 1) {
			FragmentStack fragmentStack = FragmentStack.from(navigationContext);
			List<Fragment> fragments = fragmentStack.getFragments();
			Fragment currentFragment = fragments.get(fragments.size() - 1);
			Fragment previousFragment = fragments.get(fragments.size() - 2);
			TransitionAnimation animation = getFragmentAnimation(navigationContext, currentFragment, previousFragment);
			fragmentStack.pop(animation);
			return true;
		} else {
			ActivityHelper activityHelper = ActivityHelper.from(navigationContext);
			TransitionAnimation animation = getActivityAnimation(navigationContext, navigationFactory);
			activityHelper.finish(animation);
			return false;
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(navigationContext.getActivity(), navigationFactory);
		Class<? extends Screen> screenClassTo = ScreenClassUtils.getPreviousScreenClass(navigationContext.getActivity());
		return navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext, Fragment currentFragment, Fragment previousFragment) {
		Class<? extends Screen> screenClassFrom = ScreenClassUtils.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = ScreenClassUtils.getScreenClass(previousFragment);
		return navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, false, mAnimationData);
	}
}
