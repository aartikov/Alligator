package me.aartikov.sharedelementanimation.ui;

import me.aartikov.alligator.AnimationData;

/**
 * Date: 4/19/2017
 * Time: 16:58
 *
 * @author Artur Artikov
 */
public class KittenAnimationData implements AnimationData {
	private int mKittenIndex;

	public KittenAnimationData(int kittenIndex) {
		mKittenIndex = kittenIndex;
	}

	public int getKittenIndex() {
		return mKittenIndex;
	}
}
