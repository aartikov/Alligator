package com.art.screenswitchersample.screens;

import java.io.Serializable;

import com.art.alligator.Screen;

/**
 * Date: 22.01.2016
 * Time: 0:38
 *
 * @author Artur Artikov
 */
public class InnerScreen implements Screen, Serializable {
	private int mCounter;

	public InnerScreen(int counter) {
		mCounter = counter;
	}

	public int getCounter() {
		return mCounter;
	}
}

