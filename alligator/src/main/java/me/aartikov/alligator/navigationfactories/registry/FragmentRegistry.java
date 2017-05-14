package me.aartikov.alligator.navigationfactories.registry;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.functions.Function;

/**
 * Date: 25.03.2017
 * Time: 17:01
 *
 * @author Artur Artikov
 */

/**
 * Registry for screens represented by fragments.
 */
public class FragmentRegistry {
	private static final String KEY_SCREEN = "me.aartikov.alligator.navigationfactories.registry.FragmentRegistry.KEY_SCREEN";

	private Map<Class<? extends Screen>, Element> mElements = new HashMap<>();

	public <ScreenT extends Screen> void register(Class<ScreenT> screenClass, Function<ScreenT, Fragment> fragmentCreationFunction, Function<Fragment, ScreenT> screenGettingFunction) {
		checkThatNotRegistered(screenClass);
		mElements.put(screenClass, new Element(fragmentCreationFunction, screenGettingFunction));
	}

	public boolean isRegistered(Class<? extends Screen> screenClass) {
		return mElements.containsKey(screenClass);
	}

	@SuppressWarnings("unchecked")
	public Fragment createFragment(Screen screen) {
		checkThatRegistered(screen.getClass());
		Element element = mElements.get(screen.getClass());
		return ((Function<Screen, Fragment>) element.getFragmentCreationFunction()).call(screen);
	}

	public <ScreenT extends Screen> ScreenT getScreen(Fragment fragment, Class<ScreenT> screenClass) {
		checkThatRegistered(screenClass);
		Element element = mElements.get(screenClass);
		return (ScreenT) element.getScreenGettingFunction().call(fragment);
	}

	public static <ScreenT extends Screen> Function<ScreenT, Fragment> getDefaultFragmentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		return new Function<ScreenT, Fragment>() {
			@Override
			public Fragment call(ScreenT screen) {
				try {
					Fragment fragment = fragmentClass.newInstance();
					if (screen instanceof Serializable) {
						Bundle arguments = new Bundle();
						arguments.putSerializable(KEY_SCREEN, (Serializable) screen);
						fragment.setArguments(arguments);
					} else if(screen instanceof Parcelable) {
						Bundle arguments = new Bundle();
						arguments.putParcelable(KEY_SCREEN, (Parcelable)screen);
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

	public static <ScreenT extends Screen> Function<Fragment, ScreenT> getNotImplementedScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Fragment, ScreenT>() {
			@Override
			public ScreenT call(Fragment fragment) {
				throw new RuntimeException("Screen getting function is not implemented for a screen " + screenClass.getSimpleName());
			}
		};
	}

	private void checkThatNotRegistered(Class<? extends Screen> screenClass) {
		if (isRegistered(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is is already registered.");
		}
	}

	private void checkThatRegistered(Class<? extends Screen> screenClass) {
		if (!isRegistered(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not represented by a fragment.");
		}
	}

	private static class Element {
		private Function<? extends Screen, Fragment> mFragmentCreationFunction;
		private Function<Fragment, ? extends Screen> mScreenGettingFunction;

		Element(Function<? extends Screen, Fragment> fragmentCreationFunction, Function<Fragment, ? extends Screen> screenGettingFunction) {
			mFragmentCreationFunction = fragmentCreationFunction;
			mScreenGettingFunction = screenGettingFunction;
		}

		Function<? extends Screen, Fragment> getFragmentCreationFunction() {
			return mFragmentCreationFunction;
		}

		Function<Fragment, ? extends Screen> getScreenGettingFunction() {
			return mScreenGettingFunction;
		}
	}
}