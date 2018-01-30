package me.aartikov.alligator.commands;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.screenswitchers.ScreenSwitcher;

/**
 * Date: 29.12.2016
 * Time: 11:24
 *
 * @author Artur Artikov
 */

/**
 * Command implementation for {@code switchTo} method of {@link me.aartikov.alligator.AndroidNavigator}.
 */
public class SwitchToCommand implements Command {
    private Screen mScreen;
    private AnimationData mAnimationData;
    private boolean discardIfNotImmediate;

    public SwitchToCommand(Screen screen, AnimationData animationData, boolean discardIfNotImmediate) {
        mScreen = screen;
        mAnimationData = animationData;
        this.discardIfNotImmediate = discardIfNotImmediate;
    }

    @Override
    public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
        ScreenSwitcher screenSwitcher = navigationContext.getScreenSwitcher();
        if (screenSwitcher == null) {
            throw new NavigationException("ScreenSwitcher is not set.");
        }

        Screen previousScreen = screenSwitcher.getCurrentScreen();
        if (previousScreen != null && previousScreen.equals(mScreen)) {
            return true;
        }

        screenSwitcher.switchTo(mScreen, mAnimationData);
        navigationContext.getScreenSwitchingListener().onScreenSwitched(previousScreen, mScreen);
        return true;
    }

    @Override
    public boolean discardIfNotImmediate() {
        return discardIfNotImmediate;
    }
}
