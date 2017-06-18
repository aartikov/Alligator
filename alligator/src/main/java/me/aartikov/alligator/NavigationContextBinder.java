package me.aartikov.alligator;

/**
 * Date: 11.02.2017
 * Time: 11:27
 *
 * @author Artur Artikov
 */

/**
 * Interface for binding and unbinding of a {@link NavigationContext}.
 */
public interface NavigationContextBinder {
	/**
	 * Bind a navigation context. This method should be called from {@code onResumeFragments} of an activity.
	 *
	 * @param navigationContext navigation context
	 */
	void bind(NavigationContext navigationContext);

	/**
	 * Unbind a navigation context. This method should be called from {@code onPause} of an activity.
	 */
	void unbind();
}
