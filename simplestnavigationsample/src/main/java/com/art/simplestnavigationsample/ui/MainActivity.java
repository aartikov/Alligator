package com.art.simplestnavigationsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationContextBinder;
import com.art.alligator.Navigator;
import com.art.simplestnavigationsample.R;
import com.art.simplestnavigationsample.SampleApplication;
import com.art.simplestnavigationsample.screens.MessageScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 22.01.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class MainActivity extends AppCompatActivity {
	private Navigator mNavigator;
	private NavigationContextBinder mNavigationContextBinder;

	@BindView(R.id.activity_main_button_show_message)
	Button mShowMessageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		mNavigator = SampleApplication.getNavigator();
		mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

		mShowMessageButton.setOnClickListener(v -> mNavigator.goForward(new MessageScreen("Hello!")));
	}

	@Override
	protected void onResume() {
		super.onResume();
		mNavigationContextBinder.bind(new NavigationContext(this));
	}

	@Override
	protected void onPause() {
		mNavigationContextBinder.unbind();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}
}