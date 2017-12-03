package me.aartikov.codegenerationsample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.ScreenResolver;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.codegenerationsample.R;
import me.aartikov.codegenerationsample.SampleApplication;
import me.aartikov.codegenerationsample.screens.ScreenA;
import me.aartikov.codegenerationsample.screens.ScreenD;

/**
 * Date: 15.05.2017
 * Time: 01:25
 *
 * @author Artur Artikov
 */
@RegisterScreen(ScreenD.class)
public class FragmentD extends Fragment {
	private Navigator mNavigator = SampleApplication.getNavigator();
	private ScreenResolver mScreenResolver = SampleApplication.getScreenResolver();

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_d, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		TextView messageTextView = (TextView) view.findViewById(R.id.message_text_view);
		Button goBackToAButton = (Button) view.findViewById(R.id.go_back_to_a_button);
		ScreenD screen = mScreenResolver.getScreen(this);
		messageTextView.setText(screen.getMessage());
		goBackToAButton.setOnClickListener(v -> mNavigator.goBackTo(ScreenA.class));
	}
}
