package com.arellomobile.alligator.rule;

import com.arellomobile.alligator.AvailableScreenTypes;
import com.arellomobile.alligator.Utils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

import me.aartikov.alligator.RegisterScreen;

public class ScreenAnnotationRule extends AnnotationRule {
	public ScreenAnnotationRule() {
		super(ElementKind.CLASS, Modifier.PUBLIC);
	}

	@Override
	public void checkAnnotation(final Element annotatedClass) throws IncorrectElementException {
		validateElementClass(annotatedClass);

		validateModifiers(annotatedClass);
	}

	private void validateModifiers(final Element annotatedClass) throws IncorrectElementException {
		StringBuilder errorBuilder = new StringBuilder();
		if (annotatedClass.getKind() != mValidKind) {
			errorBuilder.append("Field " + annotatedClass + " of " + annotatedClass.getEnclosingElement().getSimpleName() + " should be " + mValidKind.name() + ", or not mark it as @" + RegisterScreen.class.getSimpleName()).append("\n");
		}

		for (Modifier modifier : annotatedClass.getModifiers()) {
			if (!mValidModifiers.contains(modifier)) {
				errorBuilder.append("Field " + annotatedClass + " of " + annotatedClass.getEnclosingElement().getSimpleName() + " can't be a " + modifier).append(". Use ").append(validModifiersToString()).append("\n");
			}
		}
		if (errorBuilder.length() != 0) {
			throw new IncorrectElementException(errorBuilder.toString(), annotatedClass);
		}
	}

	private void validateElementClass(final Element annotatedClass) throws IncorrectElementException {
		boolean availableScreenType = false;
		for (AvailableScreenTypes availableScreenTypes : AvailableScreenTypes.values()) {
			if (Utils.isAssignableFrom(annotatedClass, availableScreenTypes.className)) {
				availableScreenType = true;
			}
		}

		if (!availableScreenType) {
			throw new IncorrectElementException(annotatedClass.getSimpleName() + " must extends of " + AvailableScreenTypes.availableTypes(), annotatedClass);
		}
	}
}
