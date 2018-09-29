package me.aartikov.screenresultsample;

import me.aartikov.alligator.navigationfactories.GeneratedNavigationFactory;
import me.aartikov.screenresultsample.screens.ImagePickerScreen;

/**
 * Date: 11.02.2017
 * Time: 13:09
 *
 * @author Artur Artikov
 */

public class SampleNavigationFactory extends GeneratedNavigationFactory {   // extends GeneratedNavigationFactory to register custom converters

	public SampleNavigationFactory() {
		registerActivityForResult(ImagePickerScreen.class, ImagePickerScreen.Result.class, new ImagePickerScreen.Converter(), new ImagePickerScreen.ResultConverter());
	}
}
