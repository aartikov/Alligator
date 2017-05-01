package me.aartikov.advancedscreenswitchersample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.ScreenResolver;
import me.aartikov.advancedscreenswitchersample.R;
import me.aartikov.advancedscreenswitchersample.SampleApplication;
import me.aartikov.advancedscreenswitchersample.screens.InnerScreen;
import me.aartikov.advancedscreenswitchersample.screens.TabScreen;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Date: 21.01.2016
 * Time: 23:30
 *
 * @author Artur Artikov
 */
public class TabFragment extends Fragment implements ContainerIdProvider {
	@BindView(R.id.name_text_view)
	TextView mNameTextView;

	private Unbinder mButterknifeUnbinder;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tab, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterknifeUnbinder = ButterKnife.bind(this, view);

		ScreenResolver screenResolver = SampleApplication.getScreenResolver();
		TabScreen screen = screenResolver.getScreen(this, TabScreen.class);
		mNameTextView.setText(screen.getName());

		if (getChildFragmentManager().findFragmentById(R.id.inner_container) == null) {
			Navigator navigator = SampleApplication.getNavigator();
			navigator.reset(new InnerScreen(1));
		}
	}

	@Override
	public void onDestroyView() {
		mButterknifeUnbinder.unbind();
		super.onDestroyView();
	}

	@Override
	public int getContainerId() {
		return R.id.inner_container;
	}
}
