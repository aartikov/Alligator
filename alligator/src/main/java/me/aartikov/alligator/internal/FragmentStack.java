package me.aartikov.alligator.internal;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.TransitionAnimation;

/**
 * Date: 19.03.2017
 * Time: 13:46
 *
 * @author Artur Artikov
 */

/**
 * Custom implementation of a fragment backstack with flexible animation control.
 */
public class FragmentStack {
	private static final String TAG_PREFIX = "me.aartikov.alligator.internal.FragmentStack.TAG_";
	private FragmentManager mFragmentManager;
	private int mContainerId;

	public static FragmentStack from(NavigationContext navigationContext) {
		return new FragmentStack(navigationContext.getFragmentManager(), navigationContext.getContainerId());
	}

	public FragmentStack(FragmentManager fragmentManager, int containerId) {
		if (fragmentManager == null) {
			throw new IllegalArgumentException("FragmentManager can't be null.");
		}

		if (containerId <= 0) {
			throw new IllegalArgumentException("ContainerId is not set.");
		}

		mFragmentManager = fragmentManager;
		mContainerId = containerId;
	}

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

	public int getFragmentCount() {
		return getFragments().size();
	}

	public Fragment getCurrentFragment() {
		List<Fragment> fragments = getFragments();
		return fragments.isEmpty() ? null : fragments.get(fragments.size() - 1);
	}

	public void pop(TransitionAnimation animation) {
		List<Fragment> fragments = getFragments();
		int count = fragments.size();
		if (count == 0) {
			throw new IllegalStateException("Can't pop fragment when stack is empty.");
		}
		Fragment currentFragment = fragments.get(count - 1);
		Fragment previousFragment = count > 1 ? fragments.get(count - 2) : null;

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (previousFragment != null) {
			animation.applyBeforeFragmentTransactionExecuted(transaction, previousFragment, currentFragment);
		}
		transaction.remove(currentFragment);
		if (previousFragment != null) {
			transaction.attach(previousFragment);
		}
		transaction.commitNow();
	}

	public void popUntil(Fragment fragment, TransitionAnimation animation) {
		List<Fragment> fragments = getFragments();
		int count = fragments.size();

		int index = fragments.indexOf(fragment);
		if (index == -1) {
			throw new IllegalArgumentException("Fragment is not found.");
		}

		if (index == count - 1) {
			return; // nothing to do
		}

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		for (int i = index + 1; i < count; i++) {
			if (i == count - 1) {
				animation.applyBeforeFragmentTransactionExecuted(transaction, fragment, fragments.get(i));
			}
			transaction.remove(fragments.get(i));
		}
		transaction.attach(fragment);
		transaction.commitNow();
	}

	public void push(Fragment fragment, TransitionAnimation animation) {
		Fragment currentFragment = getCurrentFragment();

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (currentFragment != null) {
			animation.applyBeforeFragmentTransactionExecuted(transaction, fragment, currentFragment);
			transaction.detach(currentFragment);
		}

		int index = getFragmentCount();
		transaction.add(mContainerId, fragment, getFragmentTag(index));
		transaction.commitNow();
	}

	public void replace(Fragment fragment, TransitionAnimation animation) {
		Fragment currentFragment = getCurrentFragment();

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (currentFragment != null) {
			animation.applyBeforeFragmentTransactionExecuted(transaction, currentFragment, fragment);
			transaction.remove(currentFragment);
		}

		int count = getFragmentCount();
		int index = count == 0 ? 0 : count - 1;
		transaction.add(mContainerId, fragment, getFragmentTag(index));
		transaction.commitNow();
	}

	public void reset(Fragment fragment, TransitionAnimation animation) {
		List<Fragment> fragments = getFragments();
		int count = fragments.size();

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		for (int i = 0; i < count; i++) {
			if (i == count - 1) {
				animation.applyBeforeFragmentTransactionExecuted(transaction, fragment, fragments.get(i));
			}
			transaction.remove(fragments.get(i));
		}

		transaction.add(mContainerId, fragment, getFragmentTag(0));
		transaction.commitNow();
	}

	private String getFragmentTag(int index) {
		return TAG_PREFIX + mContainerId + "_" + index;
	}
}
