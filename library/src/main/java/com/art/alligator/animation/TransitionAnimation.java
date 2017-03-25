package com.art.alligator.animation;

/**
 * Date: 24.02.2017
 * Time: 19:01
 *
 * @author Artur Artikov
 */

public class TransitionAnimation {
	private int mEnterAnimation;
	private int mExitAnimation;

	public static TransitionAnimation NONE = new TransitionAnimation(0, 0);
	public static TransitionAnimation DEFAULT = new TransitionAnimation(-1, -1);

	public TransitionAnimation(int enterAnimation, int exitAnimation) {
		mEnterAnimation = enterAnimation;
		mExitAnimation = exitAnimation;
	}

	public int getEnterAnimation() {
		return mEnterAnimation;
	}

	public int getExitAnimation() {
		return mExitAnimation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TransitionAnimation animation = (TransitionAnimation) o;
		return mEnterAnimation == animation.mEnterAnimation && mExitAnimation == animation.mExitAnimation;

	}

	@Override
	public int hashCode() {
		int result = mEnterAnimation;
		result = 31 * result + mExitAnimation;
		return result;
	}
}
