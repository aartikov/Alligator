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
import me.aartikov.flowscreenswitchersample.screens.TestFlow2Screen;

@RegisterScreen(TestFlow2Screen.class)
public class TestFlow2Fragment extends Fragment implements ContainerIdProvider, ScreenSwitcherProvider {
    @BindView(R.id.root_view2)
    View mRootView;

    @BindView(R.id.bottomBarSecond)
    BottomNavigationView bottomBarSecond;

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
                R.id.fragment_container2);

        if (savedInstanceState == null) {
            mNavigator.switchTo(getTabScreen(R.id.tab1_3d));
        }
    }

    private void initTabScreenMap() {
        mTabScreenMap.put(R.id.tab1_3d, new TabScreen(getString(R.string.tab_3d)));
        mTabScreenMap.put(R.id.tab2_4k, new TabScreen(getString(R.string.tab_4k)));
        mTabScreenMap.put(R.id.tab3_360, new TabScreen(getString(R.string.tab_360)));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flow2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButterKnifeUnbinder = ButterKnife.bind(this, view);



        bottomBarSecond.setOnNavigationItemSelectedListener(menuItem -> {
            Screen screen = getTabScreen(menuItem.getItemId());
            mNavigator.switchTo(screen);
            return true;
        });

        mRootView.setBackgroundColor(getRandomColor());
    }

    @Override
    public int getContainerId() {
        return R.id.fragment_container2;
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
