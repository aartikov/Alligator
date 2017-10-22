package me.aartikov.simplescreenswitchersample;

import android.support.v4.app.Fragment;

import me.aartikov.alligator.functions.FragmentConverter;
import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory;
import me.aartikov.simplescreenswitchersample.screens.MainScreen;
import me.aartikov.simplescreenswitchersample.screens.TabScreen;
import me.aartikov.simplescreenswitchersample.ui.AndroidFragment;
import me.aartikov.simplescreenswitchersample.ui.BugFragment;
import me.aartikov.simplescreenswitchersample.ui.DogFragment;
import me.aartikov.simplescreenswitchersample.ui.MainActivity;

/**
 * Date: 11.02.2017
 * Time: 13:24
 *
 * @author Artur Artikov
 */
public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerActivity(MainScreen.class, MainActivity.class);

		// It is a little bit tricky to register enum based screens
		FragmentConverter<TabScreen> tabScreenConverter = new FragmentConverter<>(
				screen -> FragmentConverter.getDefaultFragmentCreationFunction(TabScreen.class, getTabFragmentClass(screen)).call(screen),
				FragmentConverter.getDefaultScreenGettingFunction(TabScreen.class));

		registerFragment(TabScreen.class, tabScreenConverter);
	}

	private Class<? extends Fragment> getTabFragmentClass(TabScreen screen) {
		switch (screen) {
			case ANDROID:
				return AndroidFragment.class;
			case BUG:
				return BugFragment.class;
			case DOG:
				return DogFragment.class;
			default:
				throw new IllegalArgumentException("Unknown screen " + screen);
		}
	}
}
