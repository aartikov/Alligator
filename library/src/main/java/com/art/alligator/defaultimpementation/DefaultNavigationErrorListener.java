package com.art.alligator.defaultimpementation;

import com.art.alligator.NavigationErrorListener;
import com.art.alligator.exceptions.CommandExecutionException;

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
