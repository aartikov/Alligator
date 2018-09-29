package me.aartikov.screenresultsample.screens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.ImplicitIntentConverter;
import me.aartikov.alligator.converters.ImplicitScreenResultConverter;

/**
 * Date: 3/17/2017
 * Time: 10:33
 *
 * @author Artur Artikov
 */
public class ImagePickerScreen implements Screen {

	// Screen result
	public static class Result implements ScreenResult {
		private Uri mUri;

		public Result(Uri uri) {
			mUri = uri;
		}

		public Uri getUri() {
			return mUri;
		}
	}

	// Intent converter. Creates an intent from a screen.
	public static class Converter extends ImplicitIntentConverter<ImagePickerScreen> {

		@Override
		public Intent createIntent(Context context, ImagePickerScreen screen) {
			return new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
		}
	}

	// Screen result converter. Creates a screen result from ActivityResult.
	public static class ResultConverter extends ImplicitScreenResultConverter<Result> {

		@Nullable
		@Override
		public ImagePickerScreen.Result getScreenResult(ActivityResult activityResult) {
			Uri uri = activityResult.getDataUri();
			return uri != null ? new ImagePickerScreen.Result(uri) : null;
		}
	}
}
