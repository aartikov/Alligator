package com.art.simplestnavigationsample;

import com.art.alligator.navigationfactories.RegistryNavigationFactory;
import com.art.simplestnavigationsample.screens.*;
import com.art.simplestnavigationsample.ui.*;

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
	}
}
