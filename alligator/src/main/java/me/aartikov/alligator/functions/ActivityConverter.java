package me.aartikov.alligator.functions;

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import me.aartikov.alligator.Screen;

/**
 * Date: 15.10.2017
 * Time: 11:37
 *
 * @author Artur Artikov
 */

public class ActivityConverter<ScreenT extends Screen> {
	private static final String KEY_SCREEN = "me.aartikov.alligator.KEY_SCREEN";

	private Function2<Context, ScreenT, Intent> mIntentCreationFunction;
	private Function<Intent, ScreenT> mScreenGettingFunction;

	public ActivityConverter(Function2<Context, ScreenT, Intent> intentCreationFunction, Function<Intent, ScreenT> screenGettingFunction) {
		mIntentCreationFunction = intentCreationFunction;
		mScreenGettingFunction = screenGettingFunction;
	}

	public ActivityConverter(Function2<Context, ScreenT, Intent> intentCreationFunction) {
		this(intentCreationFunction, null);
	}

	public ActivityConverter(Class<ScreenT> screenClass, Class<? extends Activity> activityClass) {
		this(getDefaultIntentCreationFunction(screenClass, activityClass), getDefaultScreenGettingFunction(screenClass));
	}

	public <T extends ScreenT> Intent createIntent(Context context, T screen) {
		return mIntentCreationFunction.call(context, screen);
	}

	public ScreenT getScreen(Activity activity, Class<? extends ScreenT> screenClass) {
		if(mScreenGettingFunction == null) {
			throw new RuntimeException("Screen getting function is not implemented for a screen " + screenClass.getSimpleName());
		}
		return mScreenGettingFunction.call(activity.getIntent());
	}

	public static <ScreenT extends Screen> Function2<Context, ScreenT, Intent> getDefaultIntentCreationFunction(Class<ScreenT> screenClass, final Class<? extends Activity> activityClass) {
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

	public static <ScreenT extends Screen> Function<Intent, ScreenT> getDefaultScreenGettingFunction(final Class<ScreenT> screenClass) {
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
}
