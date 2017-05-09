package me.aartikov.alligator;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import me.aartikov.alligator.defaultimpementation.DefaultDialogAnimationProvider;
import me.aartikov.alligator.defaultimpementation.DefaultDialogShowingListener;
import me.aartikov.alligator.defaultimpementation.DefaultNavigationErrorListener;
import me.aartikov.alligator.defaultimpementation.DefaultScreenSwitchingListener;
import me.aartikov.alligator.defaultimpementation.DefaultTransitionAnimationProvider;
import me.aartikov.alligator.defaultimpementation.DefaultTransitionListener;

/**
 * Date: 29.12.2016
 * Time: 10:13
 *
 * @author Artur Artikov
 */

/**
 * Used to configure an {@link AndroidNavigator}.
 */
public class NavigationContext {
	private AppCompatActivity mActivity;
	private FragmentManager mFragmentManager;
	private int mContainerId;
	private ScreenSwitcher mScreenSwitcher;
	private TransitionAnimationProvider mTransitionAnimationProvider;
	private DialogAnimationProvider mDialogAnimationProvider;
	private TransitionListener mTransitionListener;
	private DialogShowingListener mDialogShowingListener;
	private ScreenSwitchingListener mScreenSwitchingListener;
	private NavigationErrorListener mNavigationErrorListener;

	private NavigationContext(Builder builder) {
		if (builder.mActivity == null) {
			throw new NullPointerException("Activity can't be null.");
		}

		mActivity = builder.mActivity;
		mFragmentManager = builder.mFragmentManager != null ? builder.mFragmentManager : builder.mActivity.getSupportFragmentManager();
		mContainerId = builder.mContainerId;
		mScreenSwitcher = builder.mScreenSwitcher;
		mTransitionAnimationProvider = builder.mTransitionAnimationProvider != null ? builder.mTransitionAnimationProvider : new DefaultTransitionAnimationProvider();
		mDialogAnimationProvider = builder.mDialogAnimationProvider != null ? builder.mDialogAnimationProvider : new DefaultDialogAnimationProvider();
		mTransitionListener = builder.mTransitionListener != null ? builder.mTransitionListener : new DefaultTransitionListener();
		mDialogShowingListener = builder.mDialogShowingListener != null ? builder.mDialogShowingListener : new DefaultDialogShowingListener();
		mScreenSwitchingListener = builder.mScreenSwitchingListener != null ? builder.mScreenSwitchingListener : new DefaultScreenSwitchingListener();
		mNavigationErrorListener = builder.mNavigationErrorListener != null ? builder.mNavigationErrorListener : new DefaultNavigationErrorListener();
	}

	public AppCompatActivity getActivity() {
		return mActivity;
	}

	public FragmentManager getFragmentManager() {
		return mFragmentManager;
	}

	public int getContainerId() {
		return mContainerId;
	}

	public boolean hasContainerId() {
		return mContainerId > 0;
	}

	public ScreenSwitcher getScreenSwitcher() {
		return mScreenSwitcher;
	}

	public TransitionAnimationProvider getTransitionAnimationProvider() {
		return mTransitionAnimationProvider;
	}

	public DialogAnimationProvider getDialogAnimationProvider() {
		return mDialogAnimationProvider;
	}

	public TransitionListener getTransitionListener() {
		return mTransitionListener;
	}

	public DialogShowingListener getDialogShowingListener() {
		return mDialogShowingListener;
	}

	public ScreenSwitchingListener getScreenSwitchingListener() {
		return mScreenSwitchingListener;
	}

	public NavigationErrorListener getNavigationErrorListener() {
		return mNavigationErrorListener;
	}

	/**
	 * Builder for a {@link NavigationContext}.
	 */
	public static class Builder {
		private AppCompatActivity mActivity;
		private FragmentManager mFragmentManager;
		private int mContainerId;
		private ScreenSwitcher mScreenSwitcher;
		private TransitionAnimationProvider mTransitionAnimationProvider;
		private DialogAnimationProvider mDialogAnimationProvider;
		private TransitionListener mTransitionListener;
		private DialogShowingListener mDialogShowingListener;
		private ScreenSwitchingListener mScreenSwitchingListener;
		private NavigationErrorListener mNavigationErrorListener;

