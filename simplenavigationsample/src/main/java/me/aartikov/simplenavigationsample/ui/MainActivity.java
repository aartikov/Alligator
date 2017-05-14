package me.aartikov.simplenavigationsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.simplenavigationsample.R;
import me.aartikov.simplenavigationsample.SampleApplication;
import me.aartikov.simplenavigationsample.screens.FirstScreen;

/**
 * Date: 22.01.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class MainActivity extends AppCompatActivity {
	private Navigator mNavigator;
	private NavigationContextBinder mNavigationContextBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavigator = SampleApplication.getNavigator();
		mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

		if(savedInstanceState == null) {
			mNavigator.reset(new FirstScreen());
		}
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		NavigationContext navigationContext = new NavigationContext.Builder(this)
				.containerId(R.id.main_container)
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
