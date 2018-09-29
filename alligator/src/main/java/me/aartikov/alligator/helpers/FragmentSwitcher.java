package me.aartikov.alligator.helpers;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import me.aartikov.alligator.animations.TransitionAnimation;

/**
 * Date: 13.05.2017
 * Time: 13:45
 *
 * @author Artur Artikov
 */

/**
 * Helper class for fragment switching.
 */
public class FragmentSwitcher {
	private static final String TAG_PREFIX = "me.aartikov.alligator.FRAGMENT_SWITCHER_TAG_";
	private FragmentManager mFragmentManager;
	private int mContainerId;

	public FragmentSwitcher(@NonNull FragmentManager fragmentManager, int containerId) {
		if (containerId <= 0) {
			throw new IllegalArgumentException("ContainerId is not set.");
		}

		mFragmentManager = fragmentManager;
		mContainerId = containerId;
	}

	@NonNull
	public List<Fragment> getFragments() {
		List<Fragment> result = new ArrayList<>();
		int index = 0;
		while (true) {
			String tag = getFragmentTag(index);
			Fragment fragment = mFragmentManager.findFragmentByTag(tag);
			if (fragment == null) {
				break;
			}

			if (!fragment.isRemoving()) {
				result.add(fragment);
			}
			index++;
		}
		return result;
	}

	public void switchTo(@NonNull Fragment fragment, @NonNull TransitionAnimation animation) {
		List<Fragment> fragments = getFragments();
		boolean isNewFragment = !fragments.contains(fragment);
		Fragment currentFragment = getCurrentFragment();

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (currentFragment != null) {
			animation.applyBeforeFragmentTransactionExecuted(transaction, fragment, currentFragment);
			transaction.detach(currentFragment);
		}

		if (isNewFragment) {
			transaction.add(mContainerId, fragment, getFragmentTag(fragments.size()));
		} else {
			transaction.attach(fragment);
		}

		transaction.commitNow();
		if (currentFragment != null) {
			animation.applyAfterFragmentTransactionExecuted(fragment, currentFragment);
		}
	}

	@Nullable
	public Fragment getCurrentFragment() {
		return mFragmentManager.findFragmentById(mContainerId);
	}

	private String getFragmentTag(int index) {
		return TAG_PREFIX + mContainerId + "_" + index;
	}
}
