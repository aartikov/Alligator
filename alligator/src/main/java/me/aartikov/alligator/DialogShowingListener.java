package me.aartikov.alligator;

/**
 * Date: 09.05.2017
 * Time: 16:11
 *
 * @author Artur Artikov
 */

/**
 * Interface for listening of dialog showing.
 */
public interface DialogShowingListener {
	/**
	 * Called when a dialog was shown.
	 *
	 * @param screenClass class of a screen that represents a dialog.
	 */
	void onDialogShown(Class<? extends Screen> screenClass);
}
