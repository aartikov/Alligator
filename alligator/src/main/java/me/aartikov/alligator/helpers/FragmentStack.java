package me.aartikov.alligator.helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import me.aartikov.alligator.animations.TransitionAnimation;


/**
 * Custom implementation of a fragment backstack with flexible animation control.
 */
public class FragmentStack {
	private static final String TAG_PREFIX = "me.aartikov.alligator.FRAGMENT_STACK_TAG_";
	private FragmentManager mFragmentManager;
	private int mContainerId;

	public FragmentStack(@NonNull FragmentManager fragmentManager, int containerId) {
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

	public int getFragmentCount() {
		return getFragments().size();
	}

	@Nullable
	public Fragment getCurrentFragment() {
		return mFragmentManager.findFragmentById(mContainerId);
	}

	public void pop(@NonNull TransitionAnimation animation) {
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

		if (previousFragment != null) {
			animation.applyAfterFragmentTransactionExecuted(previousFragment, currentFragment);
		}
	}

	public void popUntil(@NonNull Fragment fragment, @NonNull TransitionAnimation animation) {
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

		animation.applyAfterFragmentTransactionExecuted(fragment, fragments.get(count - 1));
	}

	public void push(@NonNull Fragment fragment, @NonNull TransitionAnimation animation) {
		Fragment currentFragment = getCurrentFragment();

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (currentFragment != null) {
			animation.applyBeforeFragmentTransactionExecuted(transaction, fragment, currentFragment);
			transaction.detach(currentFragment);
		}

		int index = getFragmentCount();
		transaction.add(mContainerId, fragment, getFragmentTag(index));
		transaction.commitNow();

		if (currentFragment != null) {
			animation.applyAfterFragmentTransactionExecuted(fragment, currentFragment);
		}
	}

	public void replace(@NonNull Fragment fragment, @NonNull TransitionAnimation animation) {
		Fragment currentFragment = getCurrentFragment();

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (currentFragment != null) {
			animation.applyBeforeFragmentTransactionExecuted(transaction, fragment, currentFragment);
			transaction.remove(currentFragment);
		}

		int count = getFragmentCount();
		int index = count == 0 ? 0 : count - 1;
		transaction.add(mContainerId, fragment, getFragmentTag(index));
		transaction.commitNow();

		if (currentFragment != null) {
			animation.applyAfterFragmentTransactionExecuted(fragment, currentFragment);
		}
	}

	public void reset(@NonNull Fragment fragment, @NonNull TransitionAnimation animation) {
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

		if (count > 0) {
			animation.applyAfterFragmentTransactionExecuted(fragment, fragments.get(count - 1));
		}
	}

	private String getFragmentTag(int index) {
		return TAG_PREFIX + mContainerId + "_" + index;
	}
}
