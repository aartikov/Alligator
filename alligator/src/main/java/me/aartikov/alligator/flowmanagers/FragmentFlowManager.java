package me.aartikov.alligator.flowmanagers;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.TransitionType;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.TransitionAnimation;
import me.aartikov.alligator.animations.providers.DefaultTransitionAnimationProvider;
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.ScreenNotFoundException;
import me.aartikov.alligator.exceptions.ScreenRegistrationException;
import me.aartikov.alligator.helpers.FragmentStack;
import me.aartikov.alligator.helpers.ScreenResultHelper;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.listeners.TransitionListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;

/**
 * Date: 22.07.2019
 * Time: 14:22
 *
 * @author Terenfear
 */

/**
 * Implementation of {@link FlowManager} that uses fragments as flow screens and inner screens
 */
public class FragmentFlowManager implements FlowManager {

    @NonNull
    private NavigationFactory mNavigationFactory;
    @NonNull
    private FragmentStack mFragmentStack;
    @NonNull
    private TransitionAnimationProvider mTransitionAnimationProvider;

    /**
     * @param navigationFactory           navigation factory that's used to create flow fragments
     * @param fragmentManager             top-level fragment manager (usually from an activity) used for flow fragment transitions
     * @param containerId                 id of a flow container where inner fragments will be added
     * @param transitionAnimationProvider animation provider
     */
    public FragmentFlowManager(@NonNull NavigationFactory navigationFactory,
                               @NonNull FragmentManager fragmentManager,
                               int containerId,
                               @Nullable TransitionAnimationProvider transitionAnimationProvider) {
        mNavigationFactory = navigationFactory;
        mTransitionAnimationProvider = transitionAnimationProvider != null ?
                transitionAnimationProvider :
                new DefaultTransitionAnimationProvider();
        mFragmentStack = new FragmentStack(fragmentManager, containerId);
    }

    /**
     * @param navigationFactory navigation factory that's used to create flow fragments
     * @param fragmentManager   top-level fragment manager (usually from an activity) used for flow fragment transitions
     * @param containerId       id of a flow container where inner fragments will be added
     */
    public FragmentFlowManager(@NonNull NavigationFactory navigationFactory,
                               @NonNull FragmentManager fragmentManager,
                               int containerId) {
        this(navigationFactory, fragmentManager, containerId, null);
    }


    /**
     * Returns a top-level fragment stack that holds flow fragments
     */
    @NonNull
    public FragmentStack getFragmentStack() {
        return mFragmentStack;
    }


    /**
     * Returns a current flow fragment.
     *
     * @return current fragment in the container, or {@code null} if there are no fragments in the container
     */
    @Nullable
    public Fragment getCurrentFragment() {
        return mFragmentStack.getCurrentFragment();
    }

    @Override
    public boolean back(@NonNull TransitionListener transitionListener,
                        @NonNull ScreenResultHelper screenResultHelper,
                        @NonNull ScreenResultListener screenResultListener,
                        @Nullable ScreenResult screenResult,
                        @Nullable AnimationData animationData) throws NavigationException {
        List<Fragment> fragments = mFragmentStack.getFragments();
        Fragment currentFragment = fragments.get(fragments.size() - 1);
        Fragment previousFragment = fragments.get(fragments.size() - 2);

        Class<? extends Screen> screenClassFrom = mNavigationFactory.getScreenClass(currentFragment);
        Class<? extends Screen> screenClassTo = mNavigationFactory.getScreenClass(previousFragment);
        TransitionAnimation animation = TransitionAnimation.DEFAULT;
        if (screenClassFrom != null && screenClassTo != null) {
            animation = mTransitionAnimationProvider.getAnimation(TransitionType.BACK,
                    screenClassFrom, screenClassTo, false, animationData);
        }

        mFragmentStack.pop(animation);
        transitionListener.onScreenTransition(TransitionType.BACK,
                screenClassFrom, screenClassTo, false);
        screenResultHelper.callScreenResultListener(currentFragment,
                screenResult, screenResultListener, mNavigationFactory);
        return true;
    }

