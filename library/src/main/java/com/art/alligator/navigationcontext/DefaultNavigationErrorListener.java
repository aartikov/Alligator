package com.art.alligator.navigationcontext;

import com.art.alligator.NavigationErrorListener;
import com.art.alligator.exception.CommandExecutionException;

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
