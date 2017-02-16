package com.art.alligator.implementation.commands;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.implementation.Command;

/**
 * Date: 11.02.2017
 * Time: 11:14
 *
 * @author Artur Artikov
 */

public class BackToCommand implements Command {
	private Class<? extends Screen> mScreenClass;

	public BackToCommand(Class<? extends Screen> screenClass) {
		mScreenClass = screenClass;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Class activityClass = navigationFactory.getActivityClass(mScreenClass);
		FragmentManager fragmentManager = navigationContext.getFragmentManager();

		if (activityClass != null) {
			Context context = navigationContext.getActivity();
			Intent intent = new Intent(context, activityClass);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			navigationContext.getActivity().startActivity(intent);
			return false;
		} else if (fragmentManager != null && hasFragmentInBackStack(fragmentManager, mScreenClass.getName())) {
			fragmentManager.popBackStackImmediate(mScreenClass.getName(), 0);
			return true;
		} else {
			throw new RuntimeException("Failed to go back to " + mScreenClass.getSimpleName());
		}
	}

	private static boolean hasFragmentInBackStack(FragmentManager fragmentManager, String name) {
		for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
			if (name.equals(fragmentManager.getBackStackEntryAt(i).getName())) {
				return true;
			}
		}
		return false;
	}
}
