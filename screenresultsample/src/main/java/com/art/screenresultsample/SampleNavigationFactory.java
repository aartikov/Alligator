package com.art.screenresultsample;

import com.art.alligator.implementation.RegistryNavigationFactory;
import com.art.screenresultsample.screens.*;
import com.art.screenresultsample.ui.*;

/**
 * Date: 11.02.2017
 * Time: 13:09
 *
 * @author Artur Artikov
 */

public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerActivity(MainScreen.class, MainActivity.class);
		registerActivity(InputScreen.class, InputActivity.class);
	}
}
