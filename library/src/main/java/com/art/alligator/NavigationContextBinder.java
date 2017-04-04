package com.art.alligator;

/**
 * Date: 11.02.2017
 * Time: 11:27
 *
 * @author Artur Artikov
 */

/**
 * Interface for binding and unbinding of {@link NavigationContext}
 */
public interface NavigationContextBinder {
	/**
	 * Bind the navigation context. Call this method from activity onResumeFragments method.
	 */
	void bind(NavigationContext navigationContext);

	/**
	 * Unbind an navigation context. Call this method from activity onPause method.
	 */
	void unbind();
}
