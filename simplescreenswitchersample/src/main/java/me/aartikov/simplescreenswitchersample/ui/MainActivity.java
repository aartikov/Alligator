package me.aartikov.simplescreenswitchersample.ui;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
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
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.alligator.listeners.ScreenSwitchingListener;
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher;
import me.aartikov.simplescreenswitchersample.R;
import me.aartikov.simplescreenswitchersample.SampleApplication;
import me.aartikov.simplescreenswitchersample.screens.MainScreen;
import me.aartikov.simplescreenswitchersample.screens.TabScreen;

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
	private Map<Integer, Screen> mTabScreenMap = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		initTabScreenMap();
		mBottomBar.setOnNavigationItemSelectedListener(this);
		mScreenSwitcher = new FragmentScreenSwitcher(SampleApplication.getNavigationFactory(), getSupportFragmentManager(), R.id.main_container);

		if (savedInstanceState == null) {
			mNavigator.switchTo(getTabScreen(R.id.tab_android));
		}
	}

	private void initTabScreenMap() {
		mTabScreenMap.put(R.id.tab_android, new TabScreen.Android());
		mTabScreenMap.put(R.id.tab_bug, new TabScreen.Bug());
		mTabScreenMap.put(R.id.tab_dog, new TabScreen.Dog());
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
		Screen screen = getTabScreen(item.getItemId());
		mNavigator.switchTo(screen);
		return true;
	}

	@Override
	public void onScreenSwitched(@Nullable Screen screenFrom, @NonNull Screen screenTo) {
		int tabId = getTabId(screenTo);
		mBottomBar.getMenu().findItem(tabId).setChecked(true);
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
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
}
