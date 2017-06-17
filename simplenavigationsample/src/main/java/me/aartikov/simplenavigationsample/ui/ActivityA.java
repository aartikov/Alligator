package me.aartikov.simplenavigationsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.simplenavigationsample.R;
import me.aartikov.simplenavigationsample.SampleApplication;
import me.aartikov.simplenavigationsample.SampleTransitionAnimationProvider;
import me.aartikov.simplenavigationsample.screens.ScreenB;

/**
 * Date: 22.01.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class ActivityA extends AppCompatActivity {
	@BindView(R.id.go_forward_to_b_button)
	Button mGoForwardToBButton;

	private Navigator mNavigator = SampleApplication.getNavigator();
	private NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		setTitle(R.string.screen_a);
		ButterKnife.bind(this);

		mGoForwardToBButton.setOnClickListener(v -> mNavigator.goForward(new ScreenB()));       // If you use MVP architectural pattern call methods of Navigator in presenters.
	}


	// Bind NavigationContext in onResumeFragments() and unbind it in onPause().
	// In a real application you can do it in a base activity class, or use plugin system like that https://github.com/passsy/CompositeAndroid

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		NavigationContext navigationContext = new NavigationContext.Builder(this)
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
