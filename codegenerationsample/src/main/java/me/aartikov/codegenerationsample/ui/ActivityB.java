package me.aartikov.codegenerationsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.codegenerationsample.R;
import me.aartikov.codegenerationsample.SampleApplication;
import me.aartikov.codegenerationsample.SampleTransitionAnimationProvider;
import me.aartikov.codegenerationsample.screens.ScreenB;
import me.aartikov.codegenerationsample.screens.ScreenC;

/**
 * Date: 22.01.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
@RegisterScreen(ScreenB.class)
public class ActivityB extends AppCompatActivity {
	private Navigator mNavigator = SampleApplication.getNavigator();
	private NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);

		if (savedInstanceState == null) {
			mNavigator.reset(new ScreenC());
		}
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		NavigationContext navigationContext = new NavigationContext.Builder(this)
				.containerId(R.id.fragment_container)
				.transitionAnimationProvider(new SampleTransitionAnimationProvider())
				.build();
		mNavigationContextBinder.bind(navigationContext);
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
}