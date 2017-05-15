package me.aartikov.alligator.navigationfactories.registry;

import java.util.HashMap;
import java.util.Map;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.functions.Function;

/**
 * Date: 25.03.2017
 * Time: 17:02
 *
 * @author Artur Artikov
 */

/**
 * Registry for screens that can return results.
 */
public class ScreenForResultRegistry {
	private static final int INITIAL_REQUEST_CODE = 1000;

	private Map<Class<? extends Screen>, Element> mElements = new HashMap<>();

	public <ScreenResultT extends ScreenResult> void register(Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass,
	                                                          Function<ScreenResultT, ActivityResult> activityResultCreationFunction,
	                                                          Function<ActivityResult, ScreenResultT> screenResultGettingFunction) {
		checkThatNotRegistered(screenClass);
		int requestCode = INITIAL_REQUEST_CODE + mElements.size();
		mElements.put(screenClass, new Element(requestCode, screenResultClass, activityResultCreationFunction, screenResultGettingFunction));
	}

	public boolean isRegistered(Class<? extends Screen> screenClass) {
		return mElements.containsKey(screenClass);
	}

	public int getRequestCode(Class<? extends Screen> screenClass) {
		checkThatRegistered(screenClass);
		Element element = mElements.get(screenClass);
		return element.getRequestCode();
	}

	public Class<? extends ScreenResult> getScreenResultClass(Class<? extends Screen> screenClass) {
		checkThatRegistered(screenClass);
		Element element = mElements.get(screenClass);
		return element.getScreenResultClass();
	}

	@SuppressWarnings("unchecked")
	public ActivityResult createActivityResult(Class<? extends Screen> screenClass, ScreenResult screenResult) {
		checkThatRegistered(screenClass);
		Element element = mElements.get(screenClass);
		return ((Function<ScreenResult, ActivityResult>) element.getActivityResultCreationFunction()).call(screenResult);
	}

	public ScreenResult getScreenResult(Class<? extends Screen> screenClass, ActivityResult activityResult) {
		checkThatRegistered(screenClass);
		Element element = mElements.get(screenClass);
		return element.getScreenResultGettingFunction().call(activityResult);
	}

	private void checkThatNotRegistered(Class<? extends Screen> screenClass) {
		if (isRegistered(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is is already registered.");
		}
	}

	private void checkThatRegistered(Class<? extends Screen> screenClass) {
		if (!isRegistered(screenClass)) {
			throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " is not registered for result.");
		}
	}

	private static class Element {
		private int mRequestCode = -1;
		private Class<? extends ScreenResult> mScreenResultClass;
		private Function<? extends ScreenResult, ActivityResult> mActivityResultCreationFunction;
		private Function<ActivityResult, ? extends ScreenResult> mScreenResultGettingFunction;

		Element(int requestCode, Class<? extends ScreenResult> screenResultClass, Function<? extends ScreenResult, ActivityResult> activityResultCreationFunction,
		        Function<ActivityResult, ? extends ScreenResult> screenResultGettingFunction) {
			mRequestCode = requestCode;
			mScreenResultClass = screenResultClass;
			mActivityResultCreationFunction = activityResultCreationFunction;
			mScreenResultGettingFunction = screenResultGettingFunction;
		}

		int getRequestCode() {
			return mRequestCode;
		}

		Class<? extends ScreenResult> getScreenResultClass() {
			return mScreenResultClass;
		}

		Function<? extends ScreenResult, ActivityResult> getActivityResultCreationFunction() {
			return mActivityResultCreationFunction;
		}

		Function<ActivityResult, ? extends ScreenResult> getScreenResultGettingFunction() {
			return mScreenResultGettingFunction;
		}
	}
}
