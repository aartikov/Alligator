package me.aartikov.advancedscreenswitchersample.ui;

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
import me.aartikov.advancedscreenswitchersample.screens.TabScreen;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenSwitchingListener;
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher;

/**
 * Date: 21.01.2016
 * Time: 23:30
 *
 * @author Artur Artikov
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ScreenSwitchingListener {
	@BindView(R.id.bottom_bar)
	BottomNavigationView mBottomBar;

	private Navigator mNavigator = SampleApplication.getNavigator();
	private NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();
	private FragmentScreenSwitcher mScreenSwitcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		mBottomBar.setOnNavigationItemSelectedListener(this);
		mScreenSwitcher = new FragmentScreenSwitcher(getSupportFragmentManager(), R.id.main_container, new SampleScreenSwitcherAnimationProvider());

		if (savedInstanceState == null) {
			mNavigator.switchTo(TabScreen.getById(R.id.tab_android));
		}
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
		Screen screen = TabScreen.getById(item.getItemId());
		mNavigator.switchTo(screen);
		return false;
	}

	@Override
	public void onScreenSwitched(@Nullable Screen screenFrom, Screen screenTo) {
		int tabId = ((TabScreen) screenTo).getId();
		mBottomBar.getMenu().findItem(tabId).setChecked(true);
		bindNavigationContext();    // rebind NavigationContext because we need to set another container id and another child fragment manager.
	}
}
