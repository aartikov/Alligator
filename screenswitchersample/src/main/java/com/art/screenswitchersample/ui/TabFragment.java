package com.art.screenswitchersample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.art.alligator.Navigator;
import com.art.alligator.implementation.ScreenUtils;
import com.art.screenswitchersample.R;
import com.art.screenswitchersample.SampleApplication;
import com.art.screenswitchersample.screens.InnerScreen;
import com.art.screenswitchersample.screens.TabScreen;

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

		if (getChildFragmentManager().getBackStackEntryCount() == 0) {
			Navigator navigator = SampleApplication.getNavigator();
			navigator.resetTo(new InnerScreen(1));
		}
	}

	@Override
	public int getContainerId() {
		return R.id.fragment_tab_container;
	}
}
