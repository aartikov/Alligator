package me.aartikov.alligator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;

/**
 * Can be used to register activities, fragments and dialog fragments with the help of an annotation processor.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RegisterScreen {
	Class<? extends Screen> value();

	Class<? extends ScreenResult> screenResult() default ScreenResult.class;
}
