package me.aartikov.sharedelementanimation.ui;

import android.view.View;

import me.aartikov.alligator.animations.AnimationData;


public interface SharedElementProvider {
	View getSharedElement(AnimationData animationData);

	String getSharedElementName(AnimationData animationData);
}
