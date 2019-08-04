package me.aartikov.alligator.helpers;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.InvalidScreenResultException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.ScreenRegistrationException;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;


/**
 * Helps to return a screen result from activities and fragments.
 */
public class ScreenResultHelper {
	public static final String KEY_REQUEST_CODE = "me.aartikov.alligator.KEY_REQUEST_CODE";
	public static final String KEY_RESULT_CODE = "me.aartikov.alligator.KEY_RESULT_CODE";

	private NavigationFactory mNavigationFactory;

	public ScreenResultHelper(NavigationFactory navigationFactory) {
		mNavigationFactory = navigationFactory;
	}

	public void setActivityResult(@NonNull Activity activity, @NonNull ScreenResult screenResult) throws NavigationException {
		ActivityDestination activityDestination = getAndValidateActivityDestination(activity, screenResult);
		ActivityResult activityResult = activityDestination.createActivityResult(screenResult);
		activity.setResult(activityResult.getResultCode(), activityResult.getIntent());
	}

	public void setResultToIntent(@NonNull Intent intent, @NonNull Activity activity, @NonNull ScreenResult screenResult) throws NavigationException {
		ActivityDestination activityDestination = getAndValidateActivityDestination(activity, screenResult);
		ActivityResult activityResult = activityDestination.createActivityResult(screenResult);
		intent.putExtra(KEY_REQUEST_CODE, activityDestination.getRequestCode());
		intent.putExtra(KEY_RESULT_CODE, activityResult.getResultCode());
		Intent resultIntent = activityResult.getIntent();
		if (resultIntent != null) {
			intent.putExtras(resultIntent);
		}
	}

	@NonNull
	private ActivityDestination getAndValidateActivityDestination(@NonNull Activity activity, @NonNull ScreenResult screenResult) throws NavigationException {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(activity);
		if (screenClass == null) {
			throw new ScreenRegistrationException("Failed to get a screen class for " + activity.getClass().getSimpleName());
		}

		ActivityDestination activityDestination = (ActivityDestination) mNavigationFactory.getDestination(screenClass);
		if (activityDestination == null) {
			throw new ScreenRegistrationException("Failed to get a destination for " + screenClass.getSimpleName());
		}

		if (activityDestination.getScreenResultClass() == null) {
			throw new InvalidScreenResultException("Screen " + screenClass.getSimpleName() + " can't return a result.");
		}

		Class<? extends ScreenResult> supportedScreenResultClass = activityDestination.getScreenResultClass();
		if (!supportedScreenResultClass.isAssignableFrom(screenResult.getClass())) {
			throw new InvalidScreenResultException("Screen " + screenClass.getSimpleName() + " can't return a result of class " + screenResult.getClass().getCanonicalName() +
			                                       ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		return activityDestination;
	}

	public void callScreenResultListener(@NonNull Fragment fragment, @Nullable ScreenResult screenResult, @NonNull ScreenResultListener screenResultListener) throws NavigationException {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(fragment);
		if (screenClass == null) {
			throw new ScreenRegistrationException("Failed to get a screen class for " + fragment.getClass().getSimpleName());
		}

		FragmentDestination fragmentDestination = (FragmentDestination) mNavigationFactory.getDestination(screenClass);
		if (fragmentDestination == null) {
			throw new ScreenRegistrationException("Failed to get a destination for " + screenClass.getSimpleName());
		}

		Class<? extends ScreenResult> supportedScreenResultClass = fragmentDestination.getScreenResultClass();
		if (supportedScreenResultClass == null) {
			if (screenResult == null) {
				return;
			} else {
				throw new InvalidScreenResultException("Screen " + screenClass.getSimpleName() + " can't return a result.");
			}
		}

		if (screenResult != null && !supportedScreenResultClass.isAssignableFrom(screenResult.getClass())) {
			throw new InvalidScreenResultException("Screen " + screenClass.getSimpleName() + " can't return a result of class " + screenResult.getClass().getCanonicalName() +
			                                       ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		screenResultListener.onScreenResult(screenClass, screenResult);
	}

	public void callScreenResultListener(@NonNull DialogFragment dialogFragment, @Nullable ScreenResult screenResult, @NonNull ScreenResultListener screenResultListener) throws NavigationException {
		Class<? extends Screen> screenClass = mNavigationFactory.getScreenClass(dialogFragment);
		if (screenClass == null) {
			throw new ScreenRegistrationException("Failed to get a screen class for " + dialogFragment.getClass().getSimpleName());
		}

		DialogFragmentDestination dialogFragmentDestination = (DialogFragmentDestination) mNavigationFactory.getDestination(screenClass);
		if (dialogFragmentDestination == null) {
			throw new ScreenRegistrationException("Failed to get a destination for " + screenClass.getSimpleName());
		}

		Class<? extends ScreenResult> supportedScreenResultClass = dialogFragmentDestination.getScreenResultClass();
		if (supportedScreenResultClass == null) {
			if (screenResult == null) {
				return;
			} else {
				throw new InvalidScreenResultException("Screen " + screenClass.getSimpleName() + " can't return a result.");
			}
		}

		if (screenResult != null && !supportedScreenResultClass.isAssignableFrom(screenResult.getClass())) {
			throw new InvalidScreenResultException("Screen " + screenClass.getSimpleName() + " can't return a result of class " + screenResult.getClass().getCanonicalName() +
			                                       ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		screenResultListener.onScreenResult(screenClass, screenResult);
	}
}
