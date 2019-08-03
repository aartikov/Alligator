package me.aartikov.alligator.listeners;

import androidx.annotation.NonNull;

import me.aartikov.alligator.Screen;


/**
 * Dialog showing listener that does nothing.
 */
public class DefaultDialogShowingListener implements DialogShowingListener {
	@Override
	public void onDialogShown(@NonNull Class<? extends Screen> screenClass) {

	}
}
