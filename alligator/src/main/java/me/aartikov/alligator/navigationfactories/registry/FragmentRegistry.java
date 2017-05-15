package me.aartikov.alligator.navigationfactories.registry;

import java.util.HashMap;
import java.util.Map;

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