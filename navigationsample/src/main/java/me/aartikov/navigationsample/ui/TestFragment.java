package me.aartikov.navigationsample.ui;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.ScreenResolver;
import me.aartikov.navigationsample.R;
import me.aartikov.navigationsample.SampleApplication;
import me.aartikov.navigationsample.screens.TestSmallScreen;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Date: 29.12.2016
 * Time: 11:33
 *
 * @author Artur Artikov
 */
public class TestFragment extends Fragment {
	@BindView(R.id.fragment_test_view_root)
	View mRootView;

	@BindView(R.id.fragment_test_text_view_counter)
	TextView mCounterTextView;

	@BindView(R.id.fragment_test_button_forward)
	Button mForwardButton;

	@BindView(R.id.fragment_test_button_replace)
	Button mReplaceButton;

	@BindView(R.id.fragment_test_button_reset)
	Button mResetButton;

	@BindView(R.id.fragment_test_button_back)
	Button mBackButton;

	@BindView(R.id.fragment_test_button_double_back)
	Button mDoubleBackButton;

	private Unbinder mButterknifeUnbinder;

	private Navigator mNavigator;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_test, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterknifeUnbinder = ButterKnife.bind(this, view);
		mNavigator = SampleApplication.getNavigator();

		mRootView.setBackgroundColor(getRandomColor());

		ScreenResolver screenResolver = SampleApplication.getScreenResolver();
		TestSmallScreen screen = screenResolver.getScreen(this, TestSmallScreen.class);
		int counter = screen.getCounter();
		mCounterTextView.setText(getString(R.string.counter_template, counter));

		mForwardButton.setOnClickListener(v -> mNavigator.goForward(new TestSmallScreen(counter + 1)));
		mReplaceButton.setOnClickListener(v -> mNavigator.replace(new TestSmallScreen(counter)));
		mResetButton.setOnClickListener(v -> mNavigator.reset(new TestSmallScreen(1)));
		mBackButton.setOnClickListener(v -> mNavigator.goBack());
		mDoubleBackButton.setOnClickListener(v -> {
			mNavigator.goBack();
			mNavigator.goBack();
		});
	}

	@Override
	public void onDestroyView() {
		mButterknifeUnbinder.unbind();
		super.onDestroyView();
	}

	private static int getRandomColor() {
		Random random = new Random();
		return Color.HSVToColor(new float[]{random.nextInt(360), 0.1f, 0.8f});
	}
}
