package com.art.alligator.animations.dialog;

import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;

import com.art.alligator.DialogAnimation;

/**
 * Date: 26.03.2017
 * Time: 12:39
 *
 * @author Artur Artikov
 */

/**
 * Dialog animation that uses a window animation style resource.
 */
public class SimpleDialogAnimation implements DialogAnimation {
	private int mWindowAnimationsStyleRes;

	/**
	 * @param windowAnimationsStyleRes window animation style resource
	 * <pre>{@code For example:
	 * <style name="SlideDialogAnimation">
	 *   <item name="android:windowEnterAnimation">@anim/slide_in_left</item>
	 *   <item name="android:windowExitAnimation">@anim/slide_out_right</item>
	 * </style>
	 * }</pre>
	 */
	public SimpleDialogAnimation(@StyleRes int windowAnimationsStyleRes) {
		mWindowAnimationsStyleRes = windowAnimationsStyleRes;
	}

	@Override
	public void applyAfterShowing(DialogFragment dialogFragment) {
		if (dialogFragment.getDialog() != null && dialogFragment.getDialog().getWindow() != null) {
			dialogFragment.getDialog().getWindow().setWindowAnimations(mWindowAnimationsStyleRes);
		}
	}
}
