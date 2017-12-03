package me.aartikov.screenresultsample;

import android.content.Intent;
import android.net.Uri;

import me.aartikov.alligator.functions.ActivityConverter;
import me.aartikov.alligator.functions.ScreenResultConverter;
import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory;
import me.aartikov.screenresultsample.screens.ImagePickerScreen;
import me.aartikov.screenresultsample.screens.MainScreen;
import me.aartikov.screenresultsample.screens.MessageInputScreen;
import me.aartikov.screenresultsample.ui.MainActivity;
import me.aartikov.screenresultsample.ui.MessageInputActivity;

/**
 * Date: 11.02.2017
 * Time: 13:09
 *
 * @author Artur Artikov
 */

// Only a screen represented by an activity can be registered for result. For each screen registered for result will be generated unique requestCode.

public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerActivity(MainScreen.class, MainActivity.class);

		// Default converters (screen result must be Serializable).
		registerActivityForResult(MessageInputScreen.class, MessageInputActivity.class, MessageInputScreen.Result.class);

		// Custom converters
		ActivityConverter<ImagePickerScreen> imagePickerConverter = new ActivityConverter<>(
				(context, screen) -> new Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
		);

		ScreenResultConverter<ImagePickerScreen.Result> imagePickerResultConverter = new ScreenResultConverter<>(
				activityResult -> {
					Uri uri = activityResult.getDataUri();
					return uri != null ? new ImagePickerScreen.Result(uri) : null;
				});

		registerActivityForResult(ImagePickerScreen.class, null, ImagePickerScreen.Result.class, imagePickerConverter, imagePickerResultConverter);
	}
}
