package me.aartikov.alligator.listeners;

import androidx.annotation.NonNull;

import me.aartikov.alligator.exceptions.NavigationException;


/**
 * Default implementation of {@link NavigationErrorListener}. Wraps {@link NavigationException} to {@code RuntimeException} and throws it.
 */
public class DefaultNavigationErrorListener implements NavigationErrorListener {
	@Override
	public void onNavigationError(@NonNull NavigationException e) {
		throw new RuntimeException(e);
	}
}
