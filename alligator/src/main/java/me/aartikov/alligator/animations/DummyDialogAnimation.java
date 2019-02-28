package me.aartikov.alligator.animations;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * Date: 26.03.2017
 * Time: 12:39
 *
 * @author Artur Artikov
 */

/**
 * Dialog animation that leaves a default animation behavior for dialog fragments.
 */
public class DummyDialogAnimation implements DialogAnimation {

	@Override
	public void applyBeforeShowing(@NonNull DialogFragment dialogFragment) {
	}

	@Override
	public void applyAfterShowing(@NonNull DialogFragment dialogFragment) {
	}
}
