package com.art.alligator.functions;

/**
 * Date: 24.03.2017
 * Time: 9:18
 *
 * @author Artur Artikov
 */

public interface Function<T, R> {
	R call(T t);
}