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
	private static final String KEY_SCREEN = "com.art.navigation.implementation.ScreenUtils.KEY_SCREEN";

	private ScreenUtils() {}

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
		if(fragment.getArguments() != null) {
			screen = (ScreenT) fragment.getArguments().getSerializable(KEY_SCREEN);
		}
		return screen != null ? screen : defaultScreen;
	}

	public static <ScreenT extends Screen> ScreenT getScreen(Fragment fragment) {
		return getScreen(fragment, null);
	}

	public static Intent createActivityIntent(Context context, Class<? extends Activity> activityClass, Screen screen) {
		Intent intent = new Intent(context, activityClass);
		if(screen instanceof Serializable) {
			intent.putExtra(KEY_SCREEN, (Serializable) screen);
		}
		return intent;
	}

	public static Fragment createFragment(Class<? extends Fragment> fragmentClass, Screen screen) {
		try {
			Fragment fragment = fragmentClass.newInstance();
			if(screen instanceof Serializable) {
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
}
