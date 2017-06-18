package me.aartikov.alligator.defaultimpementation;

import me.aartikov.alligator.DialogShowingListener;
import me.aartikov.alligator.Screen;

/**
 * Date: 09.05.2017
 * Time: 16:21
 *
 * @author Artur Artikov
 */

/**
 * Dialog showing listener that does nothing.
 */
public class DefaultDialogShowingListener implements DialogShowingListener {
	@Override
	public void onDialogShown(Class<? extends Screen> screenClass) {

	}
}
