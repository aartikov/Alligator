package me.aartikov.screenresultsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.screenresultsample.R;
import me.aartikov.screenresultsample.SampleApplication;
import me.aartikov.screenresultsample.screens.MessageInputScreen;

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

	@BindView(R.id.message_edit_text)
	EditText mMessageEditText;

	@BindView(R.id.ok_button)
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
		NavigationContext navigationContext = new NavigationContext.Builder(this).build();
		mNavigationContextBinder.bind(navigationContext);
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
