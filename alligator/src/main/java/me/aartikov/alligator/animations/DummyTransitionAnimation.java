package me.aartikov.alligator.animations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


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
