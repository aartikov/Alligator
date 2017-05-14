package me.aartikov.navigationmethodssample.ui;

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
import me.aartikov.navigationmethodssample.R;
import me.aartikov.navigationmethodssample.SampleTransitionAnimationProvider;
import me.aartikov.navigationmethodssample.SampleApplication;
import me.aartikov.navigationmethodssample.screens.TestScreen;
import me.aartikov.navigationmethodssample.screens.TestSmallScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 29.12.2016
 * Time: 11:33
 *
 * @author Artur Artikov
 */
public class TestActivity extends AppCompatActivity {
	@BindView(R.id.root_view)
	View mRootView;

	@BindView(R.id.counter_text_view)
	TextView mCounterTextView;

	@BindView(R.id.forward_button)
	Button mForwardButton;

	@BindView(R.id.replace_button)
	Button mReplaceButton;

	@BindView(R.id.reset_button)
	Button mResetButton;

	@BindView(R.id.finish_button)
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

	private static int getRandomColor() {
		Random random = new Random();
		return Color.HSVToColor(new float[]{random.nextInt(360), 0.1f, 1.0f});
	}
}
