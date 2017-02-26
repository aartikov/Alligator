package com.art.screenswitchersample.ui;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationContextBinder;
import com.art.alligator.Navigator;
import com.art.alligator.Screen;
import com.art.alligator.implementation.screenswitchers.FactoryBasedScreenSwitcher;
import com.art.screenswitchersample.R;
import com.art.screenswitchersample.SampleApplication;
import com.art.screenswitchersample.screens.TabScreen;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 21.01.2016
 * Time: 23:30
 *
 * @author Artur Artikov
 */
public class MainActivity extends AppCompatActivity implements OnTabSelectListener {
	private static final String ANDROID_SCREEN_NAME = "ANDROID";
	private static final String BUG_SCREEN_NAME = "BUG";
	private static final String DOG_SCREEN_NAME = "DOG";
	private static Map<Integer, String> sTabToNameMap = new HashMap<>();

	static {
		sTabToNameMap.put(R.id.tab_android, ANDROID_SCREEN_NAME);
		sTabToNameMap.put(R.id.tab_bug, BUG_SCREEN_NAME);
		sTabToNameMap.put(R.id.tab_dog, DOG_SCREEN_NAME);
	}

	private Navigator mNavigator;
	private NavigationContextBinder mNavigationContextBinder;
	private FactoryBasedScreenSwitcher mScreenSwitcher;

	@BindView(R.id.activity_main_bottom_bar)
	BottomBar mBottomBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNavigator = SampleApplication.getNavigator();
		mNavigationContextBinder = SampleApplication.getNavigationContextBinder();
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		mBottomBar.setOnTabSelectListener(this, false);
		initScreenSwitcher();

		if (savedInstanceState == null) {
			mNavigator.switchTo(ANDROID_SCREEN_NAME);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		bindNavigationContext();
	}

	@Override
	protected void onPause() {
		mNavigationContextBinder.unbind();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}

	@Override
	public void onTabSelected(@IdRes int tabId) {
		mNavigator.switchTo(tabIdToScreenName(tabId));
	}

	private void initScreenSwitcher() {
		mScreenSwitcher = new FactoryBasedScreenSwitcher(getSupportFragmentManager(), R.id.activity_main_container, SampleApplication.getNavigationFactory()) {
			@Override
			public Screen getScreen(String screenName) {
				return new TabScreen(screenName);
			}

			@Override
			protected void onScreenSwitched(String screenName) {
				bindNavigationContext();
				selectTab(screenNameToTabId(screenName));
			}
		};
	}

	private void bindNavigationContext() {
		Fragment fragment = mScreenSwitcher.getCurrentFragment();
		NavigationContext.Builder builder = new NavigationContext.Builder(this).screenSwitcher(mScreenSwitcher);

		if (fragment != null && fragment instanceof ContainerIdProvider) {
			int containerId = ((ContainerIdProvider) fragment).getContainerId();
			builder.fragmentManagerAndContainerId(fragment.getChildFragmentManager(), containerId);
		}
		mNavigationContextBinder.bind(builder.build());
	}

	private void selectTab(@IdRes int tabId) {
		mBottomBar.setOnTabSelectListener(null);    // workaround to don't call listener
		mBottomBar.selectTabWithId(tabId);
		mBottomBar.setOnTabSelectListener(this, false);
	}

	private static String tabIdToScreenName(@IdRes int tabId) {
		String name = sTabToNameMap.get(tabId);
		if (name == null) {
			throw new IllegalArgumentException("Unknown tab id " + tabId);
		}
		return name;
	}

	private static int screenNameToTabId(String screenName) {
		for (Map.Entry<Integer, String> entry : sTabToNameMap.entrySet()) {
			if (entry.getValue().equals(screenName)) {
				return entry.getKey();
			}
		}
		throw new IllegalArgumentException("Unknown screen name " + screenName);
	}
}
