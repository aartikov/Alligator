package me.aartikov.simplenavigationsample;

import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory;
import me.aartikov.simplenavigationsample.screens.FirstScreen;
import me.aartikov.simplenavigationsample.screens.MainScreen;
import me.aartikov.simplenavigationsample.screens.MessageScreen;
import me.aartikov.simplenavigationsample.screens.SecondScreen;
import me.aartikov.simplenavigationsample.ui.FirstFragment;
import me.aartikov.simplenavigationsample.ui.MainActivity;
import me.aartikov.simplenavigationsample.ui.MessageActivity;
import me.aartikov.simplenavigationsample.ui.SecondFragment;

/**
 * Date: 11.02.2017
 * Time: 13:09
 *
 * @author Artur Artikov
 */

public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerActivity(MainScreen.class, MainActivity.class);
		registerActivity(MessageScreen.class, MessageActivity.class);
		registerFragment(FirstScreen.class, FirstFragment.class);
		registerFragment(SecondScreen.class, SecondFragment.class);
	}
}
