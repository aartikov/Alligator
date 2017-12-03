package me.aartikov.alligator.functions;

import java.io.Serializable;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.Screen;

/**
 * Date: 21.10.2017
 * Time: 12:56
 *
 * @author Artur Artikov
 */

public class FragmentConverter<ScreenT extends Screen> {
	private static final String KEY_SCREEN = "me.aartikov.alligator.KEY_SCREEN";

	private Function<ScreenT, Fragment> mFragmentCreationFunction;
	private Function<Fragment, ScreenT> mScreenGettingFunction;

	public FragmentConverter(Function<ScreenT, Fragment> fragmentCreationFunction, Function<Fragment, ScreenT> screenGettingFunction) {
		mFragmentCreationFunction = fragmentCreationFunction;
		mScreenGettingFunction = screenGettingFunction;
	}

	public FragmentConverter(Function<ScreenT, Fragment> fragmentCreationFunction) {
		this(fragmentCreationFunction, null);
	}

	public FragmentConverter(final Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		this(getDefaultFragmentCreationFunction(screenClass, fragmentClass), getDefaultScreenGettingFunction(screenClass));
	}

	public <T extends ScreenT> Fragment createFragment(T screen) {
		return mFragmentCreationFunction.call(screen);
	}

	public ScreenT getScreen(Fragment fragment, Class<? extends ScreenT> screenClass) {
		if (mScreenGettingFunction == null) {
			throw new RuntimeException("Screen getting function is not implemented for a screen " + screenClass.getSimpleName());
		}
		return mScreenGettingFunction.call(fragment);
	}

	public static <ScreenT extends Screen> Function<ScreenT, Fragment> getDefaultFragmentCreationFunction(Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		return new Function<ScreenT, Fragment>() {
			@Override
			public Fragment call(ScreenT screen) {
				try {
					Fragment fragment = fragmentClass.newInstance();
					if (screen instanceof Serializable) {
						Bundle arguments = new Bundle();
						arguments.putSerializable(KEY_SCREEN, (Serializable) screen);
						fragment.setArguments(arguments);
					} else if (screen instanceof Parcelable) {
						Bundle arguments = new Bundle();
						arguments.putParcelable(KEY_SCREEN, (Parcelable) screen);
						fragment.setArguments(arguments);
					}
					return fragment;
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

	public static <ScreenT extends Screen> Function<Fragment, ScreenT> getDefaultScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Fragment, ScreenT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenT call(Fragment fragment) {
				if (fragment.getArguments() == null) {
					throw new IllegalArgumentException("Fragment has no arguments.");
				} else if (Serializable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) fragment.getArguments().getSerializable(KEY_SCREEN);
				} else if (Parcelable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) fragment.getArguments().getParcelable(KEY_SCREEN);
				} else {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable or Parcelable.");
				}
			}
		};
	}
}
