package me.aartikov.alligator.flowmanagers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.helpers.ScreenResultHelper;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.listeners.TransitionListener;


/**
 * Used to navigate between flow screens which can hold their own navigation stacks
 */
public interface FlowManager {

    /**
     * Closes the current flow screen with all its inner screens and then returns to the previous flow screen
     *
     * @param transitionListener   listener for flow screen transitions
     * @param screenResultHelper   screen result helper that will send the screen result
     * @param screenResultListener listener that handles the result
     * @param screenResult         result from the current flow screen to the previous one
     * @param animationData        additional animation data
     * @return true if after this command other command can be executed, false if command execution should be delayed until a new {@link NavigationContext} will be bound
     */
    boolean back(@NonNull TransitionListener transitionListener,
              @NonNull ScreenResultHelper screenResultHelper,
              @NonNull ScreenResultListener screenResultListener,
              @Nullable ScreenResult screenResult,
              @Nullable AnimationData animationData) throws NavigationException;

    /**
     * Closes the current flow screen with all its inner screens and then returns to the one of previous flow screens if it exists in the back stack
     *
     * @param screenClassTo        where to return to
     * @param transitionListener   listener for flow screen transitions
     * @param screenResultHelper   screen result helper that will send the screen result
     * @param screenResultListener listener that handles the result
     * @param screenResult         result from the current flow screen to the requested one
     * @param animationData        additional animation data
     * @return true if after this command other command can be executed, false if command execution should be delayed until a new {@link NavigationContext} will be bound
     */
    boolean backTo(@NonNull Class<? extends Screen> screenClassTo,
                @NonNull TransitionListener transitionListener,
                @NonNull ScreenResultHelper screenResultHelper,
                @NonNull ScreenResultListener screenResultListener,
                @Nullable ScreenResult screenResult,
                @Nullable AnimationData animationData) throws NavigationException;


    /**
     * Adds a new flow screen with its own navigation stack
     *
     * @param screen             request screen
     * @param transitionListener listener for flow screen transitions
     * @param animationData      additional animation data
     * @return true if after this command other command can be executed, false if command execution should be delayed until a new {@link NavigationContext} will be bound
     */
    boolean add(@NonNull Screen screen,
             @NonNull TransitionListener transitionListener,
             @Nullable AnimationData animationData) throws NavigationException;

    /**
     * Replaces the most recent flow screen in the stack with the requested one
     *
     * @param screen             request screen
     * @param transitionListener listener for flow screen transitions
     * @param animationData      additional animation data
     * @return true if after this command other command can be executed, false if command execution should be delayed until a new {@link NavigationContext} will be bound
     */
    boolean replace(@NonNull Screen screen,
                 @NonNull TransitionListener transitionListener,
                 @Nullable AnimationData animationData) throws NavigationException;

    /**
     * Clears the whole flow screen stack and adds the requested flow screen
     *
     * @param screen             request screen
     * @param transitionListener listener for flow screen transitions
     * @param animationData      additional animation data
     * @return true if after this command other command can be executed, false if command execution should be delayed until a new {@link NavigationContext} will be bound
     */
    boolean reset(@NonNull Screen screen,
               @NonNull TransitionListener transitionListener,
               @Nullable AnimationData animationData) throws NavigationException;
}
