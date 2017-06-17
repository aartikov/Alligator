package me.aartikov.advancedscreenswitchersample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.advancedscreenswitchersample.R;
import me.aartikov.advancedscreenswitchersample.SampleApplication;
import me.aartikov.advancedscreenswitchersample.screens.InnerScreen;
import me.aartikov.advancedscreenswitchersample.screens.TabScreen;
import me.aartikov.alligator.Navigator;

/**
 * Date: 21.01.2016
 * Time: 23:30
 *
 * @author Artur Artikov
 */
public class TabFragment extends Fragment implements ContainerIdProvider {
	@BindView(R.id.name_text_view)
	TextView mNameTextView;

	private Unbinder mButterKnifeUnbinder;

	private Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tab, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterKnifeUnbinder = ButterKnife.bind(this, view);

		TabScreen screen = SampleApplication.getScreenResolver().getScreen(this);
		mNameTextView.setText(screen.getName());

		if (getChildFragmentManager().findFragmentById(R.id.inner_container) == null) {
			mNavigator.reset(new InnerScreen(1));
		}
	}

	@Override
	public void onDestroyView() {
		mButterKnifeUnbinder.unbind();
		super.onDestroyView();
	}

	@Override
	public int getContainerId() {
		return R.id.inner_container;
	}
}
