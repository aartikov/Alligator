package me.aartikov.alligator.destinations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.FragmentConverter;
import me.aartikov.alligator.helpers.ScreenClassHelper;


public class FragmentDestination implements Destination {
	private Class<? extends Screen> mScreenClass;
	private FragmentConverter<? extends Screen> mFragmentConverter;
	@Nullable
	private Class<? extends ScreenResult> mScreenResultClass;
	private ScreenClassHelper mScreenClassHelper;

	public FragmentDestination(@NonNull Class<? extends Screen> screenClass,
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