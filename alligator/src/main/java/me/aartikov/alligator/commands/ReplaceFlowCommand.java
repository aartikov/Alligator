package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.exceptions.MissingFlowManagerException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.flowmanagers.FlowManager;
import me.aartikov.alligator.navigationfactories.NavigationFactory;

/**
 * Date: 22.07.2019
 * Time: 14:53
 *
 * @author Terenfear
 */

/**
 * Command implementation for {@code replaceFlow} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class ReplaceFlowCommand implements Command {
    private Screen mScreen;
    @Nullable
    private AnimationData mAnimationData;

    public ReplaceFlowCommand(@NonNull Screen screen, @Nullable AnimationData animationData) {
        mScreen = screen;
        mAnimationData = animationData;
    }

    @Override
    public boolean execute(@NonNull NavigationContext navigationContext,
                           @NonNull NavigationFactory navigationFactory) throws NavigationException {
        FlowManager flowManager = navigationContext.getFlowManager();
        if (flowManager == null) {
            throw new MissingFlowManagerException("FlowManager is not set.");
        }

        return flowManager.replace(mScreen, navigationContext.getFlowTransitionListener(), mAnimationData);
    }
}
