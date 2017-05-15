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
	private Navigator mNavigator;
	private NavigationContextBinder mNavigationContextBinder;
	private FragmentScreenSwitcher mScreenSwitcher;

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
}
