package me.aartikov.flowsample.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.flowsample.R;
import me.aartikov.flowsample.SampleApplication;
import me.aartikov.flowsample.screens.TestFlowScreen;
import me.aartikov.flowsample.screens.TestSmallScreen;

import java.util.Random;


@RegisterScreen(TestFlowScreen.class)
public class TestFlowFragment extends Fragment implements ContainerIdProvider {
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

	@BindView(R.id.finish_all_button)
	Button mFinishAllButton;

	private Unbinder mButterKnifeUnbinder;

	private Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_flow, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterKnifeUnbinder = ButterKnife.bind(this, view);

		TestFlowScreen screen = SampleApplication.getScreenResolver().getScreen(this);
		int counter = screen.getCounter();
		mCounterTextView.setText(getString(R.string.counter_template, counter));

		mForwardButton.setOnClickListener(v -> mNavigator.goForward(new TestFlowScreen(counter + 1)));
		mReplaceButton.setOnClickListener(v -> mNavigator.replace(new TestFlowScreen(counter)));
		mResetButton.setOnClickListener(v -> mNavigator.reset(new TestFlowScreen(1)));
		mFinishButton.setOnClickListener(v -> mNavigator.finish());
		mFinishAllButton.setOnClickListener(v -> mNavigator.finishTopLevel());

		mRootView.setBackgroundColor(getRandomColor());
	}

	@Override
	public void onResume() {
		super.onResume();
		setInitialFragmentIfRequired();
	}

	private void setInitialFragmentIfRequired() {
		boolean hasFragment = getChildFragmentManager().findFragmentById(R.id.flow_fragment_container) != null;
		if (!hasFragment && mNavigator.canExecuteCommandImmediately()) {
			mNavigator.reset(new TestSmallScreen(1));
		}
	}

	@Override
	public int getContainerId() {
		return R.id.fragment_container;
	}

	@Override
	public void onDestroyView() {
		mButterKnifeUnbinder.unbind();
		super.onDestroyView();
	}

	private static int getRandomColor() {
		Random random = new Random();
		return Color.HSVToColor(new float[]{random.nextInt(360), 0.1f, 1.0f});
	}
}