		/**
		 * Creates with the given activity.
		 *
		 * @param activity activity that should be current when the navigation context is bound.
		 */
		public Builder(AppCompatActivity activity) {
			mActivity = activity;
		}

		/**
		 * Sets a container id.
		 *
		 * @param containerId container id for fragments
		 * @return this object
		 */
		public Builder containerId(int containerId) {
			mContainerId = containerId;
			return this;
		}

		/**
		 * Sets a fragment manager.
		 *
		 * @param fragmentManager that will be used for fragment transactions. By default a support fragment manager of the current activity will be used.
		 *                        This method can be useful to set a child fragment manager.
		 * @return this object
		 */
		public Builder fragmentManager(FragmentManager fragmentManager) {
			mFragmentManager = fragmentManager;
			return this;
		}

		/**
		 * Sets a screen switcher.
		 *
		 * @param screenSwitcher screen switcher that will be used to switch screens by {@code switchTo} method of {@link Navigator}
		 * @return this object
		 */
		public Builder screenSwitcher(ScreenSwitcher screenSwitcher) {
			mScreenSwitcher = screenSwitcher;
			return this;
		}

		/**
		 * Sets a provider of {@link TransitionAnimation}s.
		 *
		 * @param transitionAnimationProvider provider of {@link TransitionAnimation}s. By default a provider that returns {@code TransitionAnimation.DEFAULT} is used.
		 * @return this object
		 */
		public Builder transitionAnimationProvider(TransitionAnimationProvider transitionAnimationProvider) {
			mTransitionAnimationProvider = transitionAnimationProvider;
			return this;
		}

		/**
		 * Sets a provider of {@link DialogAnimation}s.
		 *
		 * @param dialogAnimationProvider provider of {@link DialogAnimation}s. By default a provider that returns {@code DialogAnimation.DEFAULT} is used.
		 * @return this object
		 */
		public Builder dialogAnimationProvider(DialogAnimationProvider dialogAnimationProvider) {
			mDialogAnimationProvider = dialogAnimationProvider;
			return this;
		}

		/**
		 * Sets a transition listener. This listener is called after a screen transition.
		 *
		 * @param transitionListener transition listener. By default a listener that does nothing is used.
		 * @return this object
		 */
		public Builder transitionListener(TransitionListener transitionListener) {
			mTransitionListener = transitionListener;
			return this;
		}

		/**
		 * Sets a dialog showing listener. This listener is called after a dialog has been shown.
		 *
		 * @param dialogShowingListener dialog showing listener. By default a listener that does nothing is used.
		 * @return this object
		 */
		public Builder dialogShowingListener(DialogShowingListener dialogShowingListener) {
			mDialogShowingListener = dialogShowingListener;
			return this;
		}

		/**
		 * Sets a screen switching listener listener. This listener is called after a screen has been switched using {@link ScreenSwitcher}.
		 *
		 * @param screenSwitchingListener screen switcher listener. By default a listener that does nothing is used.
		 * @return this object
		 */
		public Builder screenSwitcherListener(ScreenSwitchingListener screenSwitchingListener) {
			mScreenSwitchingListener = screenSwitchingListener;
			return this;
		}

		/**
		 * Sets a navigation error listener. This listener is called when an error has occurred during {@link Command} execution.
		 *
		 * @param navigationErrorListener navigation error listener. By default a listener that wraps errors to {@code RuntimeException} and throws it is used.
		 * @return this object
		 */
		public Builder navigationErrorListener(NavigationErrorListener navigationErrorListener) {
			mNavigationErrorListener = navigationErrorListener;
			return this;
		}

		/**
		 * Builds a navigation context
		 *
		 * @return created navigation context
		 */
		public NavigationContext build() {
			return new NavigationContext(this);
		}
	}
}
