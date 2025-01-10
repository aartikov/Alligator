package me.aartikov.sharedelementanimation.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;


public class SecondScreen implements Screen, Serializable {
	private int mKittenIndex;

	public SecondScreen(int kittenIndex) {
		mKittenIndex = kittenIndex;
	}

	public int getKittenIndex() {
		return mKittenIndex;
	}
}
