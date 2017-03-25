package com.art.alligator;

import com.art.alligator.exception.CommandExecutionException;

/**
 * Date: 12.03.2017
 * Time: 15:15
 *
 * @author Artur Artikov
 */

public interface NavigationErrorListener {
	void onNavigationError(CommandExecutionException e);
}
