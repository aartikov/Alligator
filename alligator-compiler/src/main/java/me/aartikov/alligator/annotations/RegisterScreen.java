package me.aartikov.alligator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.aartikov.alligator.Screen;
import me.aartikov.alligator.ScreenResult;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface RegisterScreen {
	Class<? extends Screen> value();

	Class<? extends ScreenResult> screenResult() default ScreenResult.class;
}
