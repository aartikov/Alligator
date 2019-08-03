package me.aartikov.alligator.commands;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.List;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.flowmanagers.FragmentFlowManager;
import me.aartikov.alligator.helpers.DialogFragmentHelper;
import me.aartikov.alligator.helpers.FragmentStack;
import me.aartikov.alligator.navigationfactories.NavigationFactory;

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
	@Nullable
	private ScreenResult mScreenResult;
	@Nullable
	private AnimationData mAnimationData;

	public BackCommand(@Nullable ScreenResult screenResult, @Nullable AnimationData animationData) {
		mScreenResult = screenResult;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(@NonNull NavigationContext navigationContext, @NonNull NavigationFactory navigationFactory) throws NavigationException {
		if (navigationContext.getDialogFragmentHelper().isDialogVisible()) {
			DialogFragmentHelper dialogFragmentHelper = navigationContext.getDialogFragmentHelper();
			DialogFragment dialogFragment = dialogFragmentHelper.getDialogFragment();
			dialogFragmentHelper.hideDialog();
			navigationContext.getScreenResultHelper().callScreenResultListener(dialogFragment, mScreenResult, navigationContext.getScreenResultListener(), navigationFactory);
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
				animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, DestinationType.FRAGMENT, screenClassFrom, screenClassTo, mAnimationData);
			}

			fragmentStack.pop(animation);
			navigationContext.getTransitionListener().onScreenTransition(TransitionType.BACK, DestinationType.FRAGMENT, screenClassFrom, screenClassTo);
			navigationContext.getScreenResultHelper().callScreenResultListener(currentFragment, mScreenResult, navigationContext.getScreenResultListener(), navigationFactory);
			return true;
		} else if (navigationContext.getFlowManager() instanceof FragmentFlowManager &&
				((FragmentFlowManager) navigationContext.getFlowManager()).getFragmentStack().getFragmentCount() > 1) {
			return navigationContext.getFlowManager().back(navigationContext.getFlowTransitionListener(),
					navigationContext.getScreenResultHelper(),
					navigationContext.getScreenResultListener(),
					mScreenResult,
					mAnimationData);
		} else {
			Activity activity = navigationContext.getActivity();
			if (mScreenResult != null) {
				navigationContext.getScreenResultHelper().setActivityResult(activity, mScreenResult, navigationFactory);
			}

			Class<? extends Screen> screenClassFrom = navigationFactory.getScreenClass(activity);
			Class<? extends Screen> screenClassTo = navigationFactory.getPreviousScreenClass(activity);
			TransitionAnimation animation = TransitionAnimation.DEFAULT;
			if (screenClassFrom != null && screenClassTo != null) {
				animation = navigationContext.getTransitionAnimationProvider().getAnimation(TransitionType.BACK, DestinationType.ACTIVITY, screenClassFrom, screenClassTo, mAnimationData);
			}

			navigationContext.getActivityHelper().finish(animation);
			navigationContext.getTransitionListener().onScreenTransition(TransitionType.BACK, DestinationType.ACTIVITY, screenClassFrom, screenClassTo);
			return false;
		}
	}
}
