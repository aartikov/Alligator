package me.aartikov.sharedelementanimation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.Navigator;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.annotations.RegisterScreen;
import me.aartikov.sharedelementanimation.R;
import me.aartikov.sharedelementanimation.SampleApplication;
import me.aartikov.sharedelementanimation.screens.FirstScreen;
import me.aartikov.sharedelementanimation.screens.SecondScreen;


@RegisterScreen(FirstScreen.class)
public class FirstFragment extends Fragment implements SharedElementProvider {

	ImageView[] mKittenImageViews;

	private final Navigator mNavigator = SampleApplication.getNavigator();

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_first, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mKittenImageViews = new ImageView[]{
                view.findViewById(R.id.kitten_image_view_0),
                view.findViewById(R.id.kitten_image_view_1)
        };

        for (int i = 0; i < mKittenImageViews.length; i++) {
            int kittenIndex = i;
            mKittenImageViews[i].setOnClickListener(v -> {
                mNavigator.goForward(new SecondScreen(kittenIndex), new KittenAnimationData(kittenIndex));
            });
        }
    }

	@Override
	public View getSharedElement(AnimationData animationData) {
		KittenAnimationData kittenAnimationData = (KittenAnimationData) animationData;
		return mKittenImageViews[kittenAnimationData.getKittenIndex()];
	}

	@Override
	public String getSharedElementName(AnimationData animationData) {
		return "kitten";
	}
}
