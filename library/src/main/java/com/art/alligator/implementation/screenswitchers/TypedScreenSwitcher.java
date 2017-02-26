package com.art.alligator.implementation.screenswitchers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.FragmentManager;

import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;

/**
 * Date: 26.02.2017
 * Time: 19:36
 *
 * @author Artur Artikov
 */

public class TypedScreenSwitcher extends FactoryBasedScreenSwitcher {
	private Map<String, Screen> mScreenMap = new HashMap<>();

	public TypedScreenSwitcher(FragmentManager fragmentManager, int containerId, NavigationFactory navigationFactory, Collection<Screen> screens) {
		super(fragmentManager, containerId, navigationFactory);
		for(Screen screen: screens) {
			String screenName = screen.getClass().getName();
			if(mScreenMap.containsKey(screenName)) {
				throw new IllegalArgumentException("Screens must be of unique type");
			}
			mScreenMap.put(screenName, screen);
		}
	}

	@Override
	protected final Screen getScreen(String screenName) {
		return mScreenMap.get(screenName);
	}

	@Override
	protected final void onScreenSwitched(String screenName) {
		onScreenSwitched(mScreenMap.get(screenName).getClass());
	}

	protected void onScreenSwitched(Class<? extends Screen> screenClass) {
	}
}
