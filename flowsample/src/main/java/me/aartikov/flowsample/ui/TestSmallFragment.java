package me.aartikov.flowsample.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.flowsample.R;
import me.aartikov.flowsample.SampleApplication;
import me.aartikov.flowsample.screens.TestSmallScreen;


@RegisterScreen(TestSmallScreen.class)
public class TestSmallFragment extends Fragment {
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

	private Unbinder mButterKnifeUnbinder;

	private Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_small, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterKnifeUnbinder = ButterKnife.bind(this, view);

		TestSmallScreen screen = SampleApplication.getScreenResolver().getScreen(this);
		int counter = screen.getCounter();
		mCounterTextView.setText(getString(R.string.counter_template, counter));

		mForwardButton.setOnClickListener(v -> mNavigator.goForward(new TestSmallScreen(counter + 1)));
		mReplaceButton.setOnClickListener(v -> mNavigator.replace(new TestSmallScreen(counter)));
		mResetButton.setOnClickListener(v -> mNavigator.reset(new TestSmallScreen(1)));
		mBackButton.setOnClickListener(v -> mNavigator.goBack());
		mDoubleBackButton.setOnClickListener(v -> {
			mNavigator.goBack();        // Due to a command queue in AndroidNavigator you can combine navigation methods arbitrarily.
			mNavigator.goBack();
		});

		mRootView.setBackgroundColor(getRandomColor());
	}

	// Workaround for issue https://code.google.com/p/android/issues/detail?id=55228
	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		if (getParentFragment() != null && (getParentFragment().isDetached() || getParentFragment().isRemoving())) {
			return AnimationUtils.loadAnimation(getContext(), R.anim.stay);
		}
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public void onDestroyView() {
		mButterKnifeUnbinder.unbind();
		super.onDestroyView();
	}

	private static int getRandomColor() {
		Random random = new Random();
		return Color.HSVToColor(new float[]{random.nextInt(360), 0.1f, 0.8f});
	}
}
