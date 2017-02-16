package com.art.screenswitchersample;

import com.art.alligator.implementation.RegistryNavigationFactory;
import com.art.screenswitchersample.screens.*;
import com.art.screenswitchersample.ui.*;

/**
 * Date: 11.02.2017
 * Time: 13:24
 *
 * @author Artur Artikov
 */
public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerActivity(MainScreen.class, MainActivity.class);
		registerFragment(TabScreen.class, TabFragment.class);
		registerFragment(InnerScreen.class, InnerFragment.class);
	}
}
