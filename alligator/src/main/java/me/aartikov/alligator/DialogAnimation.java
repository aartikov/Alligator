package me.aartikov.alligator;

import android.support.v4.app.DialogFragment;

import me.aartikov.alligator.animations.dialog.DummyDialogAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:37
 *
 * @author Artur Artikov
 */

/**
 * Animation that played when a dialog fragment is shown.
 */
public interface DialogAnimation {
	DialogAnimation DEFAULT = new DummyDialogAnimation();

	/**
	 * Called before a dialog fragment is shown.
	 *
	 * @param dialogFragment dialog fragment that will be shown
	 */
	void applyBeforeShowing(DialogFragment dialogFragment);

	/**
	 * Called after a dialog fragment was shown and a related fragment transaction was executed.
	 *
	 * @param dialogFragment dialog fragment that was shown
	 */
	void applyAfterShowing(DialogFragment dialogFragment);
}
