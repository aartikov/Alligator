package me.aartikov.sharedelementanimation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.sharedelementanimation.R;
import me.aartikov.sharedelementanimation.SampleApplication;
import me.aartikov.sharedelementanimation.screens.SecondScreen;

/**
 * Date: 16.04.2017
 * Time: 10:15
 *
 * @author Artur Artikov
 */
@RegisterScreen(SecondScreen.class)
public class SecondFragment extends Fragment implements SharedElementProvider {
	@BindView(R.id.kitten_image_view)
	ImageView mKittenImageView;

	private Unbinder mButterKnifeUnbinder;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_second, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterKnifeUnbinder = ButterKnife.bind(this, view);

		SecondScreen screen = SampleApplication.getScreenResolver().getScreen(this);
		mKittenImageView.setImageResource(screen.getKittenIndex() == 0 ? R.drawable.kitten_0 : R.drawable.kitten_1);
		mKittenImageView.setOnClickListener(v -> SampleApplication.getNavigator().goBack());
	}

	@Override
	public void onDestroyView() {
		mButterKnifeUnbinder.unbind();
		super.onDestroyView();
	}

	@Override
	public View getSharedElement(AnimationData animationData) {
		return mKittenImageView;
	}

	@Override
	public String getSharedElementName(AnimationData animationData) {
		SecondScreen screen = SampleApplication.getScreenResolver().getScreen(this);
		return "kitten_" + screen.getKittenIndex();
	}
}
