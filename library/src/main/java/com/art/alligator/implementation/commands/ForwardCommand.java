package com.art.alligator.implementation.commands;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.art.alligator.NavigationContext;
import com.art.alligator.NavigationFactory;
import com.art.alligator.Screen;
import com.art.alligator.implementation.Command;

/**
 * Date: 29.12.2016
 * Time: 10:22
 *
 * @author Artur Artikov
 */
public class ForwardCommand implements Command {
	private Screen mScreen;

	public ForwardCommand(Screen screen) {
		mScreen = screen;
	}

	@Override
	public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) {
		Context context = navigationContext.getActivity();
		Intent intent = navigationFactory.createActivityIntent(context, mScreen);
		Fragment fragment = navigationFactory.createFragment(mScreen);

		if(intent != null) {
			context.startActivity(intent);
			return false;
		} else if (fragment != null) {
			FragmentManager fragmentManager = navigationContext.getFragmentManager();
			if (fragmentManager == null) {
				throw new IllegalStateException("Failed to add fragment. FragmentManager is not bound.");
			}

			fragmentManager.beginTransaction()
					.replace(navigationContext.getContainerId(), fragment)
					.addToBackStack(mScreen.getClass().getName())
					.commit();
			fragmentManager.executePendingTransactions();
			return true;

		} else {
			throw new RuntimeException("Screen " + mScreen.getClass().getSimpleName() + " is not registered.");
		}
	}
}
