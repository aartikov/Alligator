package me.aartikov.alligator.navigationfactories;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import me.aartikov.alligator.ActivityResult;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.functions.Function;
import me.aartikov.alligator.functions.Function2;

/**
 * Date: 5/15/2017
 * Time: 13:43
 *
 * @author Artur Artikov
 */
public class RegistryFunctions {
	private static final String KEY_SCREEN = "me.aartikov.alligator.navigationfactories.RegistryFunctions.KEY_SCREEN";
	private static final String KEY_SCREEN_RESULT = "me.aartikov.alligator.navigationfactories.RegistryFunctions.KEY_SCREEN_RESULT";

	public static <ScreenT extends Screen> Function2<Context, ScreenT, Intent> getDefaultIntentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends Activity> activityClass) {
		return new Function2<Context, ScreenT, Intent>() {
			@Override
			public Intent call(Context context, ScreenT screen) {
				Intent intent = new Intent(context, activityClass);
				if (screen instanceof Serializable) {
					intent.putExtra(KEY_SCREEN, (Serializable) screen);
				} else if (screen instanceof Parcelable) {
					intent.putExtra(KEY_SCREEN, (Parcelable) screen);
				}
				return intent;
			}
		};
	}

	public static <ScreenT extends Screen> Function<Intent, ScreenT> getDefaultIntentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Intent, ScreenT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenT call(Intent intent) {
				if (Serializable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) intent.getSerializableExtra(KEY_SCREEN);
				} else if (Parcelable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) intent.getParcelableExtra(KEY_SCREEN);
				} else {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable or Parcelable.");
				}
			}
		};
	}

	public static <ScreenT extends Screen> Function<Intent, ScreenT> getNotImplementedIntentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Intent, ScreenT>() {
			@Override
			public ScreenT call(Intent intent) {
				throw new RuntimeException("Screen getting function is not implemented for a screen " + screenClass.getSimpleName());
			}
		};
	}

	public static <ScreenT extends Screen> Function<ScreenT, Fragment> getDefaultFragmentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends Fragment> fragmentClass) {
		return new Function<ScreenT, Fragment>() {
			@Override
			public Fragment call(ScreenT screen) {
				try {
					Fragment fragment = fragmentClass.newInstance();
					if (screen instanceof Serializable) {
						Bundle arguments = new Bundle();
						arguments.putSerializable(KEY_SCREEN, (Serializable) screen);
						fragment.setArguments(arguments);
					} else if(screen instanceof Parcelable) {
						Bundle arguments = new Bundle();
						arguments.putParcelable(KEY_SCREEN, (Parcelable)screen);
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

	public static <ScreenT extends Screen> Function<Fragment, ScreenT> getDefaultFragmentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Fragment, ScreenT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenT call(Fragment fragment) {
				if (fragment.getArguments() == null) {
					throw new IllegalArgumentException("Fragment has no arguments.");
				} else if (Serializable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) fragment.getArguments().getSerializable(KEY_SCREEN);
				} else if (Parcelable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) fragment.getArguments().getParcelable(KEY_SCREEN);
				} else {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable or Parcelable.");
				}
			}
		};
	}

	public static <ScreenT extends Screen> Function<Fragment, ScreenT> getNotImplementedFragmentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<Fragment, ScreenT>() {
			@Override
			public ScreenT call(Fragment fragment) {
				throw new RuntimeException("Screen getting function is not implemented for a screen " + screenClass.getSimpleName());
			}
		};
	}

	public static <ScreenT extends Screen> Function<ScreenT, DialogFragment> getDefaultDialogFragmentCreationFunction(final Class<ScreenT> screenClass, final Class<? extends DialogFragment> dialogFragmentClass) {
		return new Function<ScreenT, DialogFragment>() {
			@Override
			public DialogFragment call(ScreenT screen) {
				try {
					DialogFragment dialogFragment = dialogFragmentClass.newInstance();
					if (screen instanceof Serializable) {
						Bundle arguments = new Bundle();
						arguments.putSerializable(KEY_SCREEN, (Serializable) screen);
						dialogFragment.setArguments(arguments);
					} else if(screen instanceof Parcelable) {
						Bundle arguments = new Bundle();
						arguments.putParcelable(KEY_SCREEN, (Parcelable)screen);
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

	public static <ScreenT extends Screen> Function<DialogFragment, ScreenT> getDefaultDialogFragmentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<DialogFragment, ScreenT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenT call(DialogFragment dialogFragment) {
				if (dialogFragment.getArguments() == null) {
					throw new IllegalArgumentException("Dialog fragment has no arguments.");
				} else if (Serializable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) dialogFragment.getArguments().getSerializable(KEY_SCREEN);
				} else if (Parcelable.class.isAssignableFrom(screenClass)) {
					return (ScreenT) dialogFragment.getArguments().getParcelable(KEY_SCREEN);
				} else {
					throw new IllegalArgumentException("Screen " + screenClass.getSimpleName() + " should be Serializable or Parcelable.");
				}
			}
		};
	}

	public static <ScreenT extends Screen> Function<DialogFragment, ScreenT> getNotImplementedDialogFragmentScreenGettingFunction(final Class<ScreenT> screenClass) {
		return new Function<DialogFragment, ScreenT>() {
			@Override
			public ScreenT call(DialogFragment dialogFragment) {
				throw new RuntimeException("Screen getting function is not implemented for a screen " + screenClass.getSimpleName());
			}
		};
	}

	public static <ScreenResultT extends ScreenResult> Function<ScreenResultT, ActivityResult> getDefaultActivityResultCreationFunction(Class<ScreenResultT> screenResultClass) {
		return new Function<ScreenResultT, ActivityResult>() {
			@Override
			public ActivityResult call(ScreenResultT screenResult) {
				if (screenResult == null) {
					return new ActivityResult(Activity.RESULT_CANCELED, null);
				}

				Intent data = new Intent();
				if (screenResult instanceof Serializable) {
					data.putExtra(KEY_SCREEN_RESULT, (Serializable) screenResult);
				} else if (screenResult instanceof Parcelable) {
					data.putExtra(KEY_SCREEN_RESULT, (Parcelable) screenResult);
				} else {
					throw new IllegalArgumentException("Screen result " + screenResult.getClass().getCanonicalName() + " should be Serializable or Parcelable.");
				}
				return new ActivityResult(Activity.RESULT_OK, data);
			}
		};
	}

	public static <ScreenResultT extends ScreenResult> Function<ActivityResult, ScreenResultT> getDefaultScreenResultGettingFunction(final Class<ScreenResultT> screenResultClass) {
		return new Function<ActivityResult, ScreenResultT>() {
			@Override
			@SuppressWarnings("unchecked")
			public ScreenResultT call(ActivityResult activityResult) {
				if (activityResult.getIntent() == null || activityResult.getResultCode() != Activity.RESULT_OK) {
					return null;
				} else if (Serializable.class.isAssignableFrom(screenResultClass)) {
					return (ScreenResultT) activityResult.getIntent().getSerializableExtra(KEY_SCREEN_RESULT);
				} else if (Parcelable.class.isAssignableFrom(screenResultClass)) {
					return (ScreenResultT) activityResult.getIntent().getParcelableExtra(KEY_SCREEN_RESULT);
				} else {
					throw new IllegalArgumentException("Screen result " + screenResultClass.getCanonicalName() + " should be Serializable or Parcelable.");
				}
			}
		};
	}

	public static <ScreenResultT extends ScreenResult> Function<ScreenResultT, ActivityResult> getNotImplementedActivityResultCreationFunction(final Class<? extends Screen> screenClass, Class<ScreenResultT> screenResultClass) {
		return new Function<ScreenResultT, ActivityResult>() {
			@Override
			public ActivityResult call(ScreenResultT screenResult) {
				throw new RuntimeException("ActivityResult creation function is not implemented for a screen " + screenClass.getSimpleName());
			}
		};
	}
}
