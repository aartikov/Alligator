package com.art.screenresultsample;

import android.content.Intent;
import android.net.Uri;

import com.art.alligator.implementation.RegistryNavigationFactory;
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
		registerActivity(ImagePickerScreen.class, null, (context, screen) -> new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"));

		// Don't register MessageInputScreen.Result, so it will use default converting function (result should be Serializable).

		// Custom converting function for ImagePickerScreen.Result
		registerScreenResult(ImagePickerScreen.class, ImagePickerScreen.Result.class, activityResult -> {
			Uri uri = activityResult.getDataUri();
			return uri != null ? new ImagePickerScreen.Result(uri) : null;
		});
	}
}
