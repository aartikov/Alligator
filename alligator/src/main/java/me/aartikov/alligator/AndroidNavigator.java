package me.aartikov.alligator;

import java.util.LinkedList;
import java.util.Queue;

import android.os.Looper;

import me.aartikov.alligator.commands.BackCommand;
import me.aartikov.alligator.commands.BackToCommand;
import me.aartikov.alligator.commands.FinishCommand;
import me.aartikov.alligator.commands.ForwardCommand;
import me.aartikov.alligator.commands.ReplaceCommand;
import me.aartikov.alligator.commands.ResetCommand;
import me.aartikov.alligator.commands.SwitchToCommand;
import me.aartikov.alligator.exceptions.CommandExecutionException;

/**
 * Date: 29.12.2016
 * Time: 10:13
 *
 * @author Artur Artikov
 */

/**
 * Main library object. It translates calls of navigation methods to commands and puts it to a command queue.
 * Commands can be executed if a {@link NavigationContext} is bound, otherwise command execution will be delayed until a {@link NavigationContext} will be bound.
 */
public class AndroidNavigator implements NavigationContextBinder, Navigator {
	private NavigationFactory mNavigationFactory;
	private NavigationContext mNavigationContext;
	private Queue<Command> mCommandQueue = new LinkedList<>();
	private boolean mIsExecutingCommands;
	private ScreenResolver mScreenResolver;
	private ScreenResultResolver mScreenResultResolver;

	public AndroidNavigator(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
		mScreenResolver = new ScreenResolver(navigationFactory);
		mScreenResultResolver = new ScreenResultResolver(navigationFactory);
	}

	public NavigationFactory getNavigationFactory() {
		return mNavigationFactory;
	}

	public ScreenResolver getScreenResolver() {
		return mScreenResolver;
	}

	public ScreenResultResolver getScreenResultResolver() {
		return mScreenResultResolver;
	}

	@Override
	public void bind(NavigationContext navigationContext) {
		checkThatMainThread();
		mNavigationContext = navigationContext;
		executeQueuedCommands();
	}

	@Override
	public void unbind() {
		checkThatMainThread();
		mNavigationContext = null;
	}

	/**
	 * Adds a new screen and goes to it. Implemented with {@link ForwardCommand}.
	 *
	 * @param screen new screen
	 */
	@Override
	public void goForward(Screen screen) {
		goForward(screen, null);
	}

	@Override
	public void goForward(Screen screen, AnimationData animationData) {
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
	public void goBack(AnimationData animationData) {
		executeCommand(new BackCommand(animationData));
	}

	/**
	 * Goes back to a screen with the given class. Implemented with {@link BackToCommand}.
	 *
	 * @param screenClass screen class for going back
	 */
	@Override
	public void goBackTo(Class<? extends Screen> screenClass) {
		goBackTo(screenClass, null);
	}

	@Override
	public void goBackTo(Class<? extends Screen> screenClass, AnimationData animationData) {
		executeCommand(new BackToCommand(screenClass, animationData));
	}

	/**
	 * Replaces the last screen with a new screen. Implemented with {@link ReplaceCommand}.
	 *
	 * @param screen new screen
	 */
	@Override
	public void replace(Screen screen) {
		replace(screen, null);
	}

	@Override
	public void replace(Screen screen, AnimationData animationData) {
		executeCommand(new ReplaceCommand(screen, animationData));
	}

	/**
	 * Removes all other screens and adds a new screen. Implemented with {@link ResetCommand}.
	 *
	 * @param screen new screen
	 */
	@Override
	public void reset(Screen screen) {
		reset(screen, null);
	}

	@Override
	public void reset(Screen screen, AnimationData animationData) {
		executeCommand(new ResetCommand(screen, animationData));
	}

	/**
	 * Finishes a current activity. Implemented with {@link FinishCommand}.
	 */
	@Override
	public void finish() {
		finish(null);
	}

	@Override
	public void finish(AnimationData animationData) {
		executeCommand(new FinishCommand(null, animationData));
	}

	/**
	 * Finishes a current activity with result. Implemented with {@link FinishCommand}.
	 * <p>
	 * A screen result can be handled in {@code onActivityResult} method of a previous activity with {@link ScreenResultResolver}.
	 *
	 * @param screenResult screen result that will be returned
	 */
	@Override
	public void finishWithResult(ScreenResult screenResult) {
		finishWithResult(screenResult, null);
	}

	@Override
	public void finishWithResult(ScreenResult screenResult, AnimationData animationData) {
		executeCommand(new FinishCommand(screenResult, animationData));
	}

	/**
	 * Switches screens by a name with a screen switcher. Implemented with {@link SwitchToCommand}.
	 *
	 * @param screenName screen name
	 */
	@Override
	public void switchTo(String screenName) {
		executeCommand(new SwitchToCommand(screenName));
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
				boolean canExecuteCommands = command.execute(mNavigationContext, mNavigationFactory);
				if (!canExecuteCommands) {
					mNavigationContext = null;
				}
			}
		} catch (CommandExecutionException e) {
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