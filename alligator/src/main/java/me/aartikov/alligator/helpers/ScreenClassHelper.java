package me.aartikov.alligator.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import me.aartikov.alligator.Screen;

/**
 * Date: 21.10.2017
 * Time: 11:38
 *
 * @author Artur Artikov
 */

/**
 * Helper class for storing screen class information in activities and fragments.
 */
public class ScreenClassHelper {
	private static final String KEY_SCREEN_CLASS_NAME = "me.aartikov.alligator.KEY_SCREEN_CLASS_NAME";
	private static final String KEY_PREVIOUS_SCREEN_CLASS_NAME = "me.aartikov.alligator.KEY_PREVIOUS_SCREEN_CLASS_NAME";

	private Map<Class<? extends Activity>, Class<? extends Screen>> mActivityMap = new HashMap<>();     // this map is used when there are no screen class information in an activity intent
	private Map<Integer, Class<? extends Screen>> mRequestCodeMap = new LinkedHashMap<>();

	public void putScreenClass(@NonNull Intent intent, @NonNull Class<? extends Screen> screenClass) {
		intent.putExtra(KEY_SCREEN_CLASS_NAME, screenClass.getName());
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public Class<? extends Screen> getScreenClass(@NonNull Activity activity) {
		String className = activity.getIntent().getStringExtra(KEY_SCREEN_CLASS_NAME);
		Class<? extends Screen> screenClass = getClassByName(className);
		return screenClass != null ? screenClass : mActivityMap.get(activity.getClass());
	}

	public void putScreenClass(@NonNull Fragment fragment, @NonNull Class<? extends Screen> screenClass) {
		Bundle arguments = fragment.getArguments();
		if (arguments == null) {
			arguments = new Bundle();
			fragment.setArguments(arguments);
		}
		arguments.putString(KEY_SCREEN_CLASS_NAME, screenClass.getName());
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public Class<? extends Screen> getScreenClass(@NonNull Fragment fragment) {
		if (fragment.getArguments() == null) {
			return null;
		}

		String className = fragment.getArguments().getString(KEY_SCREEN_CLASS_NAME);
		return (Class<? extends Screen>) getClassByName(className);
	}

	public void putPreviousScreenClass(@NonNull Intent intent, @NonNull Class<? extends Screen> screenClass) {
		intent.putExtra(KEY_PREVIOUS_SCREEN_CLASS_NAME, screenClass.getName());
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public Class<? extends Screen> getPreviousScreenClass(@NonNull Activity activity) {
		String className = activity.getIntent().getStringExtra(KEY_PREVIOUS_SCREEN_CLASS_NAME);
		return getClassByName(className);
	}

	@Nullable
	public Class<? extends Screen> getScreenClass(int requestCode) {
		return mRequestCodeMap.get(requestCode);
	}

	public void addActivityClass(@NonNull Class<? extends Activity> activityClass, @NonNull Class<? extends Screen> screenClass) {
		if (!mActivityMap.containsKey(activityClass)) {
			mActivityMap.put(activityClass, screenClass);
		}
	}

	public void addRequestCode(int requestCode, @NonNull Class<? extends Screen> screenClass) {
		if (!mRequestCodeMap.containsKey(requestCode)) {
			mRequestCodeMap.put(requestCode, screenClass);
		}
	}

	@Nullable
	private static Class getClassByName(@Nullable String className) {
		if (className == null || className.isEmpty()) {
			return null;
		}

		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
