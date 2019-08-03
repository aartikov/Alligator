package me.aartikov.simplenavigationsample.ui;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.simplenavigationsample.R;
import me.aartikov.simplenavigationsample.SampleApplication;
import me.aartikov.simplenavigationsample.SampleTransitionAnimationProvider;
import me.aartikov.simplenavigationsample.screens.ScreenA;
import me.aartikov.simplenavigationsample.screens.ScreenB;


@RegisterScreen(ScreenA.class)
public class ActivityA extends AppCompatActivity {
	private Navigator mNavigator = SampleApplication.getNavigator();
	private NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		setTitle(R.string.screen_a);

		Button goForwardToBButton = findViewById(R.id.go_forward_to_b_button);
		goForwardToBButton.setOnClickListener(v -> mNavigator.goForward(new ScreenB()));  // If you use MVP architectural pattern call methods of Navigator in presenters.
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
		mNavigationContextBinder.unbind(this);
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}
}
