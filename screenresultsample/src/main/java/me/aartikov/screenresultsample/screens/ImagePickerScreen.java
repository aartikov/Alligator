package me.aartikov.screenresultsample.screens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.converters.OneWayIntentConverter;
import me.aartikov.alligator.converters.OneWayScreenResultConverter;

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
	public static class Converter extends OneWayIntentConverter<ImagePickerScreen> {

		@NonNull
		@Override
		public Intent createIntent(@NonNull Context context, @NonNull ImagePickerScreen screen) {
			return new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
		}
	}

	// Screen result converter. Creates a screen result from ActivityResult.
	public static class ResultConverter extends OneWayScreenResultConverter<Result> {

		@Nullable
		@Override
		public ImagePickerScreen.Result getScreenResult(@NonNull ActivityResult activityResult) {
			Uri uri = activityResult.getDataUri();
			return uri != null ? new ImagePickerScreen.Result(uri) : null;
		}
	}
}