    @Override
    public boolean backTo(@NonNull Class<? extends Screen> screenClassTo,
                          @NonNull TransitionListener transitionListener,
                          @NonNull ScreenResultHelper screenResultHelper,
                          @NonNull ScreenResultListener screenResultListener,
                          @Nullable ScreenResult screenResult,
                          @Nullable AnimationData animationData) throws NavigationException {
        ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screenClassTo);
        if (screenImplementation instanceof FragmentScreenImplementation) {
            List<Fragment> fragments = mFragmentStack.getFragments();
            Fragment requiredFragment = null;
            boolean toPrevious = false;
            for (int i = fragments.size() - 1; i >= 0; i--) {
                if (screenClassTo == mNavigationFactory.getScreenClass(fragments.get(i))) {
                    requiredFragment = fragments.get(i);
                    toPrevious = i == fragments.size() - 2;
                    break;
                }
            }

            if (requiredFragment == null) {
                throw new ScreenNotFoundException(screenClassTo);
            }

            Fragment currentFragment = fragments.get(fragments.size() - 1);
            Class<? extends Screen> screenClassFrom = mNavigationFactory.getScreenClass(currentFragment);
            TransitionAnimation animation = TransitionAnimation.DEFAULT;
            if (screenClassFrom != null) {
                animation = mTransitionAnimationProvider.getAnimation(TransitionType.BACK,
                        screenClassFrom, screenClassTo, false, animationData);
            }

            mFragmentStack.popUntil(requiredFragment, animation);
            transitionListener.onScreenTransition(TransitionType.BACK,
                    screenClassFrom, screenClassTo, false);
            if (screenResult != null || toPrevious) {
                screenResultHelper.callScreenResultListener(currentFragment,
                        screenResult, screenResultListener, mNavigationFactory);
            }
        } else {
            throw new ScreenRegistrationException("Screen " + screenClassTo.getSimpleName()
                    + " is not represented by a fragment.");
        }
        return true;
    }

    @Override
    public boolean add(@NonNull Screen screen,
                       @NonNull TransitionListener transitionListener,
                       @Nullable AnimationData animationData) throws NavigationException {
        ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screen.getClass());
        if (screenImplementation instanceof FragmentScreenImplementation) {
            Fragment fragment = ((FragmentScreenImplementation) screenImplementation).createFragment(screen);
            if (fragment instanceof DialogFragment) {
                throw new ScreenRegistrationException("DialogFragment is used as a flow Fragment.");
            }

            Fragment currentFragment = mFragmentStack.getCurrentFragment();
            Class<? extends Screen> screenClassFrom = currentFragment == null ?
                    null :
                    mNavigationFactory.getScreenClass(currentFragment);
            Class<? extends Screen> screenClassTo = screen.getClass();
            TransitionAnimation animation = TransitionAnimation.DEFAULT;
            if (screenClassFrom != null) {
                animation = mTransitionAnimationProvider.getAnimation(TransitionType.FORWARD,
                        screenClassFrom, screenClassTo, false, animationData);
            }

            mFragmentStack.push(fragment, animation);
            transitionListener.onScreenTransition(TransitionType.FORWARD,
                    screenClassFrom, screenClassTo, false);
        } else {
            throw new ScreenRegistrationException("Screen " + screen.getClass().getSimpleName()
                    + " is not represented by a fragment.");
        }
        return true;
    }

    @Override
    public boolean replace(@NonNull Screen screen,
                           @NonNull TransitionListener transitionListener,
                           @Nullable AnimationData animationData) throws NavigationException {
        ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screen.getClass());
        if (screenImplementation instanceof FragmentScreenImplementation) {
            Fragment fragment = ((FragmentScreenImplementation) screenImplementation).createFragment(screen);
            if (fragment instanceof DialogFragment) {
                throw new ScreenRegistrationException("DialogFragment is used as a flow Fragment.");
            }

            Fragment currentFragment = mFragmentStack.getCurrentFragment();
            Class<? extends Screen> screenClassFrom = currentFragment == null ?
                    null :
                    mNavigationFactory.getScreenClass(currentFragment);
            Class<? extends Screen> screenClassTo = screen.getClass();
            TransitionAnimation animation = TransitionAnimation.DEFAULT;
            if (screenClassFrom != null) {
                animation = mTransitionAnimationProvider.getAnimation(TransitionType.REPLACE,
                        screenClassFrom, screenClassTo, false, animationData);
            }

            mFragmentStack.replace(fragment, animation);
            transitionListener.onScreenTransition(TransitionType.REPLACE,
                    screenClassFrom, screenClassTo, false);
        } else {
            throw new ScreenRegistrationException("Screen " + screen.getClass().getSimpleName() +
                    " is not represented by a fragment.");
        }
        return true;
    }

    @Override
    public boolean reset(@NonNull Screen screen,
                         @NonNull TransitionListener transitionListener,
                         @Nullable AnimationData animationData) throws NavigationException {
        ScreenImplementation screenImplementation = mNavigationFactory.getScreenImplementation(screen.getClass());
        if (screenImplementation instanceof FragmentScreenImplementation) {
            Fragment fragment = ((FragmentScreenImplementation) screenImplementation).createFragment(screen);
            if (fragment instanceof DialogFragment) {
                throw new ScreenRegistrationException("DialogFragment is used as a flow Fragment.");
            }

            Fragment currentFragment = mFragmentStack.getCurrentFragment();
            Class<? extends Screen> screenClassFrom = currentFragment == null ?
                    null :
                    mNavigationFactory.getScreenClass(currentFragment);
            Class<? extends Screen> screenClassTo = screen.getClass();
            TransitionAnimation animation = TransitionAnimation.DEFAULT;
            if (screenClassFrom != null) {
                animation = mTransitionAnimationProvider.getAnimation(TransitionType.RESET,
                        screenClassFrom, screenClassTo, false, animationData);
            }

            mFragmentStack.reset(fragment, animation);
            transitionListener.onScreenTransition(TransitionType.RESET,
                    screenClassFrom, screenClassTo, false);
        } else {
            throw new ScreenRegistrationException("Screen " + screen.getClass().getSimpleName() +
                    " is not represented by a fragment.");
        }
        return true;
    }
}
