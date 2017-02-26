package com.art.alligator;

import android.app.Activity;

/**
 * Date: 24.02.2017
 * Time: 19:00
 *
 * @author Artur Artikov
 */

public interface AnimationProvider {
	TransitionAnimation getActivityForwardAnimation(Class<? extends Screen> screenClass);
	TransitionAnimation getActivityBackAnimation(Class<? extends Activity> activityClass);
	TransitionAnimation getActivityReplaceAnimation(Class<? extends Screen> screenClass);

	TransitionAnimation getFragmentForwardAnimation(Class<? extends Screen> screenClass);
	TransitionAnimation getFragmentBackAnimation(Class<? extends Screen> screenClass);
	TransitionAnimation getFragmentReplaceAnimation(Class<? extends Screen> screenClass);
}
