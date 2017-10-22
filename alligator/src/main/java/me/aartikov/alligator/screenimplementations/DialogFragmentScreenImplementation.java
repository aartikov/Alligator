package me.aartikov.alligator.screenimplementations;

import android.support.v4.app.DialogFragment;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.functions.DialogFragmentConverter;
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
	private ScreenClassHelper mScreenClassHelper;

	public DialogFragmentScreenImplementation(Class<? extends Screen> screenClass, DialogFragmentConverter<? extends Screen> fragmentConverter, ScreenClassHelper screenClassHelper) {
		mScreenClass = screenClass;
		mDialogFragmentConverter = fragmentConverter;
		mScreenClassHelper = screenClassHelper;
	}

	@Override
	public boolean accept(ScreenImplementationVisitor visitor, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		return visitor.execute(this, navigationContext, navigationFactory);
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
		return ((DialogFragmentConverter<Screen>) mDialogFragmentConverter).getScreen(dialogFragment, mScreenClass);
	}

	private void checkScreenClass(Class<? extends Screen> screenClass) {
		if (!mScreenClass.isAssignableFrom(screenClass)) {
			throw new IllegalArgumentException("Invalid screen class " + screenClass.getSimpleName() + ". Expected " + mScreenClass.getSimpleName());
		}
	}
}
