package com.art.alligator.implementation;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.TransitionAnimation;

/**
 * Date: 26.02.2017
 * Time: 12:26
 *
 * @author Artur Artikov
 */

public class CommandUtils {
	public static void applyActivityAnimation(Activity activity, TransitionAnimation animation) {
		if (animation != null && !animation.equals(TransitionAnimation.DEFAULT)) {
			activity.overridePendingTransition(animation.getEnterAnimation(), animation.getExitAnimation());
		}
	}

	public static void applyFragmentAnimation(FragmentTransaction transaction, TransitionAnimation animation) {
		if (animation != null && !animation.equals(TransitionAnimation.DEFAULT)) {
			transaction.setCustomAnimations(animation.getEnterAnimation(), animation.getExitAnimation());
		}
	}
}
