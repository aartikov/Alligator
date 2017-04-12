package me.aartikov.simplestscreenswitchersample;

import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory;
import me.aartikov.simplestscreenswitchersample.screens.*;
import me.aartikov.simplestscreenswitchersample.ui.*;

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
