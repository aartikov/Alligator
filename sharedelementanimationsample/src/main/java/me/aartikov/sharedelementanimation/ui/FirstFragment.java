package me.aartikov.sharedelementanimation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.sharedelementanimation.R;
import me.aartikov.sharedelementanimation.SampleApplication;
import me.aartikov.sharedelementanimation.screens.SecondScreen;

/**
 * Date: 16.04.2017
 * Time: 10:15
 *
 * @author Artur Artikov
 */

public class FirstFragment extends Fragment implements SharedElementProvider {
	@BindView(R.id.fragment_first_image_view_kitten)
	ImageView mKittenImageView;

	private Unbinder mButterknifeUnbinder;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_first, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterknifeUnbinder = ButterKnife.bind(this, view);
		mKittenImageView.setOnClickListener(v -> SampleApplication.getNavigator().goForward(new SecondScreen()));
	}

	@Override
	public void onDestroyView() {
		mButterknifeUnbinder.unbind();
		super.onDestroyView();
	}

	@Override
	public View getSharedElement() {
		return mKittenImageView;
	}

	@Override
	public String getSharedElementName() {
		return "kitten";
	}
}
