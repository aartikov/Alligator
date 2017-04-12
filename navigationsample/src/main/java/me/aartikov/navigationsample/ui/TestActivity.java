package me.aartikov.navigationsample.ui;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.ScreenResolver;
import me.aartikov.navigationsample.R;
import me.aartikov.navigationsample.SampleTransitionAnimationProvider;
import me.aartikov.navigationsample.SampleApplication;
import me.aartikov.navigationsample.screens.TestScreen;
import me.aartikov.navigationsample.screens.TestSmallScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 29.12.2016
 * Time: 11:33
 *
 * @author Artur Artikov
 */
public class TestActivity extends AppCompatActivity {
	@BindView(R.id.activity_test_view_root)
	View mRootView;

	@BindView(R.id.activity_test_text_view_counter)
	TextView mCounterTextView;

	@BindView(R.id.activity_test_button_forward)
	Button mForwardButton;

	@BindView(R.id.activity_test_button_replace)
	Button mReplaceButton;

	@BindView(R.id.activity_test_button_reset)
	Button mResetButton;

	@BindView(R.id.activity_test_button_finish)
	Button mFinishButton;

	private Navigator mNavigator;
	private NavigationContextBinder mNavigationContextBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		ButterKnife.bind(this);
		mNavigator = SampleApplication.getNavigator();
		mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

		if (savedInstanceState == null) {
			mNavigator.goForward(new TestSmallScreen(1));
		}

		mRootView.setBackgroundColor(getRandomColor());

		ScreenResolver screenResolver = SampleApplication.getScreenResolver();
		TestScreen screen = screenResolver.getScreen(this, TestScreen.class);
		int counter = screen != null ? screen.getCounter() : 1;
		mCounterTextView.setText(getString(R.string.counter_template, counter));

		mForwardButton.setOnClickListener(view -> mNavigator.goForward(new TestScreen(counter + 1)));
		mReplaceButton.setOnClickListener(view -> mNavigator.replace(new TestScreen(counter)));
		mResetButton.setOnClickListener(view -> mNavigator.reset(new TestScreen(1)));
		mFinishButton.setOnClickListener(view -> mNavigator.finish());
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		NavigationContext navigationContext = new NavigationContext.Builder(this)
				.containerId(R.id.activity_test_fragment_container)
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

	private static int getRandomColor() {
		Random random = new Random();
		return Color.HSVToColor(new float[]{random.nextInt(360), 0.1f, 1.0f});
	}
}
