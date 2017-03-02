package com.art.alligator.implementation;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.art.alligator.NavigationContext;
import com.art.alligator.TransitionAnimation;

/**
 * Date: 26.02.2017
 * Time: 12:26
 *
 * @author Artur Artikov
 */

public class CommandUtils {
	private static final String TAG = "com.art.alligator.FragmentTag";

	private CommandUtils() {
	}

	public static List<Fragment> getFragments(NavigationContext navigationContext) {
		List<Fragment> result = new ArrayList<>();
		int index = 0;
		while (true) {
			String tag = getFragmentTag(navigationContext, index);
			Fragment fragment = navigationContext.getFragmentManager().findFragmentByTag(tag);
			if(fragment == null) {
				break;
			}

			if(!fragment.isRemoving()) {
				result.add(fragment);
			}
			index++;
		}
		return result;
	}

	public static int getFragmentCount(NavigationContext navigationContext) {
		return getFragments(navigationContext).size();
	}

	public static Fragment getCurrentFragment(NavigationContext navigationContext) {
		List<Fragment> fragments = getFragments(navigationContext);
		return fragments.isEmpty() ? null : fragments.get(fragments.size() - 1);
	}

	public static String getFragmentTag(NavigationContext navigationContext, int index) {
		return TAG + "_" + navigationContext.getContainerId() + "_" + index;
	}

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
