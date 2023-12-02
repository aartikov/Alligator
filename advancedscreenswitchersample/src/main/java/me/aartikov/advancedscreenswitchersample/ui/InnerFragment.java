package me.aartikov.advancedscreenswitchersample.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.advancedscreenswitchersample.R;
import me.aartikov.advancedscreenswitchersample.SampleApplication;
import me.aartikov.advancedscreenswitchersample.screens.InnerScreen;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;


@RegisterScreen(InnerScreen.class)
public class InnerFragment extends Fragment {

	TextView mCounterTextView;
	Button mForwardButton;

	private final Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_inner, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCounterTextView = view.findViewById(R.id.counter_text_view);
        mForwardButton = view.findViewById(R.id.forward_button);

        InnerScreen screen = SampleApplication.getScreenResolver().getScreen(this);
        int counter = screen.getCounter();
        mCounterTextView.setText(getString(R.string.counter_template, counter));
        mForwardButton.setOnClickListener(v -> mNavigator.goForward(new InnerScreen(counter + 1)));
    }

	// Workaround for issue https://code.google.com/p/android/issues/detail?id=55228
	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		if (getParentFragment() != null && (getParentFragment().isDetached() || getParentFragment().isRemoving())) {
			return AnimationUtils.loadAnimation(getContext(), R.anim.stay);
		}
		return super.onCreateAnimation(transit, enter, nextAnim);
	}
}