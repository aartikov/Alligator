package me.aartikov.simplenavigationsample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;

/**
 * Date: 22.01.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class MessageScreen implements Screen, Serializable {
	private String mMessage;

	public MessageScreen(String message) {
		mMessage = message;
	}

	public String getMessage() {
		return mMessage;
	}
}