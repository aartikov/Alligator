package me.aartikov.sharedelementanimation.ui;

import android.view.View;

/**
 * Date: 16.04.2017
 * Time: 14:07
 *
 * @author Artur Artikov
 */
public interface SharedElementProvider {
	View getSharedElement();

	String getSharedElementName();
}
