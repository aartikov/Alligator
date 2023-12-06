package me.aartikov.alligator.helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import me.aartikov.alligator.animations.DialogAnimation;


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
