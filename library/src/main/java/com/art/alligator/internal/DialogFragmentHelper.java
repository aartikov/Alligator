package com.art.alligator.internal;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.art.alligator.DialogAnimation;
import com.art.alligator.NavigationContext;

/**
 * Date: 25.03.2017
 * Time: 15:19
 *
 * @author Artur Artikov
 */

/**
 * Helper class for showing and hiding dialog fragment
 */
public class DialogFragmentHelper {
	private static final String TAG = "com.art.alligator.internal.DialogFragmentHelper.TAG";
	private FragmentManager mFragmentManager;

	public static DialogFragmentHelper from(NavigationContext navigationContext) {
		return new DialogFragmentHelper(navigationContext.getActivity().getSupportFragmentManager());
	}

	public DialogFragmentHelper(FragmentManager fragmentManager) {
		if (fragmentManager == null) {
			throw new IllegalArgumentException("FragmentManager can't be null.");
		}

		mFragmentManager = fragmentManager;
	}

	public boolean isDialogVisible() {
		mFragmentManager.executePendingTransactions();
		Fragment fragment = mFragmentManager.findFragmentByTag(TAG);
		return fragment != null && !fragment.isRemoving();
	}

	public void showDialog(DialogFragment dialogFragment, DialogAnimation animation) {
		dialogFragment.show(mFragmentManager, TAG);
		mFragmentManager.executePendingTransactions();
		animation.applyAfterShowing(dialogFragment);
	}

	public void hideDialog() {
		DialogFragment dialogFragment = (DialogFragment) mFragmentManager.findFragmentByTag(TAG);
		if(dialogFragment == null) {
			throw new IllegalStateException("Dialog is not visible.");
		}

		dialogFragment.dismiss();
		mFragmentManager.executePendingTransactions();
	}
}
