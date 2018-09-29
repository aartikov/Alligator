package me.aartikov.alligator.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import me.aartikov.alligator.animations.DialogAnimation;

/**
 * Date: 25.03.2017
 * Time: 15:19
 *
 * @author Artur Artikov
 */

/**
 * Helper class for showing and hiding a dialog fragment.
 */
public class DialogFragmentHelper {
	private static final String TAG = "me.aartikov.alligator.DIALOG_FRAGMENT_HELPER_TAG";
	private FragmentManager mFragmentManager;

	public DialogFragmentHelper(@NonNull FragmentManager fragmentManager) {
		mFragmentManager = fragmentManager;
	}

	@Nullable
	public DialogFragment getDialogFragment() {
		DialogFragment dialogFragment = (DialogFragment) mFragmentManager.findFragmentByTag(TAG);
		if (dialogFragment == null || dialogFragment.isRemoving()) {
			return null;
		} else {
			return dialogFragment;
		}
	}

	public boolean isDialogVisible() {
		return getDialogFragment() != null;
	}

	public void showDialog(@NonNull DialogFragment dialogFragment, @NonNull DialogAnimation animation) {
		animation.applyBeforeShowing(dialogFragment);
		dialogFragment.show(mFragmentManager, TAG);
		mFragmentManager.executePendingTransactions();
		animation.applyAfterShowing(dialogFragment);
	}

	public void hideDialog() {
		DialogFragment dialogFragment = (DialogFragment) mFragmentManager.findFragmentByTag(TAG);
		if (dialogFragment == null) {
			throw new IllegalStateException("Dialog is not visible.");
		}

		dialogFragment.dismiss();
		mFragmentManager.executePendingTransactions();
	}
}
