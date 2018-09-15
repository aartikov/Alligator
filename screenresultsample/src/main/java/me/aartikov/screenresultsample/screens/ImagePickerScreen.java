package me.aartikov.screenresultsample.screens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.IntentConverter;
import me.aartikov.alligator.converters.ScreenResultConverter;

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

	// Intent converter
	public static class Converter implements IntentConverter<ImagePickerScreen> {
		@Override
		public <T extends ImagePickerScreen> Intent createIntent(Context context, T screen) {
			return new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
		}

		@Override
		public ImagePickerScreen getScreen(Intent intent) {
			throw new UnsupportedOperationException();  // never called, because because we started an external activity
		}
	}

	// Screen result converter
	public static class ResultConverter implements ScreenResultConverter<Result> {

		@Override
		public ActivityResult createActivityResult(@Nullable ImagePickerScreen.Result screenResult) {
			throw new UnsupportedOperationException();  // never called, because we started an external activity
		}

		@Nullable
		@Override
		public ImagePickerScreen.Result getScreenResult(ActivityResult activityResult) {
			Uri uri = activityResult.getDataUri();
			return uri != null ? new ImagePickerScreen.Result(uri) : null;
		}
	}
}
