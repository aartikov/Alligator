package me.aartikov.simplescreenswitchersample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;

/**
 * Date: 11.02.2017
 * Time: 14:15
 *
 * @author Artur Artikov
 */

public class TabScreen implements Screen, Serializable {
	private String mName;

	public TabScreen(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	// Override equals() and hashCode(), because this screen is used as a key in a screen switcher.
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TabScreen tabScreen = (TabScreen) o;

		return mName != null ? mName.equals(tabScreen.mName) : tabScreen.mName == null;

	}

	@Override
	public int hashCode() {
		return mName != null ? mName.hashCode() : 0;
	}
}
