package me.aartikov.simplescreenswitchersample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.simplescreenswitchersample.R;
import me.aartikov.simplescreenswitchersample.screens.TabScreen;

/**
 * Date: 21.01.2016
 * Time: 23:30
 *
 * @author Artur Artikov
 */
@RegisterScreen(TabScreen.Bug.class)
public class BugFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bug, container, false);
	}
}
