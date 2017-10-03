package me.aartikov.navigationmethodssample;

import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory;
import me.aartikov.navigationmethodssample.screens.TestScreen;
import me.aartikov.navigationmethodssample.screens.TestSmallScreen;
import me.aartikov.navigationmethodssample.ui.TestActivity;
import me.aartikov.navigationmethodssample.ui.TestFragment;

/**
 * Date: 11.02.2017
 * Time: 12:15
 *
 * @author Artur Artikov
 */
public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerActivity(TestScreen.class, TestActivity.class);
		registerFragment(TestSmallScreen.class, TestFragment.class);
	}
}
