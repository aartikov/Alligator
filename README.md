# Alligator
Alligator is a modern Android navigation library that will help you to organize a navigation code in clean and testable way.

## Features
- Separation of navigation logic from view implementation.
- Screens based on activities, fragments and dialog fragments.
- Navigation is available at any moment (even when an activity is in background).
- Navigation methods can be combined arbitrarily.
- Passing screen arguments and hadling activity result in object oriented style.
- Screen switching with nested navigation.
- Flexible animation configuring.

## Gradle Setup
Add jitpack.io repository in project level build.gradle:

```gradle
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

Add the dependency in module level build.gradle:

```gradle
dependencies {
    compile 'com.github.aartikov:Alligator:1.0.0'
}
```

## Components to know
[AndroidNavigator](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/AndroidNavigator.html) - the main library object. It implements `Navigator` and `NavigationContextBinder` interfaces and uses a command queue internally to execute navigation commands.

[Navigator](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/Navigator.html) - has navigation methods such as `goForward`, `goBack`, `replace` and so on. It does not depend on Android SDK, so code that uses it can be tested easily. `Navigator` operates with `Screen`s.

[Screen](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/Screen.html) - a logical representation of an application screen. It is used to indicate a screen type and pass screen arguments.

[NavigationContextBinder](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/NavigationContextBinder.html) - binds and unbinds `NavigationContext` to `AndroidNavigator`.

[NavigationContext](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/NavigationContext.html) -  is used to configure `AndroidNavigator`. It contains a reference to the current activity and all the other things needed for command execution.

[Command](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/Command.html) - a command executed by `AndroidNavigator`. The library has a bunch of implemented commands corresponding to navigation methods. You don’t need to create a command manually, `AndroidNavigator` creates it when a navigation method is called.

[NavigationFactory](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/NavigationFactory.html) - converts `Screen`s to theirs Android representation (intents, fragments, dialog fragments) and vice versa. There is ready to use implementation of `NavigationFactory` - [RegistryNavigationFactory](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/navigationfactories/RegistryNavigationFactory.html).

[ScreenSwitcher](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/ScreenSwitcher.html) - an object for switching between several screens without theirs recreation. There are ready to use implementations of `ScreenSwitcher` - [FragmentScreenSwitcher](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/screenswitchers/FragmentScreenSwitcher.html).

[TransitionAnimation](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/TransitionAnimation.html), [TransitionAnimationProvider](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/TransitionAnimationProvider.html), [DialogAnimation](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/DialogAnimation.html), [DialogAnimationProvider](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/DialogAnimationProvider.html)  - are used to configure animations.

## Quick start
### 1. Declare screens

```java
// Screen without arguments
public class ScreenA implements Screen {}

// Screen with an argument
public class ScreenD implements Screen, Serializable {
  private String mMessage;

  public ScreenD(String message) {
     mMessage = message;
  }

  public String getMessage() {
     return mMessage;
  }
}
```

### 2. Create NavigationFactory
Register your screens in `RegistryNavigationFactory`. In this sample the default converting functions are used that will serialize and deserialize screen arguments if a screen is `Serializable` or `Parcelable`.

```java
public class SampleNavigationFactory extends RegistryNavigationFactory {
	public SampleNavigationFactory() {
		registerActivity(ScreenA.class, ActivityA.class);
		registerActivity(ScreenB.class, ActivityB.class);
		registerFragment(ScreenC.class, FragmentC.class);
		registerFragment(ScreenD.class, FragmentD.class);
	}
}
```

### 3. Create AndroidNavigator
It should be a single instance in your application.
```java
sAndroidNavigator = new AndroidNavigator(new SampleNavigationFactory());
```

### 4. Set NavigationContext
Use `NavigationContext.Builder` to create `NavigationContext`. In the simplest case just pass a current activity to it. You can also set a fragment container, a fragment manager (which is by default is taken from an activity), a [screen switcher](https://github.com/aartikov/Alligator#switch-screens), [animation providers](https://github.com/aartikov/Alligator#configure-animations) and [listeners](https://github.com/aartikov/Alligator#listen-navigation).

Activities are responsible to bind and unbind `NavigationContext`. Bind it in `onResumeFragments` (when state of an activity and its fragments is restored) and unbind in `onPause` (when an activity becomes inactive).

```java
@Override
protected void onResumeFragments() {
    super.onResumeFragments();
    NavigationContext navigationContext = new NavigationContext.Builder(this)
            .containerId(R.id.fragment_container)
            .build();
    mNavigationContextBinder.bind(navigationContext);
}

