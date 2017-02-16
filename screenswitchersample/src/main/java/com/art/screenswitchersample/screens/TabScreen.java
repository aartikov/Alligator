package com.art.screenswitchersample.screens;

import java.io.Serializable;

import com.art.alligator.Screen;

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
