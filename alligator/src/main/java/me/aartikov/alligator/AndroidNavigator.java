package me.aartikov.alligator;

import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Queue;

import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.commands.BackCommand;
import me.aartikov.alligator.commands.BackToClassCommand;
import me.aartikov.alligator.commands.BackToScreenCommand;
import me.aartikov.alligator.commands.Command;
import me.aartikov.alligator.commands.FinishCommand;
import me.aartikov.alligator.commands.ForwardCommand;
import me.aartikov.alligator.commands.ReplaceCommand;
import me.aartikov.alligator.commands.ResetCommand;
import me.aartikov.alligator.commands.SwitchToCommand;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.navigationfactories.NavigationFactory;


/**
 * Main library object. It translates calls of navigation methods to commands and puts them to a command queue.
 * Commands can be executed if a {@link NavigationContext} is bound, otherwise command execution will be delayed until a {@link NavigationContext} will be bound.
 */
public class AndroidNavigator implements NavigationContextBinder, Navigator {
    private NavigationFactory mNavigationFactory;
    private NavigationContext mNavigationContext;
    private Queue<Command> mCommandQueue = new LinkedList<>();
    private boolean mIsExecutingCommands;
    private ScreenResolver mScreenResolver;
    private ActivityResultHandler mActivityResultHandler;

    public AndroidNavigator(NavigationFactory navigationFactory) {
        mNavigationFactory = navigationFactory;
        mScreenResolver = new ScreenResolver(navigationFactory);
        mActivityResultHandler = new ActivityResultHandler(navigationFactory);
    }

    public NavigationFactory getNavigationFactory() {
        return mNavigationFactory;
    }

    public ScreenResolver getScreenResolver() {
        return mScreenResolver;
    }

    public ActivityResultHandler getActivityResultHandler() {
        return mActivityResultHandler;
    }

    @Nullable
    @Override
    public NavigationContext getNavigationContext() {
        return mNavigationContext;
    }

    @Override
    public boolean isBound() {
        return mNavigationContext != null;
    }

    @Override
    public void bind(@NonNull NavigationContext navigationContext) {
        checkThatMainThread();
        if (mNavigationContext != null && mNavigationContext.getActivity() != navigationContext.getActivity()) {
            return;
        }
        mNavigationContext = navigationContext;
        mActivityResultHandler.setScreenResultListener(mNavigationContext.getScreenResultListener());
        executeQueuedCommands();
    }

    @Override
    public void unbind(AppCompatActivity activity) {
        checkThatMainThread();
        if (mNavigationContext != null && mNavigationContext.getActivity() != activity) {
            return;
        }
        mActivityResultHandler.resetScreenResultListener();
        mNavigationContext = null;
    }

    @Override
    public boolean canExecuteCommandImmediately() {
        return mCommandQueue.isEmpty() && mNavigationContext != null;
    }

    @Override
    public boolean hasPendingCommands() {
        return !mCommandQueue.isEmpty();
    }

    /**
     * Adds a new screen and goes to it. Implemented with {@link ForwardCommand}.
     *
     * @param screen new screen
     */
    @Override
    public void goForward(@NonNull Screen screen) {
        goForward(screen, null);
    }

    @Override
    public void goForward(@NonNull Screen screen, @Nullable AnimationData animationData) {
        executeCommand(new ForwardCommand(screen, animationData));
    }

    /**
     * Finishes a current screen and goes back to the previous screen. Implemented with {@link BackCommand}.
     */
    @Override
    public void goBack() {
        goBack(null);
    }

    @Override
    public void goBack(@Nullable AnimationData animationData) {
        executeCommand(new BackCommand(null, animationData));
    }

    /**
     * Finishes a current screen and goes back to the previous screen with result. Implemented with {@link BackCommand}.
     */
    @Override
    public void goBackWithResult(@NonNull ScreenResult screenResult) {
        goBackWithResult(screenResult, null);
    }

    @Override
    public void goBackWithResult(@NonNull ScreenResult screenResult, @Nullable AnimationData animationData) {
        executeCommand(new BackCommand(screenResult, animationData));
    }

    /**
     * Goes back to a screen with the given class. Implemented with {@link BackToClassCommand}.
     *
     * @param screenClass screen class for going back
     */
    @Override
    public void goBackTo(@NonNull Class<? extends Screen> screenClass) {
        goBackTo(screenClass, null);
    }

    /**
     * Goes back to a screen with the given screen object. Implemented with {@link BackToScreenCommand}.
     *
     * @param screen screen for going back
     */
    @Override
    public void goBackTo(@NonNull Screen screen) {
        goBackTo(screen, null);
    }

    @Override
    public void goBackTo(@NonNull Class<? extends Screen> screenClass, @Nullable AnimationData animationData) {
        executeCommand(new BackToClassCommand(screenClass, null, animationData));
    }

    @Override
    public void goBackTo(@NonNull Screen screen, @Nullable AnimationData animationData) {
        executeCommand(new BackToScreenCommand(screen, null, animationData));
    }

