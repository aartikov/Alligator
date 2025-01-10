package me.aartikov.simplescreenswitchersample.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

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

@RegisterScreen(MainScreen.class)
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ScreenSwitchingListener {

    BottomNavigationView mBottomBar;

    private final Navigator mNavigator = SampleApplication.getNavigator();
    private final NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();
    private FragmentScreenSwitcher mScreenSwitcher;

    @SuppressLint("UseSparseArrays")
    private Map<Integer, Screen> mTabScreenMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomBar = findViewById(R.id.bottom_bar);

        initTabScreenMap();
        mBottomBar.setOnItemSelectedListener(this);
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
        NavigationContext navigationContext = new NavigationContext.Builder(this, SampleApplication.getNavigationFactory())
                .screenSwitcher(mScreenSwitcher)
                .screenSwitchingListener(this)
                .build();
        mNavigationContextBinder.bind(navigationContext);
    }

    @Override
    protected void onPause() {
        mNavigationContextBinder.unbind(this);
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

    @SuppressLint("MissingSuperCall")
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
