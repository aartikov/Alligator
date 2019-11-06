package me.aartikov.simplescreenswitchersample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.simplescreenswitchersample.R;
import me.aartikov.simplescreenswitchersample.screens.TabScreen;


@RegisterScreen(TabScreen.Android.class)
public class AndroidFragment extends Fragment {
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_android, container, false);
	}
}
