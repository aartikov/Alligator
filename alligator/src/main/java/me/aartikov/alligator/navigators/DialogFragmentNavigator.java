package me.aartikov.alligator.navigators;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.exceptions.NavigationException;

public interface DialogFragmentNavigator {

	void goForward(@NonNull Screen screen,
				   @NonNull DialogFragmentDestination destination,
				   @Nullable AnimationData animationData) throws NavigationException;

	void replace(@NonNull Screen screen,
				 @NonNull DialogFragmentDestination destination,
				 @Nullable AnimationData animationData) throws NavigationException;

	void reset(@NonNull Screen screen,
			   @NonNull DialogFragmentDestination destination,
			   @Nullable AnimationData animationData) throws NavigationException;

	boolean canGoBack();

	void goBack(@Nullable ScreenResult screenResult) throws NavigationException;
}
