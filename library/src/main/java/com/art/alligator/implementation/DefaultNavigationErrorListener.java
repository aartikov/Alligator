package com.art.alligator.implementation;

import com.art.alligator.CommandExecutionException;
import com.art.alligator.NavigationErrorListener;

/**
 * Date: 12.03.2017
 * Time: 15:16
 *
 * @author Artur Artikov
 */

public class DefaultNavigationErrorListener implements NavigationErrorListener {
	@Override
	public void onNavigationError(CommandExecutionException e) {
		throw new RuntimeException(e);
	}
}
