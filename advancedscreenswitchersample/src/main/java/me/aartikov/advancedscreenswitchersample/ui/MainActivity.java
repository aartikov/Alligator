package me.aartikov.advancedscreenswitchersample.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
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
import me.aartikov.advancedscreenswitchersample.SampleScreenSwitcherAnimationProvider;
import me.aartikov.advancedscreenswitchersample.SampleTransitionAnimationProvider;
import me.aartikov.advancedscreenswitchersample.screens.MainScreen;
import me.aartikov.advancedscreenswitchersample.screens.TabScreen;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.alligator.listeners.ScreenSwitchingListener;
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher;

/**
 * Date: 21.01.2016
 * Time: 23:30
 *
 * @author Artur Artikov
 */
@RegisterScreen(MainScreen.class)
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ScreenSwitchingListener {
	@BindView(R.id.bottom_bar)
	BottomNavigationView mBottomBar;

	private Navigator mNavigator = SampleApplication.getNavigator();
	private NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();
	private FragmentScreenSwitcher mScreenSwitcher;

	@SuppressLint("UseSparseArrays")
	private Map<Integer, Screen> mTabScreenMap = new LinkedHashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		initTabScreenMap();
		mBottomBar.setOnNavigationItemSelectedListener(this);
		mScreenSwitcher = new FragmentScreenSwitcher(SampleApplication.getNavigationFactory(), getSupportFragmentManager(),
				R.id.main_container, new SampleScreenSwitcherAnimationProvider(getTabScreens()));

		if (savedInstanceState == null) {
			mNavigator.switchTo(getTabScreen(R.id.tab_android));
		}
	}

	private void initTabScreenMap() {
		mTabScreenMap.put(R.id.tab_android, new TabScreen(getString(R.string.tab_android)));
		mTabScreenMap.put(R.id.tab_bug, new TabScreen(getString(R.string.tab_bug)));
		mTabScreenMap.put(R.id.tab_dog, new TabScreen(getString(R.string.tab_dog)));
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

	private void bindNavigationContext() {
		NavigationContext.Builder builder = new NavigationContext.Builder(this)
				.screenSwitcher(mScreenSwitcher)
				.screenSwitchingListener(this)
				.transitionAnimationProvider(new SampleTransitionAnimationProvider());

		Fragment fragment = mScreenSwitcher.getCurrentFragment();
		if (fragment != null && fragment instanceof ContainerIdProvider) {
			builder.containerId(((ContainerIdProvider) fragment).getContainerId())
					.fragmentManager(fragment.getChildFragmentManager());       // Use child fragment manager for nested navigation
		}

		mNavigationContextBinder.bind(builder.build());
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		Screen screen = getTabScreen(item.getItemId());
		mNavigator.switchTo(screen);
		return false;
	}

	@Override
	public void onScreenSwitched(@Nullable Screen screenFrom, @NonNull Screen screenTo) {
		int tabId = getTabId(screenTo);
		mBottomBar.getMenu().findItem(tabId).setChecked(true);
		bindNavigationContext();    // rebind NavigationContext because we need to set another container id and another child fragment manager.
	}

	private Screen getTabScreen(int tabId) {
		return mTabScreenMap.get(tabId);
	}

	private int getTabId(Screen tabScreen) {
		for (Map.Entry<Integer, Screen> entry : mTabScreenMap.entrySet()) {
			if (tabScreen.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return -1;
	}

	private List<Screen> getTabScreens() {
		return new ArrayList<>(mTabScreenMap.values());
	}
}
