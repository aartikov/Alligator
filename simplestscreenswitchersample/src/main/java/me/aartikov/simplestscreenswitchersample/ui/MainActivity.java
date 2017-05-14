package me.aartikov.simplestscreenswitchersample.ui;

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
import me.aartikov.alligator.ScreenSwitchingListener;
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher;
import me.aartikov.simplestscreenswitchersample.R;
import me.aartikov.simplestscreenswitchersample.SampleApplication;
import me.aartikov.simplestscreenswitchersample.screens.TabScreen;

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
	@SuppressLint("UseSparseArrays")
	private Map<Integer, Screen> mScreenMap = new HashMap<>();

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
		mScreenSwitcher = new FragmentScreenSwitcher(getSupportFragmentManager(), R.id.main_container);

		if (savedInstanceState == null) {
			mNavigator.switchTo(getScreen(R.id.tab_android));
		}
	}

	private void initScreenMap() {
		mScreenMap.put(R.id.tab_android, new TabScreen(getString(R.string.tab_android)));
		mScreenMap.put(R.id.tab_bug, new TabScreen(getString(R.string.tab_bug)));
		mScreenMap.put(R.id.tab_dog, new TabScreen(getString(R.string.tab_dog)));
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
		Screen screen = getScreen(item.getItemId());
		mNavigator.switchTo(screen);
		return false;
	}

	@Override
	public void onScreenSwitched(@Nullable Screen screenFrom, Screen screenTo) {
		int tabId = getTabId(screenTo);
		mBottomBar.getMenu().findItem(tabId).setChecked(true);
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}

	private Screen getScreen(int tabId) {
		return mScreenMap.get(tabId);
	}

	private int getTabId(Screen screen) {
		for(Map.Entry<Integer, Screen> entry: mScreenMap.entrySet()) {
			if(screen.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return -1;
	}
}
