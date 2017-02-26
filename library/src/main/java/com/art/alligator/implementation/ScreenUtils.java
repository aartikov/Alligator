package com.art.alligator.implementation;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.art.alligator.Screen;

/**
 * Date: 11.02.2017
 * Time: 16:27
 *
 * @author Artur Artikov
 */

public class ScreenUtils {
	private static final String KEY_SCREEN_CLASS = "com.art.alligator.implementation.ScreenUtils.KEY_SCREEN_CLASS";
	private static final String KEY_SCREEN = "com.art.alligator.implementation.ScreenUtils.KEY_SCREEN";

	private ScreenUtils() {
	}

	@SuppressWarnings("unchecked")
	public static <ScreenT extends Screen> ScreenT getScreen(Activity activity, ScreenT defaultScreen) {
		ScreenT screen = (ScreenT) activity.getIntent().getSerializableExtra(KEY_SCREEN);
		return screen != null ? screen : defaultScreen;
	}

	public static <ScreenT extends Screen> ScreenT getScreen(Activity activity) {
		return getScreen(activity, null);
	}

	@SuppressWarnings("unchecked")
	public static <ScreenT extends Screen> ScreenT getScreen(Fragment fragment, ScreenT defaultScreen) {
		ScreenT screen = null;
		if (fragment.getArguments() != null) {
			screen = (ScreenT) fragment.getArguments().getSerializable(KEY_SCREEN);
		}
		return screen != null ? screen : defaultScreen;
	}

	public static <ScreenT extends Screen> ScreenT getScreen(Fragment fragment) {
		return getScreen(fragment, null);
	}

	public static Intent createActivityIntent(Context context, Class<? extends Activity> activityClass, Screen screen) {
		Intent intent = new Intent(context, activityClass);
		if (screen instanceof Serializable) {
			intent.putExtra(KEY_SCREEN, (Serializable) screen);
		}
		return intent;
	}

	public static Fragment createFragment(Class<? extends Fragment> fragmentClass, Screen screen) {
		try {
			Fragment fragment = fragmentClass.newInstance();
			if (screen instanceof Serializable) {
				Bundle arguments = new Bundle();
				arguments.putSerializable(KEY_SCREEN, (Serializable) screen);
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

	@SuppressWarnings("unchecked")
	public static Class<? extends Screen> getScreenClass(Activity activity) {
		Class<? extends Screen> screenClass = (Class<? extends Screen>) activity.getIntent().getSerializableExtra(KEY_SCREEN_CLASS);
		return screenClass != null ? screenClass : Screen.class;
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends Screen> getScreenClass(Fragment fragment) {
		Class<? extends Screen> screenClass = null;
		if (fragment.getArguments() != null) {
			screenClass = (Class<? extends Screen>)  fragment.getArguments().getSerializable(KEY_SCREEN_CLASS);
		}
		return screenClass != null ? screenClass : Screen.class;
	}

	public static void putScreenClass(Intent intent, Class<? extends Screen> screenClass) {
		intent.putExtra(KEY_SCREEN_CLASS, screenClass);
	}

	public static void putScreenClass(Fragment fragment, Class<? extends Screen> screenClass) {
		Bundle arguments = fragment.getArguments();
		if(arguments == null) {
			arguments = new Bundle();
			fragment.setArguments(arguments);
		}
		arguments.putSerializable(KEY_SCREEN_CLASS, screenClass);
	}
}
