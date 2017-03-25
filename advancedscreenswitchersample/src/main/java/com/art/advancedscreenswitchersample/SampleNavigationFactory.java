package com.art.advancedscreenswitchersample;

import com.art.alligator.navigationfactory.RegistryNavigationFactory;
import com.art.advancedscreenswitchersample.screens.*;
import com.art.advancedscreenswitchersample.ui.*;

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
