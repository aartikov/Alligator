package com.art.alligator;

/**
 * Date: 11.02.2017
 * Time: 11:27
 *
 * @author Artur Artikov
 */

public interface NavigationContextBinder {
	void bind(NavigationContext navigationContext);

	void unbind();
}
