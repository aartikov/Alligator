package me.aartikov.alligator.functions;

import java.io.Serializable;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;

import me.aartikov.alligator.Screen;

/**
 * Date: 21.10.2017
 * Time: 12:56
 *
 * @author Artur Artikov
 */

/**
 * Converts a screen to a dialog fragment and vice versa.
 * @param <ScreenT> screen type
 */
public class DialogFragmentConverter<ScreenT extends Screen> {
	private static final String KEY_SCREEN = "me.aartikov.alligator.KEY_SCREEN";

	private Function<ScreenT, DialogFragment> mDialogFragmentCreationFunction;
	private Function<DialogFragment, ScreenT> mScreenGettingFunction;

	public DialogFragmentConverter(Function<ScreenT, DialogFragment> dialogFragmentCreationFunction, Function<DialogFragment, ScreenT> screenGettingFunction) {
		mDialogFragmentCreationFunction = dialogFragmentCreationFunction;
		mScreenGettingFunction = screenGettingFunction;
	}

	public DialogFragmentConverter(Function<ScreenT, DialogFragment> dialogFragmentCreationFunction) {
		this(dialogFragmentCreationFunction, null);
	}

	public DialogFragmentConverter(final Class<ScreenT> screenClass, final Class<? extends DialogFragment> dialogFragmentClass) {
		this(getDefaultDialogFragmentCreationFunction(screenClass, dialogFragmentClass), getDefaultScreenGettingFunction(screenClass));
	}

	public <T extends ScreenT> DialogFragment createDialogFragment(T screen) {
		return mDialogFragmentCreationFunction.call(screen);
	}

	public ScreenT getScreen(DialogFragment dialogFragment, Class<? extends ScreenT> screenClass) {
		if (mScreenGettingFunction == null) {
			throw new RuntimeException("Screen getting function is not implemented for a screen " + screenClass.getSimpleName());
		}
		return mScreenGettingFunction.call(dialogFragment);
	}

	public static <ScreenT extends Screen> Function<ScreenT, DialogFragment> getDefaultDialogFragmentCreationFunction(Class<ScreenT> screenClass, final Class<? extends DialogFragment> dialogFragmentClass) {
		return new Function<ScreenT, DialogFragment>() {
			@Override
			public DialogFragment call(ScreenT screen) {
				try {
					DialogFragment dialogFragment = dialogFragmentClass.newInstance();
					if (screen instanceof Serializable) {
						Bundle arguments = new Bundle();
						arguments.putSerializable(KEY_SCREEN, (Serializable) screen);
						dialogFragment.setArguments(arguments);
					} else if (screen instanceof Parcelable) {
						Bundle arguments = new Bundle();
						arguments.putParcelable(KEY_SCREEN, (Parcelable) screen);
						dialogFragment.setArguments(arguments);
					}
					return dialogFragment;
				} catch (InstantiationException e) {
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
	}

	public static <ScreenT extends Screen> Function<DialogFragment, ScreenT> getDefaultScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<DialogFragment, ScreenT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenT call(DialogFragment dialogFragment) {
				if (dialogFragment.getArguments() == null) {
					throw new IllegalArgumentException("Dialog dialogFragment has no arguments.");
				} else if (Serializable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) dialogFragment.getArguments().getSerializable(KEY_SCREEN);
				} else if (Parcelable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) dialogFragment.getArguments().getParcelable(KEY_SCREEN);
				} else {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable or Parcelable.");
				}
			}
		};
	}
}
