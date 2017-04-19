package me.aartikov.sharedelementanimation.ui;

import android.view.View;

import me.aartikov.alligator.AnimationData;

/**
 * Date: 16.04.2017
 * Time: 14:07
 *
 * @author Artur Artikov
 */
public interface SharedElementProvider {
	View getSharedElement(AnimationData animationData);

	String getSharedElementName(AnimationData animationData);
}
