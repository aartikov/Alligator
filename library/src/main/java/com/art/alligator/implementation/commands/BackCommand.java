package com.art.alligator.implementation.commands;
import android.support.v4.app.FragmentManager;

import com.art.alligator.NavigationFactory;
import com.art.alligator.implementation.Command;
import com.art.alligator.NavigationContext;

/**
 * Date: 29.12.2016
 * Time: 10:56
 *
 * @author Artur Artikov
 */
public class BackCommand implements Command {
	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		FragmentManager fragmentManager = navigationContext.getFragmentManager();
		if(fragmentManager == null || fragmentManager.getBackStackEntryCount() <= 1) {
			navigationContext.getActivity().finish();
			return false;
		} else {
			fragmentManager.popBackStackImmediate();
			return true;
		}
	}
}
