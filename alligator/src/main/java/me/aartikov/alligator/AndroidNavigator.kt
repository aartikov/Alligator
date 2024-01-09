package me.aartikov.alligator

import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import me.aartikov.alligator.animations.AnimationData
import me.aartikov.alligator.commands.BackCommand
import me.aartikov.alligator.commands.BackToClassCommand
import me.aartikov.alligator.commands.BackToScreenCommand
import me.aartikov.alligator.commands.Command
import me.aartikov.alligator.commands.FinishCommand
import me.aartikov.alligator.commands.ForwardCommand
import me.aartikov.alligator.commands.ReplaceCommand
import me.aartikov.alligator.commands.ResetCommand
import me.aartikov.alligator.commands.SwitchToCommand
import me.aartikov.alligator.exceptions.NavigationException
import me.aartikov.alligator.navigationfactories.NavigationFactory
import java.util.LinkedList
import java.util.Queue

/**
 * Main library object. It translates calls of navigation methods to commands and puts them to a command queue.
 * Commands can be executed if a [NavigationContext] is bound, otherwise command execution will be delayed until a [NavigationContext] will be bound.
 */
class AndroidNavigator(val navigationFactory: NavigationFactory) : NavigationContextBinder, Navigator {

    override var navigationContext: NavigationContext? = null
        private set

    private val commandQueue: Queue<Command> = LinkedList()
    private var isExecutingCommands = false

    val screenResolver: ScreenResolver = ScreenResolver(navigationFactory)
    val activityResultHandler: ActivityResultHandler = ActivityResultHandler(navigationFactory)

    override val isBound: Boolean
        get() = navigationContext != null

    override fun bind(navigationContext: NavigationContext) {
        checkThatMainThread()
        if (this.navigationContext != null
            && this.navigationContext!!.activity !== navigationContext.activity
        ) {
            return
        }

        this.navigationContext = navigationContext
        activityResultHandler.setScreenResultListener(navigationContext.screenResultListener)
        executeQueuedCommands()
    }

    override fun unbind(activity: AppCompatActivity) {
        checkThatMainThread()
        if (navigationContext != null && navigationContext!!.activity !== activity) {
            return
        }

        activityResultHandler.resetScreenResultListener()
        navigationContext = null
    }

    override fun canExecuteCommandImmediately(): Boolean {
        return commandQueue.isEmpty() && navigationContext != null
    }

    override fun hasPendingCommands(): Boolean {
        return !commandQueue.isEmpty()
    }

    /**
     * Adds a new screen and goes to it. Implemented with [ForwardCommand].
     *
     * @param screen new screen
     */
    override fun goForward(screen: Screen) {
        goForward(screen, null)
    }

    override fun goForward(screen: Screen, animationData: AnimationData?) {
        executeCommand(ForwardCommand(screen, animationData))
    }

    /**
     * Finishes a current screen and goes back to the previous screen. Implemented with [BackCommand].
     */
    override fun goBack() {
        goBack(null)
    }

    override fun goBack(animationData: AnimationData?) {
        executeCommand(BackCommand(null, animationData))
    }

    /**
     * Finishes a current screen and goes back to the previous screen with result. Implemented with [BackCommand].
     */
    override fun goBackWithResult(screenResult: ScreenResult) {
        goBackWithResult(screenResult, null)
    }

    override fun goBackWithResult(screenResult: ScreenResult, animationData: AnimationData?) {
        executeCommand(BackCommand(screenResult, animationData))
    }

    /**
     * Goes back to a screen with the given class. Implemented with [BackToClassCommand].
     *
     * @param screenClass screen class for going back
     */
    override fun goBackTo(screenClass: Class<out Screen>) {
        goBackTo(screenClass, null)
    }

    /**
     * Goes back to a screen with the given screen object. Implemented with [BackToScreenCommand].
     *
     * @param screen screen for going back
     */
    override fun goBackTo(screen: Screen) {
        goBackTo(screen, null)
    }

    override fun goBackTo(screenClass: Class<out Screen>, animationData: AnimationData?) {
        executeCommand(BackToClassCommand(screenClass, null, animationData))
    }

    override fun goBackTo(screen: Screen, animationData: AnimationData?) {
        executeCommand(BackToScreenCommand(screen, null, animationData))
    }

    /**
     * Goes back to a screen with the given class and returns result to it. Implemented with [BackToClassCommand].
     *
     * @param screenClass  screen class for going back
     * @param screenResult screen result that will be returned
     */
    override fun goBackToWithResult(screenClass: Class<out Screen>, screenResult: ScreenResult) {
        goBackToWithResult(screenClass, screenResult, null)
    }

