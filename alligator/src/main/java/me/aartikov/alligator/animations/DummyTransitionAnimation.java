package me.aartikov.alligator.animations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Date: 26.03.2017
 * Time: 12:01
 *
 * @author Artur Artikov
 */

/**
 * Transition animation that leaves a default animation behavior for activities and fragments.
 */
public class DummyTransitionAnimation implements TransitionAnimation {
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
	}

	@Override
	public void applyBeforeActivityFinished(@NonNull Activity activity) {
	}

	@Override
	public void applyAfterActivityFinished(@NonNull Activity activity) {
	}

	@Override
	public void applyBeforeFragmentTransactionExecuted(@NonNull FragmentTransaction transaction, @NonNull Fragment enteringFragment, @NonNull Fragment exitingFragment) {
	}

	@Override
	public void applyAfterFragmentTransactionExecuted(@NonNull Fragment enteringFragment, @NonNull Fragment exitingFragment) {
	}
}
