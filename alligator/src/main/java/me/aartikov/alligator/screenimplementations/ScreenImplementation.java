package me.aartikov.alligator.screenimplementations;

import me.aartikov.alligator.exceptions.NavigationException;

/**
 * Date: 15.10.2017
 * Time: 11:21
 *
 * @author Artur Artikov
 */

public interface ScreenImplementation {
	<R> R accept(ScreenImplementationVisitor<R> visitor) throws NavigationException;
}