package com.art.simplestscreenswitchersample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.art.alligator.implementation.ScreenResolver;
import com.art.simplestscreenswitchersample.R;
import com.art.simplestscreenswitchersample.SampleApplication;
import com.art.simplestscreenswitchersample.screens.TabScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 21.01.2016
 * Time: 23:30
 *
 * @author Artur Artikov
 */
public class TabFragment extends Fragment {
	@BindView(R.id.fragment_tab_text_view_name)
	TextView mNameTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tab, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);

		ScreenResolver screenResolver = SampleApplication.getScreenResolver();
		TabScreen screen = screenResolver.getScreen(this, TabScreen.class);
		mNameTextView.setText(screen.getName());
	}
}
