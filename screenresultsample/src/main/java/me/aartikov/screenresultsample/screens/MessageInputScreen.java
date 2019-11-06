package me.aartikov.screenresultsample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;


public class MessageInputScreen implements Screen {

	// It is convenient to declare a result as a static inner class.
	public static class Result implements ScreenResult, Serializable {
		private String mMessage;

		public Result(String message) {
			mMessage = message;
		}

		public String getMessage() {
			return mMessage;
		}
	}
}