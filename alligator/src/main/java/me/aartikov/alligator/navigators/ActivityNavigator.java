package me.aartikov.alligator.navigators;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.exceptions.NavigationException;

public interface ActivityNavigator {

	void goForward(@NonNull Screen screen,
				   @NonNull ActivityDestination destination,
				   @Nullable AnimationData animationData) throws NavigationException;

	void replace(@NonNull Screen screen,
				 @NonNull ActivityDestination destination,
				 @Nullable AnimationData animationData) throws NavigationException;

	void reset(@NonNull Screen screen,
			   @NonNull ActivityDestination destination,
			   @Nullable AnimationData animationData) throws NavigationException;

	void goBack(@Nullable ScreenResult screenResult,
				@Nullable AnimationData animationData) throws NavigationException;

	void goBackTo(@NonNull Class<? extends Screen> screenClass,
				  @NonNull ActivityDestination destination,
				  @Nullable ScreenResult screenResult,
				  @Nullable AnimationData animationData) throws NavigationException;

}
