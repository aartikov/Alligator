package me.aartikov.alligator.navigationfactories.registry;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.DialogFragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.functions.Function;

/**
 * Date: 25.03.2017
 * Time: 16:58
 *
 * @author Artur Artikov
 */

/**
 * Registry for screens represented by dialog fragments.
 */
public class DialogFragmentRegistry {
	private Map<Class<? extends Screen>, Element> mElements = new HashMap<>();

	public <ScreenT extends Screen> void register(Class<ScreenT> screenClass, Function<ScreenT, DialogFragment> dialogFragmentCreationFunction, Function<DialogFragment, ScreenT> screenGettingFunction) {
		checkThatNotRegistered(screenClass);
		mElements.put(screenClass, new Element(dialogFragmentCreationFunction, screenGettingFunction));
	}

	public boolean isRegistered(Class<? extends Screen> screenClass) {
		return mElements.containsKey(screenClass);
	}

	@SuppressWarnings("unchecked")
	public DialogFragment createDialogFragment(Screen screen) {
		checkThatRegistered(screen.getClass());
		Element element = mElements.get(screen.getClass());
		return ((Function<Screen, DialogFragment>) element.getDialogFragmentCreationFunction()).call(screen);
	}

	public <ScreenT extends Screen> ScreenT getScreen(DialogFragment dialogFragment, Class<ScreenT> screenClass) {
		checkThatRegistered(screenClass);
		Element element = mElements.get(screenClass);
		return (ScreenT) element.getScreenGettingFunction().call(dialogFragment);
	}

	private void checkThatNotRegistered(Class<? extends Screen> screenClass) {
		if (isRegistered(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is is already registered.");
		}
	}

	private void checkThatRegistered(Class<? extends Screen> screenClass) {
		if (!isRegistered(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not represented by a dialog fragment.");
		}
	}

	private static class Element {
		private Function<? extends Screen, DialogFragment> mDialogFragmentCreationFunction;
		private Function<DialogFragment, ? extends Screen> mScreenGettingFunction;

		Element(Function<? extends Screen, DialogFragment> dialogFragmentCreationFunction, Function<DialogFragment, ? extends Screen> screenGettingFunction) {
			mDialogFragmentCreationFunction = dialogFragmentCreationFunction;
			mScreenGettingFunction = screenGettingFunction;
		}

		Function<? extends Screen, DialogFragment> getDialogFragmentCreationFunction() {
			return mDialogFragmentCreationFunction;
		}

		Function<DialogFragment, ? extends Screen> getScreenGettingFunction() {
			return mScreenGettingFunction;
		}
	}
}
