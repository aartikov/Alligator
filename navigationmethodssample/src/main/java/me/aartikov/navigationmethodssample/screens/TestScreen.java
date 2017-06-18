package me.aartikov.navigationmethodssample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;

/**
 * Date: 29.12.2016
 * Time: 10:40
 *
 * @author Artur Artikov
 */
public class TestScreen implements Screen, Serializable {
	private int mCounter;

	public TestScreen(int counter) {
		mCounter = counter;
	}

	public int getCounter() {
		return mCounter;
	}
}
