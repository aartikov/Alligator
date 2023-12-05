package me.aartikov.alligator

import me.aartikov.alligator.animations.AnimationData

/**
 * Interface with navigation methods.
 */
interface Navigator {
    /**
     * Returns if a navigator can execute a command immediately
     *
     * @return true if a navigator can execute a command immediately
     */
    fun canExecuteCommandImmediately(): Boolean

    /**
     * Returns if a navigator has pending commands
     *
     * @return true if a navigator has pending commands
     */
    fun hasPendingCommands(): Boolean

    /**
     * Adds a new screen and goes to it.
     *
     * @param screen new screen
     */
    fun goForward(screen: Screen)

    /**
     * `goForward` with an animation data.
     *
     * @param screen        new screen
     * @param animationData animation data for an additional animation configuring
     */
    fun goForward(screen: Screen, animationData: AnimationData?)

    /**
     * Finishes a current screen and goes back to the previous screen.
     */
    fun goBack()

    /**
     * `goBack` with an animation data.
     *
     * @param animationData animation data for an additional animation configuring
     */
    fun goBack(animationData: AnimationData?)

    /**
     * Finishes a current screen and goes back to the previous screen with result.
     *
     * @param screenResult screen result that will be returned
     */
    fun goBackWithResult(screenResult: ScreenResult)

    /**
     * `goBackWithResult` with an animation data.
     *
     * @param screenResult  screen result that will be returned
     * @param animationData animation data for an additional animation configuring
     */
    fun goBackWithResult(screenResult: ScreenResult, animationData: AnimationData?)

    /**
     * Goes back to a screen with the given class.
     *
     * @param screenClass screen class for going back
     */
    fun goBackTo(screenClass: Class<out Screen?>)

    /**
     * Goes back to a screen with the given screen object.
     *
     * @param screen screen class for going back
     */
    fun goBackTo(screen: Screen)

    /**
     * `goBackTo` with an animation data.
     *
     * @param screenClass   screen class for going back
     * @param animationData animation data for an additional animation configuring
     */
    fun goBackTo(screenClass: Class<out Screen?>, animationData: AnimationData?)

    /**
     * `goBackTo` with an animation data.
     *
     * @param screen        screen class for going back
     * @param animationData animation data for an additional animation configuring
     */
    fun goBackTo(screen: Screen, animationData: AnimationData?)

    /**
     * Goes back to a screen with the given class and returns result to it.
     *
     * @param screenClass  screen class for going back
     * @param screenResult screen result that will be returned
     */
    fun goBackToWithResult(screenClass: Class<out Screen?>, screenResult: ScreenResult)

    /**
     * Goes back to a screen with the given class and returns result to it.
     *
     * @param screen       screen object for going back
     * @param screenResult screen result that will be returned
     */
    fun goBackToWithResult(screen: Screen, screenResult: ScreenResult)

    /**
     * `goBackToWithResult` with an animation data.
     *
     * @param screenClass   screen class for going back
     * @param screenResult  screen result that will be returned
     * @param animationData animation data for an additional animation configuring
     */
    fun goBackToWithResult(
        screenClass: Class<out Screen?>,
        screenResult: ScreenResult,
        animationData: AnimationData?
    )

    /**
     * `goBackToWithResult` with an animation data.
     *
     * @param screen        screen object for going back
     * @param screenResult  screen result that will be returned
     * @param animationData animation data for an additional animation configuring
     */
    fun goBackToWithResult(
        screen: Screen,
        screenResult: ScreenResult,
        animationData: AnimationData?
    )

    /**
     * Replaces the last screen with a new screen.
     *
     * @param screen new screen
     */
    fun replace(screen: Screen)

    /**
     * `replace` with an animation data.
     *
     * @param screen        new screen
     * @param animationData animation data for an additional animation configuring
     */
    fun replace(screen: Screen, animationData: AnimationData?)

    /**
     * Removes all other screens and adds a new screen.
     *
     * @param screen new screen
     */
    fun reset(screen: Screen)

    /**
     * `reset` with an animation data.
     *
     * @param screen        new screen
     * @param animationData animation data for an additional animation configuring
     */
    fun reset(screen: Screen, animationData: AnimationData?)

    /**
     * Finishes a current flow or a current top-level screen.
     */
    fun finish()

    /**
     * `finish` with an animation data.
     *
     * @param animationData animation data for an additional animation configuring
     */
    fun finish(animationData: AnimationData?)

    /**
     * Finishes a current flow or a current top-level screen and returns a screen result.
     *
     * @param screenResult screen result that will be returned
     */
    fun finishWithResult(screenResult: ScreenResult)

    /**
     * `finishWithResult` with an animation data.
     *
     * @param screenResult  screen result that will be returned
     * @param animationData animation data for an additional animation configuring
     */
    fun finishWithResult(screenResult: ScreenResult, animationData: AnimationData?)

    /**
     * Finishes a current top-level screen.
     */
    fun finishTopLevel()

    /**
     * `finishTopLevel` with an animation data.
     *
     * @param animationData animation data for an additional animation configuring
     */
    fun finishTopLevel(animationData: AnimationData?)

    /**
     * Finishes a current top-level screen and returns a screen result.
     *
     * @param screenResult screen result that will be returned
     */
    fun finishTopLevelWithResult(screenResult: ScreenResult)

    /**
     * `finishTopLevelWithResult` with an animation data.
     *
     * @param screenResult  screen result that will be returned
     * @param animationData animation data for an additional animation configuring
     */
    fun finishTopLevelWithResult(screenResult: ScreenResult, animationData: AnimationData?)

    /**
     * Switches screens.
     *
     * @param screen screen for switching to
     */
    fun switchTo(screen: Screen)

    /**
     * `switchTo` with an animation data.
     *
     * @param screen        screen for switching to
     * @param animationData animation data for an additional animation configuring
     */
    fun switchTo(screen: Screen, animationData: AnimationData?)
}