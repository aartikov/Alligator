package me.aartikov.alligator;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import me.aartikov.alligator.animations.DialogAnimation;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.animations.providers.DefaultDialogAnimationProvider;
import me.aartikov.alligator.animations.providers.DefaultTransitionAnimationProvider;
import me.aartikov.alligator.animations.providers.DialogAnimationProvider;
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider;
import me.aartikov.alligator.commands.Command;
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
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.navigators.ActivityNavigator;
import me.aartikov.alligator.navigators.DefaultActivityNavigator;
import me.aartikov.alligator.navigators.DefaultDialogFragmentNavigator;
import me.aartikov.alligator.navigators.DefaultFragmentNavigator;
import me.aartikov.alligator.navigators.DialogFragmentNavigator;
import me.aartikov.alligator.navigators.FragmentNavigator;
import me.aartikov.alligator.screenswitchers.ScreenSwitcher;


/**
 * Used to configure an {@link AndroidNavigator}.
 */
public class NavigationContext {
	@NonNull
	private AppCompatActivity mActivity;

	@NonNull
	private NavigationFactory mNavigationFactory;

	@NonNull
	private ActivityNavigator mActivityNavigator;

	@Nullable
	private FragmentNavigator mFragmentNavigator;

	@NonNull
	private DialogFragmentNavigator mDialogFragmentNavigator;

	@Nullable
	private ScreenSwitcher mScreenSwitcher;

	@NonNull
	private ScreenResultListener mScreenResultListener;

	@NonNull
	private NavigationErrorListener mNavigationErrorListener;

	@NonNull
	private ScreenSwitchingListener mScreenSwitchingListener;

	private NavigationContext(@NonNull AppCompatActivity activity,
							  @NonNull NavigationFactory navigationFactory,
							  @NonNull ActivityNavigator activityNavigator,
							  @Nullable FragmentNavigator fragmentNavigator,
							  @NonNull DialogFragmentNavigator dialogFragmentNavigator,
							  @Nullable ScreenSwitcher screenSwitcher,
							  @NonNull ScreenResultListener screenResultListener,
							  @NonNull NavigationErrorListener navigationErrorListener,
							  @NonNull ScreenSwitchingListener screenSwitchingListener) {
		mActivity = activity;
		mNavigationFactory = navigationFactory;
		mActivityNavigator = activityNavigator;
		mFragmentNavigator = fragmentNavigator;
		mDialogFragmentNavigator = dialogFragmentNavigator;
		mScreenSwitcher = screenSwitcher;
		mScreenResultListener = screenResultListener;
		mNavigationErrorListener = navigationErrorListener;
		mScreenSwitchingListener = screenSwitchingListener;
	}

	@NonNull
	public AppCompatActivity getActivity() {
		return mActivity;
	}

	@NonNull
	public NavigationFactory getNavigationFactory() {
		return mNavigationFactory;
	}

	@NonNull
	public ActivityNavigator getActivityNavigator() {
		return mActivityNavigator;
	}

	@Nullable
	public FragmentNavigator getFragmentNavigator() {
		return mFragmentNavigator;
	}

	@NonNull
	public DialogFragmentNavigator getDialogFragmentNavigator() {
		return mDialogFragmentNavigator;
	}

	@Nullable
	public ScreenSwitcher getScreenSwitcher() {
		return mScreenSwitcher;
	}

	@NonNull
	public ScreenResultListener getScreenResultListener() {
		return mScreenResultListener;
	}

	@NonNull
	public NavigationErrorListener getNavigationErrorListener() {
		return mNavigationErrorListener;
	}

	@NonNull
	public ScreenSwitchingListener getScreenSwitchingListener() {
		return mScreenSwitchingListener;
	}

	/**
	 * Builder for a {@link NavigationContext}.
	 */
	public static class Builder {
		@NonNull
		private AppCompatActivity mActivity;
		@NonNull
		private NavigationFactory mNavigationFactory;
		@Nullable
		private FragmentManager mFragmentManager;
		private int mFragmentContainerId;
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
		 * @param activity          activity that should be current when the navigation context is bound.
		 * @param navigationFactory navigation factory that was used to create {@link AndroidNavigator}
		 */
		public Builder(@NonNull AppCompatActivity activity, @NonNull NavigationFactory navigationFactory) {
			mActivity = activity;
			mNavigationFactory = navigationFactory;
		}

		/**
		 * Configure fragment navigation
		 *
		 * @param fragmentManager that will be used for fragment transactions
		 * @param containerId     container id for fragments
		 * @return this object
		 */
		@NonNull
		public Builder fragmentNavigation(@NonNull FragmentManager fragmentManager, @IdRes int containerId) {
			mFragmentManager = fragmentManager;
			mFragmentContainerId = containerId;
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
			TransitionListener transitionListener = mTransitionListener != null ? mTransitionListener : new DefaultTransitionListener();
			TransitionAnimationProvider transitionAnimationProvider = mTransitionAnimationProvider != null ? mTransitionAnimationProvider : new DefaultTransitionAnimationProvider();
			DialogShowingListener dialogShowingListener = mDialogShowingListener != null ? mDialogShowingListener : new DefaultDialogShowingListener();
			DialogAnimationProvider dialogAnimationProvider = mDialogAnimationProvider != null ? mDialogAnimationProvider : new DefaultDialogAnimationProvider();
			ScreenSwitchingListener screenSwitchingListener = mScreenSwitchingListener != null ? mScreenSwitchingListener : new DefaultScreenSwitchingListener();
			ScreenResultListener screenResultListener = mScreenResultListener != null ? mScreenResultListener : new DefaultScreenResultListener();
			NavigationErrorListener navigationErrorListener = mNavigationErrorListener != null ? mNavigationErrorListener : new DefaultNavigationErrorListener();

			ActivityNavigator activityNavigator = new DefaultActivityNavigator(mActivity, mNavigationFactory, transitionListener, transitionAnimationProvider);

			FragmentNavigator fragmentNavigator = mFragmentManager != null ?
					new DefaultFragmentNavigator(mFragmentManager, mFragmentContainerId, mNavigationFactory, transitionListener, screenResultListener, transitionAnimationProvider) : null;

			DialogFragmentNavigator dialogFragmentNavigator = new DefaultDialogFragmentNavigator(mActivity.getSupportFragmentManager(), mNavigationFactory,
					dialogShowingListener, screenResultListener, dialogAnimationProvider);

			return new NavigationContext(mActivity, mNavigationFactory, activityNavigator, fragmentNavigator, dialogFragmentNavigator,
					mScreenSwitcher, screenResultListener, navigationErrorListener, screenSwitchingListener);
		}
	}
}
