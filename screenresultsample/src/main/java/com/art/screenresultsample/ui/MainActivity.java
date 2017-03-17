package com.art.screenresultsample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationContextBinder;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Navigator;
import com.art.alligator.Screen;
import com.art.alligator.ScreenResult;
import com.art.alligator.ScreenResultListener;
import com.art.alligator.implementation.ScreenResultUtils;
import com.art.screenresultsample.R;
import com.art.screenresultsample.SampleApplication;
import com.art.screenresultsample.screens.InputScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 12.03.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class MainActivity extends AppCompatActivity implements ScreenResultListener {
	private Navigator mNavigator;
	private NavigationContextBinder mNavigationContextBinder;
	private NavigationFactory mNavigationFactory;

	@BindView(R.id.activity_main_text_view_message)
	TextView mMessageTextView;

	@BindView(R.id.activity_main_button_input)
	Button mInputButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		mNavigator = SampleApplication.getNavigator();
		mNavigationContextBinder = SampleApplication.getNavigationContextBinder();
		mNavigationFactory = SampleApplication.getNavigationFactory();

		mInputButton.setOnClickListener(v -> mNavigator.goForwardForResult(new InputScreen()));
	}

	@Override
	public boolean onScreenResult(Class<? extends Screen> screenClass, ScreenResult result) {
		if(screenClass == InputScreen.class) {
			InputScreen.Result inputScreenResult = (InputScreen.Result) result;
			if(inputScreenResult == null) {
				Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
			} else {
				mMessageTextView.setText(inputScreenResult.getMessage());
			}
			return true;
		}
		return false;
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ScreenResultUtils.handleActivityResult(requestCode, resultCode, data, mNavigationFactory, this);
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}
}
