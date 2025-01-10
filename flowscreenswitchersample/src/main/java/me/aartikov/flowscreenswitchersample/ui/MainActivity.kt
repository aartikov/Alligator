package me.aartikov.flowscreenswitchersample.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.flowscreenswitchersample.R;
import me.aartikov.flowscreenswitchersample.SampleApplication;
import me.aartikov.flowscreenswitchersample.screens.TestFlowScreen;

public class MainActivity extends AppCompatActivity {
    private final Navigator mNavigator = SampleApplication.getNavigator();
    private final NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        bindNavigationContext();
        setInitialFragmentIfRequired();
    }

    private void bindNavigationContext() {
        NavigationContext.Builder builder = new NavigationContext.Builder(this, SampleApplication.getNavigationFactory());

        Fragment currentFlowFragment = getCurrentFlowFragment();

        if (currentFlowFragment instanceof ContainerIdProvider) {
            int containerId = ((ContainerIdProvider) currentFlowFragment).getContainerId();
            FragmentManager childFragmentManager = currentFlowFragment.getChildFragmentManager();

            Fragment childFragment = childFragmentManager.findFragmentById(containerId);

            if (childFragment instanceof ContainerIdProvider) {
                childFragmentManager = childFragment.getChildFragmentManager();
                containerId = ((ContainerIdProvider) childFragment).getContainerId();
            }

            builder.fragmentNavigation(childFragmentManager, containerId);
        }

        if (currentFlowFragment instanceof ScreenSwitcherProvider) {
            builder.screenSwitcher(((ScreenSwitcherProvider) currentFlowFragment).getScreenSwitcher());
        }

        builder.flowFragmentNavigation(getSupportFragmentManager(), R.id.flow_fragment_container)
                .transitionListener(((transitionType, destinationType, screenClassFrom, screenClassTo) -> {
                    if (destinationType == DestinationType.FLOW_FRAGMENT) {
                        bindNavigationContext();    // rebind NavigationContext because a current flow fragment has been changed.
                    } else {
                        if (currentFlowFragment instanceof ContainerIdProvider) {
                            int containerId = ((ContainerIdProvider) currentFlowFragment).getContainerId();
                            FragmentManager childFragmentManager = currentFlowFragment.getChildFragmentManager();

                            Fragment childFragment = childFragmentManager.findFragmentById(containerId);

                            if (destinationType == DestinationType.FRAGMENT && childFragment instanceof ContainerIdProvider) {
                                bindNavigationContext();
                            }
                        }
                    }
                }))
                .screenSwitchingListener((screenFrom, screenTo) -> bindNavigationContext());

        mNavigationContextBinder.bind(builder.build());
    }

    private void setInitialFragmentIfRequired() {
        if (getCurrentFlowFragment() == null && mNavigator.canExecuteCommandImmediately()) {
            mNavigator.reset(new TestFlowScreen());
        }
    }

    @Nullable
    private Fragment getCurrentFlowFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.flow_fragment_container);
    }

    @Override
    protected void onPause() {
        mNavigationContextBinder.unbind(this);
        super.onPause();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        mNavigator.goBack();
    }
}
