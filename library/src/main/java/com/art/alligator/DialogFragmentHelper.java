package com.art.alligator;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Date: 25.03.2017
 * Time: 15:19
 *
 * @author Artur Artikov
 */

public class DialogFragmentHelper {
	private static final String TAG = "com.art.alligator.implementation.DialogFragmentHelper.TAG";
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

	public boolean hasVisibleDialog() {
		return mFragmentManager.findFragmentByTag(TAG) != null;
	}

	public void showDialog(DialogFragment dialogFragment) {
		dialogFragment.show(mFragmentManager, TAG);
		mFragmentManager.executePendingTransactions();
	}

	public void hideDialog() {
		DialogFragment dialogFragment = (DialogFragment) mFragmentManager.findFragmentByTag(TAG);
		if(dialogFragment == null) {
			throw new IllegalStateException("There are no visible dialogs.");
		}

		dialogFragment.dismiss();
		mFragmentManager.executePendingTransactions();
	}

	public void hideAllDialogs() {
		while (hasVisibleDialog()) {
			hideDialog();
		}
	}
}
