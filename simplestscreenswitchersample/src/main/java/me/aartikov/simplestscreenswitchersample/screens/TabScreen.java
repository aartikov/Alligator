package me.aartikov.simplestscreenswitchersample.screens;

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
}
