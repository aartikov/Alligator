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

public enum TabScreen implements Screen {
	ANDROID(R.id.tab_android, R.string.tab_android),
	BUG(R.id.tab_bug, R.string.tab_bug),
	DOG(R.id.tab_dog, R.string.tab_dog);

	private int id;
	private int name;

	TabScreen(@IdRes int id, @StringRes int name) {
		this.id = id;
		this.name = name;
	}

	public @IdRes int getId() {
		return id;
	}

	public @StringRes int getName() {
		return name;
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
