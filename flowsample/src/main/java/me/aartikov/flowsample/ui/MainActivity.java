package me.aartikov.flowsample.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.flowsample.R;
import me.aartikov.flowsample.SampleApplication;
import me.aartikov.flowsample.SampleTransitionAnimationProvider;
import me.aartikov.flowsample.screens.TestFlowScreen;


public class MainActivity extends AppCompatActivity {
	private Navigator mNavigator = SampleApplication.getNavigator();
	private NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

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
			builder.fragmentNavigation(currentFlowFragment.getChildFragmentManager(), ((ContainerIdProvider) currentFlowFragment).getContainerId());
		}

		builder.flowFragmentNavigation(getSupportFragmentManager(), R.id.flow_fragment_container)
				.transitionAnimationProvider(new SampleTransitionAnimationProvider())
				.transitionListener(((transitionType, destinationType, screenClassFrom, screenClassTo) -> {
					if (destinationType == DestinationType.FLOW_FRAGMENT) {
						bindNavigationContext();    // rebind NavigationContext because a current flow fragment has been changed.
					}
				}));

		mNavigationContextBinder.bind(builder.build());
	}

	private void setInitialFragmentIfRequired() {
		if (getCurrentFlowFragment() == null && mNavigator.canExecuteCommandImmediately()) {
			mNavigator.reset(new TestFlowScreen(1));
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

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}

}
