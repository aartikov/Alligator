package me.aartikov.advancedscreenswitchersample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;


// Screens used by FragmentScreenSwitcher must have equals and hashCode methods correctly overridden.

public class TabScreen implements Screen, Serializable {
	private String mName;

	public TabScreen(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TabScreen tabScreen = (TabScreen) o;

		return mName.equals(tabScreen.mName);
	}

	@Override
	public int hashCode() {
		return mName.hashCode();
	}
}
