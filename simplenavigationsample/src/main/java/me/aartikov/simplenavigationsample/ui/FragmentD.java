package me.aartikov.simplenavigationsample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.ScreenResolver;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.simplenavigationsample.R;
import me.aartikov.simplenavigationsample.SampleApplication;
import me.aartikov.simplenavigationsample.screens.ScreenA;
import me.aartikov.simplenavigationsample.screens.ScreenD;


@RegisterScreen(ScreenD.class)
public class FragmentD extends Fragment {
	private Navigator mNavigator = SampleApplication.getNavigator();
	private ScreenResolver mScreenResolver = SampleApplication.getScreenResolver();

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_d, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		TextView messageTextView = view.findViewById(R.id.message_text_view);
		Button goBackToAButton = view.findViewById(R.id.go_back_to_a_button);
		ScreenD screen = mScreenResolver.getScreen(this);     // use ScreenResolver to get a screen with its arguments
		messageTextView.setText(screen.getMessage());
		goBackToAButton.setOnClickListener(v -> mNavigator.goBackTo(ScreenA.class));
	}
}
