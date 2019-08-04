package me.aartikov.alligator.navigators;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;
import me.aartikov.alligator.animations.AnimationData;
import me.aartikov.alligator.animations.DialogAnimation;
import me.aartikov.alligator.animations.providers.DialogAnimationProvider;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.helpers.DialogFragmentHelper;
import me.aartikov.alligator.helpers.ScreenResultHelper;
import me.aartikov.alligator.listeners.DialogShowingListener;
import me.aartikov.alligator.listeners.ScreenResultListener;
import me.aartikov.alligator.navigationfactories.NavigationFactory;

public class DefaultDialogFragmentNavigator implements DialogFragmentNavigator {
	@NonNull
	private DialogFragmentHelper mDialogFragmentHelper;

	@NonNull
	private NavigationFactory mNavigationFactory;

	@NonNull
	private ScreenResultHelper mScreenResultHelper;

	@NonNull
	private DialogShowingListener mDialogShowingListener;

	@NonNull
	private ScreenResultListener mScreenResultListener;

	@NonNull
	private DialogAnimationProvider mAnimationProvider;

	public DefaultDialogFragmentNavigator(@NonNull FragmentManager fragmentManager,
										  @NonNull NavigationFactory navigationFactory,
										  @NonNull DialogShowingListener dialogShowingListener,
										  @NonNull ScreenResultListener screenResultListener,
										  @NonNull DialogAnimationProvider animationProvider) {
		mDialogFragmentHelper = new DialogFragmentHelper(fragmentManager);
		mNavigationFactory = navigationFactory;
		mScreenResultHelper = new ScreenResultHelper();
		mDialogShowingListener = dialogShowingListener;
		mScreenResultListener = screenResultListener;
		mAnimationProvider = animationProvider;
	}

	@Override
	public void goForward(@NonNull Screen screen,
						  @NonNull DialogFragmentDestination destination,
						  @Nullable AnimationData animationData) throws NavigationException {

		DialogFragment dialogFragment = destination.createDialogFragment(screen);
		DialogAnimation animation = mAnimationProvider.getAnimation(screen.getClass(), animationData);
		mDialogFragmentHelper.showDialog(dialogFragment, animation);
		mDialogShowingListener.onDialogShown(screen.getClass());
	}

	@Override
	public void replace(@NonNull Screen screen,
						@NonNull DialogFragmentDestination destination,
						@Nullable AnimationData animationData) throws NavigationException {
		if (mDialogFragmentHelper.isDialogVisible()) {
			mDialogFragmentHelper.hideDialog();
		}

		DialogFragment dialogFragment = destination.createDialogFragment(screen);
		DialogAnimation animation = mAnimationProvider.getAnimation(screen.getClass(), animationData);
		mDialogFragmentHelper.showDialog(dialogFragment, animation);
		mDialogShowingListener.onDialogShown(screen.getClass());
	}

	@Override
	public void reset(@NonNull Screen screen,
					  @NonNull DialogFragmentDestination destination,
					  @Nullable AnimationData animationData) throws NavigationException {

		while (mDialogFragmentHelper.isDialogVisible()) {
			mDialogFragmentHelper.hideDialog();
		}

		DialogFragment dialogFragment = destination.createDialogFragment(screen);
		DialogAnimation animation = mAnimationProvider.getAnimation(screen.getClass(), animationData);
		mDialogFragmentHelper.showDialog(dialogFragment, animation);
		mDialogShowingListener.onDialogShown(screen.getClass());
	}

	@Override
	public boolean canGoBack() {
		return mDialogFragmentHelper.isDialogVisible();
	}

	@Override
	public void goBack(@Nullable ScreenResult screenResult) throws NavigationException {
		DialogFragment dialogFragment = mDialogFragmentHelper.getDialogFragment();
		mDialogFragmentHelper.hideDialog();
		if (dialogFragment != null) {
			mScreenResultHelper.callScreenResultListener(dialogFragment, screenResult, mScreenResultListener, mNavigationFactory);
		}
	}
}
