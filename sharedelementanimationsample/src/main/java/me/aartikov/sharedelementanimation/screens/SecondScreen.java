package me.aartikov.sharedelementanimation.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;

/**
 * Date: 16.04.2017
 * Time: 10:13
 *
 * @author Artur Artikov
 */

public class SecondScreen implements Screen, Serializable {
	private int mKittenIndex;

	public SecondScreen(int kittenIndex) {
		mKittenIndex = kittenIndex;
	}

	public int getKittenIndex() {
		return mKittenIndex;
	}
}
