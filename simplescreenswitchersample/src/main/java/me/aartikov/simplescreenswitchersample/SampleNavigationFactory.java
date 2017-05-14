package me.aartikov.simplescreenswitchersample;

import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory;
import me.aartikov.simplescreenswitchersample.screens.*;
import me.aartikov.simplescreenswitchersample.ui.*;

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