    /**
     * Goes back to a screen with the given object screen and returns result to it. Implemented with [BackToScreenCommand].
     *
     * @param screen       screen class for going back
     * @param screenResult screen result that will be returned
     */
    override fun goBackToWithResult(screen: Screen, screenResult: ScreenResult) {
        goBackToWithResult(screen, screenResult, null)
    }

    override fun goBackToWithResult(
        screenClass: Class<out Screen>,
        screenResult: ScreenResult,
        animationData: AnimationData?
    ) {
        executeCommand(BackToClassCommand(screenClass, screenResult, animationData))
    }

    override fun goBackToWithResult(
        screen: Screen,
        screenResult: ScreenResult,
        animationData: AnimationData?
    ) {
        executeCommand(BackToScreenCommand(screen, screenResult, animationData))
    }

    /**
     * Replaces the last screen with a new screen. Implemented with [ReplaceCommand].
     *
     * @param screen new screen
     */
    override fun replace(screen: Screen) {
        replace(screen, null)
    }

    override fun replace(screen: Screen, animationData: AnimationData?) {
        executeCommand(ReplaceCommand(screen, animationData))
    }

    /**
     * Removes all other screens and adds a new screen. Implemented with [ResetCommand].
     *
     * @param screen new screen
     */
    override fun reset(screen: Screen) {
        reset(screen, null)
    }

    override fun reset(screen: Screen, animationData: AnimationData?) {
        executeCommand(ResetCommand(screen, animationData))
    }

    /**
     * Finishes a current flow or a current top-level screen (represented by activity). Implemented with [FinishCommand].
     */
    override fun finish() {
        finish(null)
    }

    override fun finish(animationData: AnimationData?) {
        executeCommand(FinishCommand(null, false, animationData))
    }

    /**
     * Finishes a current flow or a current top-level screen (represented by activity) with result. Implemented with [FinishCommand].
     *
     * @param screenResult screen result that will be returned
     */
    override fun finishWithResult(screenResult: ScreenResult) {
        finishWithResult(screenResult, null)
    }

    override fun finishWithResult(screenResult: ScreenResult, animationData: AnimationData?) {
        executeCommand(FinishCommand(screenResult, false, animationData))
    }

    /**
     * Finishes a current top-level screen (represented by activity). Implemented with [FinishCommand].
     */
    override fun finishTopLevel() {
        finishTopLevel(null)
    }

    override fun finishTopLevel(animationData: AnimationData?) {
        executeCommand(FinishCommand(null, true, animationData))
    }

    /**
     * Finishes a current top-level screen (represented by activity) with result. Implemented with [FinishCommand].
     *
     * @param screenResult screen result that will be returned
     */
    override fun finishTopLevelWithResult(screenResult: ScreenResult) {
        finishTopLevelWithResult(screenResult, null)
    }

    override fun finishTopLevelWithResult(
        screenResult: ScreenResult,
        animationData: AnimationData?
    ) {
        executeCommand(FinishCommand(screenResult, true, animationData))
    }

    /**
     * Switches screen with a screen switcher. Implemented with [SwitchToCommand].
     *
     * @param screen screen
     */
    override fun switchTo(screen: Screen) {
        switchTo(screen, null)
    }

    override fun switchTo(screen: Screen, animationData: AnimationData?) {
        executeCommand(SwitchToCommand(screen, animationData))
    }

    private fun executeCommand(command: Command) {
        checkThatMainThread()
        commandQueue.add(command)
        executeQueuedCommands()
    }

    private fun executeQueuedCommands() {
        if (isExecutingCommands) {
            return
        }
        isExecutingCommands = true
        try {
            while (navigationContext != null && !commandQueue.isEmpty()) {
                val command = commandQueue.remove()
                val canExecuteCommands = command.execute(navigationContext!!)
                if (!canExecuteCommands) {
                    navigationContext = null
                }
            }
        } catch (e: NavigationException) {
            commandQueue.clear()
            navigationContext!!.navigationErrorListener.onNavigationError(e)
        } catch (e: Exception) {
            commandQueue.clear()
            throw e
        } finally {
            isExecutingCommands = false
        }
    }

    private fun checkThatMainThread() {
        if (Thread.currentThread() !== Looper.getMainLooper().thread) {
            throw RuntimeException("Can only be called from the main thread.")
        }
    }
}