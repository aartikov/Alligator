package com.art.advancedscreenswitchersample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.art.alligator.Navigator;
import com.art.alligator.implementation.ScreenUtils;
import com.art.advancedscreenswitchersample.R;
import com.art.advancedscreenswitchersample.SampleApplication;
import com.art.advancedscreenswitchersample.screens.InnerScreen;
import com.art.advancedscreenswitchersample.screens.TabScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 21.01.2016
 * Time: 23:30
 *
 * @author Artur Artikov
 */
public class TabFragment extends Fragment implements ContainerIdProvider {
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

		TabScreen screen = ScreenUtils.getScreen(this);
		mNameTextView.setText(screen.getName());

		if (getChildFragmentManager().findFragmentById(R.id.fragment_tab_container) == null) {
			Navigator navigator = SampleApplication.getNavigator();
			navigator.reset(new InnerScreen(1));
		}
	}

	@Override
	public int getContainerId() {
		return R.id.fragment_tab_container;
	}
}
