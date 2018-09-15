package me.aartikov.screenresultsample;

import me.aartikov.alligator.navigationfactories.GeneratedNavigationFactory;
import me.aartikov.screenresultsample.screens.ImagePickerScreen;

/**
 * Date: 11.02.2017
 * Time: 13:09
 *
 * @author Artur Artikov
 */

public class SampleNavigationFactory extends GeneratedNavigationFactory {   // extends GeneratedNavigationFactory register custom converters

	public SampleNavigationFactory() {
		registerActivityForResult(
				ImagePickerScreen.class,                     // screen class
				null,                                        // activity class (null, because an implicit intent is used)
				ImagePickerScreen.Result.class,              // screen result class
				new ImagePickerScreen.Converter(),           // intent converter
				new ImagePickerScreen.ResultConverter());    // screen result converter
	}
}
