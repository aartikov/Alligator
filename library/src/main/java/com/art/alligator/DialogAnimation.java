package com.art.alligator;

import android.support.v4.app.DialogFragment;

import com.art.alligator.animations.dialog.DummyDialogAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:37
 *
 * @author Artur Artikov
 */

public interface DialogAnimation {
	DialogAnimation DEFAULT = new DummyDialogAnimation();

	void applyAfterShowing(DialogFragment dialogFragment);
}
