package com.art.alligator.implementation.commands;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.implementation.Command;

/**
 * Date: 12.02.2017
 * Time: 0:53
 *
 * @author Artur Artikov
 */

public class FinishCommand implements Command {
	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		navigationContext.getActivity().finish();
		return false;
	}
}
