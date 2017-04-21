package me.aartikov.alligator.animations.dialog;

import android.support.v4.app.DialogFragment;

import me.aartikov.alligator.DialogAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:39
 *
 * @author Artur Artikov
 */

/**
 * Dialog animation that does nothing.
 */
public class DummyDialogAnimation implements DialogAnimation {

	@Override
	public void applyBeforeShowing(DialogFragment dialogFragment) {
	}

	@Override
	public void applyAfterShowing(DialogFragment dialogFragment) {
	}
}
