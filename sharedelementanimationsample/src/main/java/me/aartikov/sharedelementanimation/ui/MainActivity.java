package me.aartikov.sharedelementanimation.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.sharedelementanimation.R;
import me.aartikov.sharedelementanimation.SampleApplication;
import me.aartikov.sharedelementanimation.SampleTransitionAnimationProvider;
import me.aartikov.sharedelementanimation.screens.FirstScreen;

public class MainActivity extends AppCompatActivity {
    private final Navigator mNavigator = SampleApplication.getNavigator();
    private final NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mNavigator.reset(new FirstScreen());
        }
    }

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		NavigationContext navigationContext = new NavigationContext.Builder(this, SampleApplication.getNavigationFactory())
				.fragmentNavigation(getSupportFragmentManager(), R.id.fragment_container)
				.transitionAnimationProvider(new SampleTransitionAnimationProvider(this))
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
