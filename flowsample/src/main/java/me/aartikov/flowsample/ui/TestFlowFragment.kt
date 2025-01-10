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

import java.util.Random;

import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.flowsample.R;
import me.aartikov.flowsample.SampleApplication;
import me.aartikov.flowsample.screens.TestFlowScreen;
import me.aartikov.flowsample.screens.TestSmallScreen;

@RegisterScreen(TestFlowScreen.class)
public class TestFlowFragment extends Fragment implements ContainerIdProvider {
	View mRootView;
	TextView mCounterTextView;
	Button mForwardButton;
	Button mReplaceButton;
	Button mResetButton;
	Button mFinishButton;
	Button mFinishAllButton;

	private final Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_flow, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRootView = view.findViewById(R.id.root_view);
        mCounterTextView = view.findViewById(R.id.counter_text_view);
        mForwardButton = view.findViewById(R.id.forward_button);
        mReplaceButton = view.findViewById(R.id.replace_button);
        mResetButton = view.findViewById(R.id.reset_button);
        mFinishButton = view.findViewById(R.id.finish_button);
        mFinishAllButton = view.findViewById(R.id.finish_all_button);

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
		if (getCurrentFragment() == null && mNavigator.canExecuteCommandImmediately()) {
			mNavigator.reset(new TestSmallScreen(1));
		}
	}

	@Nullable
	private Fragment getCurrentFragment() {
		return getChildFragmentManager().findFragmentById(R.id.fragment_container);
	}

	@Override
	public int getContainerId() {
		return R.id.fragment_container;
	}

	private static int getRandomColor() {
		Random random = new Random();
		return Color.HSVToColor(new float[]{random.nextInt(360), 0.1f, 1.0f});
	}
}
