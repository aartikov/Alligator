package com.art.alligator.implementation;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;

/**
 * Date: 29.12.2016
 * Time: 10:14
 *
 * @author Artur Artikov
 */
public interface Command {
	boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory);
}
