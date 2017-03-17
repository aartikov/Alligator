package com.art.screenresultsample.screens;

import java.io.Serializable;

import com.art.alligator.Screen;
import com.art.alligator.ScreenResult;

/**
 * Date: 12.03.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class InputScreen implements Screen {

	// It is convenient to declare a screen result as static inner class of the screen
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