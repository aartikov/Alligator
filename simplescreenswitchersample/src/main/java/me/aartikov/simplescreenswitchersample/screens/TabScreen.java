package me.aartikov.simplescreenswitchersample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;

/**
 * Date: 07.01.2018
 * Time: 10:59
 *
 * @author Artur Artikov
 */

// Screens used by FragmentScreenSwitcher must have equals and hashCode methods correctly overridden.

public abstract class TabScreen implements Screen, Serializable {

	@Override
	public boolean equals(Object obj) {
		return obj != null && this.getClass() == obj.getClass();
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	public static class Android extends TabScreen {
	}

	public static class Bug extends TabScreen {
	}

	public static class Dog extends TabScreen {
	}
}
