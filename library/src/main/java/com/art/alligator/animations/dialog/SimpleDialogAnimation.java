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
 * {@link DialogAnimation} implementation that uses an style resource to play dialog fragment animation
 */
public class SimpleDialogAnimation implements DialogAnimation {
	private int mWindowAnimationsStyleRes;

	/**
	 * Constructor
	 *
	 * @param windowAnimationsStyleRes window animation style resource, for example
	 *                                 <pre>
	 *                                                                 <style name="DialogAnimation">
	 *                                                                   <item name="android:windowEnterAnimation">@android:anim/slide_in_left</item>
	 *                                                                   <item name="android:windowExitAnimation">@android:anim/slide_out_right</item>
	 *                                                                 </style>
	 *                                                                 </pre>
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
