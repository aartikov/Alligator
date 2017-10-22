package me.aartikov.alligator.screenimplementations;

import android.support.v4.app.Fragment;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenImplementation;
import me.aartikov.alligator.ScreenImplementationVisitor;
import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.functions.FragmentConverter;
import me.aartikov.alligator.helpers.ScreenClassHelper;

/**
 * Date: 15.10.2017
 * Time: 11:35
 *
 * @author Artur Artikov
 */

public class FragmentScreenImplementation implements ScreenImplementation {
	private Class<? extends Screen> mScreenClass;
	private FragmentConverter<? extends Screen> mFragmentConverter;
	private ScreenClassHelper mScreenClassHelper;

	public FragmentScreenImplementation(Class<? extends Screen> screenClass, FragmentConverter<? extends Screen> fragmentConverter, ScreenClassHelper screenClassHelper) {
		mScreenClass = screenClass;
		mFragmentConverter = fragmentConverter;
		mScreenClassHelper = screenClassHelper;
	}

	@Override
	public boolean accept(ScreenImplementationVisitor visitor, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException {
		return visitor.execute(this, navigationContext, navigationFactory);
	}

	@SuppressWarnings("unchecked")
	public Fragment createFragment(Screen screen) {
		checkScreenClass(screen.getClass());
		Fragment fragment = ((FragmentConverter<Screen>) mFragmentConverter).createFragment(screen);
		mScreenClassHelper.putScreenClass(fragment, screen.getClass());
		return fragment;
	}

	@SuppressWarnings("unchecked")
	public Screen getScreen(Fragment fragment) {
		return ((FragmentConverter<Screen>) mFragmentConverter).getScreen(fragment, mScreenClass);
	}

	private void checkScreenClass(Class<? extends Screen> screenClass) {
		if (!mScreenClass.isAssignableFrom(screenClass)) {
			throw new IllegalArgumentException("Invalid screen class " + screenClass.getSimpleName() + ". Expected " + mScreenClass.getSimpleName());
		}
	}
}