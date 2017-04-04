package com.art.screenresultsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationContextBinder;
import com.art.alligator.Navigator;
import com.art.screenresultsample.R;
import com.art.screenresultsample.SampleApplication;
import com.art.screenresultsample.screens.MessageInputScreen;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 12.03.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
public class MessageInputActivity extends AppCompatActivity {
	private Navigator mNavigator;
	private NavigationContextBinder mNavigationContextBinder;

	@BindView(R.id.activity_message_input_edit_text_message)
	EditText mMessageEditText;

	@BindView(R.id.activity_input_button_ok)
	Button mOkButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_input);
		ButterKnife.bind(this);

		mNavigator = SampleApplication.getNavigator();
		mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

		mOkButton.setOnClickListener(v -> {
			String message = mMessageEditText.getText().toString();
			mNavigator.finishWithResult(new MessageInputScreen.Result(message));
		});
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
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
