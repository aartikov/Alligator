package me.aartikov.flowscreenswitchersample.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.alligator.screenswitchers.FragmentScreenSwitcher;
import me.aartikov.alligator.screenswitchers.ScreenSwitcher;
import me.aartikov.flowscreenswitchersample.R;
import me.aartikov.flowscreenswitchersample.SampleApplication;
import me.aartikov.flowscreenswitchersample.screens.TabScreen;
import me.aartikov.flowscreenswitchersample.screens.TestFlowScreen;

@RegisterScreen(TestFlowScreen.class)
public class TestFlowFragment extends Fragment implements ContainerIdProvider, ScreenSwitcherProvider {
    @BindView(R.id.root_view)
    View mRootView;

    @BindView(R.id.bottomBarFirst)
    BottomNavigationView bottomBarFirst;

    private Unbinder mButterKnifeUnbinder;

    private Navigator mNavigator = SampleApplication.getNavigator();
    private FragmentScreenSwitcher mScreenSwitcher;

    private Map<Integer, Screen> mTabScreenMap = new LinkedHashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabScreenMap();
        mScreenSwitcher = new FragmentScreenSwitcher(
                SampleApplication.getNavigationFactory(),
                getChildFragmentManager(),
                R.id.fragment_container);

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButterKnifeUnbinder = ButterKnife.bind(this, view);

        bottomBarFirst.setOnNavigationItemSelectedListener(menuItem -> {
            Screen screen = getTabScreen(menuItem.getItemId());
            mNavigator.switchTo(screen);
            return true;
        });

        mRootView.setBackgroundColor(getRandomColor());
    }

    @Override
    public int getContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public ScreenSwitcher getScreenSwitcher() {
        return mScreenSwitcher;
    }

    private Screen getTabScreen(int tabId) {
        return mTabScreenMap.get(tabId);
    }

    @Override
    public void onDestroyView() {
        mButterKnifeUnbinder.unbind();
        super.onDestroyView();
    }

    private static int getRandomColor() {
        Random random = new Random();
        return Color.HSVToColor(new float[]{random.nextInt(360), 0.1f, 1.0f});
    }
}
