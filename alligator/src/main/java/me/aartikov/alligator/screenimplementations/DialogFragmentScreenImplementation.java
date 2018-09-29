package me.aartikov.alligator.screenimplementations;

import android.support.v4.app.DialogFragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.DialogFragmentConverter;
import me.aartikov.alligator.helpers.ScreenClassHelper;

/**
 * Date: 15.10.2017
 * Time: 11:35
 *
 * @author Artur Artikov
 */

public class DialogFragmentScreenImplementation implements ScreenImplementation {
	private Class<? extends Screen> mScreenClass;
	private DialogFragmentConverter<? extends Screen> mDialogFragmentConverter;
	private Class<? extends ScreenResult> mScreenResultClass;
	private ScreenClassHelper mScreenClassHelper;

	public DialogFragmentScreenImplementation(Class<? extends Screen> screenClass,
	                                          DialogFragmentConverter<? extends Screen> fragmentConverter,
	                                          Class<? extends ScreenResult> screenResultClass,
	                                          ScreenClassHelper screenClassHelper) {
		mScreenClass = screenClass;
		mDialogFragmentConverter = fragmentConverter;
		mScreenResultClass = screenResultClass;
		mScreenClassHelper = screenClassHelper;
	}

	@SuppressWarnings("unchecked")
	public DialogFragment createDialogFragment(Screen screen) {
		checkScreenClass(screen.getClass());
		DialogFragment dialogFragment = ((DialogFragmentConverter<Screen>) mDialogFragmentConverter).createDialogFragment(screen);
		mScreenClassHelper.putScreenClass(dialogFragment, screen.getClass());
		return dialogFragment;
	}

	@SuppressWarnings("unchecked")
	public Screen getScreen(DialogFragment dialogFragment) {
		return mDialogFragmentConverter.getScreen(dialogFragment);
	}

	public Class<? extends ScreenResult> getScreenResultClass() {
		return mScreenResultClass;
	}

	private void checkScreenClass(Class<? extends Screen> screenClass) {
		if (!mScreenClass.isAssignableFrom(screenClass)) {
			throw new IllegalArgumentException("Invalid screen class " + screenClass.getSimpleName() + ". Expected " + mScreenClass.getSimpleName());
		}
	}
}
