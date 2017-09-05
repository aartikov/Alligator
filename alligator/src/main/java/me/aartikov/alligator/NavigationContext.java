package me.aartikov.alligator;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.aartikov.alligator.defaultimpementation.DefaultDialogAnimationProvider;
import me.aartikov.alligator.defaultimpementation.DefaultTransitionAnimationProvider;

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
    private List<TransitionListener> mTransitionListener;
    private List<DialogShowingListener> mDialogShowingListener;
    private List<ScreenSwitchingListener> mScreenSwitchingListener;
    private List<NavigationErrorListener> mNavigationErrorListener;

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
        mTransitionListener = builder.mTransitionListener;
        mDialogShowingListener = builder.mDialogShowingListener;
        mScreenSwitchingListener = builder.mScreenSwitchingListener;
        mNavigationErrorListener = builder.mNavigationErrorListener;
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

    public List<TransitionListener> getTransitionListeners() {
        return mTransitionListener;
    }

    public List<DialogShowingListener> getDialogShowingListeners() {
        return mDialogShowingListener;
    }

    public List<ScreenSwitchingListener> getScreenSwitchingListeners() {
        return mScreenSwitchingListener;
    }

    public List<NavigationErrorListener> getNavigationErrorListeners() {
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
        private List<TransitionListener> mTransitionListener;
        private List<DialogShowingListener> mDialogShowingListener;
        private List<ScreenSwitchingListener> mScreenSwitchingListener;
        private List<NavigationErrorListener> mNavigationErrorListener;

        /**
         * Creates with the given activity.
         *
         * @param activity activity that should be current when the navigation context is bound.
         */
        public Builder(AppCompatActivity activity) {
            mActivity = activity;
            mTransitionListener = new ArrayList<>();
            mDialogShowingListener = new ArrayList<>();
            mScreenSwitchingListener = new ArrayList<>();
            mNavigationErrorListener = new ArrayList<>();
        }

        /**
         * Sets a container id.
         *
         * @param containerId container id for fragments
         * @return this object
         */
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
         * Adds a transition listener. This listener is called after a screen transition.
         *
         * @param transitionListener transition listener.
         * @return this object
         */
        public Builder addTransitionListener(TransitionListener transitionListener) {
            mTransitionListener.add(transitionListener);
            return this;
        }

        /**
         * Adds a dialog showing listener. This listener is called after a dialog has been shown.
         *
         * @param dialogShowingListener dialog showing listener.
         * @return this object
         */
        public Builder addDialogShowingListener(DialogShowingListener dialogShowingListener) {
            mDialogShowingListener.add(dialogShowingListener);
            return this;
        }

        /**
         * Adds a screen switching listener listener. This listener is called after a screen has been switched using {@link ScreenSwitcher}.
         *
         * @param screenSwitchingListener screen switcher listener.
         * @return this object
         */
        public Builder addScreenSwitchingListener(ScreenSwitchingListener screenSwitchingListener) {
            mScreenSwitchingListener.add(screenSwitchingListener);
            return this;
        }

        /**
         * Adds a navigation error listener. This listener is called when an error has occurred during {@link Command} execution.
         *
         * @param navigationErrorListener navigation error listener. By default a listener that wraps errors to {@code RuntimeException} and throws it is used.
         * @return this object
         */
        public Builder addNavigationErrorListener(NavigationErrorListener navigationErrorListener) {
            mNavigationErrorListener.add(navigationErrorListener);
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
