package com.art.alligator;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.art.alligator.implementation.DefaultAnimationProvider;
import com.art.alligator.implementation.DefaultNavigationErrorListener;

/**
 * Date: 29.12.2016
 * Time: 10:13
 *
 * @author Artur Artikov
 */
public class NavigationContext {
	private Activity mActivity;
	private FragmentManager mFragmentManager;
	private int mContainerId;
	private ScreenSwitcher mScreenSwitcher;
	private AnimationProvider mAnimationProvider;
	private NavigationListener mNavigationListener;
	private NavigationErrorListener mNavigationErrorListener;

	public NavigationContext(Activity activity) {
		this(new Builder(activity));
	}

	public NavigationContext(Activity activity, FragmentManager fragmentManager, int containerId) {
		this(new Builder(activity).fragmentManagerAndContainerId(fragmentManager, containerId));
	}

	private NavigationContext(Builder builder) {
		mActivity = builder.mActivity;
		mFragmentManager = builder.mFragmentManager;
		mContainerId = builder.mContainerId;
		mScreenSwitcher = builder.mScreenSwitcher;
		mAnimationProvider = builder.mAnimationProvider;
		mNavigationListener = builder.mNavigationListener;
		mNavigationErrorListener = builder.mNavigationErrorListener;
	}

	public Activity getActivity() {
		return mActivity;
	}

	public FragmentManager getFragmentManager() {
		return mFragmentManager;
	}

	public int getContainerId() {
		return mContainerId;
	}

	public ScreenSwitcher getScreenSwitcher() {
		return mScreenSwitcher;
	}

	public AnimationProvider getAnimationProvider() {
		return mAnimationProvider;
	}

	public NavigationListener getNavigationListener() {
		return mNavigationListener;
	}

	public NavigationErrorListener getNavigationErrorListener() {
		return mNavigationErrorListener;
	}

	public static class Builder {
		private Activity mActivity;
		private FragmentManager mFragmentManager;
		private int mContainerId;
		private ScreenSwitcher mScreenSwitcher;
		private AnimationProvider mAnimationProvider = new DefaultAnimationProvider();
		private NavigationListener mNavigationListener;
		private NavigationErrorListener mNavigationErrorListener = new DefaultNavigationErrorListener();

		public Builder(Activity activity) {
			mActivity = activity;
		}

		public Builder fragmentManagerAndContainerId(FragmentManager fragmentManager, int containerId) {
			mFragmentManager = fragmentManager;
			mContainerId = containerId;
			return this;
		}

		public Builder screenSwitcher(ScreenSwitcher screenSwitcher) {
			mScreenSwitcher = screenSwitcher;
			return this;
		}

		public Builder animationProvider(AnimationProvider animationProvider) {
			mAnimationProvider = animationProvider != null ? animationProvider : new DefaultAnimationProvider();
			return this;
		}

		public Builder navigationListener(NavigationListener navigationListener) {
			mNavigationListener = navigationListener;
			return this;
		}

		public Builder navigationErrorListener(NavigationErrorListener navigationErrorListener) {
			mNavigationErrorListener = navigationErrorListener != null ? navigationErrorListener : new DefaultNavigationErrorListener();
			return this;
		}

		public NavigationContext build() {
			return new NavigationContext(this);
		}
	}
}
