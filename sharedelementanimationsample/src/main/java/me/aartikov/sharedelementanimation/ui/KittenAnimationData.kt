package me.aartikov.sharedelementanimation.ui;

import me.aartikov.alligator.animations.AnimationData;


public class KittenAnimationData implements AnimationData {
	private int mKittenIndex;

	public KittenAnimationData(int kittenIndex) {
		mKittenIndex = kittenIndex;
	}

	public int getKittenIndex() {
		return mKittenIndex;
	}
}
