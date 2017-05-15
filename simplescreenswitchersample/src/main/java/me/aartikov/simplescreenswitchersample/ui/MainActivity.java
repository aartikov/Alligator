package me.aartikov.simplescreenswitchersample.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenSwitchingListener;
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher;
import me.aartikov.simplescreenswitchersample.R;
import me.aartikov.simplescreenswitchersample.SampleApplication;
import me.aartikov.simplescreenswitchersample.screens.TabScreen;

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
		mScreenSwitcher = new FragmentScreenSwitcher(getSupportFragmentManager(), R.id.main_container);

		if (savedInstanceState == null) {
			mNavigator.switchTo(TabScreen.getById(R.id.tab_android));
		}
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		NavigationContext navigationContext = new NavigationContext.Builder(this)
				.screenSwitcher(mScreenSwitcher)
				.screenSwitchingListener(this)
				.build();
		mNavigationContextBinder.bind(navigationContext);
	}

	@Override
	protected void onPause() {
		mNavigationContextBinder.unbind();
		super.onPause();
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		Screen screen = TabScreen.getById(item.getItemId());
		mNavigator.switchTo(screen);
		return true;
	}

	@Override
	public void onScreenSwitched(@Nullable Screen screenFrom, Screen screenTo) {
		int tabId = ((TabScreen) screenTo).getId();
		mBottomBar.getMenu().findItem(tabId).setChecked(true);
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}
}
