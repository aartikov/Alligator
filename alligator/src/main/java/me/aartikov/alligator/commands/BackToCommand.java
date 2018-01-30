package me.aartikov.alligator.commands;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.helpers.FragmentStack;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;

/**
 * Date: 11.02.2017
 * Time: 11:14
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code goBackTo} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class BackToCommand extends VisitorCommand {
	private Class<? extends Screen> mScreenClass;
	private AnimationData mAnimationData;

	public BackToCommand(Class<? extends Screen> screenClass, AnimationData animationData) {
		super(screenClass);
		mScreenClass = screenClass;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(ActivityScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
		Activity activity = navigationContext.getActivity();
		Intent intent = screenImplementation.createEmptyIntent(activity, mScreenClass);
		if (intent == null) {
			throw new NavigationException("Can't create intent for a screen " + mScreenClass.getSimpleName());
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		Class<? extends Screen> screenClassFrom = navigationFactory.getScreenClass(activity);
		Class<? extends Screen> screenClassTo = mScreenClass;
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
		}

		navigationContext.getActivityHelper().start(intent, animation);
		navigationContext.getTransitionListener().onScreenTransition(TransitionType.BACK, screenClassFrom, screenClassTo, true);
		return false;
	}

	@Override
	public boolean execute(FragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
		if (navigationContext.getFragmentStack() == null) {
			throw new NavigationException("ContainerId is not set.");
		}

		FragmentStack fragmentStack = navigationContext.getFragmentStack();
		List<Fragment> fragments = fragmentStack.getFragments();
		Fragment fragment = null;
		for (int i = fragments.size() - 1; i >= 0; i--) {
			if (mScreenClass == navigationFactory.getScreenClass(fragments.get(i))) {
				fragment = fragments.get(i);
				break;
			}
		}

		if (fragment == null) {
			throw new NavigationException("Screen " + mScreenClass.getSimpleName() + " is not found.");
		}

		Class<? extends Screen> screenClassFrom = navigationFactory.getScreenClass(fragments.get(fragments.size() - 1));
		Class<? extends Screen> screenClassTo = mScreenClass;
		TransitionAnimation animation = TransitionAnimation.DEFAULT;
		if (screenClassFrom != null) {
			animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, false, mAnimationData);
		}

		fragmentStack.popUntil(fragment, animation);
		navigationContext.getTransitionListener().onScreenTransition(TransitionType.BACK, screenClassFrom, screenClassTo, false);
		return true;
	}

	@Override
	public boolean execute(DialogFragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
		throw new NavigationException("BackTo command is not supported for dialog fragments.");
	}

	@Override
	public boolean discardIfNoScreenSwitcher() {
		return false;
	}
}
