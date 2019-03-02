package me.aartikov.alligator.animations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Date: 26.03.2017
 * Time: 12:02
 *
 * @author Artur Artikov
 */

/**
 * Transition animation that uses anim resources.
 */
public class SimpleTransitionAnimation implements TransitionAnimation {
	private int mEnterAnimation;
	private int mExitAnimation;

	/**
	 * @param enterAnimation animation resource that will be used for an appearing activity/fragment
	 * @param exitAnimation  animation resource that will be used for a disappearing activity/fragment
	 */
	public SimpleTransitionAnimation(@AnimRes int enterAnimation, @AnimRes int exitAnimation) {
		mEnterAnimation = enterAnimation;
		mExitAnimation = exitAnimation;
	}

	public int getEnterAnimation() {
		return mEnterAnimation;
	}

	public int getExitAnimation() {
		return mExitAnimation;
	}

	@Override
	@Nullable
	public Bundle getActivityOptionsBundle(@NonNull Activity activity) {
		return null;
	}

	@Override
	public boolean needDelayActivityFinish() {
		return false;
	}

	@Override
	public void applyBeforeActivityStarted(@NonNull Activity currentActivity, @NonNull Intent intent) {
	}

	@Override
	public void applyAfterActivityStarted(@NonNull Activity currentActivity) {
		currentActivity.overridePendingTransition(mEnterAnimation, mExitAnimation);
	}

	@Override
	public void applyBeforeActivityFinished(@NonNull Activity activity) {
	}

	@Override
	public void applyAfterActivityFinished(@NonNull Activity activity) {
		activity.overridePendingTransition(mEnterAnimation, mExitAnimation);
	}

	@Override
	public void applyBeforeFragmentTransactionExecuted(@NonNull FragmentTransaction transaction, @NonNull Fragment enteringFragment, @NonNull Fragment exitingFragment) {
		transaction.setCustomAnimations(mEnterAnimation, mExitAnimation);
	}

	@Override
	public void applyAfterFragmentTransactionExecuted(@NonNull Fragment enteringFragment, @NonNull Fragment exitingFragment) {
	}
}
