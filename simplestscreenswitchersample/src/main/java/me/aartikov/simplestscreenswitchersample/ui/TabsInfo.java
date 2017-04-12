package me.aartikov.simplestscreenswitchersample.ui;

import java.util.HashMap;
import java.util.Map;

import me.aartikov.alligator.Screen;

/**
 * Date: 16.03.2017
 * Time: 18:43
 *
 * @author Artur Artikov
 */

public class TabsInfo {
	private Map<String, Tab> mTabs = new HashMap<>();

	public void add(String screenName, int tabId, Screen screen) {
		if(mTabs.containsKey(screenName)) {
			throw new IllegalArgumentException("Dublicate screen name");
		}

		mTabs.put(screenName, new Tab(tabId, screen));
	}

	public int getTabId(String screenName) {
		Tab tab = mTabs.get(screenName);
		if (tab == null) {
			throw new IllegalArgumentException("Unknown screen name " + screenName);
		}
		return tab.getId();
	}

	public Screen getScreen(String screenName) {
		Tab tab = mTabs.get(screenName);
		if (tab == null) {
			throw new IllegalArgumentException("Unknown screen name " + screenName);
		}
		return tab.getScreen();
	}

	public String getScreenName(int tabId) {
		for (Map.Entry<String, Tab> entry : mTabs.entrySet()) {
			Tab tab = entry.getValue();
			if (tab.getId() == tabId) {
				return entry.getKey();
			}
		}
		throw new IllegalArgumentException("Unknown tab id " + tabId);
	}

	private static class Tab {
		private int mId;
		private Screen mScreen;

		Tab(int id, Screen screen) {
			mId = id;
			mScreen = screen;
		}

		int getId() {
			return mId;
		}

		Screen getScreen() {
			return mScreen;
		}
	}
}
