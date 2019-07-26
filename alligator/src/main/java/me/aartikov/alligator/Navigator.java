package me.aartikov.alligator;

/**
 * Date: 11.02.2017
 * Time: 11:25
 *
 * @author Artur Artikov
 */

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.animations.AnimationData;

/**
 * Interface with navigation methods.
 */
public interface Navigator {

	/**
	 * Returns if a navigator can execute a command immediately
	 *
	 * @return true if a navigator can execute a command immediately
	 */
	boolean canExecuteCommandImmediately();

	/**
	 * Returns if a navigator has pending commands
	 *
	 * @return true if a navigator has pending commands
	 */
	boolean hasPendingCommands();

	/**
	 * Adds a new screen and goes to it.
	 *
	 * @param screen new screen
	 */
	void goForward(@NonNull Screen screen);

	/**
	 * {@code goForward} with an animation data.
	 *
	 * @param screen        new screen
	 * @param animationData animation data for an additional animation configuring
	 */
	void goForward(@NonNull Screen screen, @Nullable AnimationData animationData);

	/**
	 * Adds a new flow screen and goes to it.
	 *
	 * @param screen new screen
	 */
	void addFlow(@NonNull Screen screen);

	/**
	 * {@code addFlow} with an animation data.
	 *
	 * @param screen        new screen
	 * @param animationData animation data for an additional animation configuring
	 */
	void addFlow(@NonNull Screen screen, @Nullable AnimationData animationData);

	/**
	 * Finishes a current screen and goes back to the previous screen.
	 */
	void goBack();

	/**
	 * {@code goBack} with an animation data.
	 *
	 * @param animationData animation data for an additional animation configuring
	 */
	void goBack(@Nullable AnimationData animationData);

	/**
	 * Finishes a current screen and goes back to the previous screen with result.
	 *
	 * @param screenResult screen result that will be returned
	 */
	void goBackWithResult(@NonNull ScreenResult screenResult);

	/**
	 * {@code goBackWithResult} with an animation data.
	 *
	 * @param screenResult  screen result that will be returned
	 * @param animationData animation data for an additional animation configuring
	 */
	void goBackWithResult(@NonNull ScreenResult screenResult, @Nullable AnimationData animationData);

	/**
	 * Goes back to a screen with the given class.
	 *
	 * @param screenClass screen class for going back
	 */
	void goBackTo(@NonNull Class<? extends Screen> screenClass);

	/**
	 * {@code goBackTo} with an animation data.
	 *
	 * @param screenClass   screen class for going back
	 * @param animationData animation data for an additional animation configuring
	 */
	void goBackTo(@NonNull Class<? extends Screen> screenClass, @Nullable AnimationData animationData);

	/**
	 * Goes back to a flow screen with the given class.
	 *
	 * @param screenClass screen class for going back
	 */
	void goBackToFlow(@NonNull Class<? extends Screen> screenClass);

	/**
	 * {@code goBackToFlow} with an animation data.
	 *
	 * @param screenClass   screen class for going back
	 * @param animationData animation data for an additional animation configuring
	 */
	void goBackToFlow(@NonNull Class<? extends Screen> screenClass, @Nullable AnimationData animationData);

	/**
	 * Goes back to a screen with the given class and returns result to it.
	 *
	 * @param screenClass  screen class for going back
	 * @param screenResult screen result that will be returned
	 */
	void goBackToWithResult(@NonNull Class<? extends Screen> screenClass, @NonNull ScreenResult screenResult);

	/**
	 * {@code goBackToWithResult} with an animation data.
	 *
	 * @param screenClass   screen class for going back
	 * @param screenResult  screen result that will be returned
	 * @param animationData animation data for an additional animation configuring
	 */
	void goBackToWithResult(@NonNull Class<? extends Screen> screenClass, @NonNull ScreenResult screenResult, @Nullable AnimationData animationData);

	/**
	 * Goes back to a flow screen with the given class and returns result to it.
	 *
	 * @param screenClass  screen class for going back
	 * @param screenResult screen result that will be returned
	 */
	void goBackToFlowWithResult(@NonNull Class<? extends Screen> screenClass, @NonNull ScreenResult screenResult);

