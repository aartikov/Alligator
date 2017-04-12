package me.aartikov.alligator.navigationfactories.registry;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;

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
	private static final String KEY_SCREEN_RESULT = "me.aartikov.alligator.navigationfactories.registry.ScreenForResultRegistry.KEY_SCREEN_RESULT";
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

	public static <ScreenResultT extends ScreenResult> Function<ScreenResultT, ActivityResult> getDefaultActivityResultCreationFunction(Class<ScreenResultT> screenResultClass) {
		return new Function<ScreenResultT, ActivityResult>() {
			@Override
			public ActivityResult call(ScreenResultT screenResult) {
				if (screenResult == null) {
					return new ActivityResult(Activity.RESULT_CANCELED, null);
				}
				if (!(screenResult instanceof Serializable)) {
					throw new IllegalArgumentException("Screen result " + screenResult.getClass().getCanonicalName() + " should be Serializable.");
				}
				Intent data = new Intent();
				data.putExtra(KEY_SCREEN_RESULT, (Serializable) screenResult);
				return new ActivityResult(Activity.RESULT_OK, data);
			}
		};
	}

	public static <ScreenResultT extends ScreenResult> Function<ActivityResult, ScreenResultT> getDefaultScreenResultGettingFunction(final Class<ScreenResultT> screenResultClass) {
		return new Function<ActivityResult, ScreenResultT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenResultT call(ActivityResult activityResult) {
				if (activityResult.getIntent() == null || activityResult.getResultCode() != Activity.RESULT_OK) {
					return null;
				}
				if (!Serializable.class.isAssignableFrom(screenResultClass)) {
					throw new IllegalArgumentException("Screen result " + screenResultClass.getCanonicalName() + " should be Serializable.");
				}
				return (ScreenResultT) activityResult.getIntent().getSerializableExtra(KEY_SCREEN_RESULT);
			}
		};
	}

	public static <ScreenResultT extends ScreenResult> Function<ScreenResultT, ActivityResult> getNotImplementedActivityResultCreationFunction(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass) {
		return new Function<ScreenResultT, ActivityResult>() {
			@Override
			public ActivityResult call(ScreenResultT screenResult) {
				throw new RuntimeException("ActivityResult creation function is not implemented for a screen " + screenClass.getSimpleName());
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
