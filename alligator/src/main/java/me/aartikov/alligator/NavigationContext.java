package me.aartikov.alligator;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import me.aartikov.alligator.animations.DialogAnimation;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.animations.providers.DefaultDialogAnimationProvider;
import me.aartikov.alligator.animations.providers.DefaultTransitionAnimationProvider;
import me.aartikov.alligator.animations.providers.DialogAnimationProvider;
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider;
import me.aartikov.alligator.commands.Command;
import me.aartikov.alligator.helpers.ActivityHelper;
import me.aartikov.alligator.helpers.DialogFragmentHelper;
import me.aartikov.alligator.helpers.FragmentStack;
import me.aartikov.alligator.helpers.ScreenResultHelper;
import me.aartikov.alligator.listeners.DefaultDialogShowingListener;
import me.aartikov.alligator.listeners.DefaultNavigationErrorListener;
import me.aartikov.alligator.listeners.DefaultScreenResultListener;
import me.aartikov.alligator.listeners.DefaultScreenSwitchingListener;
import me.aartikov.alligator.listeners.DefaultTransitionListener;
import me.aartikov.alligator.listeners.DialogShowingListener;
import me.aartikov.alligator.listeners.NavigationErrorListener;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.listeners.ScreenSwitchingListener;
import me.aartikov.alligator.listeners.TransitionListener;
import me.aartikov.alligator.screenswitchers.ScreenSwitcher;

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
	@Nullable
	private ScreenSwitcher mScreenSwitcher;
	private TransitionAnimationProvider mTransitionAnimationProvider;
	private DialogAnimationProvider mDialogAnimationProvider;
	private TransitionListener mTransitionListener;
	private DialogShowingListener mDialogShowingListener;
	private ScreenResultListener mScreenResultListener;
	private ScreenSwitchingListener mScreenSwitchingListener;
	private NavigationErrorListener mNavigationErrorListener;
	private ActivityHelper mActivityHelper;
	private DialogFragmentHelper mDialogFragmentHelper;
	@Nullable
	private FragmentStack mFragmentStack;
	private ScreenResultHelper mScreenResultHelper;

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
		mScreenResultListener = builder.mScreenResultListener != null ? builder.mScreenResultListener : new DefaultScreenResultListener();
		mScreenSwitchingListener = builder.mScreenSwitchingListener != null ? builder.mScreenSwitchingListener : new DefaultScreenSwitchingListener();
		mNavigationErrorListener = builder.mNavigationErrorListener != null ? builder.mNavigationErrorListener : new DefaultNavigationErrorListener();
		mActivityHelper = new ActivityHelper(mActivity);
		mDialogFragmentHelper = new DialogFragmentHelper(mFragmentManager);
		mFragmentStack = mContainerId > 0 ? new FragmentStack(mFragmentManager, mContainerId) : null;
		mScreenResultHelper = new ScreenResultHelper();
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

	@Nullable
	public ScreenSwitcher getScreenSwitcher() {
		return mScreenSwitcher;
	}

	@NonNull
	public TransitionAnimationProvider getTransitionAnimationProvider() {
		return mTransitionAnimationProvider;
	}

	@NonNull
	public DialogAnimationProvider getDialogAnimationProvider() {
		return mDialogAnimationProvider;
	}

	@NonNull
	public TransitionListener getTransitionListener() {
		return mTransitionListener;
	}

	@NonNull
	public DialogShowingListener getDialogShowingListener() {
		return mDialogShowingListener;
	}

	@NonNull
	public ScreenResultListener getScreenResultListener() {
		return mScreenResultListener;
	}

	@NonNull
	public ScreenSwitchingListener getScreenSwitchingListener() {
		return mScreenSwitchingListener;
	}

	@NonNull
	public NavigationErrorListener getNavigationErrorListener() {
		return mNavigationErrorListener;
	}

	@NonNull
	public ActivityHelper getActivityHelper() {
		return mActivityHelper;
	}

	@NonNull
	public DialogFragmentHelper getDialogFragmentHelper() {
		return mDialogFragmentHelper;
	}

	@Nullable
	public FragmentStack getFragmentStack() {
		return mFragmentStack;
	}

	public ScreenResultHelper getScreenResultHelper() {
		return mScreenResultHelper;
	}

	/**
	 * Builder for a {@link NavigationContext}.
	 */
	public static class Builder {
		private AppCompatActivity mActivity;
		@Nullable
		private FragmentManager mFragmentManager;
		private int mContainerId;
		@Nullable
		private ScreenSwitcher mScreenSwitcher;
		@Nullable
		private TransitionAnimationProvider mTransitionAnimationProvider;
		@Nullable
		private DialogAnimationProvider mDialogAnimationProvider;
		@Nullable
		private TransitionListener mTransitionListener;
		@Nullable
		private ScreenResultListener mScreenResultListener;
		@Nullable
		private DialogShowingListener mDialogShowingListener;
		@Nullable
		private ScreenSwitchingListener mScreenSwitchingListener;
		@Nullable
		private NavigationErrorListener mNavigationErrorListener;

		/**
		 * Creates with the given activity.
		 *
		 * @param activity activity that should be current when the navigation context is bound.
		 */
		public Builder(@NonNull AppCompatActivity activity) {
			mActivity = activity;
		}

		/**
		 * Sets a container id.
		 *
		 * @param containerId container id for fragments
		 * @return this object
		 */
		@NonNull
		public Builder containerId(@IdRes int containerId) {
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
		@NonNull
		public Builder fragmentManager(@Nullable FragmentManager fragmentManager) {
			mFragmentManager = fragmentManager;
			return this;
		}

		/**
		 * Sets a screen switcher.
		 *
		 * @param screenSwitcher screen switcher that will be used to switch screens by {@code switchTo} method of {@link Navigator}
		 * @return this object
		 */
		@NonNull
		public Builder screenSwitcher(@Nullable ScreenSwitcher screenSwitcher) {
			mScreenSwitcher = screenSwitcher;
			return this;
		}

		/**
		 * Sets a provider of {@link TransitionAnimation}s.
		 *
		 * @param transitionAnimationProvider provider of {@link TransitionAnimation}s. By default a provider that returns {@code TransitionAnimation.DEFAULT} is used.
		 * @return this object
		 */
		@NonNull
		public Builder transitionAnimationProvider(@Nullable TransitionAnimationProvider transitionAnimationProvider) {
			mTransitionAnimationProvider = transitionAnimationProvider;
			return this;
		}

		/**
		 * Sets a provider of {@link DialogAnimation}s.
		 *
		 * @param dialogAnimationProvider provider of {@link DialogAnimation}s. By default a provider that returns {@code DialogAnimation.DEFAULT} is used.
		 * @return this object
		 */
		@NonNull
		public Builder dialogAnimationProvider(@Nullable DialogAnimationProvider dialogAnimationProvider) {
			mDialogAnimationProvider = dialogAnimationProvider;
			return this;
		}

		/**
		 * Sets a transition listener. This listener is called after a screen transition.
		 *
		 * @param transitionListener transition listener.
		 * @return this object
		 */
		@NonNull
		public Builder transitionListener(@Nullable TransitionListener transitionListener) {
			mTransitionListener = transitionListener;
			return this;
		}

		/**
		 * Sets a dialog showing listener. This listener is called after a dialog has been shown.
		 *
		 * @param dialogShowingListener dialog showing listener.
		 * @return this object
		 */
		@NonNull
		public Builder dialogShowingListener(@Nullable DialogShowingListener dialogShowingListener) {
			mDialogShowingListener = dialogShowingListener;
			return this;
		}

		/**
		 * Sets a screen result listener. This listener is called when a screen returned result to a previous screen
		 *
		 * @param screenResultListener screenResultListener screen result listener.
		 * @return this object
		 */
		@NonNull
		public Builder screenResultListener(@Nullable ScreenResultListener screenResultListener) {
			mScreenResultListener = screenResultListener;
			return this;
		}

		/**
		 * Sets a screen switching listener listener. This listener is called after a screen has been switched using {@link ScreenSwitcher}.
		 *
		 * @param screenSwitchingListener screen switcher listener.
		 * @return this object
		 */
		@NonNull
		public Builder screenSwitchingListener(@Nullable ScreenSwitchingListener screenSwitchingListener) {
			mScreenSwitchingListener = screenSwitchingListener;
			return this;
		}

		/**
		 * Sets a navigation error listener. This listener is called when an error has occurred during {@link Command} execution.
		 *
		 * @param navigationErrorListener navigation error listener. By default a listener that wraps errors to {@code RuntimeException} and throws it is used.
		 * @return this object
		 */
		@NonNull
		public Builder navigationErrorListener(@Nullable NavigationErrorListener navigationErrorListener) {
			mNavigationErrorListener = navigationErrorListener;
			return this;
		}

		/**
		 * Builds a navigation context
		 *
		 * @return created navigation context
		 */
		@NonNull
		public NavigationContext build() {
			return new NavigationContext(this);
		}
	}
}
