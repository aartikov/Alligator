package com.art.alligator.implementation.commands;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.art.alligator.AnimationProvider;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.implementation.Command;

/**
 * Date: 29.12.2016
 * Time: 10:22
 *
 * @author Artur Artikov
 */
public class ForwardCommand implements Command {
	private Screen mScreen;

	public ForwardCommand(Screen screen) {
		mScreen = screen;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Context context = navigationContext.getActivity();
		Intent intent = navigationFactory.createActivityIntent(context, mScreen);
		Fragment fragment = navigationFactory.createFragment(mScreen);
		AnimationProvider animationProvider = navigationContext.getAnimationProvider();

		if (intent != null) {
			context.startActivity(intent);
			TransitionAnimation animation = animationProvider.getActivityForwardAnimation(mScreen.getClass());
			if (animation != null && !animation.equals(TransitionAnimation.DEFAULT)) {
				navigationContext.getActivity().overridePendingTransition(animation.getEnterAnimation(), animation.getExitAnimation());
			}
			return false;
		} else if (fragment != null) {
			FragmentManager fragmentManager = navigationContext.getFragmentManager();
			if (fragmentManager == null) {
				throw new IllegalStateException("Failed to add fragment. FragmentManager is not bound.");
			}

			TransitionAnimation forwardAnimation = animationProvider.getFragmentForwardAnimation(mScreen.getClass());
			if(forwardAnimation == null || forwardAnimation.equals(TransitionAnimation.DEFAULT)) {
				forwardAnimation = TransitionAnimation.NONE;
			}

			TransitionAnimation backAnimation = animationProvider.getFragmentBackAnimation(mScreen.getClass());
			if(backAnimation == null || backAnimation.equals(TransitionAnimation.DEFAULT)) {
				backAnimation = TransitionAnimation.NONE;
			}

			fragmentManager.beginTransaction()
					.setCustomAnimations(forwardAnimation.getEnterAnimation(), forwardAnimation.getExitAnimation(), backAnimation.getEnterAnimation(), backAnimation.getExitAnimation())
					.replace(navigationContext.getContainerId(), fragment)
					.addToBackStack(mScreen.getClass().getName())
					.commit();

			fragmentManager.executePendingTransactions();
			return true;

		} else {
			throw new RuntimeException("Screen " + mScreen.getClass().getSimpleName() + " is not registered.");
		}
	}
}
