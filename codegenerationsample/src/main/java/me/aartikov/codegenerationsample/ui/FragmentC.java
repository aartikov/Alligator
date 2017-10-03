package me.aartikov.codegenerationsample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.RegisterScreen;
import me.aartikov.codegenerationsample.R;
import me.aartikov.codegenerationsample.SampleApplication;
import me.aartikov.codegenerationsample.screens.ScreenC;
import me.aartikov.codegenerationsample.screens.ScreenD;

/**
 * Date: 15.05.2017
 * Time: 01:25
 *
 * @author Artur Artikov
 */
@RegisterScreen(ScreenC.class)
public class FragmentC extends Fragment {
	@BindView(R.id.go_forward_to_d_button)
	Button mGoForwardToDButton;

	private Unbinder mButterKnifeUnbinder;

	private Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_c, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterKnifeUnbinder = ButterKnife.bind(this, view);
		mGoForwardToDButton.setOnClickListener(v -> mNavigator.goForward(new ScreenD("Message for D from C")));
	}

	@Override
	public void onDestroyView() {
		mButterKnifeUnbinder.unbind();
		super.onDestroyView();
	}
}
