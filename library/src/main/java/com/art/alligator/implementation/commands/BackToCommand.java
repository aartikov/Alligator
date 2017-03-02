package com.art.alligator.implementation.commands;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.AnimationData;
import com.art.alligator.Command;
import com.art.alligator.CommandExecutionException;
import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.TransitionAnimation;
import com.art.alligator.TransitionType;
import com.art.alligator.implementation.CommandUtils;
import com.art.alligator.implementation.ScreenUtils;

/**
 * Date: 11.02.2017
 * Time: 11:14
 *
 * @author Artur Artikov
 */

public class BackToCommand implements Command {
	private Class<? extends Screen> mScreenClass;
	private AnimationData mAnimationData;

	public BackToCommand(Class<? extends Screen> screenClass, AnimationData animationData) {
		mScreenClass = screenClass;
		mAnimationData = animationData;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		Class activityClass = navigationFactory.getActivityClass(mScreenClass);
		FragmentManager fragmentManager = navigationContext.getFragmentManager();

		if (activityClass != null) {
			Activity activity = navigationContext.getActivity();
			Intent intent = new Intent(activity, activityClass);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			ScreenUtils.putScreenClass(intent, mScreenClass);
			activity.startActivity(intent);
			CommandUtils.applyActivityAnimation(activity, getActivityAnimation(navigationContext, navigationFactory));
			return false;
		} else if (fragmentManager != null) {
			List<Fragment> fragments = CommandUtils.getFragments(navigationContext);
			int index = -1;
			for(int i = fragments.size() - 1; i >= 0; i--) {
				if(mScreenClass == ScreenUtils.getScreenClass(fragments.get(i))) {
					index = i;
					break;
				}
			}

			if(index == -1) {
				throw new CommandExecutionException("Screen " + mScreenClass.getSimpleName() + " is not found.");
			}

			if(index == fragments.size() - 1) {
				return true;
			}

			FragmentTransaction transaction = fragmentManager.beginTransaction();
			for(int i = index + 1; i < fragments.size(); i++) {
				if(i == fragments.size() - 1) {
					CommandUtils.applyFragmentAnimation(transaction, getFragmentAnimation(navigationContext, fragments.get(i)));
				}
				transaction.remove(fragments.get(i));
			}
			transaction.attach(fragments.get(index));
			transaction.commitNow();
			return true;
		} else {
			throw new CommandExecutionException("Screen " + mScreenClass.getSimpleName() + " is not found.");
		}
	}

	private TransitionAnimation getActivityAnimation(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Class<? extends Screen> screenClassFrom = ScreenUtils.getScreenClass(navigationContext.getActivity(), navigationFactory);
		Class<? extends Screen> screenClassTo = mScreenClass;
		return navigationContext.getAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, true, mAnimationData);
	}

	private TransitionAnimation getFragmentAnimation(NavigationContext navigationContext, Fragment currentFragment) {
		Class<? extends Screen> screenClassFrom = ScreenUtils.getScreenClass(currentFragment);
		Class<? extends Screen> screenClassTo = mScreenClass;
		return navigationContext.getAnimationProvider().getAnimation(TransitionType.BACK, screenClassFrom, screenClassTo, false, mAnimationData);
	}
}
