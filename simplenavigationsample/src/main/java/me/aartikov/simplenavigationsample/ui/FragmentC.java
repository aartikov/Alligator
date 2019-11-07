package me.aartikov.simplenavigationsample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.simplenavigationsample.R;
import me.aartikov.simplenavigationsample.SampleApplication;
import me.aartikov.simplenavigationsample.screens.ScreenC;
import me.aartikov.simplenavigationsample.screens.ScreenD;


@RegisterScreen(ScreenC.class)
public class FragmentC extends Fragment {
	private Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_c, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Button goForwardToDButton = view.findViewById((R.id.go_forward_to_d_button));
		goForwardToDButton.setOnClickListener(v -> mNavigator.goForward(new ScreenD("Message for D from C")));
	}
}
