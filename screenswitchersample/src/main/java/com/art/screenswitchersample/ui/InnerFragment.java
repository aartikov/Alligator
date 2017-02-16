package com.art.screenswitchersample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.art.alligator.Navigator;
import com.art.alligator.implementation.ScreenUtils;
import com.art.screenswitchersample.R;
import com.art.screenswitchersample.SampleApplication;
import com.art.screenswitchersample.screens.InnerScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 22.01.2016
 * Time: 0:38
 *
 * @author Artur Artikov
 */
public class InnerFragment extends Fragment {
	@BindView(R.id.fragment_inner_text_view_counter)
	TextView mCounterTextView;

	@BindView(R.id.fragment_inner_button_forward)
	Button mForwardButton;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_inner, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);

		InnerScreen screen = ScreenUtils.getScreen(this);
		int counter = screen.getCounter();
		mCounterTextView.setText(getString(R.string.counter_template, counter));

		Navigator navigator = SampleApplication.getNavigator();
		mForwardButton.setOnClickListener(v -> navigator.goForward(new InnerScreen(counter + 1)));
	}
}