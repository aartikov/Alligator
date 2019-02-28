package me.aartikov.screenresultsample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.aartikov.alligator.ActivityResultHandler;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.screenresultsample.R;
import me.aartikov.screenresultsample.SampleApplication;
import me.aartikov.screenresultsample.screens.ImagePickerScreen;
import me.aartikov.screenresultsample.screens.MainScreen;
import me.aartikov.screenresultsample.screens.MessageInputScreen;

/**
 * Date: 12.03.2016
 * Time: 15:53
 *
 * @author Artur Artikov
 */
@RegisterScreen(MainScreen.class)
public class MainActivity extends AppCompatActivity implements ScreenResultListener {
	private Navigator mNavigator = SampleApplication.getNavigator();
	private NavigationContextBinder mNavigationContextBinder = SampleApplication.getNavigationContextBinder();
	private ActivityResultHandler mActivityResultHandler = SampleApplication.getActivityResultHandler();

	@BindView(R.id.input_message_button)
	Button mInputMessageButton;

	@BindView(R.id.pick_image_button)
	Button mPickImageButton;

	@BindView(R.id.message_text_view)
	TextView mMessageTextView;

	@BindView(R.id.image_view)
	ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		// goForward works as startActivityForResult here
		mInputMessageButton.setOnClickListener(v -> mNavigator.goForward(new MessageInputScreen()));
		mPickImageButton.setOnClickListener(v -> mNavigator.goForward(new ImagePickerScreen()));
	}

	private void onMessageInputted(MessageInputScreen.Result messageInputResult) {
		mMessageTextView.setText(getString(R.string.inputted_message_template, messageInputResult.getMessage()));
	}

	private void onImagePicked(ImagePickerScreen.Result imagePickerResult) {
		Picasso.with(this).load(imagePickerResult.getUri()).into(mImageView);
	}

	@Override
	public void onScreenResult(Class<? extends Screen> screenClass, @Nullable ScreenResult result) {
		if (result == null) {
			Toast.makeText(MainActivity.this, getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
			return;
		}

		if (result instanceof MessageInputScreen.Result) {
			onMessageInputted((MessageInputScreen.Result) result);
		} else if (result instanceof ImagePickerScreen.Result) {
			onImagePicked((ImagePickerScreen.Result) result);
		}
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		NavigationContext navigationContext = new NavigationContext.Builder(this)
				.screenResultListener(this)      // set ScreenResultListener
				.build();
		mNavigationContextBinder.bind(navigationContext);
	}

	@Override protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		mActivityResultHandler.onNewIntent(intent);     // handle result
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mActivityResultHandler.onActivityResult(requestCode, resultCode, data);     // handle result
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