@Override
protected void onPause() {
    mNavigationContextBinder.unbind();
    super.onPause();
}
```


### 5. Call navigation methods
```java
mNavigator.goForward(new ScreenD("Message for D"));
// or
mNavigator.goBack();
```

`Navigator` provides these navigation methods:
1. `goForward(screen)` - Add a new screen and go to it.
2. `goBack()` - Remove the current screen and go back to the previous screen.
3. `goBackTo(screenClass)` - Go back to a screen with the given class.
4. `replace(screen)` - Replace the last screen with a new screen.
5. `reset(screen)` - Remove all other screens and adds a new screen.
6. `finish()` - Finish the last screen or the group of screens executing some common task (implemented as finishing of the current activity).
7. `finishWithResult(screenResult)` - Finish with [ScreenResult](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/ScreenResult.html).
8. `switchTo(screen)` - Switch a screen using a [ScreenSwitcher](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/ScreenSwitcher.html).

Navigation methods can be called at any moment, even when a `NavigationContext` is not bound. When a navigation method is called an appropriate `Command` is generated and placed to a command queue. `AndroidNavigator` can execute commands only when a `NavigationContext` is bound to it, in other case a command will be postponed. You can combine navigation methods arbitrarily (for example call two `goBack()` one by one). This will work for activities too because `AndroidNavigator` unbinds a `NavigationContext` by itself after activity finishing or starting.

See how navigation methods work in [simple navigation sample](https://github.com/aartikov/Alligator/tree/master/simplenavigationsample) an [navigation methods sample](https://github.com/aartikov/Alligator/tree/master/navigationmethodssample).

### 6. Get screen arguments
To get screen arguments from an activity or a fragment use [ScreenResolver](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/ScreenResolver.html).
```java
ScreenD screen = mScreenResolver.getScreen(this); // 'this' is Activity or Fragment
String message  = screen.getMessage();
```

## Advanced topics
### Configure animations
Create `TransitionAnimationProvider` and set it using `NavigationContext`.
```java
public class SampleTransitionAnimationProvider implements TransitionAnimationProvider {
	@Override
	public TransitionAnimation getAnimation(TransitionType transitionType, Class<? extends Screen> screenClassFrom,
    Class<? extends Screen> screenClassTo, boolean isActivity, @Nullable AnimationData animationData) {
		switch (transitionType) {
			case FORWARD:
				return new SimpleTransitionAnimation(R.anim.slide_in_right, R.anim.slide_out_left);
			case BACK:
				return new SimpleTransitionAnimation(R.anim.slide_in_left, R.anim.slide_out_right);
			default:
				return TransitionAnimation.DEFAULT;
		}
	}
}
```
```java
	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		NavigationContext navigationContext = new NavigationContext.Builder(this)
				.transitionAnimationProvider(new SampleTransitionAnimationProvider())
				.build();
		mNavigationContextBinder.bind(navigationContext);
	}
```

Lollipop transition animations are also supported, see [shared element animation sample](https://github.com/aartikov/Alligator/tree/master/sharedelementanimationsample).

### Switch screens
A navigation method `switchTo` is similar to `replace`. The difference is that during screen switching screens can be reused. For example if there are three tabs in your application and each tab screen is represented by a fragment, there are no reason to create more than three fragments. Screen switching is especially useful if you want to create a nested navigation where each tab has its own backstack.

To make screen switching posible a special object `ScreenSwitcher` should be created and set to `NavigationContext`. The library provides a `ScreenSwitcher` implementation called `FragmentScreenSwitcher`. Screens passed to  `FragmentScreenSwitcher` are used as keys to identify fragments so they must have `equals` and `hashCode` methods correctly overridden.

 See how screen switching works in [simple screen switcher sample](https://github.com/aartikov/Alligator/tree/master/simplescreenswitchersample) an [advanced screen switcher sample](https://github.com/aartikov/Alligator/tree/master/advancedscreenswitchersample).

### Open dialogs
To use a dialog fragment as a screen register it with `registerDialogFragment` method and start it with `goForward` method.

### Listen navigation
These types of listeners can be set using `NavigationContext`
- `TransitionListener` - called when usual screen transition (not screen switching and not dialog showing) has been executed.
- `DialogShowingListener` - called when a fragment dialog has been shown.
- `ScreenSwitchingListener` - called when a screen has been switched with a screen switcher.
- `NavigationErrorListener` called when a navigation error has occurred.

### Start external activity
To use an external activity (for example a phone dialer) register a screen with a custom intent creation function.

```java
registerActivity(PhoneDialerScreen.class, null, (context, screen) -> 
		new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + screen.getPhoneNumber())));
```

Start the screen with `goForward ` method. You can use `NavigationErrorListener` to check that an activity has been succesfully resolved.

### Handle activity result
Starting an activity for result is necessary to get a result from external activities such as image gallery or contact picker. You can also use it to pass data between your internal activities. With Alligator there are no needs to declare request codes and handle activity result manually.

Declare a screen result class. Register it in a navigation factory. Use [ScreenResultResolver](https://jitpack.io/com/github/aartikov/Alligator/1.0.0/javadoc/me/aartikov/alligator/ScreenResultResolver.html) to handle screen result.

See how to do it in [screen result sample](https://github.com/aartikov/Alligator/tree/master/screenresultsample).

## Developed by
Artur Artikov <a href="mailto:a.artikov@gmail.com">a.artikov@gmail.com</a>

## License
```
The MIT License (MIT)

Copyright (c) 2017 Artur Artikov

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```