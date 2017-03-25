package com.art.alligator.command;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.art.alligator.Command;
import com.art.alligator.AnimationData;
import com.art.alligator.internal.DialogFragmentHelper;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.animation.TransitionAnimation;
import com.art.alligator.TransitionType;
import com.art.alligator.internal.FragmentStack;
import com.art.alligator.internal.ScreenClassUtils;

/**
 * Date: 29.12.2016
 * Time: 10:56
 *
 * @author Artur Artikov
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
			Activity activity = navigationContext.getActivity();
			activity.finish();
			TransitionAnimation animation = getActivityAnimation(navigationContext, navigationFactory);
			CommandUtils.applyActivityAnimation(activity, animation);
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
