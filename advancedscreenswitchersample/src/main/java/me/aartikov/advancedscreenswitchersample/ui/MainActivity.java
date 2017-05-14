package me.aartikov.advancedscreenswitchersample.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aartikov.advancedscreenswitchersample.R;
import me.aartikov.advancedscreenswitchersample.SampleApplication;
import me.aartikov.advancedscreenswitchersample.SampleTransitionAnimationProvider;
import me.aartikov.advancedscreenswitchersample.screens.TabScreen;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenSwitchingListener;
import me.aartikov.alligator.animations.transition.SimpleTransitionAnimation;
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher;

/**
 * Date: 21.01.2016
 * Time: 23:30
 *
 * @author Artur Artikov
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ScreenSwitchingListener {
	private Navigator mNavigator;
	private NavigationContextBinder mNavigationContextBinder;
	private FragmentScreenSwitcher mScreenSwitcher;
	private Map<Integer, Screen> mScreenMap = new LinkedHashMap<>();

	@BindView(R.id.bottom_bar)
	BottomNavigationView mBottomBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNavigator = SampleApplication.getNavigator();
		mNavigationContextBinder = SampleApplication.getNavigationContextBinder();
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		mBottomBar.setOnNavigationItemSelectedListener(this);
		initScreenMap();
		mScreenSwitcher = new FragmentScreenSwitcher(getSupportFragmentManager(), R.id.main_container, createSwitcherAnimationProvider());

		if (savedInstanceState == null) {
			mNavigator.switchTo(getScreen(R.id.tab_android));
		}
	}


	private void initScreenMap() {
		mScreenMap.put(R.id.tab_android, new TabScreen(getString(R.string.tab_android)));
		mScreenMap.put(R.id.tab_bug, new TabScreen(getString(R.string.tab_bug)));
		mScreenMap.put(R.id.tab_dog, new TabScreen(getString(R.string.tab_dog)));
	}

	private FragmentScreenSwitcher.AnimationProvider createSwitcherAnimationProvider() {
		return (screenFrom, screenTo, animationData) -> {
			int indexFrom = getTabIndex(screenFrom);
			int indexTo = getTabIndex(screenTo);
			if (indexTo > indexFrom) {
				return new SimpleTransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left);
			} else {
				return new SimpleTransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right);
			}
		};
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
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
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		Screen screen = getScreen(item.getItemId());
		mNavigator.switchTo(screen);
		return false;
	}

	@Override
	public void onScreenSwitched(@Nullable Screen screenFrom, Screen screenTo) {
		int tabId = getTabId(screenTo);
		mBottomBar.getMenu().findItem(tabId).setChecked(true);
		bindNavigationContext();
	}

	private void bindNavigationContext() {
		Fragment fragment = mScreenSwitcher.getCurrentFragment();
		NavigationContext.Builder builder = new NavigationContext.Builder(this)
				.screenSwitcher(mScreenSwitcher)
				.screenSwitchingListener(this)
				.transitionAnimationProvider(new SampleTransitionAnimationProvider());

		if (fragment != null && fragment instanceof ContainerIdProvider) {
			builder.containerId(((ContainerIdProvider) fragment).getContainerId())
					.fragmentManager(fragment.getChildFragmentManager());
		}

		mNavigationContextBinder.bind(builder.build());
	}

	private Screen getScreen(int tabId) {
		return mScreenMap.get(tabId);
	}

	private int getTabId(Screen screen) {
		for (Map.Entry<Integer, Screen> entry : mScreenMap.entrySet()) {
			if (screen.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return -1;
	}

	private int getTabIndex(Screen screen) {
		int index = 0;
		for (Map.Entry<Integer, Screen> entry : mScreenMap.entrySet()) {
			if (screen.equals(entry.getValue())) {
				return index;
			}
			index++;
		}
		return -1;
	}

}
