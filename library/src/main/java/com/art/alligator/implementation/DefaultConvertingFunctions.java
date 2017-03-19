package com.art.alligator.implementation;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.art.alligator.ActivityResult;
import com.art.alligator.Screen;
import com.art.alligator.ScreenResult;

import com.art.alligator.implementation.RegistryNavigationFactory.ActivityResultCreationFunction;
import com.art.alligator.implementation.RegistryNavigationFactory.ActivityScreenGettingFunction;
import com.art.alligator.implementation.RegistryNavigationFactory.FragmentCreationFunction;
import com.art.alligator.implementation.RegistryNavigationFactory.FragmentScreenGettingFunction;
import com.art.alligator.implementation.RegistryNavigationFactory.IntentCreationFunction;
import com.art.alligator.implementation.RegistryNavigationFactory.ScreenResultGettingFunction;


/**
 * Date: 19.03.2017
 * Time: 12:05
 *
 * @author Artur Artikov
 */

class DefaultConvertingFunctions {
	private static final String KEY_SCREEN = "com.art.alligator.implementation.DefaultConvertingFunctions.KEY_SCREEN";
	private static final String KEY_SCREEN_RESULT = "com.art.alligator.implementation.DefaultConvertingFunctions.KEY_SCREEN_RESULT";

	static <ScreenT extends Screen> IntentCreationFunction<ScreenT> getDefaultIntentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends Activity> activityClass) {
		return new IntentCreationFunction<ScreenT>() {
			@Override
			public Intent call(Context context, ScreenT screen) {
				Intent intent = new Intent(context, activityClass);
				if (screen instanceof Serializable) {
					intent.putExtra(KEY_SCREEN, (Serializable) screen);
				}
				return intent;
			}
		};
	}

	@SuppressWarnings("unchecked")
	static <ScreenT extends Screen> ActivityScreenGettingFunction<ScreenT> getDefaultActivityScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new ActivityScreenGettingFunction<ScreenT>() {
			@Override
			public ScreenT call(Intent intent) {
				if (!Serializable.class.isAssignableFrom(screenClass)) {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable.");
				}
				return (ScreenT) intent.getSerializableExtra(KEY_SCREEN);
			}
		};
	}

	@SuppressWarnings("unchecked")
	static <ScreenT extends Screen> ActivityScreenGettingFunction<ScreenT> getNotImplementedActivityScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new ActivityScreenGettingFunction<ScreenT>() {
			@Override
			public ScreenT call(Intent intent) {
				throw new RuntimeException("ActivityScreenGettingFunction is not implemented for screen " + screenClass.getSimpleName());
			}
		};
	}

	static <ScreenT extends Screen> FragmentCreationFunction<ScreenT> getDefaultFragmentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		return new RegistryNavigationFactory.FragmentCreationFunction<ScreenT>() {
			@Override
			public Fragment call(ScreenT screen) {
				try {
					Fragment fragment = fragmentClass.newInstance();
					if (screen instanceof Serializable) {
						Bundle arguments = new Bundle();
						arguments.putSerializable(KEY_SCREEN, (Serializable) screen);
						fragment.setArguments(arguments);
					}
					return fragment;
				} catch (InstantiationException e) {
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
	}

	@SuppressWarnings("unchecked")
	static <ScreenT extends Screen> FragmentScreenGettingFunction<ScreenT> getDefaultFragmentScreenGetting(final Class<ScreenT> screenClass) {
		return new FragmentScreenGettingFunction<ScreenT>() {
			@Override
			public ScreenT call(Fragment fragment) {
				if (!Serializable.class.isAssignableFrom(screenClass)) {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable.");
				}
				if (fragment.getArguments() == null) {
					return null;
				}
				return (ScreenT) fragment.getArguments().getSerializable(KEY_SCREEN);
			}
		};
	}

	@SuppressWarnings("unchecked")
	static <ScreenT extends Screen> FragmentScreenGettingFunction<ScreenT> getNotImplementedFragmentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new FragmentScreenGettingFunction<ScreenT>() {
			@Override
			public ScreenT call(Fragment fragment) {
				throw new RuntimeException("FragmentScreenGettingFunction is not implemented for screen " + screenClass.getSimpleName());
			}
		};
	}

	static <ScreenResultT extends ScreenResult> ActivityResultCreationFunction<ScreenResultT> getDefaultActivityResultCreationFunction(Class<ScreenResultT> screenResultClass) {
		return new ActivityResultCreationFunction<ScreenResultT>() {
			@Override
			public ActivityResult call(ScreenResultT screenResult) {
				if (screenResult == null) {
					return new ActivityResult(Activity.RESULT_CANCELED, null);
				}
				if (!(screenResult instanceof Serializable)) {
					throw new IllegalArgumentException("Screen result " + screenResult.getClass().getCanonicalName() + " should be Serializable.");
				}
				Intent data = new Intent();
				data.putExtra(KEY_SCREEN_RESULT, (Serializable) screenResult);
				return new ActivityResult(Activity.RESULT_OK, data);
			}
		};
	}

	@SuppressWarnings("unchecked")
	static <ScreenResultT extends ScreenResult> ScreenResultGettingFunction<ScreenResultT> getDefaultScreenResultGettingFunction(Class<ScreenResultT> screenResultClass) {
		return new ScreenResultGettingFunction<ScreenResultT>() {
			@Override
			public ScreenResultT call(ActivityResult activityResult) {
				if (activityResult.getIntent() == null || activityResult.getResultCode() != Activity.RESULT_OK) {
					return null;
				}
				return (ScreenResultT) activityResult.getIntent().getSerializableExtra(KEY_SCREEN_RESULT);
			}
		};
	}

	@SuppressWarnings("unchecked")
	static <ScreenResultT extends ScreenResult> ActivityResultCreationFunction<ScreenResultT> getNotImplementedActivityResultCreationFunction(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass) {
		return new ActivityResultCreationFunction<ScreenResultT>() {
			@Override
			public ActivityResult call(ScreenResultT screenResult) {
				throw new RuntimeException("ActivityResultCreationFunction is not implemented for screen " + screenClass.getSimpleName());
			}
		};
	}
}
