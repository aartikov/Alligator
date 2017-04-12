package me.aartikov.screenresultsample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.NavigationContextBinder;
import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.ScreenResultListener;
import me.aartikov.alligator.ScreenResultResolver;
import me.aartikov.screenresultsample.R;
import me.aartikov.screenresultsample.SampleApplication;
import me.aartikov.screenresultsample.screens.ImagePickerScreen;
import me.aartikov.screenresultsample.screens.MessageInputScreen;
import com.squareup.picasso.Picasso;

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

	@BindView(R.id.activity_main_button_input_message)
	Button mInputMessageButton;

	@BindView(R.id.activity_main_button_pick_image)
	Button mPickImageButton;

	@BindView(R.id.activity_main_text_view_message)
	TextView mMessageTextView;

	@BindView(R.id.activity_main_image_view)
	ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		mNavigator = SampleApplication.getNavigator();
		mNavigationContextBinder = SampleApplication.getNavigationContextBinder();

		mInputMessageButton.setOnClickListener(v -> mNavigator.goForward(new MessageInputScreen()));
		mPickImageButton.setOnClickListener(v -> mNavigator.goForward(new ImagePickerScreen()));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ScreenResultResolver screenResultResolver = SampleApplication.getScreenResultResolver();
		screenResultResolver.handleActivityResult(requestCode, resultCode, data, this);
	}

	@Override
	public void onScreenResult(Class<? extends Screen> screenClass, @Nullable ScreenResult result) {
		if (screenClass == MessageInputScreen.class) {
			onMessageInputted((MessageInputScreen.Result) result);
		} else if (screenClass == ImagePickerScreen.class) {
			onImagePicked((ImagePickerScreen.Result) result);
		}
	}

	private void onMessageInputted(MessageInputScreen.Result messageInputResult) {
		if (messageInputResult != null) {
			mMessageTextView.setText(getString(R.string.inputted_message_template, messageInputResult.getMessage()));
		} else {
			Toast.makeText(this, getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
		}
	}

	private void onImagePicked(ImagePickerScreen.Result imagePickerResult) {
		if (imagePickerResult != null) {
			Picasso.with(this).load(imagePickerResult.getUri()).into(mImageView);
		} else {
			Toast.makeText(this, getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
		}
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
