package me.aartikov.navigationmethodssample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;


public class TestScreen implements Screen, Serializable {
	private int mCounter;

	public TestScreen(int counter) {
		mCounter = counter;
	}

	public int getCounter() {
		return mCounter;
	}
}
