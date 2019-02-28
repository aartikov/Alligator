package me.aartikov.alligator.animations;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

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
	 * Is called before a dialog fragment is shown.
	 *
	 * @param dialogFragment dialog fragment that will be shown
	 */
	void applyBeforeShowing(@NonNull DialogFragment dialogFragment);

	/**
	 * Is called after a dialog fragment was shown and a related fragment transaction was executed.
	 *
	 * @param dialogFragment dialog fragment that was shown
	 */
	void applyAfterShowing(@NonNull DialogFragment dialogFragment);
}
