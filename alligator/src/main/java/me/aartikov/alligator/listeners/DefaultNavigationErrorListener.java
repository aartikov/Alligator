package me.aartikov.alligator.listeners;

import android.support.annotation.NonNull;

import me.aartikov.alligator.exceptions.NavigationException;

/**
 * Date: 12.03.2017
 * Time: 15:16
 *
 * @author Artur Artikov
 */

/**
 * Default implementation of {@link NavigationErrorListener}. Wraps {@link NavigationException} to {@code RuntimeException} and throws it.
 */
public class DefaultNavigationErrorListener implements NavigationErrorListener {
	@Override
	public void onNavigationError(@NonNull NavigationException e) {
		throw new RuntimeException(e);
	}
}
