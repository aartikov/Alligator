package me.aartikov.alligator.screenimplementations;

import android.support.v4.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.exceptions.NavigationException;
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
	private Class<? extends ScreenResult> mScreenResultClass;
	private ScreenClassHelper mScreenClassHelper;

	public FragmentScreenImplementation(Class<? extends Screen> screenClass,
	                                    FragmentConverter<? extends Screen> fragmentConverter,
	                                    Class<? extends ScreenResult> screenResultClass,
	                                    ScreenClassHelper screenClassHelper) {
		mScreenClass = screenClass;
		mFragmentConverter = fragmentConverter;
		mScreenResultClass = screenResultClass;
		mScreenClassHelper = screenClassHelper;
	}

	@Override
	public <R> R accept(ScreenImplementationVisitor<R> visitor) throws NavigationException {
		return visitor.visit(this);
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

	public Class<? extends ScreenResult> getScreenResultClass() {
		return mScreenResultClass;
	}

	private void checkScreenClass(Class<? extends Screen> screenClass) {
		if (!mScreenClass.isAssignableFrom(screenClass)) {
			throw new IllegalArgumentException("Invalid screen class " + screenClass.getSimpleName() + ". Expected " + mScreenClass.getSimpleName());
		}
	}
}