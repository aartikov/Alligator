package me.aartikov.simplenavigationsample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.simplenavigationsample.R;
import me.aartikov.simplenavigationsample.SampleApplication;
import me.aartikov.simplenavigationsample.screens.MessageScreen;
import me.aartikov.simplenavigationsample.screens.SecondScreen;

/**
 * Date: 15.05.2017
 * Time: 01:25
 *
 * @author Artur Artikov
 */
public class FirstFragment extends Fragment {
	@BindView(R.id.next_button)
	Button mNextButton;

	@BindView(R.id.message_button)
	Button mMessageButton;

	private Unbinder mButterknifeUnbinder;

	private Navigator mNavigator;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_first, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterknifeUnbinder = ButterKnife.bind(this, view);
		mNavigator = SampleApplication.getNavigator();

		mNextButton.setOnClickListener(v -> mNavigator.goForward(new SecondScreen()));
		mMessageButton.setOnClickListener(v -> mNavigator.goForward(new MessageScreen("Hello!")));
	}

	@Override
	public void onDestroyView() {
		mButterknifeUnbinder.unbind();
		super.onDestroyView();
	}
}
