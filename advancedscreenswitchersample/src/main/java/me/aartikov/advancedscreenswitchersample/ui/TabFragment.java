package me.aartikov.advancedscreenswitchersample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.advancedscreenswitchersample.R;
import me.aartikov.advancedscreenswitchersample.SampleApplication;
import me.aartikov.advancedscreenswitchersample.screens.InnerScreen;
import me.aartikov.advancedscreenswitchersample.screens.TabScreen;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;


@RegisterScreen(TabScreen.class)
public class TabFragment extends Fragment implements ContainerIdProvider {

	TextView mNameTextView;

	private final Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tab, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNameTextView = view.findViewById(R.id.name_text_view);

		TabScreen screen = SampleApplication.getScreenResolver().getScreen(this);
		mNameTextView.setText(screen.getName());
	}

	@Override
	public void onResume() {
		super.onResume();
		setInitialFragmentIfRequired();
	}

	private void setInitialFragmentIfRequired() {
		boolean hasFragment = getChildFragmentManager().findFragmentById(R.id.inner_container) != null;
		if (!hasFragment && mNavigator.canExecuteCommandImmediately()) {
			mNavigator.reset(new InnerScreen(1));
		}
	}

	@Override
	public int getContainerId() {
		return R.id.inner_container;
	}
}
