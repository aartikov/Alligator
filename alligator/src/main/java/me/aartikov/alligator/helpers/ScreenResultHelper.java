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
import me.aartikov.alligator.exceptions.InvalidScreenResultException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.ScreenRegistrationException;
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

/**
 * Helps to return a screen result from activities and fragments.
 */
public class ScreenResultHelper {
	public static final String KEY_REQUEST_CODE = "me.aartikov.alligator.KEY_REQUEST_CODE";
	public static final String KEY_RESULT_CODE = "me.aartikov.alligator.KEY_RESULT_CODE";

	public void setActivityResult(@NonNull Activity activity, @NonNull ScreenResult screenResult, @NonNull NavigationFactory navigationFactory) throws NavigationException {
		ActivityScreenImplementation activityScreenImplementation = getAndValidateActivityScreenImplementation(activity, screenResult, navigationFactory);
		ActivityResult activityResult = activityScreenImplementation.createActivityResult(screenResult);
		activity.setResult(activityResult.getResultCode(), activityResult.getIntent());
	}

	public void setResultToIntent(@NonNull Intent intent, @NonNull Activity activity, @NonNull ScreenResult screenResult, @NonNull NavigationFactory navigationFactory) throws NavigationException {
		ActivityScreenImplementation activityScreenImplementation = getAndValidateActivityScreenImplementation(activity, screenResult, navigationFactory);
		ActivityResult activityResult = activityScreenImplementation.createActivityResult(screenResult);
		intent.putExtra(KEY_REQUEST_CODE, activityScreenImplementation.getRequestCode());
		intent.putExtra(KEY_RESULT_CODE, activityResult.getResultCode());
		Intent resultIntent = activityResult.getIntent();
		if (resultIntent != null) {
			intent.putExtras(resultIntent);
		}
	}

	@NonNull
	private ActivityScreenImplementation getAndValidateActivityScreenImplementation(@NonNull Activity activity, @NonNull ScreenResult screenResult, @NonNull NavigationFactory navigationFactory) throws NavigationException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(activity);
		if (screenClass == null) {
			throw new ScreenRegistrationException("Failed to get a screen class for " + activity.getClass().getSimpleName());
		}

		ActivityScreenImplementation activityScreenImplementation = (ActivityScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (activityScreenImplementation == null) {
			throw new ScreenRegistrationException("Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		if (activityScreenImplementation.getScreenResultClass() == null) {
			throw new InvalidScreenResultException("Screen " + screenClass.getSimpleName() + " can't return a result.");
		}

		Class<? extends ScreenResult> supportedScreenResultClass = activityScreenImplementation.getScreenResultClass();
		if (!supportedScreenResultClass.isAssignableFrom(screenResult.getClass())) {
			throw new InvalidScreenResultException("Screen " + screenClass.getSimpleName() + " can't return a result of class " + screenResult.getClass().getCanonicalName() +
			                                       ". It returns a result of class " + supportedScreenResultClass.getCanonicalName());
		}

		return activityScreenImplementation;
	}

	public void callScreenResultListener(@NonNull Fragment fragment, @Nullable ScreenResult screenResult, @NonNull ScreenResultListener screenResultListener, @NonNull NavigationFactory navigationFactory) throws NavigationException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(fragment);
		if (screenClass == null) {
			throw new ScreenRegistrationException("Failed to get a screen class for " + fragment.getClass().getSimpleName());
		}

		FragmentScreenImplementation fragmentScreenImplementation = (FragmentScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (fragmentScreenImplementation == null) {
			throw new ScreenRegistrationException("Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		Class<? extends ScreenResult> supportedScreenResultClass = fragmentScreenImplementation.getScreenResultClass();
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

	public void callScreenResultListener(@NonNull DialogFragment dialogFragment, @Nullable ScreenResult screenResult, @NonNull ScreenResultListener screenResultListener, @NonNull NavigationFactory navigationFactory) throws NavigationException {
		Class<? extends Screen> screenClass = navigationFactory.getScreenClass(dialogFragment);
		if (screenClass == null) {
			throw new ScreenRegistrationException("Failed to get a screen class for " + dialogFragment.getClass().getSimpleName());
		}

		DialogFragmentScreenImplementation dialogFragmentScreenImplementation = (DialogFragmentScreenImplementation) navigationFactory.getScreenImplementation(screenClass);
		if (dialogFragmentScreenImplementation == null) {
			throw new ScreenRegistrationException("Failed to get a screen implementation for " + screenClass.getSimpleName());
		}

		Class<? extends ScreenResult> supportedScreenResultClass = dialogFragmentScreenImplementation.getScreenResultClass();
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
