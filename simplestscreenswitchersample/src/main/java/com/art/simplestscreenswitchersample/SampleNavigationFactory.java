package com.art.simplestscreenswitchersample;

import com.art.alligator.navigationfactory.RegistryNavigationFactory;
import com.art.simplestscreenswitchersample.screens.*;
import com.art.simplestscreenswitchersample.ui.*;

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
	}
}