	/**
	 * {@code goBackToFlowWithResult} with an animation data.
	 *
	 * @param screenClass   screen class for going back
	 * @param screenResult  screen result that will be returned
	 * @param animationData animation data for an additional animation configuring
	 */
	void goBackToFlowWithResult(@NonNull Class<? extends Screen> screenClass, @NonNull ScreenResult screenResult, @Nullable AnimationData animationData);

	/**
	 * Replaces the last screen with a new screen.
	 *
	 * @param screen new screen
	 */
	void replace(@NonNull Screen screen);

	/**
	 * {@code replace} with an animation data.
	 *
	 * @param screen        new screen
	 * @param animationData animation data for an additional animation configuring
	 */
	void replace(@NonNull Screen screen, @Nullable AnimationData animationData);

	/**
	 * Replaces the last flow screen with a new flow screen.
	 *
	 * @param screen new screen
	 */
	void replaceFlow(@NonNull Screen screen);

	/**
	 * {@code replaceFlow} with an animation data.
	 *
	 * @param screen        new screen
	 * @param animationData animation data for an additional animation configuring
	 */
	void replaceFlow(@NonNull Screen screen, @Nullable AnimationData animationData);

	/**
	 * Removes all other screens and adds a new screen.
	 *
	 * @param screen new screen
	 */
	void reset(@NonNull Screen screen);

	/**
	 * {@code reset} with an animation data.
	 *
	 * @param screen        new screen
	 * @param animationData animation data for an additional animation configuring
	 */
	void reset(@NonNull Screen screen, @Nullable AnimationData animationData);

	/**
	 * Removes all other flow screens and adds a new flow screen.
	 *
	 * @param screen new screen
	 */
	void resetFlow(@NonNull Screen screen);

	/**
	 * {@code resetFlow} with an animation data.
	 *
	 * @param screen        new screen
	 * @param animationData animation data for an additional animation configuring
	 */
	void resetFlow(@NonNull Screen screen, @Nullable AnimationData animationData);

	/**
	 * Finishes a last screen or a group of screens executing some common task.
	 */
	void finish();

	/**
	 * {@code finish} with an animation data.
	 *
	 * @param animationData animation data for an additional animation configuring
	 */
	void finish(@Nullable AnimationData animationData);

	/**
	 * Finishes a last flow screen.
	 */
	void finishFlow();

	/**
	 * {@code finishFlow} with an animation data.
	 *
	 * @param animationData animation data for an additional animation configuring
	 */
	void finishFlow(@Nullable AnimationData animationData);

	/**
	 * Finishes a last screen or a group of screens executing some common task and returns a screen result.
	 *
	 * @param screenResult screen result that will be returned
	 */
	void finishWithResult(@NonNull ScreenResult screenResult);

	/**
	 * {@code finishWithResult} with an animation data.
	 *
	 * @param screenResult  screen result that will be returned
	 * @param animationData animation data for an additional animation configuring
	 */
	void finishWithResult(@NonNull ScreenResult screenResult, @Nullable AnimationData animationData);

	/**
	 * Finishes a last flow screen and returns a screen result.
	 *
	 * @param screenResult screen result that will be returned
	 */
	void finishFlowWithResult(@NonNull ScreenResult screenResult);

	/**
	 * {@code finishWithFlowResult} with an animation data.
	 *
	 * @param screenResult  screen result that will be returned
	 * @param animationData animation data for an additional animation configuring
	 */
	void finishWithFlowResult(@NonNull ScreenResult screenResult, @Nullable AnimationData animationData);

	/**
	 * Switches screens.
	 *
	 * @param screen screen for switching to
	 */
	void switchTo(@NonNull Screen screen);

	/**
	 * {@code switchTo} with an animation data.
	 *
	 * @param screen        screen for switching to
	 * @param animationData animation data for an additional animation configuring
	 */
	void switchTo(@NonNull Screen screen, @Nullable AnimationData animationData);
}
