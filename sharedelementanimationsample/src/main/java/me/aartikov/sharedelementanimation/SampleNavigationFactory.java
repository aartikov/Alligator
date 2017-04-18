package me.aartikov.sharedelementanimation;

import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory;
import me.aartikov.sharedelementanimation.screens.*;
import me.aartikov.sharedelementanimation.ui.*;

/**
 * Date: 11.02.2017
 * Time: 13:09
 *
 * @author Artur Artikov
 */

public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerFragment(FirstScreen.class, FirstFragment.class);
		registerFragment(SecondScreen.class, SecondFragment.class);
	}
}
