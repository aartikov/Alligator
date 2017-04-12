package me.aartikov.navigationsample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;

/**
 * Date: 29.12.2016
 * Time: 11:32
 *
 * @author Artur Artikov
 */
public class TestSmallScreen implements Screen, Serializable {
	private int mCounter;

	public TestSmallScreen(int counter) {
		mCounter = counter;
	}

	public int getCounter() {
		return mCounter;
	}
}
