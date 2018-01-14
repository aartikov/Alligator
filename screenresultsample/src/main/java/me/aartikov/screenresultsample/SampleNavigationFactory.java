package me.aartikov.screenresultsample;

import android.content.Intent;
import android.net.Uri;

import me.aartikov.alligator.functions.ActivityConverter;
import me.aartikov.alligator.functions.ScreenResultConverter;
import me.aartikov.alligator.navigationfactories.GeneratedNavigationFactory;
import me.aartikov.screenresultsample.screens.ImagePickerScreen;

/**
 * Date: 11.02.2017
 * Time: 13:09
 *
 * @author Artur Artikov
 */

public class SampleNavigationFactory extends GeneratedNavigationFactory {   // extends GeneratedNavigationFactory to add non-trivial registration

	public SampleNavigationFactory() {

		// Custom activity converter
		ActivityConverter<ImagePickerScreen> imagePickerConverter = new ActivityConverter<>(
				(context, screen) -> new Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
		);

		// Custom screen result converter
		ScreenResultConverter<ImagePickerScreen.Result> imagePickerResultConverter = new ScreenResultConverter<>(
				activityResult -> {
					Uri uri = activityResult.getDataUri();
					return uri != null ? new ImagePickerScreen.Result(uri) : null;
				});

		registerActivityForResult(ImagePickerScreen.class, null, ImagePickerScreen.Result.class, imagePickerConverter, imagePickerResultConverter);
	}
}
