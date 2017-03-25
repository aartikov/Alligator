package com.art.alligator.navigationfactory;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.art.alligator.ActivityResult;
import com.art.alligator.Screen;
import com.art.alligator.ScreenResult;
import com.art.alligator.navigationfactory.RegistryNavigationFactory.Function;
import com.art.alligator.navigationfactory.RegistryNavigationFactory.Function2;


/**
 * Date: 19.03.2017
 * Time: 12:05
 *
 * @author Artur Artikov
 */

class DefaultConvertingFunctions {
	private static final String KEY_SCREEN = "com.art.alligator.registry.DefaultConvertingFunctions.KEY_SCREEN";
	private static final String KEY_SCREEN_RESULT = "com.art.alligator.registry.DefaultConvertingFunctions.KEY_SCREEN_RESULT";

	static <ScreenT extends Screen> Function2<Context, ScreenT, Intent> getDefaultIntentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends Activity> activityClass) {
		return new Function2<Context, ScreenT, Intent>() {
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

	static <ScreenT extends Screen> Function<Intent, ScreenT> getDefaultActivityScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Intent, ScreenT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenT call(Intent intent) {
				if (!Serializable.class.isAssignableFrom(screenClass)) {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable.");
				}
				return (ScreenT) intent.getSerializableExtra(KEY_SCREEN);
			}
		};
	}

	static <ScreenT extends Screen> Function<Intent, ScreenT> getNotImplementedActivityScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Intent, ScreenT>() {
			@Override
			public ScreenT call(Intent intent) {
				throw new RuntimeException("Activity screen getting function is not implemented for screen " + screenClass.getSimpleName());
			}
		};
	}

	static <ScreenT extends Screen> Function<ScreenT, Fragment> getDefaultFragmentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		return new Function<ScreenT, Fragment>() {
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

	static <ScreenT extends Screen> Function<Fragment, ScreenT> getDefaultFragmentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Fragment, ScreenT>() {
			@Override
			@SuppressWarnings("unchecked")
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

	static <ScreenT extends Screen> Function<Fragment, ScreenT> getNotImplementedFragmentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Fragment, ScreenT>() {
			@Override
			public ScreenT call(Fragment fragment) {
				throw new RuntimeException("Fragment screen getting function is not implemented for screen " + screenClass.getSimpleName());
			}
		};
	}

	static <ScreenT extends Screen> Function<ScreenT, DialogFragment> getDefaultDialogFragmentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends DialogFragment> dialogFragmentClass) {
		return new Function<ScreenT, DialogFragment>() {
			@Override
			public DialogFragment call(ScreenT screen) {
				try {
					DialogFragment dialogFragment = dialogFragmentClass.newInstance();
					if (screen instanceof Serializable) {
						Bundle arguments = new Bundle();
						arguments.putSerializable(KEY_SCREEN, (Serializable) screen);
						dialogFragment.setArguments(arguments);
					}
					return dialogFragment;
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

	static <ScreenT extends Screen> Function<DialogFragment, ScreenT> getDefaultDialogFragmentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<DialogFragment, ScreenT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenT call(DialogFragment dialogFragment) {
				if (!Serializable.class.isAssignableFrom(screenClass)) {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable.");
				}
				if (dialogFragment.getArguments() == null) {
					return null;
				}
				return (ScreenT) dialogFragment.getArguments().getSerializable(KEY_SCREEN);
			}
		};
	}

	static <ScreenT extends Screen> Function<DialogFragment, ScreenT> getNotImplementedDialogFragmentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<DialogFragment, ScreenT>() {
			@Override
			public ScreenT call(DialogFragment dialogFragment) {
				throw new RuntimeException("Dialogragment screen getting function is not implemented for screen " + screenClass.getSimpleName());
			}
		};
	}

	static <ScreenResultT extends ScreenResult> Function<ScreenResultT, ActivityResult> getDefaultActivityResultCreationFunction(Class<ScreenResultT> screenResultClass) {
		return new Function<ScreenResultT, ActivityResult>() {
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

	static <ScreenResultT extends ScreenResult> Function<ActivityResult, ScreenResultT> getDefaultScreenResultGettingFunction(final Class<ScreenResultT> screenResultClass) {
		return new Function<ActivityResult, ScreenResultT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenResultT call(ActivityResult activityResult) {
				if (activityResult.getIntent() == null || activityResult.getResultCode() != Activity.RESULT_OK) {
					return null;
				}
				if (!Serializable.class.isAssignableFrom(screenResultClass)) {
					throw new IllegalArgumentException("Screen result " + screenResultClass.getCanonicalName() + " should be Serializable.");
				}
				return (ScreenResultT) activityResult.getIntent().getSerializableExtra(KEY_SCREEN_RESULT);
			}
		};
	}

	static <ScreenResultT extends ScreenResult> Function<ScreenResultT, ActivityResult> getNotImplementedActivityResultCreationFunction(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass) {
		return new Function<ScreenResultT, ActivityResult>() {
			@Override
			public ActivityResult call(ScreenResultT screenResult) {
				throw new RuntimeException("ActivityResult creation function is not implemented for screen " + screenClass.getSimpleName());
			}
		};
	}
}
