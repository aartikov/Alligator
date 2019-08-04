package me.aartikov.alligator.navigators;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.NavigationException;

public interface FragmentNavigator {

	void goForward(@NonNull Screen screen,
				   @NonNull FragmentDestination destination,
				   @Nullable AnimationData animationData) throws NavigationException;

	void replace(@NonNull Screen screen,
				 @NonNull FragmentDestination destination,
				 @Nullable AnimationData animationData) throws NavigationException;

	void reset(@NonNull Screen screen,
			   @NonNull FragmentDestination destination,
			   @Nullable AnimationData animationData) throws NavigationException;

	boolean canGoBack();

	void goBack(@Nullable ScreenResult screenResult,
				@Nullable AnimationData animationData) throws NavigationException;

	void goBackTo(@NonNull Class<? extends Screen> screenClass,
				  @NonNull FragmentDestination destination,
				  @Nullable ScreenResult screenResult,
				  @Nullable AnimationData animationData) throws NavigationException;

	@Nullable
	Fragment getCurrentFragment();
}
