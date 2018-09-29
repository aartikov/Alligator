package me.aartikov.alligator;

/**
 * Date: 11.02.2017
 * Time: 11:27
 *
 * @author Artur Artikov
 */

import android.support.annotation.NonNull;

/**
 * Interface for binding and unbinding of a {@link NavigationContext}.
 */
public interface NavigationContextBinder {

	/**
	 * Returns if a navigation context is bound.
	 *
	 * @return true if a navigation context is bound
	 */
	boolean isBound();

	/**
	 * Bind a navigation context. This method should be called from {@code onResumeFragments} of an activity.
	 *
	 * @param navigationContext navigation context
	 */
	void bind(@NonNull NavigationContext navigationContext);

	/**
	 * Unbind a navigation context. This method should be called from {@code onPause} of an activity.
	 */
	void unbind();
}
