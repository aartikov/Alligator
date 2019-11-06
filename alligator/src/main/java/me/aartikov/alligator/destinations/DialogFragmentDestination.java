package me.aartikov.alligator.destinations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.DialogFragmentConverter;
import me.aartikov.alligator.helpers.ScreenClassHelper;


public class DialogFragmentDestination implements Destination {
	private Class<? extends Screen> mScreenClass;
	private DialogFragmentConverter<? extends Screen> mDialogFragmentConverter;
	@Nullable
	private Class<? extends ScreenResult> mScreenResultClass;
	private ScreenClassHelper mScreenClassHelper;

	public DialogFragmentDestination(@NonNull Class<? extends Screen> screenClass,
									 @NonNull DialogFragmentConverter<? extends Screen> fragmentConverter,
									 @Nullable Class<? extends ScreenResult> screenResultClass,
									 @NonNull ScreenClassHelper screenClassHelper) {
		mScreenClass = screenClass;
		mDialogFragmentConverter = fragmentConverter;
		mScreenResultClass = screenResultClass;
		mScreenClassHelper = screenClassHelper;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	public DialogFragment createDialogFragment(@NonNull Screen screen) {
		checkScreenClass(screen.getClass());
		DialogFragment dialogFragment = ((DialogFragmentConverter<Screen>) mDialogFragmentConverter).createDialogFragment(screen);
		mScreenClassHelper.putScreenClass(dialogFragment, screen.getClass());
		return dialogFragment;
	}

	@NonNull
	public Screen getScreen(@NonNull DialogFragment dialogFragment) {
		return mDialogFragmentConverter.getScreen(dialogFragment);
	}

	@Nullable
	public Class<? extends ScreenResult> getScreenResultClass() {
		return mScreenResultClass;
	}

	private void checkScreenClass(@NonNull Class<? extends Screen> screenClass) {
		if (!mScreenClass.isAssignableFrom(screenClass)) {
			throw new IllegalArgumentException("Invalid screen class " + screenClass.getSimpleName() + ". Expected " + mScreenClass.getSimpleName());
		}
	}
}
