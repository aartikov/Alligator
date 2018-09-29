package me.aartikov.alligator.screenimplementations;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.FragmentConverter;
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
	@Nullable
	private Class<? extends ScreenResult> mScreenResultClass;
	private ScreenClassHelper mScreenClassHelper;

	public FragmentScreenImplementation(@NonNull Class<? extends Screen> screenClass,
	                                    @NonNull FragmentConverter<? extends Screen> fragmentConverter,
	                                    @Nullable Class<? extends ScreenResult> screenResultClass,
	                                    @NonNull ScreenClassHelper screenClassHelper) {
		mScreenClass = screenClass;
		mFragmentConverter = fragmentConverter;
		mScreenResultClass = screenResultClass;
		mScreenClassHelper = screenClassHelper;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	public Fragment createFragment(@NonNull Screen screen) {
		checkScreenClass(screen.getClass());
		Fragment fragment = ((FragmentConverter<Screen>) mFragmentConverter).createFragment(screen);
		mScreenClassHelper.putScreenClass(fragment, screen.getClass());
		return fragment;
	}

	@NonNull
	public Screen getScreen(@NonNull Fragment fragment) {
		return mFragmentConverter.getScreen(fragment);
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