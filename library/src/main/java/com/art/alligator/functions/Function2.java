package com.art.alligator.functions;

/**
 * Date: 24.03.2017
 * Time: 9:18
 *
 * @author Artur Artikov
 */

/**
 * Function with two arguments.
 */
public interface Function2<T1, T2, R> {
	R call(T1 t1, T2 t2);
}
