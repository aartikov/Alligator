package me.aartikov.alligator.commands;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.helpers.DialogFragmentHelper;
import me.aartikov.alligator.helpers.FragmentStack;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;

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
	private ScreenResult mScreenResult;
	private AnimationData mAnimationData;

	public BackCommand(ScreenResult screenResult, AnimationData animationData) {
		mScreenResult = screenResult;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		if (navigationContext.getDialogFragmentHelper().isDialogVisible()) {
			DialogFragmentHelper dialogFragmentHelper = navigationContext.getDialogFragmentHelper();
			DialogFragment dialogFragment = dialogFragmentHelper.getDialogFragment();
			dialogFragmentHelper.hideDialog();
			callScreenResultListener(dialogFragment, navigationContext.getScreenResultListener(), navigationFactory);
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
			callScreenResultListener(currentFragment, navigationContext.getScreenResultListener(), navigationFactory);
			return true;
		} else {
			Activity activity = navigationContext.getActivity();
			if (mScreenResult != null) {
				setActivityResult(activity, navigationFactory);
			}

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

	private void callScreenResultListener(Fragment fragment, ScreenResultListener screenResultListener, NavigationFactory navigationFactory) throws CommandExecutionException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(fragment);
		if (screenClass == null) {
			throw new CommandExecutionException(this, "Failed to get a screen class for " + fragment.getClass().getSimpleName());
		}

		FragmentScreenImplementation fragmentScreenImplementation = (FragmentScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (fragmentScreenImplementation == null) {
			throw new CommandExecutionException(this, "Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		if (mScreenResult == null) {
			screenResultListener.onCancelled(screenClass);
			return;
		}

		if (fragmentScreenImplementation.getScreenResultClass() == null) {
			throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't return a result.");
		}

		Class<? extends ScreenResult> supportedScreenResultClass = fragmentScreenImplementation.getScreenResultClass();
		if (!supportedScreenResultClass.isAssignableFrom(mScreenResult.getClass())) {
			throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't return a result of class " + mScreenResult.getClass().getCanonicalName() +
			                                          ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		screenResultListener.onScreenResult(screenClass, mScreenResult);
	}

	private void callScreenResultListener(DialogFragment dialogFragment, ScreenResultListener screenResultListener, NavigationFactory navigationFactory) throws CommandExecutionException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(dialogFragment);
		if (screenClass == null) {
			throw new CommandExecutionException(this, "Failed to get a screen class for " + dialogFragment.getClass().getSimpleName());
		}

		DialogFragmentScreenImplementation dialogFragmentScreenImplementation = (DialogFragmentScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (dialogFragmentScreenImplementation == null) {
			throw new CommandExecutionException(this, "Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		if (mScreenResult == null) {
			screenResultListener.onCancelled(screenClass);
			return;
		}

		if (dialogFragmentScreenImplementation.getScreenResultClass() == null) {
			throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't return a result.");
		}

		Class<? extends ScreenResult> supportedScreenResultClass = dialogFragmentScreenImplementation.getScreenResultClass();
		if (!supportedScreenResultClass.isAssignableFrom(mScreenResult.getClass())) {
			throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't return a result of class " + mScreenResult.getClass().getCanonicalName() +
			                                          ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		screenResultListener.onScreenResult(screenClass, mScreenResult);
	}

	private void setActivityResult(Activity activity, NavigationFactory navigationFactory) throws CommandExecutionException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(activity);
		if (screenClass == null) {
			throw new CommandExecutionException(this, "Failed to get a screen class for " + activity.getClass().getSimpleName());
		}

		ActivityScreenImplementation activityScreenImplementation = (ActivityScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (activityScreenImplementation == null) {
			throw new CommandExecutionException(this, "Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		if (activityScreenImplementation.getScreenResultClass() == null) {
			throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't return a result.");
		}

		Class<? extends ScreenResult> supportedScreenResultClass = activityScreenImplementation.getScreenResultClass();
		if (!supportedScreenResultClass.isAssignableFrom(mScreenResult.getClass())) {
			throw new CommandExecutionException(this, "Screen " + screenClass.getSimpleName() + " can't return a result of class " + mScreenResult.getClass().getCanonicalName() +
			                                          ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		ActivityResult activityResult = activityScreenImplementation.createActivityResult(mScreenResult);
		activity.setResult(activityResult.getResultCode(), activityResult.getIntent());
	}
}
