package me.aartikov.simplenavigationsample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;


public class ScreenD implements Screen, Serializable {  // This screen is Serializable, so default fragmentCreationFunction and default screenGettingFunction
														// will be able to serialize and deserialize its arguments.
	private String mMessage;

	public ScreenD(String message) {
		mMessage = message;
	}

	public String getMessage() {
		return mMessage;
	}
}