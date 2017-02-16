package com.art.alligator;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

/**
 * Date: 29.12.2016
 * Time: 10:13
 *
 * @author Artur Artikov
 */
public class NavigationContext {
	private Activity mActivity;
	private FragmentManager mFragmentManager;
	private int mContainerId;
	private ScreenSwitcher mScreenSwitcher;

	public NavigationContext(Activity activity) {
		this(activity, null, 0, null);
	}

	public NavigationContext(Activity activity, FragmentManager fragmentManager, int containerId) {
		this(activity, fragmentManager, containerId, null);
	}

	public NavigationContext(Activity activity, ScreenSwitcher screenSwitcher) {
		this(activity, null, 0, screenSwitcher);
	}

	public NavigationContext(Activity activity, FragmentManager fragmentManager, int containerId, ScreenSwitcher screenSwitcher) {
		mActivity = activity;
		mFragmentManager = fragmentManager;
		mContainerId = containerId;
		mScreenSwitcher = screenSwitcher;
	}

	public Activity getActivity() {
		return mActivity;
	}

	public FragmentManager getFragmentManager() {
		return mFragmentManager;
	}

	public int getContainerId() {
		return mContainerId;
	}

	public ScreenSwitcher getScreenSwitcher() {
		return mScreenSwitcher;
	}
}
