package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.exceptions.MissingFlowManagerException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.flowmanagers.FlowManager;
import me.aartikov.alligator.navigationfactories.NavigationFactory;


/**
 * Command implementation for {@code goBackToFlow} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class BackToFlowCommand implements Command {
    private Class<? extends Screen> mScreenClass;
    @Nullable
    private ScreenResult mScreenResult;
    @Nullable
    private AnimationData mAnimationData;

    public BackToFlowCommand(@NonNull Class<? extends Screen> screenClass,
                             @Nullable ScreenResult screenResult,
                             @Nullable AnimationData animationData) {
        mScreenClass = screenClass;
        mScreenResult = screenResult;
        mAnimationData = animationData;
    }

    @Override
    public boolean execute(@NonNull NavigationContext navigationContext,
                           @NonNull NavigationFactory navigationFactory) throws NavigationException {
        FlowManager flowManager = navigationContext.getFlowManager();
        if (flowManager == null) {
            throw new MissingFlowManagerException("FlowManager is not set.");
        }

        return flowManager.backTo(mScreenClass,
                navigationContext.getFlowTransitionListener(),
                navigationContext.getScreenResultHelper(),
                navigationContext.getScreenResultListener(),
                mScreenResult,
                mAnimationData);
    }
}