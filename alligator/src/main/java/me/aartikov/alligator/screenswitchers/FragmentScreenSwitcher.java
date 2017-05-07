package me.aartikov.alligator.screenswitchers;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import me.aartikov.alligator.AnimationData;
import me.aartikov.alligator.ScreenSwitcher;
import me.aartikov.alligator.TransitionAnimation;

/**
 * Date: 01/30/2016
 * Time: 10:13
 *
 * @author Artur Artikov
 */

/**
 * Screen switcher that switches fragments in a container.
 */
public abstract class FragmentScreenSwitcher implements ScreenSwitcher {
	private FragmentManager mFragmentManager;
	private int mContainerId;

	/**
	 * @param fragmentManager fragment manager used for fragment transactions
	 * @param containerId     id of a container where fragments will be added
	 */
	public FragmentScreenSwitcher(FragmentManager fragmentManager, int containerId) {
		mFragmentManager = fragmentManager;
		mContainerId = containerId;
	}

	/**
	 * Creates a new fragment by a screen name.
	 *
	 * @param screenName screen name
	 * @return new fragment
	 */
	protected abstract Fragment createFragment(String screenName);

	/**
	 * Screen switching callback. Called after a screen has been switched.
	 *
	 * @param screenName name of a new screen
	 */
	protected void onScreenSwitched(String screenName) {
	}

	/**
	 * Return a {@link TransitionAnimation} that will be used for a screen transition. Can be overridden for animation configuring.
	 *
	 * @param screenNameFrom name of the screen that disappears during a switching
	 * @param screenNameTo   name of the screen that appears during a switching
	 * @param animationData  animation data for an additional animation configuring
	 * @return an animation that will be used for a transition
	 */
	protected TransitionAnimation getAnimation(String screenNameFrom, String screenNameTo, @Nullable AnimationData animationData) {
		return TransitionAnimation.DEFAULT;
	}

	@Override
	public boolean switchTo(String screenName, @Nullable AnimationData animationData) {
		Fragment currentFragment = getCurrentFragment();

		Fragment newFragment = mFragmentManager.findFragmentByTag(screenName);
		boolean justCreated = newFragment == null;
		if (newFragment == null) {
			newFragment = createFragment(screenName);
			if (newFragment == null) {
				return false;
			}
		}

		if (currentFragment == newFragment) {
			return true;
		}

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		TransitionAnimation animation = currentFragment != null ? getAnimation(currentFragment.getTag(), screenName, animationData) : TransitionAnimation.DEFAULT;

		if (currentFragment != null) {
			animation.applyBeforeFragmentTransactionExecuted(transaction, newFragment, currentFragment);
			transaction.detach(currentFragment);
		}

		if (justCreated) {
			transaction.add(mContainerId, newFragment, screenName);
		} else {
			transaction.attach(newFragment);
		}

		transaction.commitNow();

		if (currentFragment != null) {
			animation.applyAfterFragmentTransactionExecuted(newFragment, currentFragment);
		}
		onScreenSwitched(screenName);
		return true;
	}

	@Override
	public
	@Nullable
	String getCurrentScreenName() {
		Fragment currentFragment = getCurrentFragment();
		return currentFragment != null ? currentFragment.getTag() : null;
	}

	/**
	 * Returns a current fragment.
	 *
	 * @return current fragment in the container, or {@code null} if there are no fragments in the container
	 */
	public Fragment getCurrentFragment() {
		return mFragmentManager.findFragmentById(mContainerId);
	}
}
