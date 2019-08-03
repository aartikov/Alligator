package me.aartikov.navigationmethodssample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;

public class TestSmallScreen implements Screen, Serializable {
	private int mCounter;

	public TestSmallScreen(int counter) {
		mCounter = counter;
	}

	public int getCounter() {
		return mCounter;
	}
}
