package me.aartikov.simplescreenswitchersample.screens;

import android.support.annotation.IdRes;

import me.aartikov.alligator.Screen;
import me.aartikov.simplescreenswitchersample.R;

/**
 * Date: 11.02.2017
 * Time: 14:15
 *
 * @author Artur Artikov
 */

public enum TabScreen implements Screen {
	ANDROID(R.id.tab_android),
	BUG(R.id.tab_bug),
	DOG(R.id.tab_dog);

	private int id;

	TabScreen(@IdRes int id) {
		this.id = id;
	}

	public @IdRes int getId() {
		return id;
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
