package me.aartikov.alligator.converters;

/**
 * Date: 15.09.2018
 * Time: 10:18
 *
 * @author Artur Artikov
 */

import java.io.Serializable;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import me.aartikov.alligator.Screen;

/**
 * Creates a dialog fragment of the given class. It also puts a screen to the fragment's arguments if {@code ScreenT} is {@code Serializable} or {@code Parcelable}.
 *
 * @param <ScreenT> screen type
 */

public class DefaultDialogFragmentConverter<ScreenT extends Screen> implements DialogFragmentConverter<ScreenT> {
	private static final String KEY_SCREEN = "me.aartikov.alligator.KEY_SCREEN";

	private Class<ScreenT> mScreenClass;
	private Class<? extends DialogFragment> mDialogFragmentClass;

	public DefaultDialogFragmentConverter(Class<ScreenT> screenClass, Class<? extends DialogFragment> dialogFragmentClass) {
		mScreenClass = screenClass;
		mDialogFragmentClass = dialogFragmentClass;
	}

	@Override
	@NonNull
	public DialogFragment createDialogFragment(@NonNull ScreenT screen) {
		try {
			DialogFragment dialogFragment = mDialogFragmentClass.newInstance();
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
			throw new RuntimeException("Failed to create a dialog fragment", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed to create a dialog fragment", e);
		}
	}

	@Override
	@NonNull
	@SuppressWarnings("unchecked")
	public ScreenT getScreen(@NonNull DialogFragment dialogFragment) {
		if (dialogFragment.getArguments() == null) {
			throw new IllegalArgumentException("Fragment has no arguments.");
		} else if (Serializable.class.isAssignableFrom(mScreenClass)) {
			return checkNotNull((ScreenT) dialogFragment.getArguments().getSerializable(KEY_SCREEN));
		} else if (Parcelable.class.isAssignableFrom(mScreenClass)) {
			return checkNotNull((ScreenT) dialogFragment.getArguments().getParcelable(KEY_SCREEN));
		} else {
			throw new IllegalArgumentException("Screen " + mScreenClass.getSimpleName() + " should be Serializable or Parcelable.");
		}
	}

	private ScreenT checkNotNull(@Nullable ScreenT screen) {
		if (screen == null) {
			throw new IllegalArgumentException("Failed to get screen from arguments of fragment.");
		}
		return screen;
	}
}
