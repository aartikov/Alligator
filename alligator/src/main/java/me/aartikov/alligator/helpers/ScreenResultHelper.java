package me.aartikov.alligator.helpers;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;

/**
 * Date: 03.12.2017
 * Time: 10:24
 *
 * @author Artur Artikov
 */

public class ScreenResultHelper {

	public void setActivityResult(Activity activity, ScreenResult screenResult, NavigationFactory navigationFactory) throws NavigationException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(activity);
		if (screenClass == null) {
			throw new NavigationException("Failed to get a screen class for " + activity.getClass().getSimpleName());
		}

		ActivityScreenImplementation activityScreenImplementation = (ActivityScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (activityScreenImplementation == null) {
			throw new NavigationException("Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		if (activityScreenImplementation.getScreenResultClass() == null) {
			throw new NavigationException("Screen " + screenClass.getSimpleName() + " can't return a result.");
		}

		Class<? extends ScreenResult> supportedScreenResultClass = activityScreenImplementation.getScreenResultClass();
		if (!supportedScreenResultClass.isAssignableFrom(screenResult.getClass())) {
			throw new NavigationException("Screen " + screenClass.getSimpleName() + " can't return a result of class " + screenResult.getClass().getCanonicalName() +
			                              ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		ActivityResult activityResult = activityScreenImplementation.createActivityResult(screenResult);
		activity.setResult(activityResult.getResultCode(), activityResult.getIntent());
	}

	public void callScreenResultListener(Fragment fragment, @Nullable ScreenResult screenResult, ScreenResultListener screenResultListener, NavigationFactory navigationFactory) throws NavigationException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(fragment);
		if (screenClass == null) {
			throw new NavigationException("Failed to get a screen class for " + fragment.getClass().getSimpleName());
		}

		FragmentScreenImplementation fragmentScreenImplementation = (FragmentScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (fragmentScreenImplementation == null) {
			throw new NavigationException("Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		if (screenResult == null) {
			screenResultListener.onCancelled(screenClass);
			return;
		}

		if (fragmentScreenImplementation.getScreenResultClass() == null) {
			throw new NavigationException("Screen " + screenClass.getSimpleName() + " can't return a result.");
		}

		Class<? extends ScreenResult> supportedScreenResultClass = fragmentScreenImplementation.getScreenResultClass();
		if (!supportedScreenResultClass.isAssignableFrom(screenResult.getClass())) {
			throw new NavigationException("Screen " + screenClass.getSimpleName() + " can't return a result of class " + screenResult.getClass().getCanonicalName() +
			                              ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		screenResultListener.onScreenResult(screenClass, screenResult);
	}

	public void callScreenResultListener(DialogFragment dialogFragment, @Nullable ScreenResult screenResult, ScreenResultListener screenResultListener, NavigationFactory navigationFactory) throws NavigationException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(dialogFragment);
		if (screenClass == null) {
			throw new NavigationException("Failed to get a screen class for " + dialogFragment.getClass().getSimpleName());
		}

		DialogFragmentScreenImplementation dialogFragmentScreenImplementation = (DialogFragmentScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (dialogFragmentScreenImplementation == null) {
			throw new NavigationException("Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		if (screenResult == null) {
			screenResultListener.onCancelled(screenClass);
			return;
		}

		if (dialogFragmentScreenImplementation.getScreenResultClass() == null) {
			throw new NavigationException("Screen " + screenClass.getSimpleName() + " can't return a result.");
		}

		Class<? extends ScreenResult> supportedScreenResultClass = dialogFragmentScreenImplementation.getScreenResultClass();
		if (!supportedScreenResultClass.isAssignableFrom(screenResult.getClass())) {
			throw new NavigationException("Screen " + screenClass.getSimpleName() + " can't return a result of class " + screenResult.getClass().getCanonicalName() +
			                              ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		screenResultListener.onScreenResult(screenClass, screenResult);
	}
}
