package me.aartikov.advancedscreenswitchersample.screens;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import me.aartikov.advancedscreenswitchersample.R;
import me.aartikov.alligator.Screen;

/**
 * Date: 11.02.2017
 * Time: 14:15
 *
 * @author Artur Artikov
 */

// Screens used by FragmentScreenSwitcher must have equals and hashCode methods correctly overridden. Or you can use enums that already have valid equals and hashCode.

public enum TabScreen implements Screen {
	ANDROID(R.id.tab_android, R.string.tab_android),
	BUG(R.id.tab_bug, R.string.tab_bug),
	DOG(R.id.tab_dog, R.string.tab_dog);

	private int mId;
	private int mName;

	TabScreen(@IdRes int id, @StringRes int name) {
		mId = id;
		mName = name;
	}

	public @IdRes int getId() {
		return mId;
	}

	public @StringRes int getName() {
		return mName;
	}

	public static TabScreen getById(@IdRes int id) {
		for (TabScreen screen : values()) {
			if (screen.getId() == id) {
				return screen;
			}
		}
		throw new IllegalArgumentException("Unknown id " + id);
	}
}