    /**
     * Goes back to a screen with the given class and returns result to it. Implemented with {@link BackToClassCommand}.
     *
     * @param screenClass  screen class for going back
     * @param screenResult screen result that will be returned
     */
    @Override
    public void goBackToWithResult(@NonNull Class<? extends Screen> screenClass, @NonNull ScreenResult screenResult) {
        goBackToWithResult(screenClass, screenResult, null);
    }

    /**
     * Goes back to a screen with the given object screen and returns result to it. Implemented with {@link BackToScreenCommand}.
     *
     * @param screen  screen class for going back
     * @param screenResult screen result that will be returned
     */
    @Override
    public void goBackToWithResult(@NonNull Screen screen, @NonNull ScreenResult screenResult) {
        goBackToWithResult(screen, screenResult, null);
    }

    @Override
    public void goBackToWithResult(@NonNull Class<? extends Screen> screenClass, @NonNull ScreenResult screenResult, @Nullable AnimationData animationData) {
        executeCommand(new BackToClassCommand(screenClass, screenResult, animationData));
    }

    @Override
    public void goBackToWithResult(@NonNull Screen screen, @NonNull ScreenResult screenResult, @Nullable AnimationData animationData) {
        executeCommand(new BackToScreenCommand(screen, screenResult, animationData));
    }

    /**
     * Replaces the last screen with a new screen. Implemented with {@link ReplaceCommand}.
     *
     * @param screen new screen
     */
    @Override
    public void replace(@NonNull Screen screen) {
        replace(screen, null);
    }

    @Override
    public void replace(@NonNull Screen screen, @Nullable AnimationData animationData) {
        executeCommand(new ReplaceCommand(screen, animationData));
    }

    /**
     * Removes all other screens and adds a new screen. Implemented with {@link ResetCommand}.
     *
     * @param screen new screen
     */
    @Override
    public void reset(@NonNull Screen screen) {
        reset(screen, null);
    }

    @Override
    public void reset(@NonNull Screen screen, @Nullable AnimationData animationData) {
        executeCommand(new ResetCommand(screen, animationData));
    }

    /**
     * Finishes a current flow or a current top-level screen (represented by activity). Implemented with {@link FinishCommand}.
     */
    @Override
    public void finish() {
        finish(null);
    }

    @Override
    public void finish(@Nullable AnimationData animationData) {
        executeCommand(new FinishCommand(null, false, animationData));
    }

    /**
     * Finishes a current flow or a current top-level screen (represented by activity) with result. Implemented with {@link FinishCommand}.
     *
     * @param screenResult screen result that will be returned
     */
    @Override
    public void finishWithResult(@NonNull ScreenResult screenResult) {
        finishWithResult(screenResult, null);
    }

    @Override
    public void finishWithResult(@NonNull ScreenResult screenResult, @Nullable AnimationData animationData) {
        executeCommand(new FinishCommand(screenResult, false, animationData));
    }

    /**
     * Finishes a current top-level screen (represented by activity). Implemented with {@link FinishCommand}.
     */
    @Override
    public void finishTopLevel() {
        finishTopLevel(null);
    }

    @Override
    public void finishTopLevel(@Nullable AnimationData animationData) {
        executeCommand(new FinishCommand(null, true, animationData));
    }

    /**
     * Finishes a current top-level screen (represented by activity) with result. Implemented with {@link FinishCommand}.
     *
     * @param screenResult screen result that will be returned
     */
    @Override
    public void finishTopLevelWithResult(@NonNull ScreenResult screenResult) {
        finishTopLevelWithResult(screenResult, null);
    }

    @Override
    public void finishTopLevelWithResult(@NonNull ScreenResult screenResult, @Nullable AnimationData animationData) {
        executeCommand(new FinishCommand(screenResult, true, animationData));
    }

    /**
     * Switches screen with a screen switcher. Implemented with {@link SwitchToCommand}.
     *
     * @param screen screen
     */
    @Override
    public void switchTo(@NonNull Screen screen) {
        switchTo(screen, null);
    }

    @Override
    public void switchTo(@NonNull Screen screen, AnimationData animationData) {
        executeCommand(new SwitchToCommand(screen, animationData));
    }

    protected void executeCommand(Command command) {
        checkThatMainThread();
        mCommandQueue.add(command);
        executeQueuedCommands();
    }

    private void executeQueuedCommands() {
        if (mIsExecutingCommands) {
            return;
        }

        mIsExecutingCommands = true;
        try {
            while (mNavigationContext != null && !mCommandQueue.isEmpty()) {
                Command command = mCommandQueue.remove();
                boolean canExecuteCommands = command.execute(mNavigationContext);
                if (!canExecuteCommands) {
                    mNavigationContext = null;
                }
            }
        } catch (NavigationException e) {
            mCommandQueue.clear();
            mNavigationContext.getNavigationErrorListener().onNavigationError(e);
        } catch (Exception e) {
            mCommandQueue.clear();
            throw e;
        } finally {
            mIsExecutingCommands = false;
        }
    }

    private void checkThatMainThread() {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new RuntimeException("Can only be called from the main thread.");
        }
    }
}