package me.aartikov.codegenerationsample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.RegisterScreen;
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
	@BindView(R.id.message_text_view)
	TextView mMessageTextView;

	@BindView(R.id.go_back_to_a_button)
	Button mGoBackToAButton;

	private Unbinder mButterKnifeUnbinder;

	private Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_d, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterKnifeUnbinder = ButterKnife.bind(this, view);

		ScreenD screen = SampleApplication.getScreenResolver().getScreen(this);
		mMessageTextView.setText(screen.getMessage());
		mGoBackToAButton.setOnClickListener(v -> mNavigator.goBackTo(ScreenA.class));
	}

	@Override
	public void onDestroyView() {
		mButterKnifeUnbinder.unbind();
		super.onDestroyView();
	}
}
