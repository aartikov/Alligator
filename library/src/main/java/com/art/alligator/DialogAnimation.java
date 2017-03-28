package com.art.alligator;

import android.support.v4.app.DialogFragment;

import com.art.alligator.animations.dialog.DummyDialogAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:37
 *
 * @author Artur Artikov
 */

/**
 *  Animation that played when a dialog fragment are shown
 */
public interface DialogAnimation {
	DialogAnimation DEFAULT = new DummyDialogAnimation();

	/**
	 * Called after dialogFragment show method was called
	 */
	void applyAfterShowing(DialogFragment dialogFragment);
}
