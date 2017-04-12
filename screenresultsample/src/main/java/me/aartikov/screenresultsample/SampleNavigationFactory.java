package me.aartikov.screenresultsample;

import android.content.Intent;
import android.net.Uri;

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

public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerActivity(MainScreen.class, MainActivity.class);

		registerActivity(MessageInputScreen.class, MessageInputActivity.class);
		registerScreenForResult(MessageInputScreen.class, MessageInputScreen.Result.class); // Register with default converting functions (result should be Serializable).

		registerActivity(ImagePickerScreen.class, null, (context, screen) -> new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"));
		registerScreenForResult(ImagePickerScreen.class, ImagePickerScreen.Result.class, activityResult -> {        // Register with custom converting function
			Uri uri = activityResult.getDataUri();
			return uri != null ? new ImagePickerScreen.Result(uri) : null;
		});
	}
}
