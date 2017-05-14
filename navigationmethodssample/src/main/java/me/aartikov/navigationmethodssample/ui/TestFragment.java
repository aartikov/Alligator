package me.aartikov.navigationmethodssample.ui;

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
import me.aartikov.navigationmethodssample.R;
import me.aartikov.navigationmethodssample.SampleApplication;
import me.aartikov.navigationmethodssample.screens.TestSmallScreen;

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

	@BindView(R.id.back_button)
	Button mBackButton;

	@BindView(R.id.double_back_button)
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
