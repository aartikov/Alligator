package me.aartikov.alligator.screenimplementations;

import me.aartikov.alligator.exceptions.NavigationException;

/**
 * Date: 15.10.2017
 * Time: 11:22
 *
 * @author Artur Artikov
 */

public interface ScreenImplementationVisitor<R> {
	R visit(ActivityScreenImplementation screenImplementation) throws NavigationException;

	R visit(FragmentScreenImplementation screenImplementation) throws NavigationException;

	R visit(DialogFragmentScreenImplementation screenImplementation) throws NavigationException;
}