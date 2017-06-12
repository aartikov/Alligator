package me.aartikov.simplenavigationsample;

import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory;
import me.aartikov.simplenavigationsample.screens.*;
import me.aartikov.simplenavigationsample.ui.*;

/**
 * Date: 11.02.2017
 * Time: 13:09
 *
 * @author Artur Artikov
 */

public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerActivity(ScreenA.class, ActivityA.class);
		registerActivity(ScreenB.class, ActivityB.class);
		registerFragment(ScreenC.class, FragmentC.class);
		registerFragment(ScreenD.class, FragmentD.class);
	}
}
