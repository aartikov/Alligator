package com.art.screenresultsample;

import android.content.Intent;
import android.net.Uri;

import com.art.alligator.navigationfactories.RegistryNavigationFactory;
import com.art.screenresultsample.screens.ImagePickerScreen;
import com.art.screenresultsample.screens.MainScreen;
import com.art.screenresultsample.screens.MessageInputScreen;
import com.art.screenresultsample.ui.MainActivity;
import com.art.screenresultsample.ui.MessageInputActivity;

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
