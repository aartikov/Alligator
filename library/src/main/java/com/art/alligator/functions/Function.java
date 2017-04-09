package com.art.alligator.functions;

/**
 * Date: 24.03.2017
 * Time: 9:18
 *
 * @author Artur Artikov
 */

/**
 * Function with one argument.
 */
public interface Function<T, R> {
	R call(T t);
}