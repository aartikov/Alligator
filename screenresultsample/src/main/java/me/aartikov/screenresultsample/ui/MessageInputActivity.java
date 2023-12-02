package me.aartikov.screenresultsample.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.screenresultsample.R;
import me.aartikov.screenresultsample.SampleApplication;
import me.aartikov.screenresultsample.screens.MessageInputScreen;


@RegisterScreen(value = MessageInputScreen.class, screenResult = MessageInputScreen.Result.class)
public class MessageInputActivity extends AppCompatActivity {
    private final Navigator mNavigator = SampleApplication.getNavigator();
    private final NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

    EditText mMessageEditText;
    Button mOkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_input);

        mMessageEditText = findViewById(R.id.message_edit_text);
        mOkButton = findViewById(R.id.ok_button);

        mOkButton.setOnClickListener(v -> {
            String message = mMessageEditText.getText().toString();
            mNavigator.goBackWithResult(new MessageInputScreen.Result(message));    // Easy-peasy!
        });
    }

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		NavigationContext navigationContext = new NavigationContext.Builder(this, SampleApplication.getNavigationFactory())
				.build();
		mNavigationContextBinder.bind(navigationContext);
	}

	@Override
	protected void onPause() {
		mNavigationContextBinder.unbind(this);
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		mNavigator.goBack();
	}
}
