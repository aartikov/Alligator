package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.exceptions.MissingFlowManagerException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.flowmanagers.FlowManager;
import me.aartikov.alligator.navigationfactories.NavigationFactory;

/**
 * Date: 22.07.2019
 * Time: 14:48
 *
 * @author Terenfear
 */

/**
 * Command implementation for {@code finishFlow} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class FinishFlowCommand implements Command {
    @Nullable
    private ScreenResult mScreenResult;
    @Nullable
    private AnimationData mAnimationData;

    public FinishFlowCommand(@Nullable ScreenResult screenResult, @Nullable AnimationData animationData) {
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

        return flowManager.back(navigationContext.getFlowTransitionListener(),
                navigationContext.getScreenResultHelper(),
                navigationContext.getScreenResultListener(),
                mScreenResult,
                mAnimationData);
    }
}
