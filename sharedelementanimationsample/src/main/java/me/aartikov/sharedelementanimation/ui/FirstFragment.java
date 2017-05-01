package me.aartikov.sharedelementanimation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aartikov.alligator.AnimationData;
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
	@BindViews({R.id.kitten_image_view_0, R.id.kitten_image_view_1})
	ImageView[] mKittenImageViews;

	private Unbinder mButterknifeUnbinder;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_first, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mButterknifeUnbinder = ButterKnife.bind(this, view);
		for(int i = 0; i < mKittenImageViews.length; i++) {
			int kittenIndex = i;
			mKittenImageViews[i].setOnClickListener(v -> {
				SampleApplication.getNavigator().goForward(new SecondScreen(kittenIndex), new KittenAnimationData(kittenIndex));
			});
		}
	}

	@Override
	public void onDestroyView() {
		mButterknifeUnbinder.unbind();
		super.onDestroyView();
	}

	@Override
	public View getSharedElement(AnimationData animationData) {
		KittenAnimationData kittenAnimationData = (KittenAnimationData)animationData;
		return mKittenImageViews[kittenAnimationData.getKittenIndex()];
	}

	@Override
	public String getSharedElementName(AnimationData animationData) {
		return "kitten";
	}
}
